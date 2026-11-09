package com.nekoadventure.entity.mob;

import com.nekoadventure.entity.ModEntities;
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
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.EnumSet;

public class ChestMimicEntity extends HostileEntity implements Monster {

    private static final TrackedData<Boolean> ATTACKING =
            DataTracker.registerData(ChestMimicEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private int attackTicks = 0;

    private boolean coinsSummoned = false;
    private int coinsRemaining = 0;
    private int coinCooldown = 0;

    private static final int COIN_COUNT = 16;
    private static final int COIN_INTERVAL = 10;

    @Environment(EnvType.CLIENT)
    public final AnimationState attackAnimationState = new AnimationState();

    public ChestMimicEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 50.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 24.0D)
                .add(EntityAttributes.GENERIC_ARMOR, 4.0D);
    }

    @Override
    protected void initGoals() {
        this.targetSelector.add(0, new RevengeGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, false));

        this.goalSelector.add(0, new FleeGoal(1.3D));
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

    // ===== 近战攻击 Goal（1s 动画） =====
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
                if (this.mob instanceof ChestMimicEntity entity) {
                    entity.dataTracker.set(ATTACKING, true);
                }
            }
        }
    }

    // ===== 半血后逃跑 Goal =====
    private class FleeGoal extends Goal {
        private final double speed;

        public FleeGoal(double speed) {
            this.speed = speed;
            this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        }


        @Override
        public boolean canStart() {
            if (!coinsSummoned) {
                return false;
            }
            LivingEntity target = ChestMimicEntity.this.getTarget();
            if (target == null || !target.isAlive()) {
                return false;
            }
            return ChestMimicEntity.this.squaredDistanceTo(target) < 32.0 * 32.0;
        }

        @Override
        public boolean shouldContinue() {
            return canStart();
        }

        @Override
        public void tick() {
            LivingEntity target = ChestMimicEntity.this.getTarget();
            if (target == null) {
                return;
            }
            Vec3d fleeVec = ChestMimicEntity.this.getPos().subtract(target.getPos()).normalize();
            Vec3d dest = ChestMimicEntity.this.getPos().add(fleeVec.multiply(10));
            ChestMimicEntity.this.getNavigation().startMovingTo(dest.x, dest.y, dest.z, this.speed);
        }
    }

    // ===== 伤害检测：半血触发 =====
    @Override
    public boolean damage(DamageSource source, float amount) {
        boolean result = super.damage(source, amount);
        if (result && !coinsSummoned && this.getHealth() <= this.getMaxHealth() * 0.5f) {
            triggerHalfHealth();
        }
        return result;
    }

    private void triggerHalfHealth() {
        coinsSummoned = true;
        coinsRemaining = COIN_COUNT;
        coinCooldown = 0;

        // 生命恢复 III，10 秒 = 200 tick
        this.addStatusEffect(new StatusEffectInstance(
                StatusEffects.REGENERATION, 200, 2));

        // 播放一个逃跑音效
        this.playSound(SoundEvents.ENTITY_VILLAGER_HURT, 1.0F, 1.4F);
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
            // 攻击动画 1s = 20 tick
            if (this.dataTracker.get(ATTACKING)) {
                attackTicks++;
                if (attackTicks >= 20) {
                    this.dataTracker.set(ATTACKING, false);
                    attackTicks = 0;
                }
            }

            // 持续吐硬币
            if (coinsRemaining > 0) {
                if (coinCooldown > 0) {
                    coinCooldown--;
                } else {
                    if (this.getWorld() instanceof ServerWorld serverWorld) {
                        spawnCoinMonster(serverWorld);
                    }
                    coinsRemaining--;
                    coinCooldown = COIN_INTERVAL;
                }
            }
        }
    }

    private void spawnCoinMonster(ServerWorld world) {
        Vec3d spawnPos = this.getPos().add(
                (world.random.nextDouble() - 0.5) * 2.0,
                0.5,
                (world.random.nextDouble() - 0.5) * 2.0
        );

         CoinMonsterEntity coin = new CoinMonsterEntity(ModEntities.COIN_MONSTER, world);
         coin.setPosition(spawnPos);
         world.spawnEntity(coin);
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