package com.nekoadventure.entity.mob;

import com.nekoadventure.entity.ModEntities;
import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.entity.missile.MissileModelType;
import com.nekoadventure.other.attackApart.AttackTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.AnimationState;
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
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ShooterEntity extends HostileEntity implements Monster {

    @Environment(EnvType.CLIENT)
    public final AnimationState attackAnimationState = new AnimationState();

    private static final TrackedData<Boolean> ATTACKING =
            DataTracker.registerData(ShooterEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private int shootCooldown = 0;
    private static final int SHOOT_INTERVAL = 12;


    public ShooterEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createShooterAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0D)
                .add(EntityAttributes.GENERIC_ARMOR, 2.0D)
                .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 1.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.5D);
    }

    @Override
    protected void initGoals() {
        this.targetSelector.add(0, new RevengeGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, false));

        this.goalSelector.add(1, new ShootMissileGoal());
        this.goalSelector.add(2, new WanderAroundGoal(this, 0.8D));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(4, new LookAroundGoal(this));
    }


    private void shootMissile(LivingEntity target) {
        if (!this.getWorld().isClient) {
            Vec3d pos = this.getPos().add(0, 1.5, 0);
            Vec3d direction = target.getPos().add(0, 1.5, 0).subtract(pos).normalize();
            ShooterEntity.this.playSound( SoundEvents.BLOCK_DISPENSER_LAUNCH,1.0f,1.0f);
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
                32.0,
                10,
                5.0);

        double yaw = Math.toDegrees(Math.atan2(-direction.x, direction.z));
        double pitch = Math.toDegrees(Math.atan2(-direction.y, Math.sqrt(direction.x * direction.x + direction.z * direction.z)));
        missile.setMissileModelType(MissileModelType.BULLET);
        missile.setPosition(pos);
        missile.setYaw((float) yaw);
        missile.setPitch((float) pitch);
        world.spawnEntity(missile);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACKING, false);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.shootCooldown > 0) {
            this.shootCooldown--;
        }

        if (this.getWorld().isClient) {
            if (this.dataTracker.get(ATTACKING)) {
                if (!attackAnimationState.isRunning()) {
                    attackAnimationState.start(this.age);
                }
            }
            else {
                if (attackAnimationState.isRunning()) {
                    attackAnimationState.stop();
                }
            }
        }

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
            LivingEntity livingEntity = ShooterEntity.this.getTarget();
            if (livingEntity == null || !livingEntity.isAlive()) {
                return false;
            }
            if (!ShooterEntity.this.canSee(livingEntity)) {
                return false;
            }
            if (shootCooldown > 0) {
                return false;
            }
            double distance = ShooterEntity.this.squaredDistanceTo(livingEntity);
            double followRange = ShooterEntity.this.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE);
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
                    && ShooterEntity.this.canSee(this.target)
                    && !ShooterEntity.this.isDead();
        }

        @Override
        public void start() {
            this.cooldown = SHOOT_INTERVAL;
            ShooterEntity.this.shootCooldown = SHOOT_INTERVAL;
        }

        @Override
        public void tick() {
            if (ShooterEntity.this.shootCooldown<=0) {
                if (this.target == null) return;
                ShooterEntity.this.getLookControl().lookAt(this.target, 30.0F, 30.0F);
                if (this.cooldown==SHOOT_INTERVAL){
                    ShooterEntity.this.dataTracker.set(ATTACKING, true);
                }
                if (this.cooldown>-10) {
                    this.cooldown--;
                }
                if (this.cooldown <= 0) {
                    ShooterEntity.this.shootMissile(this.target);
                    ShooterEntity.this.dataTracker.set(ATTACKING,false);
                    this.cooldown = SHOOT_INTERVAL;
                    ShooterEntity.this.shootCooldown = SHOOT_INTERVAL;
                }
            }
        }

        @Override
        public void stop() {
            this.target = null;
            this.cooldown = 0;
            ShooterEntity.this.dataTracker.set(ATTACKING,false);
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_PILLAGER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_PILLAGER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PILLAGER_DEATH;
    }
}