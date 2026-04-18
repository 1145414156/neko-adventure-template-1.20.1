package com.nekoadventure.entity.boss;


import com.nekoadventure.effect.ModStatusEffects;
import com.nekoadventure.entity.ModEntities;
import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.other.attackApart.AttackTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.EvokerFangsEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
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

public class PriestSkeletonEntity extends HostileEntity implements Monster {

    @Environment(EnvType.CLIENT)
    public static final AnimationState FIRE_SKILL =new AnimationState();
    private static final int FIRE_SKILL_DURATION =80;
    @Environment(EnvType.CLIENT)
    public static final AnimationState SWEEP_SKILL =new AnimationState();
    private static final int SWEEP_SKILL_DURATION =45;
    @Environment(EnvType.CLIENT)
    public static final AnimationState SPIKE_SKILL =new AnimationState();
    private static final int SPIKE_SKILL_DURATION =50;
    @Environment(EnvType.CLIENT)
    public static final AnimationState BULLET_SKILL =new AnimationState();
    private static final int BULLET_SKILL_DURATION =70;

    private final ServerBossBar bossBar = new ServerBossBar(this.getDisplayName(), BossBar.Color.PURPLE, BossBar.Style.PROGRESS);

    private int skillTick = 0;
    private int skillStage = 0;
    private int skill = -114514;
    private Vec3d targetPos;
    private int bulletSkillAngle;

