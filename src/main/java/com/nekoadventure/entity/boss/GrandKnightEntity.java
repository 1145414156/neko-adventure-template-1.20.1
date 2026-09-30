package com.nekoadventure.entity.boss;

import com.nekoadventure.block.ModBlocks;
import com.nekoadventure.effect.ModStatusEffects;
import com.nekoadventure.entity.ModEntities;
import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.entity.missile.MissileModelType;
import com.nekoadventure.item.ModItems;
import com.nekoadventure.network.mob.FloorShakeNetworking;
import com.nekoadventure.network.mob.ScreenShakeNetworking;
import com.nekoadventure.other.attackApart.AttackTypes;
import com.nekoadventure.other.itemApart.SpawnRandomSoulItems;
import com.nekoadventure.sound.ModSoundEvents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;


public class GrandKnightEntity extends HostileEntity implements Monster {

    // ========== 动画状态 ==========
    @Environment(EnvType.CLIENT)
    public final AnimationState GRAB_ANI = new AnimationState();
    private static final int GRAB_DURATION = 125;

    @Environment(EnvType.CLIENT)
    public final AnimationState SHIELD_SMASH_ANI = new AnimationState();
    private static final int SHIELD_SMASH_DURATION = 85;

    @Environment(EnvType.CLIENT)
    public final AnimationState SHIELD_SLAP_ANI = new AnimationState();
    private static final int SHIELD_SLAP_DURATION = 60;

    @Environment(EnvType.CLIENT)
    public final AnimationState EARTH_SHAKER_ANI = new AnimationState();
    private static final int EARTH_SHAKER_DURATION = 50;

    @Environment(EnvType.CLIENT)
    public final AnimationState BRIMSTONE_ANI = new AnimationState();
    private static final int BRIMSTONE_DURATION = 70;

    @Environment(EnvType.CLIENT)
    public final AnimationState RAPID_SLASHES_ANI = new AnimationState();
    private static final int RAPID_SLASHES_DURATION = 70;

    @Environment(EnvType.CLIENT)
    public final AnimationState DELAY_BULLET_ANI = new AnimationState();
    private static final int DELAY_BULLET_DURATION = 50;

    @Environment(EnvType.CLIENT)
    public final AnimationState ATTACK_ANI = new AnimationState();
    private static final int ATTACK_DURATION = 60;
    @Environment(EnvType.CLIENT)
    public final AnimationState STAGE_2_START_APART_ANI = new AnimationState();
    @Environment(EnvType.CLIENT)
    public final AnimationState STAGE_2_STOP_APART_ANI = new AnimationState();

    private static final int SUMMON_DURATION=180;
    @Environment(EnvType.CLIENT)
    public final AnimationState SUMMON_ANI=new AnimationState();

    // ========== 生成召唤 ==========
    // SUMMON_STATE：0=无 1=召唤动画播放中（服务端广播，客户端收到后播放 SUMMON_ANI）
    private static final TrackedData<Integer> SUMMON_STATE =
            DataTracker.registerData(GrandKnightEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private int playedSummonState = 0;
    private boolean isSummoning = false;
    private int summonTick = 0;
    private boolean summonChecked = false;

    // 技能轴状态
    private int skillTick = 0;
    private int skillStage = 0;
    private int skill = -114514;
    private LivingEntity target;

    // 服务端 -> 客户端 的动作同步字段
    // ACTIVE_SKILL：当前技能编号（0.盾推 1.盾扫 2.震地 3.喷射 4.连斩 5.延迟弹幕 6.抓取 7.剑扫），-1=空闲
    private static final TrackedData<Integer> ACTIVE_SKILL =
            DataTracker.registerData(GrandKnightEntity.class, TrackedDataHandlerRegistry.INTEGER);
    // STAGE_CHANGE：0=无 1=二阶段起手 2=二阶段成型
    private static final TrackedData<Integer> STAGE_CHANGE =
            DataTracker.registerData(GrandKnightEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private int playedSkill = -1;
    private int playedStageChange = 0;

    // ========== BossBar ==========
    private final ServerBossBar bossBar = new ServerBossBar(this.getDisplayName(), BossBar.Color.PURPLE, BossBar.Style.PROGRESS);

    //=========== 阶段2切换 ===========
    private int stage=1;
    private static final TrackedData<Integer> STAGE =
            DataTracker.registerData(GrandKnightEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private boolean isChangingStage=false;
    private int changingTime=0;


    public GrandKnightEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 50;
    }

    // ========== 基础属性 ==========
    public static DefaultAttributeContainer.Builder createGrandKnightAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1000.0)
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
        if (this.getWorld().isClient) {
            this.syncClientAnimations();
            return;
        }
        this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());

