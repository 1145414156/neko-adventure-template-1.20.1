package com.nekoadventure.entity.mob;

import com.nekoadventure.entity.ModEntities;
import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.other.mobApart.AttackTypes;
import com.nekoadventure.other.mobApart.MissileModelType;
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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.EnumSet;

public class JarGremlinEntity extends HostileEntity implements Monster {

    private static final double MELEE_RANGE = 8.0;
    private static final int SHOOT_INTERVAL = 30;

    private static final TrackedData<Boolean> MELEE_ATTACKING =
            DataTracker.registerData(JarGremlinEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> RANGED_ATTACKING =
            DataTracker.registerData(JarGremlinEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private int meleeAttackTicks = 0;
    private int rangedAttackTicks = 0;
    private int shootCooldown = 0;

    @Environment(EnvType.CLIENT)
    public final AnimationState meleeAttackAnimationState = new AnimationState();
    @Environment(EnvType.CLIENT)
    public final AnimationState rangedAttackAnimationState = new AnimationState();

    public JarGremlinEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 40.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0D)
                .add(EntityAttributes.GENERIC_ARMOR, 1.0D);
    }

    @Override
    protected void initGoals() {
        this.targetSelector.add(0, new RevengeGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, false));

        this.goalSelector.add(1, new MeleeGoal(this, 1.0D, false));
        this.goalSelector.add(2, new ShootMissileGoal());
        this.goalSelector.add(3, new WanderAroundGoal(this, 0.8D));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(5, new LookAroundGoal(this));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(MELEE_ATTACKING, false);
        this.dataTracker.startTracking(RANGED_ATTACKING, false);
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.UNDEAD;
    }

    @Override
    public void tick() {
        super.tick();

        if (shootCooldown > 0) {
            shootCooldown--;
        }

        if (this.getWorld().isClient) {
            if (this.dataTracker.get(MELEE_ATTACKING)) {
                if (!meleeAttackAnimationState.isRunning()) {
                    meleeAttackAnimationState.start(this.age);
                }
            } else {
                meleeAttackAnimationState.stop();
            }

            if (this.dataTracker.get(RANGED_ATTACKING)) {
                if (!rangedAttackAnimationState.isRunning()) {
                    rangedAttackAnimationState.start(this.age);
                }
            } else {
                rangedAttackAnimationState.stop();
            }
        } else {
            if (this.dataTracker.get(MELEE_ATTACKING)) {
                meleeAttackTicks++;
                if (meleeAttackTicks > 10) {
                    this.dataTracker.set(MELEE_ATTACKING, false);
                    meleeAttackTicks = 0;
                }
            }
            if (this.dataTracker.get(RANGED_ATTACKING)) {
                rangedAttackTicks++;
                if (rangedAttackTicks > 10) {
                    this.dataTracker.set(RANGED_ATTACKING, false);
                    rangedAttackTicks = 0;
                }
            }
        }
    }
    private static class MeleeGoal extends MeleeAttackGoal {
        public MeleeGoal(PathAwareEntity mob, double speed, boolean pauseWhenMobIdle) {
            super(mob, speed, pauseWhenMobIdle);
        }

        @Override
        public boolean canStart() {
            LivingEntity target = this.mob.getTarget();
            if (target == null || !target.isAlive()) {
                return false;
            }
            if (this.mob.squaredDistanceTo(target) > MELEE_RANGE * MELEE_RANGE) {
                return false;
            }
            return super.canStart();
        }

        @Override
        public boolean shouldContinue() {
            LivingEntity target = this.mob.getTarget();
            if (target == null || !target.isAlive()) {
                return false;
            }
            if (this.mob.squaredDistanceTo(target) > MELEE_RANGE * MELEE_RANGE) {
                return false;
            }
            return super.shouldContinue();
        }

        @Override
        protected void attack(LivingEntity target, double squaredDistance) {
            double d = this.getSquaredMaxAttackDistance(target);
            if (squaredDistance <= d && this.getCooldown() <= 0) {
                this.resetCooldown();
                this.mob.tryAttack(target);
                if (this.mob instanceof JarGremlinEntity entity) {
                    entity.dataTracker.set(MELEE_ATTACKING, true);
                }
            }
        }
    }
    private class ShootMissileGoal extends Goal {
        private LivingEntity target;
        private int cooldown = 0;

        public ShootMissileGoal() {
            this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        }

        @Override
        public boolean canStart() {
            LivingEntity livingEntity = JarGremlinEntity.this.getTarget();
            if (livingEntity == null || !livingEntity.isAlive()) {
                return false;
            }
            if (livingEntity instanceof PlayerEntity player) {
                if (player.isCreative() || player.isSpectator()) {
                    return false;
                }
            }
            if (!JarGremlinEntity.this.canSee(livingEntity)) {
                return false;
            }
            if (shootCooldown > 0) {
                return false;
            }
            double sqDist = JarGremlinEntity.this.squaredDistanceTo(livingEntity);
            if (sqDist <= MELEE_RANGE * MELEE_RANGE) {
                return false;
            }
            double followRange = JarGremlinEntity.this.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE);
            if (sqDist > followRange * followRange) {
                return false;
            }
            this.target = livingEntity;
            return true;
        }

        @Override
        public boolean shouldContinue() {
            if (this.target instanceof PlayerEntity player) {
                if (player.isCreative() || player.isSpectator()) {
                    return false;
                }
            }
            return this.target != null
                    && this.target.isAlive()
                    && JarGremlinEntity.this.canSee(this.target)
                    && !JarGremlinEntity.this.isDead();
        }

        @Override
        public void start() {
            this.cooldown = 15;
        }

        @Override
        public void tick() {
            if (this.target == null) {
                return;
            }
            JarGremlinEntity.this.getLookControl().lookAt(this.target, 30.0F, 30.0F);
            this.cooldown--;
            if (this.cooldown <= 0) {
                JarGremlinEntity.this.shootMissile(this.target);
                JarGremlinEntity.this.dataTracker.set(RANGED_ATTACKING, true);
                JarGremlinEntity.this.playSound(SoundEvents.BLOCK_DISPENSER_LAUNCH, 1.0F, 1.0F);
                this.cooldown = 20;
                JarGremlinEntity.this.shootCooldown = SHOOT_INTERVAL;
            }
        }

        @Override
        public void stop() {
            this.target = null;
            this.cooldown = 0;
        }
    }
    private void shootMissile(LivingEntity target) {
        if (!this.getWorld().isClient) {
            Vec3d pos = this.getEyePos();
            Vec3d direction = target.getEyePos().subtract(pos).normalize();
            for (int i=-1; i<=1; i++) {
                createMissile((ServerWorld) this.getWorld(), pos, direction,this.getHeadYaw()+i*30);
            }
        }
    }

    private void createMissile(ServerWorld world, Vec3d pos, Vec3d direction,float yaw) {
        MissileEntity missile = new MissileEntity(
                ModEntities.MISSILE,
                world,
                this,
                new AttackTypes(AttackTypes.AttackType.BULLET),
                false,
                32.0,
                20,
                5.0);
        missile.setMissileModelType(MissileModelType.BULLET);
        double pitch = Math.toDegrees(Math.atan2(-direction.y,
                Math.sqrt(direction.x * direction.x + direction.z * direction.z)));
        missile.setPosition(pos);
        missile.setYaw(yaw);
        missile.setPitch((float) pitch);
        world.spawnEntity(missile);
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);
        if (this.getWorld().isClient) {
            return;
        }
        ServerWorld serverWorld = (ServerWorld) this.getWorld();

        JarGremlinBodyEntity body = new JarGremlinBodyEntity(ModEntities.JAR_GREMLIN_BODY, serverWorld);
        body.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getBodyYaw(), 0.0F);
        serverWorld.spawnEntity(body);

        JarGremlinHeadEntity head = new JarGremlinHeadEntity(ModEntities.JAR_GREMLIN_HEAD, serverWorld);
        head.refreshPositionAndAngles(this.getX(), this.getY() + 1.0, this.getZ(), this.getYaw(), 0.0F);
        serverWorld.spawnEntity(head);
    }

    @Override
    protected void drop(DamageSource source) {
    }

    @Override
    protected SoundEvent getDeathSound() {
        return null;
    }

    @Override
    public void handleStatus(byte status) {
        if (status == 60) {
            return;
        }
        super.handleStatus(status);
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
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_SKELETON_STEP, 0.15F, 1.0F);
    }
}