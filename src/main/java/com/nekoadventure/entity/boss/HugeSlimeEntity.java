package com.nekoadventure.entity.boss;

import com.nekoadventure.effect.ModStatusEffects;
import com.nekoadventure.entity.ModEntities;
import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.network.FloorShakeNetworking;
import com.nekoadventure.network.ScreenShakeNetworking;
import com.nekoadventure.other.attackApart.AttackTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;


public class HugeSlimeEntity extends HostileEntity implements Monster {

    @Environment(EnvType.CLIENT)
    public static final AnimationState TRAMPLE_SKILL_ANI=new AnimationState();
    private static final int TRAMPLE_SKILL_DURATION=180;
    @Environment(EnvType.CLIENT)
    public static final AnimationState SUMMON_SKILL_ANI=new AnimationState();
    private static final int SUMMON_SKILL_DURATION=40;
    @Environment(EnvType.CLIENT)
    public static final AnimationState BULLET_SKILL_ANI=new AnimationState();
    private static final int BULLET_SKILL_DURATION=100;
    @Environment(EnvType.CLIENT)
    public static final AnimationState DASH_SKILL_ANI=new AnimationState();
    private static final int DASH_SKILL_DURATION=60;

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
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.8D);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimmingGoal(this));
        this.goalSelector.add(2, new FaceTowardTargetGoal(this));
        this.goalSelector.add(3, new RandomLookGoal(this));
        this.goalSelector.add(5, new MoveGoal(this));

        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, false, false,
                livingEntity -> Math.abs(livingEntity.getY() - this.getY()) <= 8.0));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, IronGolemEntity.class, true));
    }

    @Override
    public void tick() {
        super.tick();
        this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
        this.stretch = this.stretch + (this.targetStretch - this.stretch) * 0.5F;
        this.lastStretch = this.stretch;

        // 落地效果：粒子、音效、动画的触发点
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
            if (jumpCount >= 3) {
                setCanJump(false);
            }
        } else if (!this.isOnGround() && this.onGroundLastTick&&canJump) {
            this.targetStretch = 1.0F;
        }
        //技能入口（跳跃三次之后会触发的事件轴）
        else if (!canJump){
            applySkill();
        }

        this.onGroundLastTick = this.isOnGround();
        this.updateStretch();
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
        else {
            if (skillStage==0){
                if (this.getTarget()==null){
                    setCanJump(true);
                    skillStage=0;
                    return;
                }
               skill=this.random.nextInt(4);
                //0.三连跳，1.召唤怪物，2.子弹，3.冲刺
                switch (skill) {
                    case 0:{
                        skillTick= TRAMPLE_SKILL_DURATION;
                        TRAMPLE_SKILL_ANI.startIfNotRunning(this.age);
                        break;
                    }
                    case 1:{
                        skillTick= SUMMON_SKILL_DURATION;
                        SUMMON_SKILL_ANI.startIfNotRunning(this.age);
                        break;
                    }
                    case 2:{
                        skillTick= BULLET_SKILL_DURATION;
                        BULLET_SKILL_ANI.startIfNotRunning(this.age);
                        break;
                    }
                    case 3:{
                        skillTick= DASH_SKILL_DURATION;
                        DASH_SKILL_ANI.startIfNotRunning(this.age);
                        break;
                    }
                }
                skillStage++;
            }
            else {
                setCanJump(true);
                skillTick=this.random.nextInt(10)+10;
                skillStage=0;
                switch (skill){
                    case 0:{
                        skill=-999;
                        TRAMPLE_SKILL_ANI.stop();
                        break;
                    }
                    case 1:{
                        skill=-999;
                        SUMMON_SKILL_ANI.stop();
                        break;
                    }
                    case 2:{
                        skill=-999;
                        BULLET_SKILL_ANI.stop();
                        break;
                    }
                    case 3:{
                        skill=-999;
                        DASH_SKILL_ANI.stop();
                        break;
                    }
                }
            }
        }
    }

    //这些是全部技能
    private void dashSkill(){
        if (this.getTarget() == null) {return;}
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
        }
    }

    private void summonSkill() {
        if (this.getTarget() == null) {return;}
        int time = -(skillTick - SUMMON_SKILL_DURATION);
        if (time >= 9 && time <= 11) {
            if (this.summonCount < 12) {
                this.playSound(SoundEvents.ENTITY_SLIME_ATTACK, 2.0F, 0.8F);
                SlimeEntity slime = new SlimeEntity(EntityType.SLIME, this.getWorld());
                slime.setSize(this.random.nextInt(4)+1, true);
                slime.setPos(this.getX(), this.getY() + 0.5, this.getZ());
                this.getWorld().spawnEntity(slime);
                this.summonCount++;
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
            }
        }
    }

    private void trampleSkill(){
        if (this.getTarget()==null&&targetPos==null){return;}
        int times=-(skillTick-TRAMPLE_SKILL_DURATION);
        int cycle=60;
        int time=times;
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
            double range = 16;
            for (int i = 0; i < 16; i++) {
                float yaw = i * 22.5f;
                MissileEntity missile = getMissileEntity(this, range,40);
                missile.setPos(this.getX(), this.getY() + 1.0, this.getZ());
                missile.setYaw(yaw);
                missile.setPitch(0.0F);
                this.getWorld().spawnEntity(missile);
            }
            List<? extends PlayerEntity> playerEntities=this.getWorld().getPlayers();
            if (!playerEntities.isEmpty()){
                for (PlayerEntity playerEntity : playerEntities) {
                    if (playerEntity instanceof ServerPlayerEntity){
                        ScreenShakeNetworking.sendToPlayer((ServerPlayerEntity) playerEntity, 5, 0.2F);
                    }
                }
            }
            if (this.getWorld() instanceof ServerWorld serverWorld){
                FloorShakeNetworking.sendToAll(serverWorld,this.getBlockPos().down(1),5,1.0f,3.0f);
            }
            this.playSound(SoundEvents.ENTITY_PLAYER_BIG_FALL, 1.0F, 1.0F);
            this.playSound(SoundEvents.ENTITY_SLIME_SQUISH,2.0f,0.5f);
        }
        this.fallDistance=0;
    }

    private void bulletSkill(){
        if (this.getTarget() == null) {return;}
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
        }
    }

    protected void updateStretch() {
        this.targetStretch *= 0.6F;
    }

    private @NotNull MissileEntity getMissileEntity(Entity owner, double range,int time) {
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

    protected void damage(LivingEntity target) {
        if (this.isAlive()) {
            float reach = 3.5F; // 攻击范围
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
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("SummonCount", this.summonCount);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.summonCount = nbt.getInt("SummonCount");

        if (this.hasCustomName()) {
            this.bossBar.setName(this.getDisplayName());
        }
    }

    @Override
    public void setCustomName(@Nullable Text name) {
        super.setCustomName(name);
        this.bossBar.setName(this.getDisplayName());
    }

    // Boss 只显示血条，不显示头顶名字
    @Override
    public boolean isCustomNameVisible() {
        return false;
    }
}