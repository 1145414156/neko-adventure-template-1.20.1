package com.nekoadventure.entity.mob;


import com.nekoadventure.entity.ModEntities;
import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.other.mobApart.AttackTypes;
import com.nekoadventure.other.mobApart.MissileModelType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import java.util.EnumSet;
public class JarGremlinHeadEntity extends HostileEntity implements Monster {

    private static final double KEEP_MIN_DIST = 8.0;
    private static final double KEEP_MAX_DIST = 16.0;
    private static final int SHOOT_INTERVAL = 30;

    private static final TrackedData<Boolean> RANGED_ATTACKING =
            DataTracker.registerData(JarGremlinHeadEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private int rangedAttackTicks = 0;

    @Environment(EnvType.CLIENT)
    public final AnimationState rangedAttackAnimationState = new AnimationState();

    public JarGremlinHeadEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 20, true);
        this.setNoGravity(true);
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation navigation = new BirdNavigation(this, world);
        navigation.setCanPathThroughDoors(false);
        navigation.setCanSwim(true);
        navigation.setCanEnterOpenDoors(true);
        return navigation;
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35D)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.35D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0D);
    }

    @Override
    protected void initGoals() {
        this.targetSelector.add(0, new RevengeGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, false));

        this.goalSelector.add(1, new KeepDistanceAndShootGoal());
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 0.8D));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(4, new LookAroundGoal(this));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(RANGED_ATTACKING, false);
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.UNDEAD;
    }

    @Override
    public void tick() {
        super.tick();
        this.setNoGravity(true);

        if (this.getWorld().isClient) {
            if (this.dataTracker.get(RANGED_ATTACKING)) {
                if (!rangedAttackAnimationState.isRunning()) {
                    rangedAttackAnimationState.start(this.age);
                }
            } else {
                rangedAttackAnimationState.stop();
            }
        } else {
            if (this.dataTracker.get(RANGED_ATTACKING)) {
                rangedAttackTicks++;
                if (rangedAttackTicks > 10) {
                    this.dataTracker.set(RANGED_ATTACKING, false);
                    rangedAttackTicks = 0;
                }
            }
        }
    }
    private class KeepDistanceAndShootGoal extends Goal {
        private LivingEntity target;
        private int cooldown = 0;

        public KeepDistanceAndShootGoal() {
            this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        }

        @Override
        public boolean canStart() {
            LivingEntity t = JarGremlinHeadEntity.this.getTarget();
            if (t == null || !t.isAlive()) {
                return false;
            }
            if (t instanceof PlayerEntity p && (p.isCreative() || p.isSpectator())) {
                return false;
            }
            double sqDist = JarGremlinHeadEntity.this.squaredDistanceTo(t);
            double followRange = JarGremlinHeadEntity.this.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE);
            if (sqDist > followRange * followRange) {
                return false;
            }
            this.target = t;
            return true;
        }

        @Override
        public boolean shouldContinue() {
            if (this.target == null || !this.target.isAlive() || JarGremlinHeadEntity.this.isDead()) {
                return false;
            }
            if (this.target instanceof PlayerEntity p && (p.isCreative() || p.isSpectator())) {
                return false;
            }
            double sqDist = JarGremlinHeadEntity.this.squaredDistanceTo(this.target);
            double followRange = JarGremlinHeadEntity.this.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE);
            return sqDist <= followRange * followRange;
        }

        @Override
        public void start() {
            this.cooldown = 10;
        }

        @Override
        public void tick() {
            if (this.target == null) {
                return;
            }
            JarGremlinHeadEntity.this.getLookControl().lookAt(this.target, 30.0F, 30.0F);

            double dist = Math.sqrt(JarGremlinHeadEntity.this.squaredDistanceTo(this.target));

            if (dist < KEEP_MIN_DIST) {
                Vec3d away = JarGremlinHeadEntity.this.getPos().subtract(this.target.getPos());
                Vec3d horizontal = new Vec3d(away.x, 0.0D, away.z);
                if (horizontal.lengthSquared() < 1.0E-4D) {
                    horizontal = new Vec3d(1.0D, 0.0D, 0.0D);
                }
                Vec3d desired = this.target.getPos()
                        .add(horizontal.normalize().multiply(KEEP_MAX_DIST * 0.75D))
                        .add(0.0D, 2.0D, 0.0D);
                JarGremlinHeadEntity.this.getNavigation().stop();
                JarGremlinHeadEntity.this.getMoveControl().moveTo(desired.x, desired.y, desired.z, 3.0D);
            } else if (dist > KEEP_MAX_DIST) {
                JarGremlinHeadEntity.this.getNavigation().startMovingTo(this.target, 1.0D);
            } else {
                JarGremlinHeadEntity.this.getNavigation().stop();
            }

            if (this.cooldown > 0) {
                this.cooldown--;
            }
            if (this.cooldown <= 0
                    && dist <= KEEP_MAX_DIST
                    && JarGremlinHeadEntity.this.canSee(this.target)) {
                JarGremlinHeadEntity.this.shootMissile(this.target);
                JarGremlinHeadEntity.this.dataTracker.set(RANGED_ATTACKING, true);
                JarGremlinHeadEntity.this.playSound(SoundEvents.BLOCK_DISPENSER_LAUNCH, 1.0F, 1.0F);
                this.cooldown = SHOOT_INTERVAL;
            }
        }

        @Override
        public void stop() {
            this.target = null;
            this.cooldown = 0;
            JarGremlinHeadEntity.this.getNavigation().stop();
        }
    }

    private void shootMissile(LivingEntity target) {
        if (this.getWorld().isClient) {
            return;
        }
        Vec3d pos = this.getEyePos();
        Vec3d direction = target.getEyePos().subtract(pos).normalize();
        for (int i = -1; i <= 1; i++) {
            createMissile((ServerWorld) this.getWorld(), pos, direction, this.getHeadYaw() + i * 30);
        }
    }

    private void createMissile(ServerWorld world, Vec3d pos, Vec3d direction, float yaw) {
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
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
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
}