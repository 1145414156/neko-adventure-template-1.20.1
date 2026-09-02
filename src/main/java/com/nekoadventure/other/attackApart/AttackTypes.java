package com.nekoadventure.other.attackApart;

import com.nekoadventure.entity.ModEntities;
import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.entity.missile.MissileModelType;
import com.nekoadventure.item.other.NekoPackageItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

//全部攻击方法的移动方法
public class AttackTypes {
    public enum AttackType {
        BULLET,
        BRIMSTONE,
        LAZY,
        JOYEUSE,
        DELAY,
        BOOMERANG
    }

    private final AttackType type;
    private Vec3d lastOwnerPos = null;
    private Vec3d lastTargetPos =null;
    public AttackTypes(AttackType type) {
        this.type = type;
    }

    public AttackType getType() {
        return type;
    }

    //执行移动逻辑的方法
    public void executeMovement(MissileEntity entity,double range,double speed) {
        switch (type) {
            case BULLET -> bulletMovement(entity, speed);
            case BRIMSTONE -> brimstoneMovement(entity);
            case LAZY -> lazyMovement(entity);
            case JOYEUSE -> joyeuseMovement(entity, speed);
            case DELAY -> delayMovement(entity, range, speed);
            case BOOMERANG -> boomerangMovement(entity,range,speed);
        }
    }

    // 坐标抖动方法（检测实体）
    private void lazyMovement(MissileEntity entity) {
        entity.setVelocity(entity.getVelocity().add(0, 0.1, 0));
        entity.setVelocity(entity.getVelocity().add(0, -0.1, 0));
    }

    //与持有者同步移动方法
    public void followOwnerMovement(MissileEntity entity) {
        if (entity.getOwner() == null) return;

        Vec3d currentOwnerPos = entity.getOwner().getPos();

        if (lastOwnerPos != null) {
            Vec3d displacement = currentOwnerPos.subtract(lastOwnerPos);
            entity.addVelocity(displacement.x, displacement.y, displacement.z);
        }
        lastOwnerPos = currentOwnerPos;
        lazyMovement(entity);
    }

    // ==============================================================
    // 从这里开始就是每个特殊实际运动方法了

    private void bulletMovement(MissileEntity entity, double speed) {
        Vec3d direction = Vec3d.fromPolar(entity.getPitch(), entity.getYaw()).normalize();
        Vec3d desiredVelocity = direction.multiply(speed);
        entity.addVelocity(
                (desiredVelocity.x - entity.getVelocity().x) * 0.5,
                (desiredVelocity.y - entity.getVelocity().y) * 0.5,
                (desiredVelocity.z - entity.getVelocity().z) * 0.5
        );
    }

    private void delayMovement(MissileEntity entity,double range,double speed) {
        lazyMovement(entity);
        if (entity.getAliveDuration() == range / speed / 2) {
            if (entity.getOwner() != null) {
                List<LivingEntity> targets = entity.getWorld().getEntitiesByClass(
                        LivingEntity.class,
                        entity.getOwner().getBoundingBox().expand(range * 3),
                        z -> z instanceof LivingEntity &&
                                z != entity.getOwner()
                );
                Entity nearest = targets.stream()
                        .min(Comparator.comparingDouble(entity::distanceTo))
                        .orElse(null);
                if (entity.getOwner() instanceof HostileEntity hostileEntity) {
                    nearest = hostileEntity.getTarget();
                }
                if (nearest != null) {
                    lastTargetPos = nearest.getEyePos();
                    Vec3d direction = lastTargetPos.subtract(entity.getPos()).normalize();
                    double yaw = Math.toDegrees(Math.atan2(-direction.x, direction.z));
                    double pitch = Math.toDegrees(Math.atan2(-direction.y, Math.sqrt(direction.x * direction.x + direction.z * direction.z)));
                    entity.setYaw((float) yaw);
                    entity.setPitch((float) pitch);
                } else {
                    entity.remove(Entity.RemovalReason.DISCARDED);
                }
            }
        }
        else if (entity.getAliveDuration()==(range / speed / 2)+1){
            if (lastTargetPos!=null){
                Vec3d direction = lastTargetPos.subtract(entity.getPos()).normalize();
                Vec3d desiredVelocity = direction.multiply(speed*2.0);
                entity.addVelocity(
                        (desiredVelocity.x - entity.getVelocity().x) * 0.5,
                        (desiredVelocity.y - entity.getVelocity().y) * 0.5,
                        (desiredVelocity.z - entity.getVelocity().z) * 0.5
                );
            }
        }
    }

