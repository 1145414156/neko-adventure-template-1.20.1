package com.nekoadventure.other.attackApart;

import com.nekoadventure.entity.missile.MissileEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

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
        BOOMERANG,
        HOMING,
        ELLIPSE,
        ZENITH
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
            case JOYEUSE -> joyeuseMovement(entity,range,speed);
            case DELAY -> delayMovement(entity, range, speed);
            case BOOMERANG -> boomerangMovement(entity,range,speed);
            case HOMING -> homingMovement(entity,range,speed);
            case ELLIPSE -> ellipsoidMovement(entity,range);
            case ZENITH -> zenithMovement(entity, range);
        }
    }
    private void lazyMovement(MissileEntity entity) {
        entity.setVelocity(entity.getVelocity().add(0, 0.1, 0));
        entity.setVelocity(entity.getVelocity().add(0, -0.1, 0));
    }
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

    private void homingMovement(MissileEntity entity,double range,double speed) {
        this.delayMovement(entity,range,speed);
        LivingEntity nearest = entity.getWorld().getClosestEntity(
                LivingEntity.class,
                TargetPredicate.DEFAULT,
                entity.getControllingPassenger(),
                entity.getX(), entity.getY(), entity.getZ(),
                entity.getBoundingBox().expand(8)
        );
        if (nearest != null) {
            entity.teleport(nearest.getX(), nearest.getY(), nearest.getZ());
        }
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
        if (entity.getAliveDuration() == entity.getTime()/ 2) {
            if (entity.getOwner() != null) {
                List<LivingEntity> targets = entity.getWorld().getEntitiesByClass(
                        LivingEntity.class,
                        entity.getOwner().getBoundingBox().expand(range),
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
        else if (entity.getAliveDuration()==(entity.getTime()/ 2)+1){
            if (lastTargetPos!=null){
                Vec3d direction = lastTargetPos.subtract(entity.getPos()).normalize();
                Vec3d desiredVelocity = direction.multiply(speed*3.0);
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

    private void joyeuseMovement(MissileEntity entity,double range ,double speed) {
        delayMovement(entity,range,speed);
        if (entity.getAliveDuration() < entity.getTime() / 2) {
            Entity owner = entity.getOwner();
            if (owner == null) {
                return;
            }
            double yawRad = Math.toRadians(owner.getYaw());
            Vec3d forwardH = new Vec3d(-Math.sin(yawRad), 0, Math.cos(yawRad));
            Vec3d rightH = new Vec3d(-forwardH.z, 0, forwardH.x);
            int flag = MathHelper.clamp(entity.getFlag(), 0, 5);
            double spacing = 1.0;
            Vec3d center = owner.getEyePos();
            double lateral = (flag - 2.5) * spacing;
            Vec3d target = center.add(rightH.multiply(lateral));
            entity.setPosition(target.x, target.y, target.z);
            entity.setYaw(owner.getYaw());
            entity.setPitch(owner.getPitch());
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

    private void ellipsoidMovement(MissileEntity entity,double range) {
        int time =  entity.getTime();
        int t = entity.getAliveDuration();
        double a = range / 2.0;
        double b = 3;
        double omega = 2 * Math.PI / time;
        boolean isLeft = entity.getFlag() < 11;
        double sign = isLeft ? 1.0 : -1.0;
        double offsetPrev = 0.0;
        if (t > 0) {
            double thetaPrev = omega * (t - 1);
            offsetPrev = Math.atan2(b * Math.cos(thetaPrev), -a * Math.sin(thetaPrev) * sign);
        }
        float baseYaw = (t == 0)
                ? (float) (entity.getYaw() + (isLeft ? 180.0 : 0.0))
                : (float) (entity.getYaw() - Math.toDegrees(offsetPrev));
        double bYRad = Math.toRadians(baseYaw);
        Vec3d forward = new Vec3d(-Math.sin(bYRad), 0, Math.cos(bYRad));
        Vec3d right = new Vec3d(-forward.z, 0, forward.x);
        double theta = omega * t;
        double thetaNext = omega * (t + 1);
        Vec3d step = forward.multiply(a * sign * (Math.cos(thetaNext) - Math.cos(theta)))
                .add(right.multiply(b * (Math.sin(thetaNext) - Math.sin(theta))));

        Vec3d currentVelocity = entity.getVelocity();
        entity.addVelocity(
                step.x - currentVelocity.x,
                step.y - currentVelocity.y,
                step.z - currentVelocity.z
        );
        double sinT = Math.sin(theta) * sign;
        double cosT = Math.cos(theta);
        Vec3d tangent = forward.multiply(-a * sinT * omega)
                .add(right.multiply(b * cosT * omega));
        float displayYaw = (float) Math.toDegrees(Math.atan2(-tangent.x, tangent.z));
        entity.setYaw(displayYaw);
    }
    private void zenithMovement(MissileEntity entity, double range) {
        ellipsoidMovement(entity,range);
        followOwnerMovement(entity);
    }
}