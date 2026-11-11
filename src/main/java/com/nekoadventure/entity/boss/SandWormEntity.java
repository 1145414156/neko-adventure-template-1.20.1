package com.nekoadventure.entity.boss;

import com.nekoadventure.effect.ModStatusEffects;
import com.nekoadventure.entity.ModEntities;
import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.item.ModItems;
import com.nekoadventure.network.mob.ScreenShakeNetworking;
import com.nekoadventure.other.itemApart.SpawnRandomSoulItems;
import com.nekoadventure.other.mobApart.AttackTypes;
import com.nekoadventure.other.mobApart.MissileModelType;
import com.nekoadventure.sound.ModSoundEvents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
public class SandWormEntity extends HostileEntity implements Monster {
    public static final int SEGMENT_COUNT = 10;
    public static final double SEGMENT_SPACING = 2.5;
    private static final double SAMPLE_DISTANCE = 0.25;
    private static final double FOLLOW_FACTOR = 0.6;
    private static final float CLIENT_TURN_STRENGTH = 0.4F;

    private final Deque<Vec3d> positionHistory = new ArrayDeque<>();
    private final List<SandWormBodyEntity> body = new ArrayList<>();
    private Vec3d lastRecordedPos;
    private long lastHurtTime = Long.MIN_VALUE;