    private void brimstoneMovement(MissileEntity entity) {
        entity.setVelocity(Vec3d.ZERO);
        followOwnerMovement(entity);
    }

    private void joyeuseMovement(MissileEntity entity, double speed) {

        if (entity.getAliveDuration()==1) {
            Vec3d direction = Vec3d.fromPolar(entity.getPitch(), entity.getYaw()).normalize();
            entity.addVelocity(direction.multiply(speed));
        }

        var entities = entity.getWorld().getOtherEntities(
                entity,
                entity.getBoundingBox().expand(0.7),
                e -> e instanceof LivingEntity && e != entity.getOwner()
        );

        if (!entities.isEmpty()) {
            entity.setVelocity(Vec3d.ZERO);
            if (!entity.getWorld().isClient) {
                MissileEntity missile = getMissileEntity(entity);
                missile.setPosition(entity.getPos());
                LivingEntity target = missile.getWorld().getEntitiesByClass(
                        LivingEntity.class,
                        missile.getBoundingBox().expand(4),
                        tar -> tar.isAlive() && tar != missile.getOwner()
                ).stream().min(Comparator.comparingDouble(tar -> tar.distanceTo(missile))).orElse(null);
                if (target != null) {
                    Vec3d pos = missile.getPos();
                    Vec3d direction = target.getEyePos().subtract(pos).normalize();
                    double yaw = Math.toDegrees(Math.atan2(-direction.x, direction.z));
                    double pitch = Math.toDegrees(Math.atan2(-direction.y, Math.sqrt(direction.x * direction.x + direction.z * direction.z)));
                    missile.setYaw((float) yaw);
                    missile.setPitch((float) pitch);
                }
            }
            entity.remove(Entity.RemovalReason.KILLED);
        }
    }

    private void boomerangMovement(MissileEntity entity,double range,double speed) {
        int aliveDuration = entity.getAliveDuration();
        int maxDistanceDuration = (int) (range / speed / 2);
        double progress = (double) aliveDuration / maxDistanceDuration;
        double speedMultiplier = 1.0 - progress;
        Vec3d direction = Vec3d.fromPolar(entity.getPitch(), entity.getYaw()).normalize();
        double currentSpeed = speed * speedMultiplier;
        Vec3d targetVelocity = direction.multiply(currentSpeed);
        Vec3d currentVelocity = entity.getVelocity();
        Vec3d velocityDelta = targetVelocity.subtract(currentVelocity);
        entity.addVelocity(velocityDelta.x, velocityDelta.y, velocityDelta.z);
    }

    private @NotNull MissileEntity getMissileEntity(MissileEntity entity) {
        Vec3d pos = entity.getPos();
        AttackTypes lazyAttack = new AttackTypes(AttackType.LAZY);
        double attackRange = 3;
        double strength = 1;
        if (entity.getOwner() instanceof PlayerEntity player
                && player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackage) {
            attackRange = nekoPackage.getAttackRange(player);
            strength = nekoPackage.getStrength(player);
        }
        MissileEntity missile = new MissileEntity(
                ModEntities.MISSILE,
                entity.getWorld(),
                entity.getOwner(),
                lazyAttack,
                true,
                attackRange,
                80,
                strength * 2
        );
        missile.setPosition(pos);
        missile.setMissileModelType(MissileModelType.JOYEUSE);
        missile.setYaw(0);
        missile.setPitch(0);
        return missile;
    }
}