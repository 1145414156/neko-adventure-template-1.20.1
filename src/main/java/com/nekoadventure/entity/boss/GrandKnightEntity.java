package com.nekoadventure.entity.boss;

import com.nekoadventure.effect.ModStatusEffects;
import com.nekoadventure.entity.ModEntities;
import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.entity.missile.MissileModelType;
import com.nekoadventure.network.FloorShakeNetworking;
import com.nekoadventure.network.ScreenShakeNetworking;
import com.nekoadventure.other.attackApart.AttackTypes;
import com.nekoadventure.sound.ModSoundEvents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
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
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;


public class GrandKnightEntity extends HostileEntity implements Monster {

    // ========== 动画状态 ==========
    @Environment(EnvType.CLIENT)
    public static final AnimationState GRAB_ANI = new AnimationState();
    private static final int GRAB_DURATION = 125;

    @Environment(EnvType.CLIENT)
    public static final AnimationState SHIELD_SMASH_ANI = new AnimationState();
    private static final int SHIELD_SMASH_DURATION = 85;

    @Environment(EnvType.CLIENT)
    public static final AnimationState SHIELD_SLAP_ANI = new AnimationState();
    private static final int SHIELD_SLAP_DURATION = 60;

    @Environment(EnvType.CLIENT)
    public static final AnimationState EARTH_SHAKER_ANI = new AnimationState();
    private static final int EARTH_SHAKER_DURATION = 50;

    @Environment(EnvType.CLIENT)
    public static final AnimationState BRIMSTONE_ANI = new AnimationState();
    private static final int BRIMSTONE_DURATION = 70;

    @Environment(EnvType.CLIENT)
    public static final AnimationState RAPID_SLASHES_ANI = new AnimationState();
    private static final int RAPID_SLASHES_DURATION = 70;

    @Environment(EnvType.CLIENT)
    public static final AnimationState DELAY_BULLET_ANI = new AnimationState();
    private static final int DELAY_BULLET_DURATION = 50;

    @Environment(EnvType.CLIENT)
    public static final AnimationState ATTACK_ANI = new AnimationState();
    private static final int ATTACK_DURATION = 60;

    private int skillTick = 0;
    private int skillStage = 0;
    private int skill = -114514;
    private LivingEntity target;

    private static final TrackedData<Boolean> ATTACKING = DataTracker.registerData(GrandKnightEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    // ========== BossBar ==========
    private final ServerBossBar bossBar = new ServerBossBar(this.getDisplayName(), BossBar.Color.PURPLE, BossBar.Style.PROGRESS);

    //=========== 阶段2切换 ===========
    private int stage=1;
    private static final TrackedData<Integer> STAGE =
            DataTracker.registerData(GrandKnightEntity.class, TrackedDataHandlerRegistry.INTEGER);

    @Environment(EnvType.CLIENT)
    public static final AnimationState STAGE_2_START_APART_ANI =new AnimationState();
    @Environment(EnvType.CLIENT)
    public static final AnimationState STAGE_2_STOP_APART_ANI =new AnimationState();

    private boolean isChangingStage=false;
    private int changingTime=0;


    public GrandKnightEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 50;
    }

