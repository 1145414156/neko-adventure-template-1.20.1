package com.nekoadventure.other.mobApart;

import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.item.nekoItem.AbstractNekoItem;
import com.nekoadventure.item.other.NekoPackageItem;
import com.nekoadventure.network.mob.ScreenFlashNetworking;
import com.nekoadventure.network.mob.ScreenShakeNetworking;
import com.nekoadventure.network.mob.ScreenSwayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Comparator;
import java.util.List;

//这个类是执行每种不一样的弹幕的技能轴与运动效果

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
        ZENITH,
        MISSILE,
        SINGULARITY,
        SINGULARITY_HOLD
    }

    private final AttackType type;
    private Vec3d lastOwnerPos = null;
     private LivingEntity nearest=null;
    public AttackTypes(AttackType type) {
        this.type = type;
    }

    public AttackType getType() {
        return type;
    }

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
            case MISSILE -> missileMovement(entity,range);
            case SINGULARITY, SINGULARITY_HOLD -> singularMovement(entity, range);
        }
    }
    // ==============================================================
    private void lazyMovement(MissileEntity entity) {
        entity.setVelocity(entity.getVelocity().add(0, 0.1, 0));
        entity.setVelocity(entity.getVelocity().add(0, -0.1, 0));
    }
    public void followOwnerMovement(MissileEntity missile) {
        if (missile.getOwner() == null) return;

        Vec3d currentOwnerPos = missile.getOwner().getPos();

        if (lastOwnerPos != null) {
            Vec3d displacement = currentOwnerPos.subtract(lastOwnerPos);
            missile.addVelocity(displacement.x, displacement.y, displacement.z);
        }
        lastOwnerPos = currentOwnerPos;
        lazyMovement(missile);
    }

    private void homingMovement(MissileEntity entity,double range,double speed) {
        LivingEntity nearest = entity.getWorld().getClosestEntity(
                LivingEntity.class,
                TargetPredicate.DEFAULT,
                entity.getControllingPassenger(),
                entity.getX(), entity.getEyeY(), entity.getZ(),
                entity.getBoundingBox().expand(8)
        );
        if (nearest != null) {
            entity.teleport(nearest.getX(), nearest.getY(), nearest.getZ());
        }
        delayMovement(entity,range,speed);
    }

    // ==============================================================
    // 从这里开始就是每个特殊实际运动方法了

    private void bulletMovement(MissileEntity entity, double speed) {
        Vec3d direction = Vec3d.fromPolar(entity.getPitch(), entity.getYaw()).normalize();
        Vec3d desiredVelocity = direction.multiply(speed);
        if (entity.getRealOwner() instanceof PlayerEntity player){
            float initialYaw= entity.getInitialYaw();
            if (player.getOffHandStack().getItem() instanceof NekoPackageItem){
                List<AbstractNekoItem> attackItems = NekoPackageItem.getNekoItems(player.getOffHandStack(),
                        true, 0);
                int count=0;
                float addYaw=initialYaw;
                for(int l=attackItems.size()-1;l>=0;l--){
                    AbstractNekoItem attackItem=attackItems.get(l);
                    if (attackItem.getCalculateYaw(entity.getAliveDuration(),entity.getTime())!=114514){
                        addYaw+=(attackItem.getCalculateYaw(entity.getAliveDuration(),entity.getTime()));
                        count++;
                        if (count>=3){
                            entity.setYaw(addYaw);
                            break;
                        }
                    }
                }

            }
        }
        entity.addVelocity(
                (desiredVelocity.x - entity.getVelocity().x) * 0.5,
                (desiredVelocity.y - entity.getVelocity().y) * 0.5,
                (desiredVelocity.z - entity.getVelocity().z) * 0.5
        );

    }

    private void delayMovement(MissileEntity entity, double range, double speed) {
        lazyMovement(entity);
        if (entity.getAliveDuration() == entity.getTime() / 2) {
            if (entity.getOwner() != null) {
                nearest = findNearestTarget(entity, range);
                if (nearest != null) {
                    Vec3d direction = nearest.getEyePos().subtract(entity.getPos()).normalize();
                    double yaw = Math.toDegrees(Math.atan2(-direction.x, direction.z));
                    double pitch = Math.toDegrees(Math.atan2(-direction.y,
                            Math.sqrt(direction.x * direction.x + direction.z * direction.z)));
                    entity.setYaw((float) yaw);
                    entity.setPitch((float) pitch);
                }
                else {
                    entity.remove(Entity.RemovalReason.DISCARDED);
                }
            }
        }
        else if (entity.getAliveDuration() == (entity.getTime() / 2) + 1) {
            if (nearest != null) {
                Vec3d direction = nearest.getEyePos().subtract(entity.getPos()).normalize();
                Vec3d desiredVelocity = direction.multiply(speed*5);
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
            if (owner == null) return;
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
        ellipsoidMovement(entity, range, 0.0f);
    }

    private void ellipsoidMovement(MissileEntity entity, double range, float trackPitch) {
        int time =  entity.getTime();
        int t = entity.getAliveDuration();
        double a = range / 2.0;
        double b = 3;
        double omega = 2 * Math.PI / time;
        boolean isLeft = entity.getFlag() < 11;
        double lateralSign = isLeft ? -1.0 : 1.0;
        float baseYaw = entity.getInitialYaw();
        double bYRad = Math.toRadians(baseYaw);
        double pRad = Math.toRadians(trackPitch);
        Vec3d forward = new Vec3d(-Math.sin(bYRad) * Math.cos(pRad), -Math.sin(pRad),
                Math.cos(bYRad) * Math.cos(pRad));
        Vec3d right = new Vec3d(-Math.cos(bYRad), 0, -Math.sin(bYRad));
        double theta = omega * t;
        double thetaNext = omega * (t + 1);
        Vec3d step = forward.multiply(-a * (Math.cos(thetaNext) - Math.cos(theta)))
                .add(right.multiply(b * lateralSign * (Math.sin(thetaNext) - Math.sin(theta))));

        Vec3d currentVelocity = entity.getVelocity();
        entity.addVelocity(
                step.x - currentVelocity.x,
                step.y - currentVelocity.y,
                step.z - currentVelocity.z
        );
        Vec3d tangent = forward.multiply(a * Math.sin(theta) * omega)
                .add(right.multiply(b * lateralSign * Math.cos(theta) * omega));
        float displayYaw = (float) Math.toDegrees(Math.atan2(-tangent.x, tangent.z));
        entity.setYaw(displayYaw);
    }
    private void zenithMovement(MissileEntity entity, double range) {
        ellipsoidMovement(entity, range, entity.getInitialPitch());
        followOwnerMovement(entity);
    }

    private void missileMovement(MissileEntity entity, double range) {
        int t = entity.getAliveDuration();
        if (t==0){
            nearest = findRandomTarget(entity, range);
        }
        else if (t <= 40) {
            entity.addVelocity(0,  0.5, 0);
            Entity owner = entity.getOwner();
            entity.setYaw(owner != null ? owner.getYaw() : entity.getYaw());
            entity.setPitch(-90.0F);
            if (entity.getWorld() instanceof ServerWorld) {
                ((ServerWorld) entity.getWorld()).spawnParticles(ParticleTypes.CLOUD,
                        entity.getX(), entity.getY(), entity.getZ(), 1, 0.1, 0, 0, 0.1);
            }
        }
        else if (t == 50) {
            if (nearest != null) {
                entity.setPosition(nearest.getX(), nearest.getY() + 20.0, nearest.getZ());
                entity.setVelocity(Vec3d.ZERO);
            }
            else {
                entity.kill();
            }
        }
        else if (t <= 60) {
            if (nearest != null && nearest.isAlive()) {
                Vec3d targetPos = new Vec3d(nearest.getX(), nearest.getY()+1, nearest.getZ());
                Vec3d currentPos = entity.getPos().add(0,-1,0);
                Vec3d diff = targetPos.subtract(currentPos);
                double dist = diff.length();
                int remainingTicks = 60 - t + 1;
                if (entityCollision(entity) || remainingTicks <= 1) {
                    entity.setPosition(targetPos.x, targetPos.y+0.5, targetPos.z);
                    entity.setVelocity(Vec3d.ZERO);
                    if (entity.getMissileModelType().equals(MissileModelType.MISSILE)) {
                        entity.setMissileModelType(MissileModelType.NULL);
                        entity.getWorld().playSound(null, entity.getBlockPos(), SoundEvents.ENTITY_GENERIC_EXPLODE,
                                SoundCategory.PLAYERS, 1.0f+ (float) entity.getDamage() /50, 1.0F);
                        if (entity.getWorld() instanceof ServerWorld) {
                            ((ServerWorld) entity.getWorld()).spawnParticles(ParticleTypes.EXPLOSION,
                                    entity.getX(), entity.getY(), entity.getZ(), 5+(int)entity.getDamage(), 2, 2, 2, 0);
                        }
                        if (entity.getDamage()>=100) {
                            List<PlayerEntity> playerEntities=entity.getWorld().getEntitiesByClass(
                                    PlayerEntity.class,
                                    entity.getBoundingBox().expand(range),
                                    player -> true
                            );
                            if (!playerEntities.isEmpty()) {
                                for (PlayerEntity playerEntity : playerEntities) {
                                    if (playerEntity instanceof ServerPlayerEntity) {
                                        ScreenFlashNetworking.sendToPlayer((ServerPlayerEntity) playerEntity,7);
                                        ScreenShakeNetworking.sendToPlayer((ServerPlayerEntity)playerEntity,60,0.5f);
                                        ScreenSwayNetworking.sendToPlayer((ServerPlayerEntity) playerEntity,50,30,5);
                                    }
                                }
                            }
                        }
                    }
                }
                else {
                    Vec3d dir = diff.normalize();
                    Vec3d velocity = dir.multiply(dist / remainingTicks);
                    entity.setVelocity(velocity);
                    entity.setYaw((float) Math.toDegrees(Math.atan2(-dir.x, dir.z)));
                    entity.setPitch((float) Math.toDegrees(Math.atan2(-dir.y,
                            Math.sqrt(dir.x * dir.x + dir.z * dir.z))));
                }
            }
        }
    }

    private void singularMovement(MissileEntity entity, double range) {
        if (entity.getAttackType() == AttackType.SINGULARITY_HOLD) {
            entity.setVelocity(Vec3d.ZERO);
            singularPull(entity, range);
            return;
        }
        if (blockCollision(entity) || entityCollision(entity)) {
            entity.setVelocity(Vec3d.ZERO);
            entity.setAttackType(new AttackTypes(AttackType.SINGULARITY_HOLD));
            singularPull(entity, range);
            return;
        }
        singularPull(entity, range);
        bulletMovement(entity, entity.getSpeed());
    }

    private void singularPull(MissileEntity entity, double range) {
        if (range <= 0) {
            return;
        }
        Vec3d center = entity.getBoundingBox().getCenter();
        Entity realOwner = entity.getRealOwner();
        List<LivingEntity> targets = entity.getWorld().getEntitiesByClass(
                LivingEntity.class,
                entity.getBoundingBox().expand(Math.min(8,range)),
                e -> e != realOwner && e.isAlive()
        );
        for (LivingEntity target : targets) {
            Vec3d diff = center.subtract(target.getBoundingBox().getCenter());
            double distance = diff.length();
            if (distance < 0.01) {
                continue;
            }
            double strength = 0.04 + 0.15 * (1.0 - Math.min(distance / range, 1.0));
            Vec3d pull = diff.multiply(strength / distance);
            target.addVelocity(pull.x, pull.y, pull.z);
            target.velocityModified = true;
        }
    }

    //===================================================================================================
    private LivingEntity findNearestTarget(MissileEntity entity, double range) {
        Entity owner = entity.getOwner();
        if (owner == null) {
            return null;
        }
        List<LivingEntity> targets = entity.getWorld().getEntitiesByClass(
                LivingEntity.class,
                owner.getBoundingBox().expand(range),
                z -> z != owner && z.isAttackable()
        );
        Entity nearest = targets.stream()
                .min(Comparator.comparingDouble(entity::distanceTo))
                .orElse(null);
        if (owner instanceof HostileEntity hostileEntity) {
            nearest = hostileEntity.getTarget();
        }
        return nearest instanceof LivingEntity living ? living : null;
    }
    private LivingEntity findRandomTarget(MissileEntity entity, double range) {
        Entity owner = entity.getOwner();
        if (owner == null) {
            return null;
        }
        List<LivingEntity> targets = entity.getWorld().getEntitiesByClass(
                LivingEntity.class,
                owner.getBoundingBox().expand(range),
                z -> z != owner && z.isAttackable()
        );
        if (!targets.isEmpty()) {
            Entity random = targets.get(entity.getWorld().getRandom().nextInt(targets.size()));
            if (owner instanceof HostileEntity hostileEntity) {
                random = hostileEntity.getTarget();
            }
            return random instanceof LivingEntity living ? living : null;
        }
        else {
            return null;
        }
    }
    public boolean entityCollision(MissileEntity entity) {
        return entity.entityCheckCollision();
    }
    public boolean blockCollision(MissileEntity entity) {
        return entity.blockCheckCollision();
    }
}