    private final ServerBossBar bossBar = new ServerBossBar(
            this.getDisplayName(), BossBar.Color.PURPLE, BossBar.Style.PROGRESS);
    @Environment(EnvType.CLIENT)
    public final AnimationState DASH_SKILL_ANI = new AnimationState();
    @Environment(EnvType.CLIENT)
    public final AnimationState BULLET_SKILL_ANI = new AnimationState();
    @Environment(EnvType.CLIENT)
    public final AnimationState SUCK_SKILL_ANI = new AnimationState();
    @Environment(EnvType.CLIENT)
    public final AnimationState ACCELERATION_SKILL_ANI = new AnimationState();
    @Environment(EnvType.CLIENT)
    public final AnimationState BARRAGE_SKILL_ANI = new AnimationState();
    public static final int DASH_SKILL_DURATION = 60;
    public static final int BULLET_SKILL_DURATION = 90;
    public static final int SUCK_SKILL_DURATION = 100;
    public static final int ACCELERATION_SKILL_DURATION = 20;
    public static final int BARRAGE_SKILL_DURATION = 80;
    private static final TrackedData<Integer> ACTIVE_SKILL =
            DataTracker.registerData(SandWormEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private int playedSkill = -1;
    private int skillTick = 0;
    private int skillStage = 0;
    private int skill = -999;
    private Vec3d dashStartPos;
    private Vec3d dashDirection;
    private boolean isDashing = false;

    public SandWormEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new SmoothMoveControl(this);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 200.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.29D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64.0D)
                .add(EntityAttributes.GENERIC_ARMOR, 4.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.5D);
    }

    @Override
    protected void initGoals() {
        this.targetSelector.add(0, new RevengeGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, false, false,
                livingEntity -> Math.abs(livingEntity.getY() - this.getY()) <= 12.0));
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.goalSelector.add(5, new WanderAroundGoal(this, 0.8D));
    }
    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ACTIVE_SKILL, -1);
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.UNDEAD;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        boolean damaged = super.damage(source, amount);
        if (damaged) {
            this.lastHurtTime = this.getWorld().getTime();
        }
        return damaged;
    }

    public boolean wasHurtThisTick() {
        return this.lastHurtTime == this.getWorld().getTime();
    }

    @Override
    public void tick() {
        float bodyYawBefore = this.bodyYaw;
        super.tick();

        if (this.getWorld().isClient) {
            if (this.dataTracker.get(ACTIVE_SKILL) == 2) {
                spawnSuckParticle(this.getWorld());
            }
        }
        if (this.getWorld().isClient) {
            this.syncClientAnimations();
            this.smoothClientHeadRotation(bodyYawBefore);
            return;
        }

        this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
        if (this.isDead()) {
            return;
        }


        if (this.skillStage != 0 && this.skill >= 0&&skill!=0&&skill!=3&&skill!=4) {
            this.setVelocity(new Vec3d(0,this.getVelocity().y,0));
        }
        this.applySkill();
        this.syncHeadRotation();
        Vec3d currentPos = this.getPos();
        if (this.lastRecordedPos == null) {
            this.lastRecordedPos = currentPos;
            this.positionHistory.addFirst(currentPos);
        } else if (currentPos.distanceTo(this.lastRecordedPos) >= SAMPLE_DISTANCE) {
            this.lastRecordedPos = currentPos;
            this.positionHistory.addFirst(currentPos);
        }
        double neededLength = (SEGMENT_COUNT + 1) * SEGMENT_SPACING;
        int maxHistory = (int) Math.ceil(neededLength / SAMPLE_DISTANCE) + 4;
        while (this.positionHistory.size() > maxHistory) {
            this.positionHistory.removeLast();
        }
        this.body.removeIf(Entity::isRemoved);
        for (int index = 0; index < SEGMENT_COUNT; index++) {
            if (getSegmentByIndex(index) != null) {
                continue;
            }
            Vec3d initPos = getPointBehind((index + 1) * SEGMENT_SPACING);
            if (initPos == null) {
                break;
            }
            SandWormBodyEntity seg = new SandWormBodyEntity(ModEntities.SAND_WORM_BODY, this.getWorld());
            seg.setHead(this);
            seg.setBodyIndex(index);
            seg.setPosition(initPos.x, initPos.y, initPos.z);
            this.body.add(seg);
            this.getWorld().spawnEntity(seg);
        }
        for (int index = 0; index < SEGMENT_COUNT; index++) {
            SandWormBodyEntity seg = getSegmentByIndex(index);
            if (seg == null) {
                continue;
            }
            Vec3d target = getPointBehind((index + 1) * SEGMENT_SPACING);
            if (target == null) {
                seg.setVelocity(Vec3d.ZERO);
                seg.velocityModified = true;
                continue;
            }
            Vec3d velocity = target.subtract(seg.getPos()).multiply(FOLLOW_FACTOR);
            seg.setVelocity(velocity);
            seg.velocityModified = true;

            Vec3d prevPos = index == 0 ? currentPos : getSegmentPos(index - 1, currentPos);
            Vec3d dir = prevPos.subtract(seg.getPos());
            if (dir.lengthSquared() > 0.001) {
                dir = dir.normalize();
                float yaw = (float) Math.toDegrees(Math.atan2(-dir.x, dir.z));
                float pitch = (float) Math.toDegrees(Math.atan2(-dir.y,
                        Math.sqrt(dir.x * dir.x + dir.z * dir.z)));
                seg.setYaw(yaw);
                seg.setPitch(pitch);
            }
        }
    }
    private void syncHeadRotation() {
        this.headYaw = this.getYaw();
        this.bodyYaw = this.getYaw();
    }
    @Environment(EnvType.CLIENT)
    private void smoothClientHeadRotation(float lastBodyYaw) {
        float delta = MathHelper.wrapDegrees(this.getYaw() - lastBodyYaw);
        float strength = Math.abs(delta) > 30.0F ? 0.8F : CLIENT_TURN_STRENGTH;
        float yaw = MathHelper.lerpAngleDegrees(strength, lastBodyYaw, this.getYaw());
        this.bodyYaw = yaw;
        this.headYaw = yaw;
    }

    @Environment(EnvType.CLIENT)
    private void syncClientAnimations() {
        int activeSkill = this.dataTracker.get(ACTIVE_SKILL);
        if (activeSkill != this.playedSkill) {
            DASH_SKILL_ANI.stop();
            BULLET_SKILL_ANI.stop();
            SUCK_SKILL_ANI.stop();
            ACCELERATION_SKILL_ANI.stop();
            BARRAGE_SKILL_ANI.stop();

            if (activeSkill >= 0) {
                switch (activeSkill) {
                    case 0 -> DASH_SKILL_ANI.start(this.age);
                    case 1 -> BULLET_SKILL_ANI.start(this.age);
                    case 2 -> SUCK_SKILL_ANI.start(this.age);
                    case 3 -> ACCELERATION_SKILL_ANI.start(this.age);
                    case 4 -> BARRAGE_SKILL_ANI.start(this.age);
                }
            }
            this.playedSkill = activeSkill;
        }
    }
    private void applySkill() {
        //0.冲撞；1.弹幕；2.吸入；3.加速；4.压制
        if (skillTick > 0) {
            skillTick--;
            if (skillStage != 0) {
                switch (skill) {
                    case 0 -> dashSkill();
                    case 1 -> bulletSkill();
                    case 2 -> suckSkill();
                    case 3 -> accelerationSkill();
                    case 4 -> barrageSkill();
                }
            }
        } else if (skillStage == 0) {
            if (this.getTarget() == null) {
                skillTick = this.random.nextInt(40) + 40;
                skillStage = 0;
                return;
            }
            skill = this.random.nextInt(5);
            switch (skill) {
                case 0 -> skillTick = DASH_SKILL_DURATION;
                case 1 -> skillTick = BULLET_SKILL_DURATION;
                case 2 -> skillTick = SUCK_SKILL_DURATION;
                case 3 -> skillTick = ACCELERATION_SKILL_DURATION;
                case 4 -> skillTick = BARRAGE_SKILL_DURATION;
            }
            skillStage++;
            this.dataTracker.set(ACTIVE_SKILL, skill);
        } else {
            skillTick = this.random.nextInt(60) + 80;
            skillStage = 0;
            skill = -999;
            this.dataTracker.set(ACTIVE_SKILL, -1);
        }
    }
    private void dashSkill() {
        int times = -(skillTick - DASH_SKILL_DURATION);
        if (times <= 0) {
            return;
        }
        else if (times < 30) {
            this.setVelocity(0, this.getVelocity().y, 0);
        }
        else if (times == 30) {
            this.dashStartPos = this.getPos();
            LivingEntity target = this.getTarget();
            Vec3d dir;
            if (target != null) {
                dir = target.getEyePos().subtract(this.getEyePos());
            } else {
                dir = this.getRotationVec(1.0F);
            }

            dir = new Vec3d(dir.x, 0, dir.z);
            if (dir.lengthSquared() < 0.001) {
                dir = new Vec3d(0, 0, 1);
            }
            this.dashDirection = dir.normalize();
            this.isDashing = true;

            this.playSound(SoundEvents.ENTITY_RAVAGER_ROAR, 1.0F, 0.8F);
        }
        if (times > 30 && times <= 60 && this.isDashing) {
            double progress = (times - 30) / 30.0;
            Vec3d newPos = this.dashStartPos.add(this.dashDirection.multiply(32.0 * progress));

            this.setPosition(newPos.x, this.dashStartPos.y, newPos.z);
            this.setVelocity(Vec3d.ZERO);

            float yaw = (float) Math.toDegrees(Math.atan2(-this.dashDirection.x, this.dashDirection.z));
            this.setYaw(yaw);
            this.setPitch(0);

            if (this.getWorld() instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(
                        ParticleTypes.CLOUD,
                        this.getX(), this.getY() + 0.5, this.getZ(),
                        6,
                        0.6, 0.4, 0.6,
                        0.05
                );

                if (this.getWorld().getBlockCollisions(this, this.getBoundingBox().expand(1.0,0,1.0)).iterator().hasNext()) {
                    skillTick=0;
                    times=60;
                }
            }

            if (times == 60) {
                this.isDashing = false;
                this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0F, 0.6F);
            }
        }
    }

    private void bulletSkill() {
        int times = -(skillTick - BULLET_SKILL_DURATION);
        LivingEntity target = this.getTarget();
        if (target == null) {
            return;
        }
        if (this.getWorld().getBlockCollisions(this, this.getBoundingBox().expand(0.5,0,0.5)).iterator().hasNext()) {
            this.skillTick = 0;
            this.setVelocity(0, this.getVelocity().y, 0);
            this.velocityModified = true;
            return;
        }
        this.getNavigation().stop();
        double orbitAngle = (times * 5.0) % 360.0;
        double rad = Math.toRadians(orbitAngle);
        double orbitX = target.getX() + Math.cos(rad) * 12.0;
        double orbitZ = target.getZ() + Math.sin(rad) * 12.0;
        Vec3d diff = new Vec3d(orbitX - this.getX(), 0, orbitZ - this.getZ());
        double dist = diff.length();
        if (dist > 0.05) {
            Vec3d dir = diff.normalize();
            double moveSpeed = Math.min(dist, 0.8);
            this.setVelocity(dir.x * moveSpeed, this.getVelocity().y, dir.z * moveSpeed);
            this.velocityModified = true;
        } else {
            this.setVelocity(0, this.getVelocity().y, 0);
        }
        Vec3d lookDir = target.getEyePos().subtract(this.getEyePos());
        if (lookDir.lengthSquared() > 0.01) {
            double horizontal = Math.sqrt(lookDir.x * lookDir.x + lookDir.z * lookDir.z);
            float yaw = (float) Math.toDegrees(Math.atan2(-lookDir.x, lookDir.z));
            float pitch = (float) Math.toDegrees(Math.atan2(-lookDir.y, horizontal));
            this.setYaw(yaw);
            this.setPitch(pitch);
        }
        if (times > 0 && times % 15 == 0) {
            Vec3d spawnPos = this.getEyePos().add(0, -1.5, 0);
            Vec3d dir = target.getEyePos().subtract(spawnPos).normalize();
            float baseYaw = (float) Math.toDegrees(Math.atan2(-dir.x, dir.z));
            for (int i = 0; i < 5; i++) {
                this.playSound(SoundEvents.BLOCK_DISPENSER_LAUNCH, 1.0f, 0.5f);
                MissileEntity bullet = getMissileEntity(this, 32, 80, 1);
                bullet.setTreadable(true);
                bullet.setYaw(baseYaw - 40 + 20 * i);
                bullet.setPitch(0);
                bullet.setPosition(spawnPos);
                this.getWorld().spawnEntity(bullet);
            }
        }
    }

    private void suckSkill() {
        int times = -(skillTick - SUCK_SKILL_DURATION);
        if (times <= 0) {
            return;
        }
        LivingEntity target = this.getTarget();
        this.getNavigation().stop();
        if (target==null){
            return;
        }

        Vec3d dir = target.getEyePos().subtract(this.getEyePos());
        double seeHorizontal = Math.sqrt(dir.x * dir.x + dir.z * dir.z);
        float yaw = (float) Math.toDegrees(Math.atan2(-dir.x, dir.z));
        float pitch = (float) Math.toDegrees(Math.atan2(-dir.y, seeHorizontal));
        this.setYaw(yaw);
        this.setPitch(pitch);

        Vec3d center = this.getEyePos().add(0,-1.5,0);
        Vec3d lookVec = this.getRotationVec(1.0F);
        Vec3d horizontalLook = new Vec3d(lookVec.x, 0, lookVec.z).normalize();
        double absorbRange = 32.0;

        List<LivingEntity> targets = this.getWorld().getEntitiesByClass(
                LivingEntity.class,
                this.getBoundingBox().expand(absorbRange),
                e -> e != this && e.isAlive()
        );

        for (LivingEntity entity : targets) {
            Vec3d diff = center.subtract(entity.getBoundingBox().getCenter());
            double distance = diff.length();
            if (distance < 0.01 || distance > absorbRange) {
                continue;
            }
            Vec3d horizontalToTarget = new Vec3d(
                    entity.getX() - this.getX(), 0, entity.getZ() - this.getZ());
            if (horizontalToTarget.lengthSquared() > 0.1) {
                horizontalToTarget = horizontalToTarget.normalize();
                if (horizontalToTarget.dotProduct(horizontalLook) < 0) {
                    continue;
                }
            }

            double strength = 0.02 + 0.06 * (1.0 - distance / absorbRange);
            Vec3d pull = diff.multiply(strength / distance);
            entity.addVelocity(pull.x, pull.y, pull.z);
            entity.velocityModified = true;
        }
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            float randomYaw = this.getYaw() + (this.random.nextFloat() * 180.0f - 90.0f);
            double spawnDist = 14.0 + this.random.nextDouble() * 6.0;
            double rad = Math.toRadians(randomYaw);
            double spawnX = this.getX() - Math.sin(rad) * spawnDist;
            double spawnY = this.getEyeY()-1.5;
            double spawnZ = this.getZ() + Math.cos(rad) * spawnDist;
            Vec3d spawnPos = new Vec3d(spawnX, spawnY, spawnZ);
            Vec3d toSelf = this.getEyePos().subtract(spawnPos);
            double horizontal = Math.sqrt(toSelf.x * toSelf.x + toSelf.z * toSelf.z);
            float bulletYaw = (float) Math.toDegrees(Math.atan2(-toSelf.x, toSelf.z));
            float bulletPitch = (float) Math.toDegrees(Math.atan2(-toSelf.y, horizontal));
            double actualDist = toSelf.length();
            MissileEntity bullet = getMissileEntity(this, actualDist, 60, 2);
            bullet.setTreadable(true);
            bullet.setPosition(spawnPos.x, spawnPos.y-0.75, spawnPos.z);
            bullet.setYaw(bulletYaw);
            bullet.setPitch(bulletPitch);
            serverWorld.spawnEntity(bullet);
            serverWorld.playSound(null, spawnPos.x, spawnPos.y, spawnPos.z,
                    SoundEvents.BLOCK_DISPENSER_LAUNCH, SoundCategory.HOSTILE, 1.0f, 0.8f);
        }
        if (times==80){
            List<PlayerEntity> playerEntities=this.getWorld().getEntitiesByClass(
                    PlayerEntity.class,
                    this.getBoundingBox().expand(12),
                    e-> true
            );
            int range=16;
            double spacing = 1.0;
            double cubeSize = 0.5;
            for (int length = 1; length <= range; length++) {
                Vec3d mobPos = this.getEyePos().add(0,-1.2,0);
                Vec3d lookDirection = this.getRotation(this.getYaw());
                MissileEntity missile=getMissileEntity(this,range,20,3);
                missile.setFlag(length);
                AttackTypes brimstoneType = new AttackTypes(AttackTypes.AttackType.BRIMSTONE);
                missile.setAttackType(brimstoneType);
                missile.setTreadable(true);
                missile.setMissileModelType(MissileModelType.BRIMSTONE);
                Vec3d spawnPos = mobPos.add(lookDirection.multiply(length * spacing - cubeSize / 2));
                missile.setPos(spawnPos.x, this.getEyeY()-1.2, spawnPos.z);
                missile.setYaw(this.getYaw());
                missile.setPitch(0);
                this.getWorld().spawnEntity(missile);
                this.getWorld().playSound(null, this.getBlockPos(), ModSoundEvents.BRIMSTONE_ATTACK,
                        SoundCategory.HOSTILE, 0.1F, 1.3F);
                for (PlayerEntity playerEntity : playerEntities) {
                    if (playerEntity instanceof ServerPlayerEntity){
                        ScreenShakeNetworking.sendToPlayer((ServerPlayerEntity) playerEntity, 20, 0.1F);
                    }
                }
            }
        }
    }

    private void accelerationSkill() {
         int times = -(skillTick - ACCELERATION_SKILL_DURATION);
         if (times == 1) {
             if (this.getWorld() instanceof ServerWorld serverWorld) {
                 serverWorld.spawnParticles(
                         ParticleTypes.CLOUD,
                         this.getX()+random.nextFloat()-0.5, this.getY()+random.nextFloat()-0.5, this.getZ()+random.nextFloat()-0.5,
                         100,
                         1.0, 1.0, 1.0,
                         0.01
                 );
                 this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED,200,1,false,false));
             }
         }
    }
    private void barrageSkill() {
        int times = -(skillTick - BARRAGE_SKILL_DURATION);
        if (times <= 0) {
            return;
        }
        LivingEntity target = this.getTarget();
        if (target == null) {
            return;
        }
        Vec3d dir = target.getEyePos().subtract(this.getEyePos());
        if (dir.lengthSquared() > 0.01) {
            double horizontal = Math.sqrt(dir.x * dir.x + dir.z * dir.z);
            float yaw = (float) Math.toDegrees(Math.atan2(-dir.x, dir.z));
            float pitch = (float) Math.toDegrees(Math.atan2(-dir.y, horizontal));
            this.setYaw(yaw);
            this.setPitch(pitch);
        }
        if (times % 5 == 0&&times<80) {
            Vec3d spawnPos = this.getEyePos().add(0, -0.75, 0);
            float baseYaw = this.getYaw();
            for (int i = 0; i < 3; i++) {
                float offsetYaw = -20.0f + 20.0f * i;
                MissileEntity bullet = getMissileEntity(this, 32, 20, 3);
                bullet.setTreadable(true);
                bullet.setPosition(spawnPos.x, spawnPos.y, spawnPos.z);
                bullet.setYaw(baseYaw + offsetYaw);
                bullet.setPitch(0);
                this.getWorld().spawnEntity(bullet);
            }
            this.playSound(SoundEvents.BLOCK_DISPENSER_LAUNCH, 1.0f, 0.6f);
        }
        else if (times == 80){
            List<PlayerEntity> playerEntities=this.getWorld().getEntitiesByClass(
                    PlayerEntity.class,
                    this.getBoundingBox().expand(12),
                    e-> true
            );
            int range=12;
            double spacing = 1.0;
            double cubeSize = 0.5;
            for (int length = 1; length <= range; length++) {
                Vec3d mobPos = this.getEyePos().add(0,-1.2,0);
                Vec3d lookDirection = this.getRotation(this.getYaw());
                MissileEntity missile=getMissileEntity(this,range,20,4);
                missile.setFlag(length);
                AttackTypes brimstoneType = new AttackTypes(AttackTypes.AttackType.BRIMSTONE);
                missile.setAttackType(brimstoneType);
                missile.setTreadable(true);
                missile.setMissileModelType(MissileModelType.BRIMSTONE);
                Vec3d spawnPos = mobPos.add(lookDirection.multiply(length * spacing - cubeSize / 2));
                missile.setPos(spawnPos.x, this.getEyeY()-1.2, spawnPos.z);
                missile.setYaw(this.getYaw());
                missile.setPitch(0);
                this.getWorld().spawnEntity(missile);
                this.getWorld().playSound(null, this.getBlockPos(), ModSoundEvents.BRIMSTONE_ATTACK,
                        SoundCategory.HOSTILE, 0.1F, 1.3F);
                for (PlayerEntity playerEntity : playerEntities) {
                    if (playerEntity instanceof ServerPlayerEntity){
                        ScreenShakeNetworking.sendToPlayer((ServerPlayerEntity) playerEntity, 20, 0.1F);
                    }
                }
            }
        }
    }

    @Environment(EnvType.CLIENT)
    private void spawnSuckParticle(World world) {
        var random = world.random;
        Vec3d center = new Vec3d(this.getX(), this.getBoundingBox().getCenter().y, this.getZ());
        Vec3d look = this.getRotationVec(1.0F);
        Vec3d right = look.crossProduct(new Vec3d(0, 1, 0));
        if (right.lengthSquared() < 1.0E-4) {
            right = new Vec3d(1, 0, 0);
        }
        right = right.normalize();
        Vec3d up = right.crossProduct(look);

        for (int i = 0; i < 4; i++) {
            double phi = Math.acos(random.nextDouble());
            double theta = random.nextDouble() * Math.PI * 2;
            double r = random.nextDouble() * 32.0;
            double sinPhi = Math.sin(phi);
            Vec3d offset = look.multiply(Math.cos(phi) * r)
                    .add(right.multiply(sinPhi * Math.cos(theta) * r))
                    .add(up.multiply(sinPhi * Math.sin(theta) * r));
            double px = center.x + offset.x;
            double py = center.y + random.nextDouble() * 2.0;
            double pz = center.z + offset.z;
            Vec3d toCenter = center.subtract(px, py, pz);
            double dist = toCenter.length();
            if (dist < 0.01) {
                continue;
            }
            Vec3d dir = toCenter.normalize();
            double speed = 0.2 + random.nextDouble() * 0.8;
            world.addParticle(
                    ParticleTypes.CLOUD,
                    px, py, pz,
                    dir.x * speed,
                    dir.y * speed,
                    dir.z * speed
            );
        }
    }

    public void adoptExistingBody(SandWormBodyEntity body) {
        if (this.getWorld().isClient || body.isRemoved() || this.body.contains(body)) {
            return;
        }
        int index = body.getBodyIndex();
        if (index < 0 || index >= SEGMENT_COUNT) {
            body.discard();
            return;
        }
        this.body.removeIf(Entity::isRemoved);
        if (getSegmentByIndex(index) != null) {
            body.discard();
            return;
        }
        this.body.add(body);
    }

    private Vec3d getPointBehind(double distance) {
        Vec3d prev = this.getPos();
        double remaining = distance;
        for (Vec3d point : this.positionHistory) {
            double segmentLength = prev.distanceTo(point);
            if (segmentLength >= remaining) {
                if (segmentLength < 1.0E-4) {
                    return prev;
                }
                return prev.lerp(point, remaining / segmentLength);
            }
            remaining -= segmentLength;
            prev = point;
        }
        return null;
    }

    private SandWormBodyEntity getSegmentByIndex(int index) {
        for (SandWormBodyEntity seg : this.body) {
            if (!seg.isRemoved() && seg.getBodyIndex() == index) {
                return seg;
            }
        }
        return null;
    }

    private Vec3d getSegmentPos(int index, Vec3d fallback) {
        for (int i = index; i >= 0; i--) {
            SandWormBodyEntity seg = getSegmentByIndex(i);
            if (seg != null) {
                return seg.getPos();
            }
        }
        return fallback;
    }
    protected Vec3d getRotation(float yaw) {
        float f = (float) -10 * (float) (Math.PI / 180.0);
        float g = -yaw * (float) (Math.PI / 180.0);
        float h = MathHelper.cos(g);
        float i = MathHelper.sin(g);
        float j = MathHelper.cos(f);
        float k = MathHelper.sin(f);
        return new Vec3d(i * j, -k, h * j);
    }

    @Override
    public void onDeath(DamageSource source) {
        if (!this.getWorld().isClient) {
            clearBody();
        }
        List<? extends PlayerEntity> playerEntities = this.getWorld().getPlayers();
        if (!playerEntities.isEmpty()) {
            for (PlayerEntity player : playerEntities) {
                player.removeStatusEffect(ModStatusEffects.BOSS_FIGHT);
            }
        }
        super.onDeath(source);
    }

    @Override
    public void remove(RemovalReason reason) {
        if (!this.getWorld().isClient) {
            clearBody();
        }
        super.remove(reason);
    }

    private void clearBody() {
        for (SandWormBodyEntity seg : this.body) {
            if (!seg.isRemoved()) seg.discard();
        }
        this.body.clear();
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            for (SandWormBodyEntity seg : serverWorld.getEntitiesByClass(SandWormBodyEntity.class,
                    this.getBoundingBox().expand(96.0), seg -> seg.isHeadOf(this))) {
                seg.discard();
            }
        }
    }

    protected float getDamageAmount() {
        return (float) this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
    }

    private @NotNull MissileEntity getMissileEntity(Entity owner, double range, int time, int flag) {
        AttackTypes bulletType = new AttackTypes(AttackTypes.AttackType.BULLET);
        MissileEntity missile= new MissileEntity(ModEntities.MISSILE,
                owner.getWorld(),
                owner,
                bulletType,
                false,
                range,
                time,
                this.getDamageAmount(),
                flag);
        missile.setMissileModelType(MissileModelType.BULLET);
        return missile;
    }
    @Override
    protected void dropLoot(DamageSource damageSource, boolean causedByPlayer) {
        SpawnRandomSoulItems spawnRandomSoulItems = new SpawnRandomSoulItems();
        this.dropItem(ModItems.PROP_PROTOTYPE);
        this.dropItem(spawnRandomSoulItems.summonRandomSoulItem(this.getWorld()).getItem());
    }

    @Override
    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);
        this.bossBar.addPlayer(player);
    }

    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        this.bossBar.removePlayer(player);
    }

    @Override
    public void setCustomName(@Nullable Text name) {
        super.setCustomName(name);
        this.bossBar.setName(this.getDisplayName());
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
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("SkillTick", this.skillTick);
        nbt.putInt("SkillStage", this.skillStage);
        nbt.putInt("Skill", this.skill);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.skillTick = nbt.getInt("SkillTick");
        this.skillStage = nbt.getInt("SkillStage");
        this.skill = nbt.getInt("Skill");
        if (this.hasCustomName()) {
            this.bossBar.setName(this.getDisplayName());
        }
        // 0~4 = 冲撞/弹幕/吸入/加速/连续弹幕
        if (this.skillStage != 0 && this.skill >= 0 && this.skill <= 4) {
            this.dataTracker.set(ACTIVE_SKILL, this.skill);
        }
    }
    private static class SmoothMoveControl extends MoveControl {
        private static final float MAX_TURN_PER_TICK = 15.0F;

        private SmoothMoveControl(MobEntity entity) {
            super(entity);
        }

        @Override
        public void tick() {
            float yawBefore = this.entity.getYaw();
            super.tick();
            float delta = MathHelper.wrapDegrees(this.entity.getYaw() - yawBefore);
            if (Math.abs(delta) > MAX_TURN_PER_TICK) {
                this.entity.setYaw(yawBefore + Math.signum(delta) * MAX_TURN_PER_TICK);
            }
        }
    }
}