    // ========== 基础属性（自行设置数值） ==========
    public static DefaultAttributeContainer.Builder createGrandKnightAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 800.00)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
                .add(EntityAttributes.GENERIC_ARMOR, 10.0D)
                .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 8.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 14.0D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 42.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D);
    }

    // ========== AI Goals ==========
    @Override
    protected void initGoals() {
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, false, false,
                livingEntity -> Math.abs(livingEntity.getY() - this.getY()) <= 8.0));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, IronGolemEntity.class, false));

        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.goalSelector.add(5, new WanderAroundGoal(this, 0.8D));
    }

    @Override
    public void tick() {
        super.tick();
        this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());

        if (skillStage != 0&&skill!=6) {
            LivingEntity target = this.getTarget();
            if (target != null) {
                this.lookAtEntity(target, 360.0F, 360.0F);
            }
        }
        // 0.盾推，1.盾扫，2.震地，3.喷射，4.连斩，5.延迟弹幕，6.抓取，7.剑扫
        if ((skillStage != 0 &&skill!=1&&skill!=4&&skill!=7)||isChangingStage) {
            this.setVelocity(0, this.getVelocity().y, 0);
        }
        this.applyBulletEffect();
        this.changeStage();
        if (!this.isChangingStage) {
            this.applySkill();
        }
    }

    private void changeStage() {
        if (skill==-114514) {
            if (this.getHealth()<=this.getMaxHealth()/2 && getStage()==1 && changingTime==0){
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE,5*20,100,false,false,false));
               isChangingStage=true;
               if (!STAGE_2_START_APART_ANI.isRunning()){
                   STAGE_2_START_APART_ANI.start(this.age);
               }
               changingTime++;
            }
            else if (changingTime>0&&changingTime<100){
                changingTime++;
                if (changingTime==20){
                    this.setStage(2);
                    this.stage=2;
                    STAGE_2_START_APART_ANI.stop();
                    if (!STAGE_2_STOP_APART_ANI.isRunning()){
                        STAGE_2_STOP_APART_ANI.start(this.age);
                    }
                }
            }
            else if (changingTime==100){
                STAGE_2_STOP_APART_ANI.stop();
                isChangingStage=false;
            }
        }
    }

    private void applyBulletEffect(){
        List<MissileEntity> bullets=this.getWorld().getEntitiesByClass(
                MissileEntity.class,
                this.getBoundingBox().expand(64),
                e->{if (e.getOwner()!=null){return e.getOwner().equals(this);}
                    return false;
                }
        );

        if (!bullets.isEmpty()){
            for (MissileEntity bullet:bullets){

                //技能0的特殊弹幕
                if (bullet.getSkillType()==0&&bullet.getAliveDuration()==bullet.getTime()/2){
                        for (int i = 0; i < 2; i++) {
                            MissileEntity missile = getMissileEntity(this, 32, 200);
                            missile.setPitch(0);
                            missile.setPosition(bullet.getPos());
                            missile.setYaw(bullet.getYaw() - 90+180*i);
                            this.getWorld().spawnEntity(missile);
                        }
                }
                //技能4的特殊弹幕
                if (bullet.getSkillType()==4&&bullet.getAliveDuration()%5==0){
                        MissileEntity missile = getMissileEntity(this, 32, 200);
                        missile.setUuid(UUID.randomUUID());
                        if (this.getTarget() != null) {
                            Vec3d direction = this.getTarget().getPos().add(0, 1.0, 0).subtract(bullet.getPos()).normalize();
                            double yaw = Math.toDegrees(Math.atan2(-direction.x, direction.z));
                            missile.setYaw((float) yaw + this.random.nextInt(30) - 15);
                            missile.setPitch(0);
                            missile.setPosition(bullet.getPos());
                            this.getWorld().spawnEntity(missile);
                        }
                }
                //技能5的特殊弹幕
                if (bullet.getSkillType()==5&&bullet.getAliveDuration()%40==0&&bullet.getAliveDuration()>bullet.getTime()/2){
                    if (this.getTarget() != null) {
                        for (int i=0;i<8;i++) {
                            MissileEntity missile = getMissileEntity(this, 32, 200);
                            missile.setYaw(45*i);
                            missile.setPitch(0);
                            missile.setPosition(bullet.getPos());
                            this.getWorld().spawnEntity(missile);
                        }
                    }
                }
            }
        }

    }

    private void applySkill() {
        if (skillTick > 0) {
            skillTick--;
            if (skillStage != 0) {
                if (skill == 0) {
                    shieldSmashSkill();
                } else if (skill == 1) {
                    shieldSlapSkill();
                } else if (skill == 2) {
                    earthShakerSkill();
                } else if (skill == 3) {
                    brimstoneSkill();
                } else if (skill==4){
                    rapidSlashesSkill();
                } else if (skill==5){
                    delayBulletSkill();
                } else if (skill==6){
                    grabSkill();
                }else if (skill==7){
                    attackSkill();
                }
            }
        } else {
            if (skillStage == 0) {
                if (!ATTACK_ANI.isRunning()) {
                    if (this.getTarget() == null) {
                        skillTick = this.random.nextInt(20) + 20;
                        skillStage = 0;
                        return;
                    }
                    skill = this.random.nextInt(4);
                    if (getStage() == 1) {
                        if (skill == 3) {
                            skill = 6;
                        }
                        if (isTargetInRange(3)&&this.random.nextInt(3)<2) {
                            skill=1;
                        }
                    } else {
                        skill = this.random.nextInt(6);
                        if (isTargetInRange(5)&&this.random.nextInt(4)<3&&this.getStage()==2){
                            skill=7;
                        }
                    }
                    // 0.盾推，1.盾扫，2.震地，3.喷射，4.连斩，5.延迟弹幕，6.抓取
                    switch (skill) {
                        case 0 -> {
                            skillTick = SHIELD_SMASH_DURATION;
                            SHIELD_SMASH_ANI.startIfNotRunning(this.age);
                        }
                        case 1 -> {
                            skillTick = SHIELD_SLAP_DURATION;
                            SHIELD_SLAP_ANI.startIfNotRunning(this.age);
                        }
                        case 2 -> {
                            skillTick = EARTH_SHAKER_DURATION;
                            EARTH_SHAKER_ANI.startIfNotRunning(this.age);
                        }
                        case 3 ->{
                            skillTick=BRIMSTONE_DURATION;
                            BRIMSTONE_ANI.startIfNotRunning(this.age);
                        }
                        case 4 ->{
                            skillTick=RAPID_SLASHES_DURATION;
                            RAPID_SLASHES_ANI.startIfNotRunning(this.age);
                        }
                        case 5->{
                            skillTick=DELAY_BULLET_DURATION;
                            DELAY_BULLET_ANI.startIfNotRunning(this.age);
                        }
                        case 6->{
                            skillTick=GRAB_DURATION;
                            GRAB_ANI.startIfNotRunning(this.age);
                        }
                        case 7->{
                            skillTick=ATTACK_DURATION;
                            ATTACK_ANI.startIfNotRunning(this.age);
                        }
                    }
                    skillStage++;
                }
            }
            else {
                skillTick = this.random.nextInt(40) + 40;
                if (stage==2){
                    skillTick=this.random.nextInt(20)+20;
                }
                if (ATTACK_ANI.isRunning()){
                    skillTick=this.random.nextInt(30)+10;
                }
                skill=-114514;
                skillStage = 0;
                target=null;
                GRAB_ANI.stop();
                SHIELD_SMASH_ANI.stop();
                SHIELD_SLAP_ANI.stop();
                EARTH_SHAKER_ANI.stop();
                BRIMSTONE_ANI.stop();
                RAPID_SLASHES_ANI.stop();
                DELAY_BULLET_ANI.stop();
                ATTACK_ANI.stop();
            }
        }
    }


    @Override
    public boolean damage(DamageSource source, float amount) {
        if(STAGE_2_START_APART_ANI.isRunning()||STAGE_2_STOP_APART_ANI.isRunning()){
            return false;
        }
        else {
        return super.damage(source, amount);
        }
    }


    // ========== 技能方法 ==========

    private void shieldSmashSkill() {
        if (this.getTarget() == null) {return;}
        int time=-(skillTick-SHIELD_SMASH_DURATION);
        if(time==15){
            for (int i=0;i<3;i++){
                this.playSound(SoundEvents.BLOCK_DISPENSER_LAUNCH,1.0f,0.5f);
                MissileEntity bullet=getMissileEntity(this,24,100);
                bullet.setYaw(this.getYaw()-30+30*i);
                bullet.setPitch(0);
                bullet.setPosition(this.getEyePos().add(0,-2,0));
                this.getWorld().spawnEntity(bullet);
                this.damageEntitiesInFront(4,2,2,this.getDamageAmount());
            }
        }
        else if (time==35){
            for (int i=0;i<6;i++){
                this.playSound(SoundEvents.BLOCK_DISPENSER_LAUNCH,1.0f,0.5f);
                MissileEntity bullet=getMissileEntity(this,24,100);
                bullet.setYaw(this.getYaw()-60+24*i);
                bullet.setPitch(0);
                bullet.setPosition(this.getEyePos().add(0,-2,0));
                this.getWorld().spawnEntity(bullet);
                this.damageEntitiesInFront(4,2,2,this.getDamageAmount());
            }
        }
        else if (time==70){
            for (int i=0;i<7;i++){
                this.playSound(SoundEvents.BLOCK_DISPENSER_LAUNCH,1.0f,0.5f);
                MissileEntity bullet=getMissileEntity(this,24,100,0);
                bullet.setYaw(this.getYaw()-60+20*i);
                bullet.setPitch(0);
                bullet.setPosition(this.getEyePos().add(0,-2,0));
                this.getWorld().spawnEntity(bullet);
                this.damageEntitiesInFront(6,5,2,this.getDamageAmount());
            }
        }

    }
    private void shieldSlapSkill() {
        if (this.getTarget() == null) {return;}
        int time=-(skillTick-SHIELD_SLAP_DURATION);
        if (time==30){
            for (int i=0;i<=9;i++){
                this.playSound(SoundEvents.BLOCK_DISPENSER_LAUNCH,1.0f,0.5f);
                MissileEntity bullet=getMissileEntity(this,16,40);
                bullet.setPosition(this.getEyePos().add(0,-2,0));
                if (i==9){
                    bullet=getMissileEntity(this,36,40);
                    bullet.setPosition(this.getEyePos().add(0,-2,0));
                    bullet.setAttackType(new AttackTypes(AttackTypes.AttackType.DELAY));
                }
                bullet.setYaw(this.getYaw()-40+10*i);
                bullet.setPitch(0);
                this.getWorld().spawnEntity(bullet);

                this.damageEntitiesInFront(6,4,4,this.getDamageAmount());
            }
            this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 2.0F, 0.2F);
        }
    }

    private void earthShakerSkill() {
        if (this.getTarget() == null) {return;}
        int time=-(skillTick-EARTH_SHAKER_DURATION);
        if (time==15){
            List<PlayerEntity> playerEntities=this.getWorld().getEntitiesByClass(
                    PlayerEntity.class,
                    this.getBoundingBox().expand(12+getStage()*4),
                    e-> true
            );
            if (!playerEntities.isEmpty()){
                this.playSound(ModSoundEvents.ENTITY_GRAND_KNIGHT_EARTH_QUAKER,10.0f,1.0f);
                for (PlayerEntity playerEntity : playerEntities) {
                    if (playerEntity instanceof ServerPlayerEntity){
                        ScreenShakeNetworking.sendToPlayer((ServerPlayerEntity) playerEntity, 10, 0.2F);
                    }
                }
                if (!this.getWorld().isClient) {
                    for (PlayerEntity playerEntity : playerEntities) {
                        if (playerEntity.isOnGround()) {
                            Vec3d pullDirection = this.getEyePos().subtract(playerEntity.getPos()).normalize();
                            double pullStrength = 3.0;
                            this.getTarget().addVelocity(pullDirection.multiply(pullStrength));
                            this.getTarget().move(MovementType.SELF, playerEntity.getVelocity());
                            DamageSource damageSource = this.getDamageSources().mobProjectile(this,this);
                            if (this.getTarget() != null) {
                                this.getTarget().damage(damageSource,getDamageAmount());
                                if (this.getStage()==2){
                                    playerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS,5*20,1));
                                }
                            }
                        }
                    }
                }
            }
            if (this.getWorld() instanceof ServerWorld serverWorld){
                FloorShakeNetworking.sendToAll(serverWorld,this.getBlockPos().down(1),12+getStage()*4,1.0f,4.0f);
            }
        }
    }

    private void brimstoneSkill() {
        if (this.getTarget() == null) {return;}
        int time=-(skillTick-BRIMSTONE_DURATION);
        if (time==30){
            int range=12;
            double spacing = 1.0;
            double cubeSize = 0.5;
            for (int length = 1; length < range; length++) {
                Vec3d mobPos = this.getEyePos().add(0,-1.5,0);
                Vec3d lookDirection = this.getRotationVec(1.0F);
                MissileEntity missile=getMissileEntity(this,10,40);
                AttackTypes brimstoneType = new AttackTypes(AttackTypes.AttackType.BRIMSTONE);
                missile.setAttackType(brimstoneType);
                missile.setTreadable(true);
                missile.setMissileModelType(MissileModelType.BRIMSTONE);
                Vec3d spawnPos = mobPos.add(lookDirection.multiply(length * spacing - cubeSize / 2));
                missile.setPos(spawnPos.x, spawnPos.y, spawnPos.z);
                missile.setYaw(this.getYaw());
                missile.setPitch(this.getPitch());
                this.getWorld().spawnEntity(missile);
                this.getWorld().playSound(null, this.getBlockPos(), ModSoundEvents.BRIMSTONE_ATTACK,
                        SoundCategory.HOSTILE, 0.1F, 1.3F);
            }
        }
    }
    private void rapidSlashesSkill() {
        if (this.getTarget() == null) {return;}
        int time = -(skillTick - RAPID_SLASHES_DURATION);
        Vec3d targetPos;
        if (time==1){
            this.playSound(ModSoundEvents.ENTITY_GRAND_KNIGHT_RAPID_ATTACK,10.0f,1.0f);
        }
        if (time== 32){
            if (isTargetInRange(12)){
                targetPos =this.getTarget().getPos();
                if (targetPos!=null){
                    this.teleport(targetPos.x, targetPos.y, targetPos.z);
                }
            }
        }
        if (time == 42) {
            this.playSound(ModSoundEvents.ENTITY_GRAND_KNIGHT_SWORD_ATTACK,10.0f,1.0f);
            this.damageEntitiesInFront(3,2,2,this.getDamageAmount());
            MissileEntity missile=getMissileEntity(this,32,100,4);
            missile.setYaw(getYaw());
            missile.setPitch(0);
            missile.setPosition(this.getEyePos().add(0,-1.5,0));
            this.getWorld().spawnEntity(missile);
        }
        if (time== 42){
            if (isTargetInRange(12)){
                targetPos =this.getTarget().getPos();
                if (targetPos!=null){
                    this.teleport(targetPos.x, targetPos.y, targetPos.z);
                }
            }
        }
        if (time == 54) {
            this.playSound(ModSoundEvents.ENTITY_GRAND_KNIGHT_SWORD_ATTACK,10.0f,1.0f);
            this.damageEntitiesInFront(3,2,2,this.getDamageAmount());
            MissileEntity missile=getMissileEntity(this,32,100,4);
            missile.setYaw(getYaw());
            missile.setPitch(0);
            missile.setPosition(this.getEyePos().add(0,-1.5,0));
            this.getWorld().spawnEntity(missile);
        }
    }

    private void delayBulletSkill(){
        if (this.getTarget() == null) {return;}
        int time=-(skillTick-DELAY_BULLET_DURATION);
        if (time==1){
            this.playSound(ModSoundEvents.ENTITY_GRAND_KNIGHT_DELAY_BULLET,10.0f,1.0f);
        }
        if (time<25){
            if (time%4==0){
                MissileEntity missile=getMissileEntity(this,36,200,5);
                missile.setAttackType(new AttackTypes(AttackTypes.AttackType.DELAY));
                missile.setUuid(UUID.randomUUID());
                missile.setPitch(0);
                missile.setYaw(this.getYaw());
                missile.setPosition(this.getEyePos().add((this.random.nextInt(80)+1)*0.1-4,-1.5,(this.random.nextInt(80)+1)*0.1-4));
                this.getWorld().spawnEntity(missile);
                this.playSound(SoundEvents.BLOCK_DISPENSER_LAUNCH,1.0f,0.5f);
                ParticleEffect particle=ParticleTypes.LAVA;
                if (getWorld().isClient) {
                    getWorld().addParticle(particle, missile.getX(),missile.getY(), missile.getZ(), 0, 0, 0);
                } else {
                    ((ServerWorld) getWorld()).spawnParticles(particle,
                            missile.getX(),missile.getY(), missile.getZ(), 1, 0, 0, 0, speed);
                }
            }
        }
    }
    private void grabSkill(){
        if (this.getTarget() == null) {return;}
        int time=-(skillTick-GRAB_DURATION);
        if (time<40){
            this.setVelocity(0,0,0);
        }
        if(time>45&&time<50) {
            Vec3d direction = this.getTarget().getPos().subtract(this.getPos()).normalize();
            double speed = 1.5;
            this.setVelocity(this.getVelocity().x + direction.x * speed, this.getVelocity().y, this.getVelocity().z + direction.z * speed);
            this.move(MovementType.SELF, this.getVelocity());
            List<LivingEntity> entities = this.getWorld().getNonSpectatingEntities(
                    LivingEntity.class,
                    this.getBoundingBox().expand(0.5)
            );
            for (LivingEntity entity : entities) {
                if (entity != this && entity.isAlive()) {
                    target = entity;
                    break;
                }
            }
        }
        if (time > 50 && time < 115) {
            if (target != null) {
                Vec3d lookVector = this.getRotationVector();
                Vec3d forwardVector = lookVector.normalize();
                Vec3d catchPos = this.getEyePos().add(forwardVector.multiply(2.5));
                target.teleport(catchPos.x, catchPos.y - 1.5, catchPos.z);
                if (!target.isOnGround() && time % 5 == 0) {
                    DamageSource damageSource = this.getDamageSources().mobProjectile(this, this);
                    target.damage(damageSource, getDamageAmount()/2);
                }
            }
            else {
                skillTick = 0;
            }
        }
        if (time==115){
            Vec3d pullDirection = this.getPos().subtract(getTarget().getPos()).normalize();
            double pullStrength = -2.0;
           target.addVelocity(pullDirection.multiply(pullStrength));
           target.addVelocity(this.getVelocity().x, this.getVelocity().y+1.0, this.getVelocity().z);
           target.move(MovementType.SELF, this.getTarget().getVelocity());
            DamageSource damageSource = this.getDamageSources().mobProjectile(this, this);
            target.damage(damageSource, getDamageAmount());
        }
    }

    private void attackSkill(){
        if (this.getTarget() == null) {return;}
        int time=-(skillTick-ATTACK_DURATION);
        if (time==23){
            float lostHealth=this.getMaxHealth()-this.getTarget().getHealth();
            this.damageEntitiesInFront(4.0, 3.0, 3.0, (float) (this.getDamageAmount()+lostHealth*0.01));
            this.playSound(ModSoundEvents.ENTITY_GRAND_KNIGHT_SWORD_ATTACK,10.0f,1.0f);
        }
        if (time==36){
            float lostHealth=this.getMaxHealth()-this.getTarget().getHealth();
            this.damageEntitiesInFront(4.0, 3.0, 3.0, (float) (this.getDamageAmount()+lostHealth*0.01));
            this.playSound(ModSoundEvents.ENTITY_GRAND_KNIGHT_SWORD_ATTACK,10.0f,1.0f);
        }
    }

    private boolean isTargetInRange(double range) {
        LivingEntity target = this.getTarget();
        return target != null && this.squaredDistanceTo(target) <= range * range;
    }

    private @NotNull MissileEntity getMissileEntity(Entity owner, double range, int time) {
        AttackTypes bulletType = new AttackTypes(AttackTypes.AttackType.BULLET);
        return new MissileEntity(ModEntities.MISSILE,
                owner.getWorld(),
                owner,
                bulletType,
                false,
                range,
                time,
                this.getDamageAmount());
    }
    private @NotNull MissileEntity getMissileEntity(Entity owner, double range, int time,int skillType) {
        AttackTypes bulletType = new AttackTypes(AttackTypes.AttackType.BULLET);
        return new MissileEntity(ModEntities.MISSILE,
                owner.getWorld(),
                owner,
                bulletType,
                skillType,
                range,
                time,
                this.getDamageAmount());
    }

    private void damageEntitiesInFront(double range, double width, double height, float damage) {
        if (this.getWorld().isClient) return;
        Vec3d eyePos = this.getEyePos();
        Vec3d lookVec = this.getRotationVec(1.0F);
        Vec3d horizontalLook = new Vec3d(lookVec.x, 0, lookVec.z).normalize();
        if (horizontalLook.lengthSquared() < 1.0E-6) return;
        Vec3d leftVec = new Vec3d(-horizontalLook.z, 0, horizontalLook.x);
        Box searchBox = this.getBoundingBox().expand(range, height / 2.0, range);
        List<LivingEntity> entities = this.getWorld().getNonSpectatingEntities(LivingEntity.class, searchBox);
        for (LivingEntity target : entities) {
            if (target == this || !target.isAlive()) continue;
            Vec3d toTarget = target.getPos().subtract(eyePos);
            Vec3d horizontalToTarget = new Vec3d(toTarget.x, 0, toTarget.z);
            double horizontalDist = horizontalToTarget.length();
            if (horizontalDist > range || horizontalDist < 0.1) {
                continue;
            }
            double dotProduct = horizontalToTarget.dotProduct(horizontalLook);
            if (dotProduct < 0.1) {
                continue;
            }
            double lateral = Math.abs(horizontalToTarget.dotProduct(leftVec));
            if (lateral > width / 2) {
                continue;
            }
            double dy = target.getY() - this.getY();
            if (Math.abs(dy) > height / 2) {
                continue;
            }
            target.damage(this.getDamageSources().mobAttack(this), damage);
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

    //==================音效=======================
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.ENTITY_GRAND_KNIGHT_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSoundEvents.ENTITY_GRAND_KNIGHT_HURT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(ModSoundEvents.ENTITY_GRAND_KNIGHT_WALK, 0.15F, 1.0F);
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.ENTITY_GRAND_KNIGHT_DEATH;
    }

    // ========== BossBar ==========
    @Override
    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);
        this.bossBar.addPlayer(player);
    }

    @Override
    public void setCustomName(@Nullable Text name) {
        super.setCustomName(name);
        this.bossBar.setName(this.getDisplayName());
    }

    @Override
    public boolean isCustomNameVisible() {
        return false;
    }

    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        this.bossBar.removePlayer(player);
    }

    // ========== NBT 持久化 ==========
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("SkillTick", this.skillTick);
        nbt.putInt("SkillStage", this.skillStage);
        nbt.putInt("Skill", this.skill);
        nbt.putInt("stage",this.stage);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.skillTick = nbt.getInt("SkillTick");
        this.skillStage = nbt.getInt("SkillStage");
        this.skill = nbt.getInt("Skill");
        this.stage = nbt.getInt("stage");

        if (this.hasCustomName()) {
            this.bossBar.setName(this.getDisplayName());
        }
    }
    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACKING, false);
        this.dataTracker.startTracking(STAGE,1);
    }
    protected float getDamageAmount() {
        return (float) this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
    }

    public void setStage(int type) {
        this.dataTracker.set(STAGE, type);
    }
    public int getStage() {
        return this.dataTracker.get(STAGE);
    }
}