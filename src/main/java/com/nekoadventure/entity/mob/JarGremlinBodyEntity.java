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
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class JarGremlinBodyEntity extends HostileEntity implements Monster {

    private static final float HEAL_ON_HIT = 2.0f;

    private static final TrackedData<Boolean> ATTACKING =
            DataTracker.registerData(JarGremlinBodyEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private int attackTicks = 0;

    @Environment(EnvType.CLIENT)
    public final AnimationState attackAnimationState = new AnimationState();

    public JarGremlinBodyEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.28D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 24.0D)
                .add(EntityAttributes.GENERIC_ARMOR, 2.0D);
    }

    @Override
    protected void initGoals() {
        this.targetSelector.add(0, new RevengeGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, false));

        this.goalSelector.add(1, new CustomMeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.add(2, new WanderAroundGoal(this, 0.8D));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(4, new LookAroundGoal(this));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACKING, false);
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.UNDEAD;
    }

    private static class CustomMeleeAttackGoal extends MeleeAttackGoal {
        public CustomMeleeAttackGoal(PathAwareEntity mob, double speed, boolean pauseWhenMobIdle) {
            super(mob, speed, pauseWhenMobIdle);
        }

        @Override
        protected void attack(LivingEntity target, double squaredDistance) {
            double d = this.getSquaredMaxAttackDistance(target);
            if (squaredDistance <= d && this.getCooldown() <= 0) {
                this.resetCooldown();
                this.mob.tryAttack(target);
                if (this.mob instanceof JarGremlinBodyEntity entity) {
                    entity.heal(HEAL_ON_HIT);
                    entity.dataTracker.set(ATTACKING, true);
                }
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getWorld().isClient) {
            if (this.dataTracker.get(ATTACKING)) {
                if (!attackAnimationState.isRunning()) {
                    attackAnimationState.start(this.age);
                }
            } else {
                attackAnimationState.stop();
            }
        } else {
            if (this.dataTracker.get(ATTACKING)) {
                attackTicks++;
                // 0.75s = 15 tick
                if (attackTicks >= 15) {
                    this.dataTracker.set(ATTACKING, false);
                    attackTicks = 0;
                }
            }
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SKELETON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SKELETON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SKELETON_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_SKELETON_STEP, 0.15F, 1.0F);
    }
}