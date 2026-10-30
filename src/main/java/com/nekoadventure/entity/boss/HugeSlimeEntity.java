package com.nekoadventure.entity.boss;

import com.nekoadventure.effect.ModStatusEffects;
import com.nekoadventure.entity.ModEntities;
import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.item.ModItems;
import com.nekoadventure.network.mob.FloorShakeNetworking;
import com.nekoadventure.network.mob.ScreenShakeNetworking;
import com.nekoadventure.other.itemApart.SpawnRandomSoulItems;
import com.nekoadventure.other.mobApart.AttackTypes;
import com.nekoadventure.other.mobApart.MissileModelType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.RevengeGoal;
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
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;


public class HugeSlimeEntity extends HostileEntity implements Monster {

    @Environment(EnvType.CLIENT)
    public final AnimationState TRAMPLE_SKILL_ANI=new AnimationState();
    private static final int TRAMPLE_SKILL_DURATION=180;
    @Environment(EnvType.CLIENT)
    public final AnimationState SUMMON_SKILL_ANI=new AnimationState();
    private static final int SUMMON_SKILL_DURATION=40;
    @Environment(EnvType.CLIENT)
    public final AnimationState BULLET_SKILL_ANI=new AnimationState();
    private static final int BULLET_SKILL_DURATION=100;
    @Environment(EnvType.CLIENT)
    public final AnimationState DASH_SKILL_ANI=new AnimationState();
    private static final int DASH_SKILL_DURATION=60;

    @Environment(EnvType.CLIENT)
    public final AnimationState SUMMONING_ANI=new AnimationState();
    private static final int SUMMONING_DURATION = 40;
    private static final int SUMMONING_REVEAL_TICK = 30;
    private static final TrackedData<Integer> SUMMONING_STATE =
            DataTracker.registerData(HugeSlimeEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private int playedSummoningState = 0;
    private boolean isSummoning = false;
    private int summoningTick = 0;
    private boolean summoningChecked = false;

    private static final TrackedData<Integer> ACTIVE_SKILL =
            DataTracker.registerData(HugeSlimeEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private int playedSkill = -1;

    private int summonCount = 0;
    private Vec3d targetPos;
    public float targetStretch;
    public float stretch;
    public float lastStretch;
    private boolean onGroundLastTick;
     private boolean canJump = true;
     private int jumpCount = 0;

     private int skillTick=40;
     private int skillStage=0;
     private int skill;

    private final ServerBossBar bossBar = new ServerBossBar(this.getDisplayName(), BossBar.Color.PURPLE, BossBar.Style.PROGRESS);

    public HugeSlimeEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);

        this.moveControl = new SlimeMoveControl(this);
    }

