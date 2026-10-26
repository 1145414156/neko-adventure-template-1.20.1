package com.nekoadventure.entity.missile;


import com.nekoadventure.item.nekoItem.attackTypeItem.AbstractNekoSpecificItem;
import com.nekoadventure.item.other.NekoPackageItem;
import com.nekoadventure.other.mobApart.AttackTypes;
import com.nekoadventure.other.mobApart.MissileModelType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class MissileEntity extends Entity {
    private static final TrackedData<Integer> MISSILE_MODEL_TYPE =
            DataTracker.registerData(MissileEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Optional<UUID>> OWNER_UUID = DataTracker.registerData(MissileEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    private static final TrackedData<Integer> FLAG =
            DataTracker.registerData(MissileEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> TIME =
            DataTracker.registerData(MissileEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> ALIVE_DURATION =
            DataTracker.registerData(MissileEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Float> RANGE =
            DataTracker.registerData(MissileEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Integer> ATTACK_TYPE =
            DataTracker.registerData(MissileEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private double speed = 0;
    private AttackTypes attackTypes;
    private Entity owner;
    private boolean isTreadable;
    private double damage;
    private int skillType=-1;
    private float initialYaw;
    private float initialPitch;

    // 攻击弹幕构造1
    public MissileEntity(EntityType<? extends MissileEntity> entityType, World world, Entity owner,
                        AttackTypes attackTypes, boolean isTreadable, double range,double time,double damage) {
        super(entityType, world);
        this.setOwner(owner);
        this.setAttackType(attackTypes);
        this.isTreadable = isTreadable;
        this.setRange(range);
        this.damage = damage;
        this.setTime((int) time);
        this.setNoGravity(true);
        this.speed = range/time;
    }
    // 攻击弹幕构造2
    public MissileEntity(EntityType<? extends MissileEntity> entityType, World world, Entity owner,
                         AttackTypes attackTypes, boolean isTreadable, double range,double time,double damage,int flag) {
        super(entityType, world);
        this.setOwner(owner);
        this.setAttackType(attackTypes);
        this.isTreadable = isTreadable;
        this.setRange(range);
        this.damage = damage;
        this.setTime((int) time);
        this.setNoGravity(true);
        this.speed = range/time;
        this.setFlag(flag);
    }
    //boss特殊弹幕构造
    public MissileEntity(EntityType<? extends MissileEntity> entityType, World world, Entity owner,
                         AttackTypes attackTypes,int skillType, double range,double time,double damage) {
        super(entityType,world);
        this.setOwner(owner);
        this.setAttackType(attackTypes);
        this.skillType=skillType;
        this.setRange(range);
        this.damage = damage;
        this.setTime((int) time);
        this.setNoGravity(true);
        this.speed = range/time;
    }

    public MissileEntity(EntityType<MissileEntity> entityEntityType, World world) {
        super(entityEntityType,world);
    }

    @Override
    public void tick() {
        super.tick();
        this.noClip=true;

        //mark
        if (this.getWorld().isClient) {return;}

        if(getAliveDuration()==0) {
            initialYaw=this.getYaw();
            initialPitch=this.getPitch();
        }
        if (this.getOwner() instanceof PlayerEntity player) {
            NekoPackageItem.applyOffAttackTypeItem(player,player.getYaw());
        }

        if (!this.getWorld().isClient&&attackTypes != null) {
            attackTypes.executeMovement(this, this.getRange() , speed);
            this.move(MovementType.SELF, this.getVelocity());
            this.setAliveDuration(this.getAliveDuration() + 1);
        }
        if (this.getOwner()==null) {this.kill();return;}
        if (this.age>2000){this.kill();}
        if (this.getAliveDuration()==this.getTime()){this.kill();}
        if (entityCheckCollision()) this.kill();
        blockCheckCollision();
    }

    private boolean entityCheckCollision() {
        Vec3d velocity = this.getVelocity();
        boolean stationary = velocity.lengthSquared() < 1.0E-6;
        if (stationary && this.getAttackType() != AttackTypes.AttackType.HOMING&&
                this.getAttackType() != AttackTypes.AttackType.LAZY) {
            return false;
        }
        Vec3d direction = stationary ? Vec3d.ZERO : velocity.normalize();
        double forwardExpand = stationary ? 0.0 : speed * 0.3;
        double expandX = Math.abs(direction.x) * forwardExpand + 0.1;
        double expandY = Math.abs(direction.y) * forwardExpand + 0.1;
        double expandZ = Math.abs(direction.z) * forwardExpand + 0.1;
        Entity realOwner = getRealOwner();
        boolean ownerIsPlayer = realOwner instanceof PlayerEntity;
        var targets = this.getWorld().getEntitiesByClass(
                Entity.class,
                this.getBoundingBox().expand(expandX, expandY, expandZ),
                e -> e != this
                        && e != realOwner
                        && (ownerIsPlayer || e instanceof PlayerEntity)
        );
        if (targets.isEmpty()) {
            return false;
        }
        DamageSource damageSource = realOwner instanceof LivingEntity livingOwner
                ? this.getDamageSources().mobProjectile(this, livingOwner)
                : this.getDamageSources().mobProjectile(this, null);
        PlayerEntity player = this.getOwner() instanceof PlayerEntity p ? p : null;
        List<AbstractNekoSpecificItem> specificItems = List.of();
        if (player != null) {
            ItemStack offhand = player.getOffHandStack();
            if (offhand.getItem() instanceof NekoPackageItem
                    && NekoPackageItem.getIsFinished(offhand) != 2) {
                List<AbstractNekoSpecificItem> collected = new ArrayList<>();
                for (Object obj : NekoPackageItem.getNekoItems(offhand, true, 1)) {
                    if (obj instanceof AbstractNekoSpecificItem item) {
                        collected.add(item);
                    }
                }
                specificItems = collected;
            }
        }
        for (Entity target : targets) {
            if (target instanceof LivingEntity livingEntity) {
                boolean damaged = target.damage(damageSource, (float) damage);
                if (damaged && player != null) {
                    for (AbstractNekoSpecificItem item : specificItems) {
                        item.applySpecificItem(player, livingEntity);
                    }
                }
                if (!isTreadable) {
                    return true;
                }
            }
        }
        return false;
    }

    private void blockCheckCollision() {
        if (!this.getWorld().isClient) {
            if (!isTreadable) {
                var blockCollisions = this.getWorld().getBlockCollisions(this, this.getBoundingBox().expand(0.1));
                if (blockCollisions.iterator().hasNext()) {
                    this.kill();
                }
            }
        }
    }

    //=================================================================================================

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(MISSILE_MODEL_TYPE, MissileModelType.BULLET.ordinal());
        this.dataTracker.startTracking(OWNER_UUID,Optional.empty());
        this.dataTracker.startTracking(FLAG, 0);
        this.dataTracker.startTracking(TIME, 1);
        this.dataTracker.startTracking(ALIVE_DURATION, 0);
        this.dataTracker.startTracking(RANGE, 0.0f);
        this.dataTracker.startTracking(ATTACK_TYPE, -1);
    }

    @Override
    public void setNoGravity(boolean noGravity) {
        super.setNoGravity(true);
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putInt("time", this.getTime());
        nbt.putInt("aliveDuration", this.getAliveDuration());
        nbt.putInt("skillType",skillType);
        nbt.putDouble("speed", this.speed);
        nbt.putDouble("range", this.getRange());
        nbt.putInt("flag", this.getFlag());
        nbt.putDouble("damage", this.damage);
        nbt.putFloat("initialYaw",this.initialYaw);
        nbt.putFloat("initialPitch", this.initialPitch);

        nbt.putBoolean("isTreadable", this.isTreadable);

        if (this.getOwner()!=null) {
            nbt.putUuid("Owner", this.owner.getUuid());
        }

        if (this.attackTypes != null && this.attackTypes.getType() != null) {
            nbt.putString("AttackType", this.attackTypes.getType().name());
        }
        nbt.putString("MissileType", this.getMissileType().name());
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.setTime(nbt.getInt("time"));
        this.setAliveDuration(nbt.getInt("aliveDuration"));
        this.skillType=nbt.getInt("skillType");
        this.setFlag(nbt.getInt("flag"));
        this.initialYaw=nbt.getFloat("initialYaw");
        this.initialPitch=nbt.getFloat("initialPitch");

        this.setRange(nbt.getDouble("range"));
        this.damage = nbt.getDouble("damage");
        this.isTreadable = nbt.getBoolean("isTreadable");
        if (nbt.contains("speed", NbtCompound.DOUBLE_TYPE)) {
            this.speed = nbt.getDouble("speed");
        } else if (this.getTime() > 0) {
            this.speed = this.getRange() / this.getTime();
        }
        if (nbt.containsUuid("Owner")) {
            UUID ownerUuid = nbt.getUuid("Owner");
            this.dataTracker.set(OWNER_UUID, Optional.of(ownerUuid));
            if (this.getWorld() instanceof ServerWorld serverWorld) {
                Entity ownerEntity = serverWorld.getEntity(ownerUuid);
                if (ownerEntity != null) {
                    this.owner = ownerEntity;
                }
            }
        }
        if (nbt.contains("AttackType", NbtCompound.STRING_TYPE)) {
            String typeName = nbt.getString("AttackType");
            try {
                this.setAttackType(new AttackTypes(AttackTypes.AttackType.valueOf(typeName)));
            } catch (IllegalArgumentException e) {
                this.remove(Entity.RemovalReason.DISCARDED);
            }
        }
        if (nbt.contains("MissileType", NbtCompound.STRING_TYPE)) {
            try {this.setMissileModelType(MissileModelType.valueOf(nbt.getString("MissileType")));
            } catch (IllegalArgumentException ignored) {}
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
    public boolean shouldRender(double distance) {
        return distance < 64.0*64.0;
    }
    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }
    public int getTime(){return this.dataTracker.get(TIME);}
    public void setTime(int time){this.dataTracker.set(TIME,time);}
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

    @Nullable
    public Entity getRealOwner() {
        Entity current = this.getOwner();
        int depth = 0;
        while (current instanceof MissileEntity missileOwner && depth < 16) {
            Entity next = missileOwner.getOwner();
            if (next == null || next == current) break;
            current = next;
            depth++;
        }
        return current;
    }

    public float getInitialYaw() {
        return initialYaw;
    }

    public float getInitialPitch() {
        return initialPitch;
    }

    public void setOwner(@Nullable Entity owner) {
        this.owner = owner;
        this.dataTracker.set(OWNER_UUID, owner != null ? Optional.of(owner.getUuid()) : Optional.empty());
    }
    public void setAliveDuration(int aliveDuration){this.dataTracker.set(ALIVE_DURATION,aliveDuration);}
    public void setDamage(double damage){this.damage=damage;}
    public double getDamage(){return damage;}
    @Nullable
    public AttackTypes.AttackType getAttackType(){
        int ordinal = this.dataTracker.get(ATTACK_TYPE);
        AttackTypes.AttackType[] values = AttackTypes.AttackType.values();
        return ordinal >= 0 && ordinal < values.length ? values[ordinal] : null;
    }
    public void setMissileModelType(MissileModelType type) {
        this.dataTracker.set(MISSILE_MODEL_TYPE, type.ordinal());
    }
    public MissileModelType getMissileType() {return MissileModelType.values()[this.dataTracker.get(MISSILE_MODEL_TYPE)];}
    public int getFlag(){return this.dataTracker.get(FLAG);}
    public void setFlag(int flag){this.dataTracker.set(FLAG,flag);}
    public int getAliveDuration(){return this.dataTracker.get(ALIVE_DURATION);}
    public double getRange(){return this.dataTracker.get(RANGE);}
    public void setRange(double range){this.dataTracker.set(RANGE,(float) range);}
    public int getSkillType(){return this.skillType;}
    public void setAttackType(@Nullable AttackTypes attackType){
        this.attackTypes=attackType;
        this.dataTracker.set(ATTACK_TYPE, attackType != null ? attackType.getType().ordinal() : -1);
    }
    public void setTreadable(boolean isTreadable){this.isTreadable=isTreadable;}
}