package com.nekoadventure.entity.mob;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

public class MuddySpiderEntity extends HostileEntity implements Monster {

    @Environment(EnvType.CLIENT)
    public static final AnimationState attackAnimation=new AnimationState();

    private int skillCooldown = 0;

    private boolean isJumpCharging = false;
    private int jumpChargeTimer = 0;
    private LivingEntity jumpTarget = null;


    public MuddySpiderEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 24.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0D);
    }

    @Override
    protected void initGoals() {
        this.targetSelector.add(0, new RevengeGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, false));

        this.goalSelector.add(1, new LeapAttackGoal());
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.add(3, new WanderAroundGoal(this, 0.8D));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(5, new LookAroundGoal(this));
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.ARTHROPOD;
    }

    @Override
    public void tick() {
        super.tick();

        if (skillCooldown > 0) {
            skillCooldown--;
        }

        if (isJumpCharging && jumpTarget != null) {
            if (!attackAnimation.isRunning()) {
                this.playSound(SoundEvents.ENTITY_SPIDER_AMBIENT,1.5f,0.5f);
                attackAnimation.start(40);
            }
            jumpChargeTimer--;
            this.getLookControl().lookAt(jumpTarget, 30.0F, 30.0F);
            if (!jumpTarget.isAlive() || this.squaredDistanceTo(jumpTarget) > 400) {
                attackAnimation.stop();
                isJumpCharging = false;
                jumpTarget = null;
                jumpChargeTimer = 0;
                return;
            }

            if (jumpChargeTimer <= 0) {
                attackAnimation.stop();
                executeLeap(jumpTarget);
                isJumpCharging = false;
                jumpTarget = null;
                skillCooldown = 80;
            }
        }
    }

    /**
     * 执行跳跃突进
     */
    private void executeLeap(LivingEntity target) {
        if (target == null) return;

        Vec3d currentPos = this.getPos();
        Vec3d targetPos = target.getPos();

        double dx = targetPos.x - currentPos.x;
        double dz = targetPos.z - currentPos.z;
        double horizontalDistance = Math.sqrt(dx * dx + dz * dz);

        Vec3d velocity = getVec3d(horizontalDistance, dx, dz);
        this.setVelocity(velocity);
        this.velocityDirty = true;
        this.velocityModified = true;

    }

    private @NotNull Vec3d getVec3d(double horizontalDistance, double dx, double dz) {
        double jumpVelocity = (0.4 + horizontalDistance * 0.06)*2.5;

        double verticalVelocity = 0.7 + horizontalDistance * 0.02;
        verticalVelocity = Math.min(verticalVelocity, 0.5);
        verticalVelocity = Math.max(verticalVelocity, 0.2);

        return new Vec3d(
                dx / horizontalDistance * jumpVelocity,
                verticalVelocity,
                dz / horizontalDistance * jumpVelocity
        );
    }

    private class LeapAttackGoal extends Goal {
        private LivingEntity target;

        public LeapAttackGoal() {
            this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
        }

        @Override
        public boolean canStart() {
            if (skillCooldown > 0) {
                return false;
            }

            LivingEntity livingEntity = MuddySpiderEntity.this.getTarget();
            if (livingEntity == null || !livingEntity.isAlive()) {
                return false;
            }

            double distance = MuddySpiderEntity.this.squaredDistanceTo(livingEntity);
            if (distance < 25) {
                return false;
            }

            if (!MuddySpiderEntity.this.canSee(livingEntity)) {
                return false;
            }

            this.target = livingEntity;
            return true;
        }

        @Override
        public boolean shouldContinue() {
            if (!isJumpCharging || target == null || !target.isAlive()) {
                return false;
            }

            double distance = MuddySpiderEntity.this.squaredDistanceTo(target);
            return !(distance > 400);
        }

        @Override
        public boolean canStop() {
            return !isJumpCharging || jumpChargeTimer <= 0 || target == null || !target.isAlive();
        }

        @Override
        public void start() {
            isJumpCharging = true;
            jumpChargeTimer = 40;
            jumpTarget = target;

            MuddySpiderEntity.this.getNavigation().stop();
        }

        @Override
        public void tick() {
            if (target == null) return;

            MuddySpiderEntity.this.getLookControl().lookAt(target, 30.0F, 30.0F);
            MuddySpiderEntity.this.getNavigation().stop();
        }

        @Override
        public void stop() {
            if (isJumpCharging) {
                isJumpCharging = false;
                jumpTarget = null;
                jumpChargeTimer = 0;
            }
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SPIDER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SPIDER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SPIDER_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15F, 0.5F);
    }
}