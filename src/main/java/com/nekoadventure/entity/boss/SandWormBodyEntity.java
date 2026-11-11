package com.nekoadventure.entity.boss;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.UUID;

public class SandWormBodyEntity extends Entity {

    private static final TrackedData<Integer> BODY_INDEX =
            DataTracker.registerData(SandWormBodyEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Optional<UUID>> HEAD_UUID =
            DataTracker.registerData(SandWormBodyEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);

    private Entity head;
    private int orphanTicks = 0;
    private boolean registeredWithHead = false;

    public SandWormBodyEntity(EntityType<? extends SandWormBodyEntity> type, World world) {
        super(type, world);
        this.noClip = true;
        this.setNoGravity(true);
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(BODY_INDEX, 0);
        this.dataTracker.startTracking(HEAD_UUID, Optional.empty());
    }

    public void setHead(Entity head) {
        this.head = head;
        this.dataTracker.set(HEAD_UUID, Optional.of(head.getUuid()));
    }

    public Entity getHead() {
        if (this.head != null && !this.head.isRemoved()) {
            return this.head;
        }
        Optional<UUID> uuid = this.dataTracker.get(HEAD_UUID);
        if (uuid.isEmpty()) {
            return null;
        }
        World world = this.getWorld();
        if (world instanceof ServerWorld serverWorld) {
            Entity e = serverWorld.getEntity(uuid.get());
            if (e != null) {
                this.head = e;
            }
            return e;
        }
        for (Entity e : world.getEntitiesByClass(Entity.class,
                this.getBoundingBox().expand(64),
                e -> e.getUuid().equals(uuid.get()))) {
            this.head = e;
            return e;
        }
        return null;
    }

    public void setBodyIndex(int index) {
        this.dataTracker.set(BODY_INDEX, index);
    }

    public int getBodyIndex() {
        return this.dataTracker.get(BODY_INDEX);
    }
    public boolean isHeadOf(SandWormEntity entity) {
        return this.dataTracker.get(HEAD_UUID)
                .map(uuid -> uuid.equals(entity.getUuid()))
                .orElse(false);
    }

    @Override
    public void tick() {
        super.tick();
        this.move(MovementType.SELF, this.getVelocity());
        if (this.getWorld().isClient) {
            return;
        }
        Entity h = getHead();
        if (h == null) {
            orphanTicks++;
            if (orphanTicks > 100) {
                this.discard();
            }
            return;
        }
        if (h.isRemoved() || (h instanceof LivingEntity living && living.isDead())) {
            this.discard();
            return;
        }
        orphanTicks = 0;
        if (!this.registeredWithHead && h instanceof SandWormEntity sandWorm) {
            sandWorm.adoptExistingBody(this);
            this.registeredWithHead = true;
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putInt("BodyIndex", this.getBodyIndex());
        Optional<UUID> headUuid = this.dataTracker.get(HEAD_UUID);
        headUuid.ifPresent(uuid -> nbt.putUuid("HeadUuid", uuid));
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.setBodyIndex(nbt.getInt("BodyIndex"));
        if (nbt.containsUuid("HeadUuid")) {
            UUID uuid = nbt.getUuid("HeadUuid");
            this.dataTracker.set(HEAD_UUID, Optional.of(uuid));
            this.head = null;
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.getWorld().isClient || this.isRemoved() || amount <= 0.0f) {
            return false;
        }
        Entity h = getHead();
        if (!(h instanceof SandWormEntity headEntity) || h.isRemoved() || headEntity.isDead()) {
            return false;
        }
        if (headEntity.wasHurtThisTick()) {
            return false;
        }
        return headEntity.damage(source, amount * 0.5f);
    }

    @Override
    public boolean canHit() {
        return !this.isRemoved();
    }

    @Override
    public boolean isAttackable() {
        return !this.isRemoved();
    }

    @Override
    public boolean isFireImmune() {
        return true;
    }

    @Override
    public boolean canAvoidTraps() {
        return true;
    }

    @Override
    public boolean shouldRender(double distance) {
        return distance < 256.0 * 256.0;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }
}