    public static DefaultAttributeContainer.Builder createHugeSlimeAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 400.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5D)
                .add(EntityAttributes.GENERIC_ARMOR, 4.0D)
                .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 4.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0D)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 2.0D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 50.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.9D);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimmingGoal(this));
        this.goalSelector.add(2, new FaceTowardTargetGoal(this));
        this.goalSelector.add(3, new RandomLookGoal(this));
        this.goalSelector.add(5, new MoveGoal(this));
        this.targetSelector.add(0, new RevengeGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, false, false,
                livingEntity -> Math.abs(livingEntity.getY() - this.getY()) <= 8.0));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, IronGolemEntity.class, true));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient) {
            this.syncClientAnimations();
            return;
        }
        this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
        this.stretch = this.stretch + (this.targetStretch - this.stretch) * 0.5F;
        this.lastStretch = this.stretch;

        List<? extends PlayerEntity> players=this.getWorld().getPlayers();
        if (!players.isEmpty()){
            players.forEach(p-> p.addStatusEffect(new StatusEffectInstance(ModStatusEffects.BOSS_FIGHT,40,0,false,false,true)));
        }

        if (!this.getWorld().isClient) {
            this.applySummoning();
            this.checkSummonOnSpawn();
        }

        if (this.isSummoning) {
            this.setVelocity(0, 0, 0);
            this.onGroundLastTick = this.isOnGround();
            this.updateStretch();
            return;
        }

        if (this.isOnGround() && !this.onGroundLastTick&&canJump) {
            int particleCount = 12;
            for (int j = 0; j < particleCount; j++) {
                float f = this.random.nextFloat() * (float) (Math.PI * 2);
                float g = this.random.nextFloat() * 0.5F + 0.5F;
                float h = MathHelper.sin(f) * 2.0F * g;
                float k = MathHelper.cos(f) * 2.0F * g;
                this.getWorld().addParticle(this.getParticles(), this.getX() + h, this.getY(), this.getZ() + k, 0.0, 0.0, 0.0);
            }
            this.playSound(this.getSquishSound(), this.getSoundVolume(),
                    ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) / 0.8F);
            this.targetStretch = -0.5F;
            if (jumpCount >= 2) {
                setCanJump(false);
            }
        } else if (!this.isOnGround() && this.onGroundLastTick&&canJump) {
            this.targetStretch = 1.0F;
        }
        else if (!canJump){
            applySkill();
        }

        this.onGroundLastTick = this.isOnGround();
        this.updateStretch();
    }

    private void syncClientAnimations() {
        int summoningState = this.dataTracker.get(SUMMONING_STATE);
        if (summoningState != this.playedSummoningState) {
            if (summoningState == 1) {
                SUMMONING_ANI.start(this.age);
            } else {
                SUMMONING_ANI.stop();
            }
            this.playedSummoningState = summoningState;
        }

        int activeSkill = this.dataTracker.get(ACTIVE_SKILL);
        if (activeSkill != this.playedSkill) {
            TRAMPLE_SKILL_ANI.stop();
            SUMMON_SKILL_ANI.stop();
            BULLET_SKILL_ANI.stop();
            DASH_SKILL_ANI.stop();
            if (activeSkill >= 0) {
                switch (activeSkill) {
                    case 0 -> TRAMPLE_SKILL_ANI.start(this.age);
                    case 1 -> SUMMON_SKILL_ANI.start(this.age);
                    case 2 -> BULLET_SKILL_ANI.start(this.age);
                    case 3 -> DASH_SKILL_ANI.start(this.age);
                }
            }
            this.playedSkill = activeSkill;
        }
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

    private void checkSummonOnSpawn() {
        if (this.summoningChecked) {
            return;
        }
        this.summoningChecked = true;
        this.startSummoning();
    }

    private void startSummoning() {
        this.isSummoning = true;
        this.summoningTick = SUMMONING_DURATION;
        this.setAiDisabled(true);
        this.dataTracker.set(SUMMONING_STATE, 1);
    }

    private void applySummoning() {
        if (!this.isSummoning) {
            return;
        }
        if (this.summoningTick > 0) {
            this.summoningTick--;
            this.summoningAnimationTick();
        } else {
            this.isSummoning = false;
            this.setAiDisabled(false);
            this.dataTracker.set(SUMMONING_STATE, 0);
        }
    }

    private void summoningAnimationTick() {
        int time = SUMMONING_DURATION - this.summoningTick;
        this.setVelocity(0, 0, 0);
        this.targetStretch = 0.0F;
        if (time == 1) {
            this.playSound(this.getSquishSound(), 1.5F, 0.5F);
        }
        if (time == SUMMONING_REVEAL_TICK) {
            this.playSound(this.getJumpSound(), 1.5F, 0.8F);
            this.spawnSummoningParticles();
        }
    }

    private void spawnSummoningParticles() {
        if (!(this.getWorld() instanceof ServerWorld serverWorld)) {
            return;
        }
        double width = this.getWidth();
        serverWorld.spawnParticles(ParticleTypes.ITEM_SLIME,
                this.getX(), this.getY() + this.getHeight() * 0.5, this.getZ(),
                100, width * 0.8, this.getHeight() * 0.4, width * 0.8, 0.05);
    }

    private void applySkill() {
        if(skillTick>0){
            skillTick--;
            if (skillStage!=0){
                if (skill == 0) {
                    trampleSkill();
                } else if (skill == 1) {
                    summonSkill();
                } else if (skill == 2) {
                    bulletSkill();
                } else if (skill == 3) {
                    dashSkill();
                }
            }
        }
        else if (skillStage==0){
            if (this.getTarget()==null){
                setCanJump(true);
                skillStage=0;
                return;
            }
            skill=this.random.nextInt(4);
            switch (skill) {
                case 0 -> skillTick = TRAMPLE_SKILL_DURATION;
                case 1 -> skillTick = SUMMON_SKILL_DURATION;
                case 2 -> skillTick = BULLET_SKILL_DURATION;
                case 3 -> skillTick = DASH_SKILL_DURATION;
            }
            skillStage++;
            this.dataTracker.set(ACTIVE_SKILL, skill);
        }
        else {
            setCanJump(true);
            skillTick=this.random.nextInt(10)+10;
            skillStage=0;
            skill=-999;
            this.dataTracker.set(ACTIVE_SKILL, -1);
        }
    }
    private void dashSkill(){
        if (this.getTarget() == null) {
            skillTick=0;
            return;
        }
        int time=-(skillTick-DASH_SKILL_DURATION);
        Vec3d direction;
        if (time==10){
            LivingEntity target = this.getTarget();
            this.targetPos = target.getPos();
        }
        if (time == 19) {
                this.playSound(SoundEvents.ENTITY_SLIME_ATTACK,2.0f,1.5f);
                direction =this.targetPos.subtract(this.getPos()).normalize();
                double dashSpeed = 5.0;
                this.setVelocity(direction.multiply(dashSpeed).add(0, this.getVelocity().y, 0));

        }
        if (time>19&&time<60){
                List<SlimeEntity> slimes = this.getWorld().getEntitiesByClass(
                        SlimeEntity.class,
                        this.getBoundingBox().expand(0.3),
                        SlimeEntity::isAlive
                );
                if (!slimes.isEmpty()) {
                    if (this.getHealth() < this.getMaxHealth()) {
                        this.heal(5.0F*slimes.get(0).getSize());
                        slimes.get(0).remove(RemovalReason.KILLED);
                    }
            }
            if (this.getWorld() instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(
                        ParticleTypes.CLOUD,
                        this.getX(), this.getY() + 0.5, this.getZ(),
                        8,
                        0.3, 0.2, 0.3,
                        0.1
                );
            }
        }
    }

    private void summonSkill() {
        if (this.getTarget() == null) {
            skillTick=0;
            return;
        }
        int time = -(skillTick - SUMMON_SKILL_DURATION);
        if (time >= 10 && time <= 14) {
            if (this.summonCount < 20) {
                this.playSound(SoundEvents.ENTITY_SLIME_ATTACK, 2.0F, 0.8F);
                SlimeEntity slime = new SlimeEntity(EntityType.SLIME, this.getWorld());
                slime.setSize(this.random.nextInt(4)+1, true);
                slime.setPos(this.getX(), this.getY() + 0.5, this.getZ());
                this.getWorld().spawnEntity(slime);
                this.summonCount++;
                if (this.getWorld() instanceof ServerWorld serverWorld) {
                    serverWorld.spawnParticles(
                            ParticleTypes.ITEM_SLIME,
                            this.getX(), this.getEyeY(), this.getZ(),
                            70,
                            1.5, 1.5, 1.5,
                            0
                    );
                }
            } else {
                LivingEntity target = this.getTarget();
                Vec3d pos = this.getPos().add(0, 1.0, 0);
                    MissileEntity missile = getMissileEntity(this, 16, 10);
                    Vec3d direction = target.getPos().add(0, 1.0, 0).subtract(pos).normalize();
                    double yaw = Math.toDegrees(Math.atan2(-direction.x, direction.z));
                    double pitch = Math.toDegrees(Math.atan2(-direction.y, Math.sqrt(direction.x * direction.x + direction.z * direction.z)));
                    missile.setPosition(pos);
                    missile.setYaw((float) yaw + this.random.nextInt(30) - 15);
                    missile.setPitch((float) pitch + this.random.nextInt(2) - 1);
                    this.getWorld().spawnEntity(missile);
                if (this.getWorld() instanceof ServerWorld serverWorld) {
                    serverWorld.spawnParticles(
                            ParticleTypes.ANGRY_VILLAGER,
                            this.getX(), this.getEyeY(), this.getZ(),
                            50,
                            1.5, 1.5, 1.5,
                            0
                    );
                }
            }
        }
    }

    private void trampleSkill(){
        if (this.getTarget()==null&&targetPos==null){
            skillTick=0;
            return;
        }
        int times=-(skillTick-TRAMPLE_SKILL_DURATION);
        int cycle=60;
        int time=times;
        List<? extends PlayerEntity> playerEntities=this.getWorld().getPlayers();
        while (time>=cycle){
            time=time%cycle;
        }
        if (time==20){
            LivingEntity target = this.getTarget();
            if (target != null) {
                targetPos = target.getPos();
            }
            this.setVelocity(0, 5, 0);
        }
        else if (time==40){
            Vec3d targetPos=this.targetPos;
            this.setPos(targetPos.x,targetPos.y+20,targetPos.z);
            this.setVelocity(this.getVelocity().add(this.getVelocity().x,-10,this.getVelocity().z));
        }
        else if (time==50){
            double range = 20;
            int count=20;
            for (int i = 0; i < count; i++) {
                float yaw = (float) (i * 360) /count;
                MissileEntity missile = getMissileEntity(this, range,40);
                missile.setPos(this.getX(), this.getY() + 1.0, this.getZ());
                missile.setYaw(yaw);
                missile.setPitch(0.0F);
                this.getWorld().spawnEntity(missile);
            }
            if (!playerEntities.isEmpty()){
                for (PlayerEntity playerEntity : playerEntities) {
                    if (playerEntity instanceof ServerPlayerEntity){
                        ScreenShakeNetworking.sendToPlayer((ServerPlayerEntity) playerEntity, 10, 0.3F);
                    }
                }
            }
            if (this.getWorld() instanceof ServerWorld serverWorld){
                FloorShakeNetworking.sendToAll(serverWorld,this.getBlockPos().down(1),5,1.0f,3.0f);
                BlockPos center = this.getBlockPos();
                int r = 5;
                for (int dx = -r; dx <= r; dx++) {
                    for (int dz = -r; dz <= r; dz++) {
                        if (dx * dx + dz * dz > r * r) continue;
                        BlockPos ground = center.add(dx, 0, dz);
                        while (ground.getY() > serverWorld.getBottomY()
                                && serverWorld.getBlockState(ground).isAir()) {
                            ground = ground.down();
                        }
                        BlockState state = serverWorld.getBlockState(ground);
                        if (state.isAir()) continue;
                        serverWorld.spawnParticles(
                                new BlockStateParticleEffect(ParticleTypes.BLOCK, state),
                                ground.getX() + 0.5, ground.getY() + 1.0, ground.getZ() + 0.5,
                                10,
                                0.5, 0.1, 0.5,
                                0.1
                        );
                    }
                }
            }
            this.playSound(SoundEvents.ENTITY_PLAYER_BIG_FALL, 1.0F, 1.0F);
            this.playSound(SoundEvents.ENTITY_SLIME_SQUISH,2.0f,0.5f);
        }
        this.fallDistance=0;
    }

    private void bulletSkill(){
        if (this.getTarget() == null) {
            skillTick=0;
            return;
        }
        int time=-(skillTick-BULLET_SKILL_DURATION);
        if (time>30&&time<100){
            this.playSound(SoundEvents.BLOCK_DISPENSER_LAUNCH, 1.0F, 0.5F);
            MissileEntity missile=getMissileEntity(this,this.random.nextInt(24)+8,this.random.nextInt(40)+20);
            LivingEntity target = this.getTarget();
            Vec3d pos = this.getPos().add(0, 1.0, 0);
                Vec3d direction = target.getPos().add(0, 1.0, 0).subtract(pos).normalize();
                double yaw = Math.toDegrees(Math.atan2(-direction.x, direction.z));
                double pitch = Math.toDegrees(Math.atan2(-direction.y, Math.sqrt(direction.x * direction.x + direction.z * direction.z)));
                missile.setPosition(pos);
                missile.setYaw((float) yaw+this.random.nextInt(30)-15);
                missile.setPitch((float) pitch+this.random.nextInt(2)-1);
                this.getWorld().spawnEntity(missile);
            if (this.getWorld() instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(
                        ParticleTypes.ANGRY_VILLAGER,
                        this.getX(), this.getEyeY(), this.getZ(),
                        1,
                        1.5, 1.5, 1.5,
                        0
                );
            }
        }
    }

    protected void updateStretch() {
        this.targetStretch *= 0.6F;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    protected void dropLoot(DamageSource damageSource, boolean causedByPlayer) {
        SpawnRandomSoulItems spawnRandomSoulItems = new SpawnRandomSoulItems();
        this.dropItem(ModItems.PROP_PROTOTYPE);
        this.dropItem(spawnRandomSoulItems.summonRandomSoulItem(this.getWorld()).getItem());
    }

    private @NotNull MissileEntity getMissileEntity(Entity owner, double range,int time) {
        AttackTypes bulletType = new AttackTypes(AttackTypes.AttackType.BULLET);
        MissileEntity missile= new MissileEntity(ModEntities.MISSILE,
                owner.getWorld(),
                owner,
                bulletType,
                false,
                range,
                time,
                this.getDamageAmount());
        missile.setMissileModelType(MissileModelType.BULLET);
        return missile;
    }

    protected ParticleEffect getParticles() {
        return ParticleTypes.ITEM_SLIME;
    }

    public void setCanJump(boolean canJump) {
        this.canJump=canJump;
        if (canJump) {
            jumpCount = 0;
        }
    }
    public boolean canJump() {
        return canJump;
    }

    @Override
    public void pushAwayFrom(Entity entity) {
        super.pushAwayFrom(entity);
        if (entity instanceof IronGolemEntity && this.canAttack()) {
            this.damage((LivingEntity) entity);
        }
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        if (this.canAttack()) {
            this.damage(player);
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isSummoning) {
            return false;
        }
        return super.damage(source, amount);
    }

    protected void damage(LivingEntity target) {
        if (this.isAlive()) {
            float reach = 3.6F; // 攻击范围
            if (this.squaredDistanceTo(target) < reach * reach
                    && this.canSee(target)
                    && target.damage(this.getDamageSources().mobAttack(this), this.getDamageAmount())) {
                this.playSound(SoundEvents.ENTITY_SLIME_ATTACK, 1.0F,
                        (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                this.applyDamageEffects(this, target);
            }
        }
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        List<? extends PlayerEntity> playerEntities=this.getWorld().getPlayers();
        if (!playerEntities.isEmpty()){
            for (PlayerEntity player : playerEntities) {
                player.removeStatusEffect(ModStatusEffects.BOSS_FIGHT);
            }
        }
        super.onDeath(damageSource);
    }

    protected boolean canAttack() {
        return this.canMoveVoluntarily();
    }

    protected float getDamageAmount() {
        return (float) this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SLIME_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SLIME_DEATH;
    }

    protected SoundEvent getSquishSound() {
        return SoundEvents.ENTITY_SLIME_SQUISH;
    }

    protected SoundEvent getJumpSound() {
        return SoundEvents.ENTITY_SLIME_JUMP;
    }

    protected boolean makesJumpSound() {
        return true;
    }

    protected float getJumpSoundPitch() {
        return ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * 0.8F;
    }

    @Override
    protected float getSoundVolume() {
        return 0.8F;
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (this.isLogicalSideForUpdatingMovement()) {
            if (this.isTouchingWater() || this.isInLava()) {
                this.setVelocity(this.getVelocity().multiply(0.98));
            }
            else if (!this.isOnGround()) {
                this.setVelocity(this.getVelocity().multiply(0.98, 1.0, 0.98));
            }
            else if (this.skillStage!=0){
                this.setVelocity(this.getVelocity().multiply(1.0, 0.98, 1.0));
            }
            super.travel(movementInput);
        }
        else {
            super.travel(movementInput);
        }
    }

    @Override
    public void jump() {
        if (!this.canJump) return;

        Vec3d currentPos = this.getPos();
        LivingEntity target = this.getTarget();
        if (target != null) {
            Vec3d targetPos = target.getPos();
            double dx = targetPos.x - currentPos.x;
            double dz = targetPos.z - currentPos.z;
            double horizontalDistance = Math.sqrt(dx * dx + dz * dz);
            Vec3d velocity = getVec3d(horizontalDistance, dx, dz);
            this.setVelocity(velocity);

        }
        else {
            Vec3d vec3d = this.getVelocity();
            this.setVelocity(vec3d.x, this.getJumpVelocity(), vec3d.z);
        }
        this.velocityDirty = true;
        this.jumpCount++;
    }


    private @NotNull Vec3d getVec3d(double horizontalDistance, double dx, double dz) {
        double jumpVelocity = (0.4 + horizontalDistance * 0.06)*2.2;

        double verticalVelocity = 0.7 + horizontalDistance * 0.02;
        if (horizontalDistance<3){verticalVelocity =horizontalDistance * 0.01;}
        verticalVelocity = Math.min(verticalVelocity, 0.5);
        verticalVelocity = Math.max(verticalVelocity, 0.2);

        return new Vec3d(
                dx / horizontalDistance * jumpVelocity,
                verticalVelocity*1.5,
                dz / horizontalDistance * jumpVelocity
        );
    }

    @Override
    protected float getJumpVelocity() {
        return 0.6F * this.getJumpVelocityMultiplier();
    }

    protected int getTicksUntilNextJump() {
        return this.random.nextInt(20) + 20;
    }

    static class SlimeMoveControl extends MoveControl {
        private float targetYaw;
        private int ticksUntilJump;
        private final HugeSlimeEntity slime;
        private boolean jumpOften;

        public SlimeMoveControl(HugeSlimeEntity slime) {
            super(slime);
            this.slime = slime;
            this.targetYaw = 180.0F * slime.getYaw() / (float) Math.PI;
        }

        public void look(float targetYaw, boolean jumpOften) {
            this.targetYaw = targetYaw;
            this.jumpOften = jumpOften;
        }

        public void move(double speed) {
            this.speed = speed;
            this.state = State.MOVE_TO;
        }

        @Override
        public void tick() {
            this.entity.setYaw(this.wrapDegrees(this.entity.getYaw(), this.targetYaw, 90.0F));
            this.entity.headYaw = this.entity.getYaw();
            this.entity.bodyYaw = this.entity.getYaw();

            if (this.state != State.MOVE_TO) {
                this.entity.setForwardSpeed(0.0F);
            } else {
                this.state = State.WAIT;
                if (this.entity.isOnGround()) {
                    this.entity.setMovementSpeed((float) (this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
                    if (this.ticksUntilJump-- <= 0) {
                        this.ticksUntilJump = this.slime.getTicksUntilNextJump();
                        if (this.jumpOften) {
                            this.ticksUntilJump /= 3;
                        }
                        this.slime.getJumpControl().setActive();
                        if (this.slime.makesJumpSound()) {
                            this.slime.playSound(this.slime.getJumpSound(), this.slime.getSoundVolume(), this.slime.getJumpSoundPitch());
                        }
                    } else {
                        this.slime.sidewaysSpeed = 0.0F;
                        this.slime.forwardSpeed = 0.0F;
                        this.entity.setMovementSpeed(0.0F);
                    }
                } else {
                    this.entity.setMovementSpeed((float) (this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
                }
            }
        }
    }

    static class SwimmingGoal extends Goal {
        private final HugeSlimeEntity slime;

        public SwimmingGoal(HugeSlimeEntity slime) {
            this.slime = slime;
            this.setControls(EnumSet.of(Control.JUMP, Control.MOVE));
            slime.getNavigation().setCanSwim(true);
        }

        @Override
        public boolean canStart() {

            return (this.slime.isTouchingWater() || this.slime.isInLava())
                    && this.slime.getMoveControl() instanceof SlimeMoveControl
                    && this.slime.canJump();
        }

        @Override
        public boolean shouldRunEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            if (this.slime.getRandom().nextFloat() < 0.8F) {
                this.slime.getJumpControl().setActive();
            }
            if (this.slime.getMoveControl() instanceof SlimeMoveControl slimeMoveControl) {
                slimeMoveControl.move(1.2);
            }
        }
    }

    static class FaceTowardTargetGoal extends Goal {
        private final HugeSlimeEntity slime;
        private int ticksLeft;

        public FaceTowardTargetGoal(HugeSlimeEntity slime) {
            this.slime = slime;
            this.setControls(EnumSet.of(Control.LOOK));
        }

        @Override
        public boolean canStart() {
            LivingEntity target = this.slime.getTarget();
            return target != null && this.slime.canTarget(target)
                    && this.slime.getMoveControl() instanceof SlimeMoveControl;
        }

        @Override
        public void start() {
            this.ticksLeft = toGoalTicks(300);
        }

        @Override
        public boolean shouldContinue() {
            LivingEntity target = this.slime.getTarget();
            return target != null && this.slime.canTarget(target) && --this.ticksLeft > 0;
        }

        @Override
        public boolean shouldRunEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity target = this.slime.getTarget();
            if (target != null) {
                this.slime.lookAtEntity(target, 10.0F, 10.0F);
            }
            if (this.slime.getMoveControl() instanceof SlimeMoveControl sc) {
                sc.look(this.slime.getYaw(), this.slime.canAttack());
            }
        }
    }

    static class RandomLookGoal extends Goal {
        private final HugeSlimeEntity slime;
        private float targetYaw;
        private int timer;

        public RandomLookGoal(HugeSlimeEntity slime) {
            this.slime = slime;
            this.setControls(EnumSet.of(Control.LOOK));
        }

        @Override
        public boolean canStart() {
            return this.slime.getTarget() == null
                    && (this.slime.isOnGround() || this.slime.isTouchingWater()
                    || this.slime.isInLava() || this.slime.hasStatusEffect(StatusEffects.LEVITATION))
                    && this.slime.getMoveControl() instanceof SlimeMoveControl;
        }

        @Override
        public void tick() {
            if (--this.timer <= 0) {
                this.timer = this.getTickCount(40 + this.slime.getRandom().nextInt(60));
                this.targetYaw = this.slime.getRandom().nextInt(360);
            }
            if (this.slime.getMoveControl() instanceof SlimeMoveControl sc) {
                sc.look(this.targetYaw, false);
            }
        }
    }

    static class MoveGoal extends Goal {
        private final HugeSlimeEntity slime;

        public MoveGoal(HugeSlimeEntity slime) {
            this.slime = slime;
            this.setControls(EnumSet.of(Control.JUMP, Control.MOVE));
        }

        @Override
        public boolean canStart() {
            return !this.slime.hasVehicle()&& this.slime.canJump();
        }

        @Override
        public void tick() {
            if (this.slime.getMoveControl() instanceof SlimeMoveControl sc) {
                sc.move(1.0);
            }
        }
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.625F * dimensions.height;
    }

    @Override
    public int getMaxLookPitchChange() {
        return 0;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ACTIVE_SKILL, -1);
        this.dataTracker.startTracking(SUMMONING_STATE, 0);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("SummonCount", this.summonCount);
        nbt.putBoolean("SummoningChecked", this.summoningChecked);
        nbt.putBoolean("IsSummoning", this.isSummoning);
        nbt.putInt("SummoningTick", this.summoningTick);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.summonCount = nbt.getInt("SummonCount");
        this.summoningChecked = nbt.getBoolean("SummoningChecked");
        this.isSummoning = nbt.getBoolean("IsSummoning");
        this.summoningTick = nbt.getInt("SummoningTick");
        if (this.isSummoning) {
            this.dataTracker.set(SUMMONING_STATE, 1);
        }

        if (this.hasCustomName()) {
            this.bossBar.setName(this.getDisplayName());
        }
    }

    @Override
    public void setCustomName(@Nullable Text name) {
        super.setCustomName(name);
        this.bossBar.setName(this.getDisplayName());
    }
    @Override
    protected boolean canStartRiding(Entity entity) {
        return false;
    }
}