        // 生成召唤：先推进技能轴再做生成检测，触发当 tick 不推进轴（与技能起手一致）
        this.applySummon();
        this.checkSummonOnSpawn();

        List<? extends PlayerEntity> players=this.getWorld().getPlayers();
        if (!players.isEmpty()){
            players.forEach(p-> p.addStatusEffect(new StatusEffectInstance(ModStatusEffects.BOSS_FIGHT,40,0,false,false,true)));
        }

        if (skillStage != 0&&skill!=6) {
            LivingEntity target = this.getTarget();
            if (target != null) {
                this.lookAtEntity(target, 360.0F, 360.0F);
            }
        }

        // 0.盾推，1.盾扫，2.震地，3.喷射，4.连斩，5.延迟弹幕，6.抓取，7.剑扫
        if ((skillStage != 0 &&skill!=1&&skill!=4&&skill!=7)||isChangingStage||isSummoning) {
            this.setVelocity(0,0, 0);
        }
        this.applyBulletEffect();
        this.changeStage();
        if (!this.isChangingStage && !this.isSummoning) {
            this.applySkill();
        }
    }

    // 把服务端广播的 ACTIVE_SKILL / STAGE_CHANGE 翻译成实例动画的 start/stop（仅客户端调用）
    private void syncClientAnimations() {
        int activeSkill = this.dataTracker.get(ACTIVE_SKILL);
        if (activeSkill != this.playedSkill) {
            GRAB_ANI.stop();
            SHIELD_SMASH_ANI.stop();
            SHIELD_SLAP_ANI.stop();
            EARTH_SHAKER_ANI.stop();
            BRIMSTONE_ANI.stop();
            RAPID_SLASHES_ANI.stop();
            DELAY_BULLET_ANI.stop();
            ATTACK_ANI.stop();
            if (activeSkill >= 0) {
                switch (activeSkill) {
                    case 0 -> SHIELD_SMASH_ANI.start(this.age);
                    case 1 -> SHIELD_SLAP_ANI.start(this.age);
                    case 2 -> EARTH_SHAKER_ANI.start(this.age);
                    case 3 -> BRIMSTONE_ANI.start(this.age);
                    case 4 -> RAPID_SLASHES_ANI.start(this.age);
                    case 5 -> DELAY_BULLET_ANI.start(this.age);
                    case 6 -> GRAB_ANI.start(this.age);
                    case 7 -> ATTACK_ANI.start(this.age);
                }
            }
            this.playedSkill = activeSkill;
        }

        int stageChange = this.dataTracker.get(STAGE_CHANGE);
        if (stageChange != this.playedStageChange) {
            if (stageChange == 1) {
                STAGE_2_STOP_APART_ANI.stop();
                STAGE_2_START_APART_ANI.start(this.age);
            } else if (stageChange == 2) {
                STAGE_2_START_APART_ANI.stop();
                STAGE_2_STOP_APART_ANI.start(this.age);
            } else {
                STAGE_2_START_APART_ANI.stop();
                STAGE_2_STOP_APART_ANI.stop();
            }
            this.playedStageChange = stageChange;
        }

        int summonState = this.dataTracker.get(SUMMON_STATE);
        if (summonState != this.playedSummonState) {
            if (summonState == 1) {
                SUMMON_ANI.start(this.age);
            } else {
                SUMMON_ANI.stop();
            }
            this.playedSummonState = summonState;
        }
    }

    private void changeStage() {
        if (skill != -114514) {
            return;
        }
        if (this.getHealth()<=this.getMaxHealth()/2 && getStage()==1 && changingTime==0){
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE,5*20,100,false,false,false));
            isChangingStage=true;
            changingTime++;
            this.dataTracker.set(STAGE_CHANGE, 1);
        }
        else if (changingTime>0&&changingTime<100){
            changingTime++;
            if (changingTime==20){
                this.setStage(2);
                this.stage=2;
                replaceDeepSlateWithLava(this.getBlockPos());
                this.dataTracker.set(STAGE_CHANGE, 2);
            }
        }
        else if (changingTime==100){
            isChangingStage=false;
            changingTime=0;
            this.dataTracker.set(STAGE_CHANGE, 0);
        }
    }

    private void replaceDeepSlateWithLava(BlockPos center) {
        World world = this.getWorld();
        int halfX = 24;
        int halfZ = 24;
        int height = 10;
        for (int dx = -halfX; dx <= halfX; dx++) {
            for (int dy = -2; dy <= height; dy++) {
                for (int dz = -halfZ; dz <= halfZ; dz++) {
                    BlockPos pos = center.add(dx, dy, dz);
                    BlockState state = world.getBlockState(pos);

                    if (state.isOf(Blocks.DEEPSLATE)) {
                        world.setBlockState(pos, Blocks.LAVA.getDefaultState(), 3);
                    }
                }
            }
        }
    }
    private void applySummon() {
        if (!this.isSummoning) {
            return;
        }
        if (this.summonTick > 0) {
            this.summonTick--;
            this.summonApart();
        } else {

            this.isSummoning = false;
            this.dataTracker.set(SUMMON_STATE, 0);
        }
    }

    // 生成时的一次性检测：周围存在首领房方块则播放召唤动画。
    private void checkSummonOnSpawn() {
        if (this.summonChecked) {
            return;
        }
        this.summonChecked = true;
        if (findBossRoomBlock(this.getBlockPos())!=null){
            this.setAiDisabled(true);
            this.startSummon();
        }
    }
    private void startSummon() {
        this.isSummoning = true;
        this.summonTick = SUMMON_DURATION;
        this.dataTracker.set(SUMMON_STATE, 1);
    }
    private void summonApart() {
        int time = -(this.summonTick - SUMMON_DURATION);
        this.setVelocity(Vec3d.ZERO);
        this.setYaw(0);
        this.setPitch(0);
        List<PlayerEntity> playerEntities=this.getWorld().getEntitiesByClass(
                PlayerEntity.class,
                this.getBoundingBox().expand(32),
                e-> true
        );
        Direction skillDirection = Direction.SOUTH;
        BlockPos skillCenter = findBossRoomBlock(this.getBlockPos());
        if (skillCenter == null) {
            return;
        }
        if (time == 36) {
            Direction leftDir = skillDirection.rotateYCounterclockwise();
            breakObsidianArea(skillCenter, leftDir);
            for (PlayerEntity playerEntity : playerEntities) {
                if (playerEntity instanceof ServerPlayerEntity){
                    ScreenShakeNetworking.sendToPlayer((ServerPlayerEntity) playerEntity, 5, 0.5F);
                }
            }
        }
        if (time == 90) {
            Direction rightDir = skillDirection.rotateYClockwise();
            breakObsidianArea(skillCenter, rightDir);
            for (PlayerEntity playerEntity : playerEntities) {
                if (playerEntity instanceof ServerPlayerEntity){
                    ScreenShakeNetworking.sendToPlayer((ServerPlayerEntity) playerEntity, 5, 0.5F);
                }
            }
        }
        if (time == 126) {
            transformRoom(skillCenter);
            spawnParticles();
            for (PlayerEntity playerEntity : playerEntities) {
                if (playerEntity instanceof ServerPlayerEntity){
                    ScreenShakeNetworking.sendToPlayer((ServerPlayerEntity) playerEntity, 20, 0.1F);
                }
            }
            this.setAiDisabled(false);
        }
    }
    private BlockPos findBossRoomBlock(BlockPos origin) {
        World world = this.getWorld();
        for (int dx = -2; dx <= 2; dx++) {
            for (int dy = -2; dy <= 2; dy++) {
                for (int dz = -2; dz <= 2; dz++) {
                    BlockPos pos = origin.add(dx, dy, dz);
                    if (world.getBlockState(pos).isOf(ModBlocks.BOSS_ROOM_BLOCK)) {
                        return pos;
                    }
                }
            }
        }
        return origin;
    }
    private void breakObsidianArea(BlockPos center, Direction dir) {
        World world = this.getWorld();
        int dx = dir.getOffsetX();
        int dz = dir.getOffsetZ();
        int px = -dz;
        for (int f = 0; f <= 32; f++) {
            for (int y = 0; y <= 12; y++) {
                for (int s = -32 / 2; s <= 32 / 2; s++) {
                    BlockPos pos = center.add(dx * f + px * s, y, dz * f + dx * s);
                    if (world.getBlockState(pos).isOf(Blocks.OBSIDIAN)) {
                        world.breakBlock(pos, false);
                    }
                }
            }
        }
    }
    private void transformRoom(BlockPos center) {
        World world = this.getWorld();
        int length =32;
        for (int dx = -length; dx <= length; dx++) {
            for (int dy = 1; dy <= 12; dy++) {
                for (int dz = -length; dz <= length; dz++) {
                    BlockPos pos = center.add(dx, dy, dz);
                    BlockState state = world.getBlockState(pos);
                    if (state.isOf(Blocks.REDSTONE_LAMP)) {
                        world.setBlockState(pos, Blocks.SHROOMLIGHT.getDefaultState(), 3);
                    }
                    if (state.isOf(Blocks.NETHERRACK)) {
                        BlockPos above = pos.up();
                        if (world.getBlockState(above).isAir()) {
                            world.setBlockState(above, Blocks.FIRE.getDefaultState(), 3);
                        }
                    }
                }
            }
        }
    }
    private void spawnParticles() {
        if (!(this.getWorld() instanceof ServerWorld serverWorld)) {
            return;
        }
        Vec3d center = this.getPos().add(0, this.getHeight() * 0.5, 0);
        for (int i = 0; i < 120; i++) {
            double theta = serverWorld.random.nextDouble() * Math.PI * 2;
            double phi = Math.acos(2.0 * serverWorld.random.nextDouble() - 1.0);
            double dirX = Math.sin(phi) * Math.cos(theta);
            double dirY = Math.cos(phi);
            double dirZ = Math.sin(phi) * Math.sin(theta);

            double speed = 0.6 + serverWorld.random.nextDouble() * 1.2;

            serverWorld.spawnParticles(
                    ParticleTypes.CLOUD,
                    center.x, center.y, center.z,
                    1,
                    dirX * speed, dirY * speed, dirZ * speed,
                    1.0
            );
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
        } else if (skillStage == 0) {
            // 空闲期挑选下一个技能
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
            // 0.盾推，1.盾扫，2.震地，3.喷射，4.连斩，5.延迟弹幕，6.抓取，7.剑扫
            switch (skill) {
                case 0 -> skillTick = SHIELD_SMASH_DURATION;
                case 1 -> skillTick = SHIELD_SLAP_DURATION;
                case 2 -> skillTick = EARTH_SHAKER_DURATION;
                case 3 -> skillTick = BRIMSTONE_DURATION;
                case 4 -> skillTick = RAPID_SLASHES_DURATION;
                case 5 -> skillTick = DELAY_BULLET_DURATION;
                case 6 -> skillTick = GRAB_DURATION;
                case 7 -> skillTick = ATTACK_DURATION;
            }
            skillStage++;
            // 起手：把技能编号广播给所有客户端播放动画
            this.dataTracker.set(ACTIVE_SKILL, skill);
        } else {
            // 当前技能结束
            skillTick = this.random.nextInt(40) + 40;
            if (stage==2){
                skillTick=this.random.nextInt(20)+20;
            }
            // 剑扫(7)结束后把下一次起手间隔缩短一点（原逻辑判断刚结束的是否为剑扫动画）
            if (skill==7){
                skillTick=this.random.nextInt(30)+10;
            }
            skill=-114514;
            skillStage = 0;
            target=null;
            // 收尾：通知客户端停止动画
            this.dataTracker.set(ACTIVE_SKILL, -1);
        }
    }


    @Override
    public boolean damage(DamageSource source, float amount) {
        // 阶段切换/召唤期间无敌：直接使用服务端实例字段判断，不再依赖客户端动画状态
        if (isChangingStage || isSummoning) {
            return false;
        }
        return super.damage(source, amount);
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
                            double pullStrength = 2.0;
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
                Vec3d mobPos = this.getEyePos().add(0,-0.7,0);
                Vec3d lookDirection = this.getRotation(this.getYaw());
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
        MissileEntity missile=new MissileEntity(ModEntities.MISSILE,
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
    private @NotNull MissileEntity getMissileEntity(Entity owner, double range, int time,int skillType) {
        AttackTypes bulletType = new AttackTypes(AttackTypes.AttackType.BULLET);
        MissileEntity missile= new MissileEntity(ModEntities.MISSILE,
                owner.getWorld(),
                owner,
                bulletType,
                skillType,
                range,
                time,
                this.getDamageAmount());
        missile.setMissileModelType(MissileModelType.BULLET);
        return missile;
    }

    @Override
    public boolean isFireImmune() {
        return true;
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
    protected boolean canStartRiding(Entity entity) {
        return false;
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
    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        this.bossBar.removePlayer(player);
    }

    @Override
    protected void dropLoot(DamageSource damageSource, boolean causedByPlayer) {
        SpawnRandomSoulItems spawnRandomSoulItems = new SpawnRandomSoulItems();
        this.dropItem(ModItems.PROP_PROTOTYPE);
        this.dropItem(spawnRandomSoulItems.summonRandomSoulItem(this.getWorld()).getItem());
    }

    // ========== NBT 持久化 ==========
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("SkillTick", this.skillTick);
        nbt.putInt("SkillStage", this.skillStage);
        nbt.putInt("Skill", this.skill);
        nbt.putInt("stage",this.stage);
        nbt.putBoolean("SummonChecked", this.summonChecked);
        nbt.putBoolean("IsSummoning", this.isSummoning);
        nbt.putInt("SummonTick", this.summonTick);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.skillTick = nbt.getInt("SkillTick");
        this.skillStage = nbt.getInt("SkillStage");
        this.skill = nbt.getInt("Skill");
        this.stage = nbt.getInt("stage");
        this.summonChecked = nbt.getBoolean("SummonChecked");
        this.isSummoning = nbt.getBoolean("IsSummoning");
        this.summonTick = nbt.getInt("SummonTick");
        if (this.isSummoning) {
            this.dataTracker.set(SUMMON_STATE, 1);
        }

        if (this.hasCustomName()) {
            this.bossBar.setName(this.getDisplayName());
        }
        if (this.skillStage != 0 && this.skill >= 0 && this.skill <= 7) {
            this.dataTracker.set(ACTIVE_SKILL, this.skill);
        }
    }
    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ACTIVE_SKILL, -1);
        this.dataTracker.startTracking(STAGE,1);
        this.dataTracker.startTracking(STAGE_CHANGE, 0);
        this.dataTracker.startTracking(SUMMON_STATE, 0);
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