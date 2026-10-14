package com.nekoadventure.entity.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class AbstractParticleEntity extends Entity {
    private static final TrackedData<Integer> MODEL_TYPE =
            DataTracker.registerData(AbstractParticleEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Optional<UUID>> OWNER_UUID =
            DataTracker.registerData(AbstractParticleEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    private static final TrackedData<Boolean> IS_FOLLOWING =
            DataTracker.registerData(AbstractParticleEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    @Environment(EnvType.CLIENT)
    public final AnimationState ANI = new AnimationState();
    @Environment(EnvType.CLIENT)
    private int playedModelType = -1;

    private Entity owner;
    private int duration;
    private int aliveDuration;
    private Vec3d lastOwnerPos;

    public AbstractParticleEntity(EntityType<?> type, World world) {
        super(type, world);
        this.setNoGravity(true);
        this.noClip = true;
    }

    public AbstractParticleEntity(EntityType<?> type, World world, @Nullable Entity owner, boolean isFollowing, int duration, AbstractParticleModelType modelType) {
        this(type, world);
        this.duration = duration;
        this.setOwner(owner);
        this.setFollowing(isFollowing);
        this.setModelType(modelType);
    }

    public AbstractParticleEntity(EntityType<?> type, World world, int duration, AbstractParticleModelType modelType) {
        this(type, world);
        this.duration = duration;
        this.setModelType(modelType);
    }

    @Override
    public void tick() {
        super.tick();
        this.noClip = true;
        if (this.getWorld().isClient) {
            this.syncClientAnimations();
        }
        if (this.isFollowing() && this.getOwner() != null) {
            this.setVelocity(Vec3d.ZERO);
            this.followOwnerMovement();
        }
        this.move(MovementType.SELF, this.getVelocity());
        this.aliveDuration++;
        if (this.duration > 0 && this.aliveDuration >= this.duration) {
            this.kill();
        }
    }
    @Environment(EnvType.CLIENT)
    private void syncClientAnimations() {
        int modelType = this.dataTracker.get(MODEL_TYPE);
        if (modelType != this.playedModelType) {
            this.ANI.stop();
            this.ANI.start(this.age);
            this.playedModelType = modelType;
        }
        else if (!this.ANI.isRunning()) {
            this.ANI.start(this.age);
        }
    }
    private void followOwnerMovement() {
        Entity owner = this.getOwner();
        if (owner == null) {
            return;
        }
        Vec3d currentOwnerPos = owner.getPos();
        if (this.lastOwnerPos != null) {
            Vec3d displacement = currentOwnerPos.subtract(this.lastOwnerPos);
            this.addVelocity(displacement.x, displacement.y, displacement.z);
        }
        this.lastOwnerPos = currentOwnerPos;
        this.setVelocity(this.getVelocity().add(0, 0.1, 0).add(0, -0.1, 0));
    }
    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(MODEL_TYPE, AbstractParticleModelType.PARTICLE_TEXT.ordinal());
        this.dataTracker.startTracking(OWNER_UUID, Optional.empty());
        this.dataTracker.startTracking(IS_FOLLOWING, false);
    }

    @Override
    public void setNoGravity(boolean noGravity) {
        super.setNoGravity(true);
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putInt("Duration", this.duration);
        nbt.putInt("AliveDuration", this.aliveDuration);
        nbt.putBoolean("IsFollowing", this.isFollowing());
        nbt.putString("ModelType", this.getModelType().name());
        if (this.getOwner() != null) {
            nbt.putUuid("Owner", this.getOwner().getUuid());
        }
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.duration = nbt.getInt("Duration");
        this.aliveDuration = nbt.getInt("AliveDuration");
        this.setFollowing(nbt.getBoolean("IsFollowing"));
        if (nbt.contains("ModelType", NbtCompound.STRING_TYPE)) {
            try {
                this.setModelType(AbstractParticleModelType.valueOf(nbt.getString("ModelType")));
            } catch (IllegalArgumentException ignored) {}
        }
        if (nbt.containsUuid("Owner")) {
            UUID ownerUuid = nbt.getUuid("Owner");
            this.dataTracker.set(OWNER_UUID, Optional.of(ownerUuid));
            if (this.getWorld() instanceof ServerWorld serverWorld) {
                this.owner = serverWorld.getEntity(ownerUuid);
            }
        }
        this.setNoGravity(true);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        return false;
    }

    @Override
    public boolean isInvulnerable() {
        return true;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return true;
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    public boolean canAvoidTraps() {
        return true;
    }

    @Override
    public boolean isFireImmune() {
        return true;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }
    @Override
    public boolean shouldRender(double distance) {
        return distance < 64.0*64.0;
    }

    @Nullable
    public Entity getOwner() {
        if (this.owner != null && !this.owner.isRemoved()) {
            return this.owner;
        }
        Optional<UUID> ownerUuid = this.dataTracker.get(OWNER_UUID);
        if (ownerUuid.isPresent()) {
            Entity resolvedOwner = this.resolveOwnerByUuid(ownerUuid.get());
            if (resolvedOwner != null) {
                this.owner = resolvedOwner;
                return resolvedOwner;
            }
        }
        return null;
    }

    @Nullable
    private Entity resolveOwnerByUuid(UUID ownerUuid) {
        World world = this.getWorld();
        if (world instanceof ServerWorld serverWorld) {
            return serverWorld.getEntity(ownerUuid);
        }
        return world.getEntitiesByClass(Entity.class, this.getBoundingBox().expand(128),
                entity -> entity.getUuid().equals(ownerUuid)).stream().findFirst().orElse(null);
    }

    public void setOwner(@Nullable Entity owner) {
        this.owner = owner;
        this.dataTracker.set(OWNER_UUID, owner != null ? Optional.of(owner.getUuid()) : Optional.empty());
    }

    public void setFollowing(boolean isFollowing) {
        this.dataTracker.set(IS_FOLLOWING, isFollowing);
    }

    public boolean isFollowing() {
        return this.dataTracker.get(IS_FOLLOWING);
    }

    public void setModelType(AbstractParticleModelType type) {
        this.dataTracker.set(MODEL_TYPE, type.ordinal());
    }

    public AbstractParticleModelType getModelType() {
        return AbstractParticleModelType.values()[this.dataTracker.get(MODEL_TYPE)];
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getAliveDuration() {
        return this.aliveDuration;
    }
}