    public PriestSkeletonEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 50;
    }

    public static DefaultAttributeContainer.Builder createPriestSkeletonAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 200.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.33D)
                .add(EntityAttributes.GENERIC_ARMOR, 8.0D)
                .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 4.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 40.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.5D);
    }

    @Override
    protected void initGoals() {
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, false, false,
                livingEntity -> Math.abs(livingEntity.getY() - this.getY()) <= 8.0));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, IronGolemEntity.class, true));

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

        if (skillStage != 0&&skill!=0&&skill!=1){
            this.setVelocity(0,0,0);
        }

        this.applySkill();
    }

    private void applySkill() {
        if(skillTick>0){
            skillTick--;
            if (skillStage!=0){
                if (skill == 0) {
                    fireballSkill();
                } else if (skill == 1) {
                    sweepSkill();
                } else if (skill == 2) {
                    spikeSkill();
                } else if (skill == 3) {
                    bulletSkill();
                }
            }
        }
        else {
            if (skillStage==0){
                if (this.getTarget()==null){
                    skillTick=this.random.nextInt(40)+40;
                    skillStage=0;
                    return;
                }
               skill=this.random.nextInt(4);
                //0.火球术，1.横扫，2.尖刺，3.弹幕
                switch (skill) {
                    case 0:{
                        skillTick= FIRE_SKILL_DURATION;
                        FIRE_SKILL.startIfNotRunning(this.age);
                        break;
                    }
                    case 1:{
                        skillTick= SWEEP_SKILL_DURATION;
                        SWEEP_SKILL.startIfNotRunning(this.age);
                        break;
                    }
                    case 2:{
                        skillTick= SPIKE_SKILL_DURATION;
                        SPIKE_SKILL.startIfNotRunning(this.age);
                        break;
                    }
                    case 3:{
                        skillTick= BULLET_SKILL_DURATION;
                        BULLET_SKILL.startIfNotRunning(this.age);
                        break;
                    }
                }
                skillStage++;
            }
            else {
                skillTick=this.random.nextInt(30)+30;
                skillStage=0;
                switch (skill){
                    case 0:{
                        skill=-999;
                        FIRE_SKILL.stop();
                        break;
                    }
                    case 1:{
                        skill=-999;
                        SWEEP_SKILL.stop();
                        break;
                    }
                    case 2:{
                        skill=-999;
                        SPIKE_SKILL.stop();
                        break;
                    }
                    case 3:{
                        skill=-999;
                        BULLET_SKILL.stop();
                        break;
                    }
                }
            }
        }
    }

    private void fireballSkill(){
        if (this.getTarget()==null&&targetPos==null){return;}
        int times=-(skillTick-FIRE_SKILL_DURATION);
        if (times==1){this.playSound(SoundEvents.ENTITY_EVOKER_CAST_SPELL, 0.7F, 1.0F);}
      if (times==34||times==44||times==54){
          LivingEntity target = this.getTarget();
          if (target != null) {targetPos = target.getPos().add(0,1.5,0);}
          Vec3d pos = this.getEyePos();
          Vec3d direction = targetPos.subtract(pos).normalize();
          FireballEntity fireball = new FireballEntity(
                  this.getWorld(), this, direction.x, direction.y, direction.z, 0);
          fireball.setPosition(pos.x, pos.y-0.5, pos.z);
          this.getWorld().spawnEntity(fireball);
          this.playSound(SoundEvents.ENTITY_BLAZE_SHOOT, 1.0F, 1.0F);
      }
    }

    private void sweepSkill(){
        if (this.getTarget()==null&&targetPos==null){return;}
        int times = -(skillTick - SWEEP_SKILL_DURATION);
        if (times==1){this.playSound(SoundEvents.ENTITY_EVOKER_CAST_SPELL, 0.7F, 1.0F);}
        if (times == 27) {
            int count = 8;
            float halfAngle = 60.0F;
            Vec3d pos=this.getEyePos();
            damageEntitiesInFront(5,3,3,this.getDamageAmount());
            for (int i = 0; i < count; i++) {
                float offset = -halfAngle + (halfAngle*2/count)*i;
                MissileEntity missile = getMissileEntity(this,20,40);
                missile.setPos(pos.x, pos.y-0.5, pos.z);
                missile.setYaw(getYaw()+offset);
                missile.setPitch(this.getPitch());
                this.getWorld().spawnEntity(missile);
            }
            this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0F, 1.0F);
        }
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

    private void spikeSkill(){
        if (this.getTarget()==null&&targetPos==null){return;}
        int times = -(skillTick - SPIKE_SKILL_DURATION);
        if (times==1){this.playSound(SoundEvents.ENTITY_EVOKER_CAST_SPELL, 0.7F, 1.0F);}
        if (times>10&&times<35){
                LivingEntity target = this.getTarget();
                if (target != null) {
                    EvokerFangsEntity fangs = new EvokerFangsEntity(
                            this.getWorld(),
                            target.getX(),
                            target.getBlockY(),
                            target.getZ(),
                            this.getYaw(),
                            2,
                            this
                    );
                    this.getWorld().spawnEntity(fangs);
                    this.playSound(SoundEvents.ENTITY_EVOKER_CAST_SPELL, 1.0F, 1.0F);
            }
        }
    }
    private void bulletSkill(){
        if (this.getTarget()==null&&targetPos==null){return;}
        int times = -(skillTick - BULLET_SKILL_DURATION);
        if (times==1){this.playSound(SoundEvents.ENTITY_EVOKER_CAST_SPELL, 0.7F, 1.0F);}
        if (times<=10){
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 50, 2));
            this.setVelocity(this.getVelocity().add(0,0.2,0));
        }
        else if (times>20&&times<60){
            this.playSound(SoundEvents.BLOCK_DISPENSER_LAUNCH, 1.0F, 0.5F);
            bulletSkillAngle++;
            MissileEntity missile1 =getMissileEntity(this,32,160);
            missile1.setPitch(0);
            missile1.setYaw(this.getYaw()+bulletSkillAngle*10);
            missile1.setPos(this.getX(), this.getY()-0.5, this.getZ());
            this.getWorld().spawnEntity(missile1);

            MissileEntity missile2 =getMissileEntity(this,32,160);
            missile2.setPitch(0);
            missile2.setYaw(this.getYaw()+bulletSkillAngle*10+180);
            missile2.setPos(this.getX(), this.getY()-0.5, this.getZ());
            this.getWorld().spawnEntity(missile2);
        }

    }

    private void damageEntitiesInFront(double range, double width, double height, float damage) {
        if (this.getWorld().isClient) return;
        range=range+1;
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
    }

    @Override
    public void setCustomName(@Nullable Text name) {
        super.setCustomName(name);
        this.bossBar.setName(this.getDisplayName());
    }

    protected float getDamageAmount() {
        return (float) this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
    }
}