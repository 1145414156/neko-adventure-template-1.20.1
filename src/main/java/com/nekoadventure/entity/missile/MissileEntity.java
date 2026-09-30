package com.nekoadventure.entity.missile;


import com.nekoadventure.item.nekoItem.attackTypeItem.NekoAttackTypeItem;
import com.nekoadventure.item.other.NekoPackageItem;
import com.nekoadventure.other.attackApart.AttackTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;


public class MissileEntity extends Entity {
    private static final TrackedData<Integer> MISSILE_MODEL_TYPE =
            DataTracker.registerData(MissileEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Optional<UUID>> OWNER_UUID = DataTracker.registerData(MissileEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);

    private double speed = 0;
    private int time=1;
    private int aliveDuration;
    private AttackTypes attackTypes;
    private Entity owner;
    private boolean isTreadable;
    private double range;
    private double damage;
    private int skillType=-1;

    // 攻击弹幕构造
    public MissileEntity(EntityType<? extends MissileEntity> entityType, World world, Entity owner,
                        AttackTypes attackTypes, boolean isTreadable, double range,double time,double damage) {
        super(entityType, world);
        this.setOwner(owner);
        this.attackTypes=attackTypes;
        this.isTreadable = isTreadable;
        this.range = range;
        this.damage = damage;
        this.time= (int) time;
        this.setNoGravity(true);
        this.speed = range/time;
        this.setMissileModelType(MissileModelType.BULLET);
    }
    //boss特殊弹幕构造
    public MissileEntity(EntityType<? extends MissileEntity> entityType, World world, Entity owner,
                         AttackTypes attackTypes,int skillType, double range,double time,double damage) {
        super(entityType,world);
        this.setOwner(owner);
        this.attackTypes=attackTypes;
        this.skillType=skillType;
        this.range = range;
        this.damage = damage;
        this.time= (int) time;
        this.setNoGravity(true);
        this.speed = range/time;
        this.setMissileModelType(MissileModelType.BULLET);
    }

    public MissileEntity(EntityType<MissileEntity> entityEntityType, World world) {
        super(entityEntityType,world);
    }

    @Override
    public void tick() {
        super.tick();
        this.noClip=true;
        if (!this.getWorld().isClient && this.getOwner() instanceof PlayerEntity player) {
            NekoPackageItem.applyOffAttackTypeItem(player);
        }

        if (attackTypes != null) {
            attackTypes.executeMovement(this, range , speed);
            this.move(MovementType.SELF, this.getVelocity());
            aliveDuration++;
        }
        if (this.getOwner()==null) {
            this.kill();
            return;
        }
        if (this.age>2000){this.kill();}
        if (aliveDuration==time){this.kill();}
        if (entityCheckCollision()) return;
        blockCheckCollision();
    }

    private boolean entityCheckCollision() {
        if (!this.getWorld().isClient) {
            Vec3d velocity = this.getVelocity();
            Vec3d direction = velocity.normalize();
            double forwardExpand = speed * 0.3;
            double expandX = Math.abs(direction.x) * forwardExpand + 0.1;
            double expandY = Math.abs(direction.y) * forwardExpand + 0.1;
            double expandZ = Math.abs(direction.z) * forwardExpand + 0.1;
            Entity directOwner = this.getOwner();
            var entities = this.getWorld().getOtherEntities(
                    this,
                    this.getBoundingBox().expand(expandX, expandY, expandZ),
                    entity -> {
                        if (!(entity instanceof LivingEntity) || entity == directOwner) return false;
                        // 跳过 owner 弹幕的直属发射者，避免误伤玩家
                        if (directOwner instanceof MissileEntity missileOwner && entity.equals(missileOwner.getOwner())) return false;
                        // 非玩家发射的弹幕（Boss等）只攻击玩家
                        if (!(getRealOwner() instanceof PlayerEntity)) {
                            return entity instanceof PlayerEntity;
                        }
                        return true;
                    }
            );

            for (var target : entities) {
                if (target instanceof LivingEntity livingTarget) {
                    Entity finalOwner = getRealOwner();
                    DamageSource damageSource = finalOwner instanceof LivingEntity livingOwner
                            ? this.getDamageSources().mobProjectile(this, livingOwner)
                            : this.getDamageSources().mobProjectile(this, null);

                    livingTarget.damage(damageSource, (float) damage);
                    if (this.getOwner() instanceof PlayerEntity player) {
                        if (player.getOffHandStack().getItem() instanceof NekoPackageItem
                                && NekoPackageItem.getIsFinished(player.getOffHandStack())!=2) {
                            ArrayList specificItems=NekoPackageItem.getNekoItem(player.getOffHandStack(),true,1);
                            for (Object specificItem:specificItems){
                                for (Entity livingEntity:entities){
                                    if (livingEntity instanceof LivingEntity){
                                        ((NekoAttackTypeItem) specificItem).applySpecificItem(player, (LivingEntity) livingEntity);
                                    }
                                }
                            }
                        }
                    }
                    if (!isTreadable) {
                        this.kill();
                        return true;
                    }
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
    }

    @Override
    public void setNoGravity(boolean noGravity) {
        super.setNoGravity(true);
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putInt("time", this.time);
        nbt.putInt("aliveDuration", this.aliveDuration);
        nbt.putInt("skillType",skillType);
        nbt.putDouble("speed", this.speed);
        nbt.putDouble("range", this.range);
        nbt.putDouble("damage", this.damage);

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
        this.time = nbt.getInt("time");
        this.aliveDuration = nbt.getInt("aliveDuration");
        this.skillType=nbt.getInt("skillType");

        this.range = nbt.getDouble("range");
        this.damage = nbt.getDouble("damage");
        this.isTreadable = nbt.getBoolean("isTreadable");
        if (nbt.contains("speed", NbtCompound.DOUBLE_TYPE)) {
            this.speed = nbt.getDouble("speed");
        } else if (this.time > 0) {
            this.speed = this.range / this.time;
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
                this.attackTypes = new AttackTypes(AttackTypes.AttackType.valueOf(typeName));
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
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }
    public int getTime(){return time;}
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

    /** 追溯弹幕链，返回最顶层的真实发射者（防止循环链与递归过深） */
    @Nullable
    private Entity getRealOwner() {
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

    public void setOwner(@Nullable Entity owner) {
        this.owner = owner;
        this.dataTracker.set(OWNER_UUID, owner != null ? Optional.of(owner.getUuid()) : Optional.empty());
    }
    public void setAliveDuration(int aliveDuration){this.aliveDuration=aliveDuration;}
    public void setDamage(double damage){this.damage=damage;}
    public double getDamage(){return damage;}
    public AttackTypes.AttackType getAttackType(){return attackTypes.getType();}
    public void setMissileModelType(MissileModelType type) {
        this.dataTracker.set(MISSILE_MODEL_TYPE, type.ordinal());
    }
    public MissileModelType getMissileType() {
        return MissileModelType.values()[this.dataTracker.get(MISSILE_MODEL_TYPE)];
    }
    public int getAliveDuration(){return this.aliveDuration;}
    public int getSkillType(){return this.skillType;}
    public void setAttackType(AttackTypes attackType){
        this.attackTypes=attackType;
    }
    public void setTreadable(boolean isTreadable){this.isTreadable=isTreadable;}
}