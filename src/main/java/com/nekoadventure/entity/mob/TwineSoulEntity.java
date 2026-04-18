package com.nekoadventure.entity.mob;

import com.nekoadventure.entity.ModEntities;
import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.other.attackApart.AttackTypes;
import net.minecraft.block.BlockState;
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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;


public class TwineSoulEntity extends HostileEntity implements Monster {
        private int shootCooldown = 0;
        private static final int SHOOT_INTERVAL = 20;

        public TwineSoulEntity(EntityType<? extends HostileEntity> entityType, World world) {
            super(entityType, world);
        }

        public static DefaultAttributeContainer.Builder createAttributes() {
            return MobEntity.createMobAttributes()
                    .add(EntityAttributes.GENERIC_MAX_HEALTH, 18.0D)
                    .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0D)
                    .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0D)
                    .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0D)
                    .add(EntityAttributes.GENERIC_ARMOR, 2.0D);
        }

        @Override
        protected void initGoals() {
            this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, false));

            this.goalSelector.add(1, new ShootMissileGoal());
            this.goalSelector.add(2, new WanderAroundGoal(this, 0.8D));
            this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
            this.goalSelector.add(4, new LookAroundGoal(this));
        }

        @Override
        public void tick() {
            super.tick();
            if (shootCooldown > 0) {
                shootCooldown--;
            }
        }

        private void shootMissile(LivingEntity target) {
            if (!this.getWorld().isClient) {
                Vec3d pos = this.getPos().add(0, 1.5, 0);
                Vec3d direction = target.getPos().add(0,1.5,0).subtract(pos).normalize();
                createMissile((ServerWorld) this.getWorld(), pos, direction);
            }
        }

        private void createMissile(ServerWorld world, Vec3d pos, Vec3d direction) {
            MissileEntity missile = new MissileEntity(
                    ModEntities.MISSILE,
                    world,
                    this,
                    new AttackTypes(AttackTypes.AttackType.BULLET),
                    false,
                    20.0,
                    40,
                    5.0);
            double yaw = Math.toDegrees(Math.atan2(-direction.x, direction.z));
            double pitch = Math.toDegrees(Math.atan2(-direction.y, Math.sqrt(direction.x * direction.x + direction.z * direction.z)));

            missile.setPosition(pos);
            missile.setYaw((float) yaw);
            missile.setPitch((float) pitch);
            world.spawnEntity(missile);
        }
        private class ShootMissileGoal extends Goal {
            private LivingEntity target;
            private int cooldown = 0;

            public ShootMissileGoal() {
                this.setControls();
            }

            private void setControls() {}

            @Override
            public boolean canStart() {
                LivingEntity livingEntity = TwineSoulEntity.this.getTarget();
                if (livingEntity == null || !livingEntity.isAlive()) {
                    return false;
                }
                if (!TwineSoulEntity.this.canSee(livingEntity)) {
                    return false;
                }
                if (shootCooldown > 0) {
                    return false;
                }
                double distance = TwineSoulEntity.this.squaredDistanceTo(livingEntity);
                double followRange = TwineSoulEntity.this.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE);
                if (distance > followRange * followRange) {
                    return false;
                }

                this.target = livingEntity;
                return true;
            }

            @Override
            public boolean shouldContinue() {
                if (this.target instanceof PlayerEntity player) {
                    if(player.isCreative()||player.isSpectator()){
                        return false;
                    }
                }
                return this.target != null
                        && this.target.isAlive()
                        && TwineSoulEntity.this.canSee(this.target)
                        && !TwineSoulEntity.this.isDead();
            }

            @Override
            public void start() {
                this.cooldown = 20;
                TwineSoulEntity.this.shootCooldown = SHOOT_INTERVAL;
            }

            @Override
            public void tick() {
                if (this.target == null) return;
                TwineSoulEntity.this.getLookControl().lookAt(this.target, 30.0F, 30.0F);
                this.cooldown--;
                if (this.cooldown <= 0) {
                    TwineSoulEntity.this.shootMissile(this.target);
                    TwineSoulEntity.this.playSound( SoundEvents.BLOCK_DISPENSER_LAUNCH,1.0f,1.0f);
                    this.cooldown = SHOOT_INTERVAL;
                    TwineSoulEntity.this.shootCooldown = SHOOT_INTERVAL;
                }
            }

            @Override
            public void stop() {
                this.target = null;
                this.cooldown = 0;
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