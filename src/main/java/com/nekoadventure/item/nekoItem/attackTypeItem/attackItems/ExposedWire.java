package com.nekoadventure.item.nekoItem.attackTypeItem.attackItems;

import com.nekoadventure.client.ShiftKeyHelper;
import com.nekoadventure.entity.ModEntities;
import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.item.nekoItem.attackTypeItem.AttackTypeItem;
import com.nekoadventure.item.other.NekoPackageItem;
import com.nekoadventure.network.NekoPackageDataManager;
import com.nekoadventure.other.attackApart.AttackTypes;
import com.nekoadventure.sound.ModSoundEvents;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ExposedWire extends AttackTypeItem {
    int maxConnect=8;
    public ExposedWire(Settings settings, double health, double strength, double speed, double attackSpeed, double attackRange, double attackMultiplier, double attackSpeedMultiplier, boolean isSpecific) {
        super(settings, health, strength, speed, attackSpeed, attackRange, attackMultiplier, attackSpeedMultiplier, isSpecific);
    }

    @Override
    public void changeMainAttackType(PlayerEntity player) {
        super.changeMainAttackType(player);
        //这里是选择实体部分
        World world = player.getWorld();
        double attackRange = 3;
        double strength = 1;
        if (player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackage) {
            attackRange = nekoPackage.getAttackRange(player);
            strength = nekoPackage.getStrength(player);
        }
        List<Entity> entities = getEntitiesInRange(player, world,attackRange<=8? (int) attackRange :8);
        entityChooseHandler(player, entities, world,maxConnect);

        //这里是伤害部分
        if (!entities.isEmpty()) {
            for (Entity entity : entities) {
                Vec3d pos=new Vec3d(entity.getX(),entity.getEyeY(),entity.getZ());
                AttackTypes lazyAttack=new AttackTypes(AttackTypes.AttackType.LAZY);
                MissileEntity missile=new MissileEntity(ModEntities.MISSILE,world,player,
                        lazyAttack,true,attackRange,5, strength);
                missile.setPosition(pos);
                world.spawnEntity(missile);

            }
            player.getWorld().playSound(null, player.getBlockPos(), ModSoundEvents.ELECTRICITY_RUN_THROUGH,
                    SoundCategory.PLAYERS, 1.0F, 1.0F);
        }
        maxConnect=8;
        double[] finalData = NekoPackageDataManager.getFinalData(player);
        double attackSpeed = finalData != null ? finalData[3] : 0;
        int cooldown = (int) (60 - (attackSpeed / 12.0) * 60);
        cooldown = Math.max(4, Math.min(60, cooldown));
        player.getItemCooldownManager().set(player.getOffHandStack().getItem(), cooldown);
    }

    @Override
    public void changeOffAttackType(PlayerEntity player) {
        super.changeOffAttackType(player);
        if (canExecute(player,10)){
            World world = player.getWorld();
            double attackRange = 3;
            if (player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackage) {
                attackRange = nekoPackage.getAttackRange(player);
            }
            List<MissileEntity> missileEntities = world.getEntitiesByClass(
                    MissileEntity.class,
                    player.getBoundingBox().expand(attackRange*4),
                    entity ->entity.getOwner()!=null&&entity.getOwner().equals(player)
            );

            MissileEntity missileEntity = missileEntities.stream()
                    .max(Comparator.comparingDouble(
                            missile -> missile.squaredDistanceTo(player)
                    ))
                    .orElse(null);

            if (missileEntity!=null){
                List<Entity> entities = getEntitiesInRange(missileEntity, world, attackRange<=4? (int) attackRange :4);

                List<LivingEntity> livingEntities = entities.stream()
                        .filter(e -> e instanceof LivingEntity&&!missileEntity.getOwner().equals(e))
                        .map(e -> (LivingEntity) e)
                        .collect(Collectors.toList());

                entityChooseHandler(missileEntity, livingEntities, world, maxConnect);
                applyDamageHandler(missileEntity, entities);
            }
        }
    }

    private List<Entity> getEntitiesInRange(Entity center, World world,int range) {
        return world.getEntitiesByClass(
                Entity.class,
                center.getBoundingBox().expand(range),
                entity -> entity != center &&
                        entity instanceof LivingEntity &&
                        !(entity instanceof PlayerEntity)
        );
    }


    private void applyDamageHandler(Entity owner, @UnknownNullability List<Entity> entities) {
        for (Entity entity : entities) {
            DamageSource damageSource=null;
            if (owner instanceof MissileEntity){
                damageSource=owner.getDamageSources().mobAttack((LivingEntity) ((MissileEntity) owner).getOwner());
            }
            if (owner instanceof PlayerEntity) {
               damageSource=owner.getDamageSources().mobAttack((PlayerEntity)owner);
            }

            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.damage(damageSource,
                        (float) getPlayerStrength(owner)/2);
                owner.getWorld().playSound(null, owner.getBlockPos(), ModSoundEvents.ELECTRICITY_RUN_THROUGH,
                        SoundCategory.PLAYERS, 1.0F, 1.0F);
            }
        }
    }

    //从弹幕/玩家所有者身上获取 NekoPackageItem 的力量值
    private double getPlayerStrength(Entity owner) {
        Entity playerEntity = owner;
        if (owner instanceof MissileEntity missile) {
            playerEntity = missile.getOwner();
        }
        if (playerEntity instanceof PlayerEntity player
                && player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackage) {
            return nekoPackage.getStrength(player);
        }
        return 1;
    }


    private <T extends Entity> void entityChooseHandler(
            Entity owner, List<T> entities, World world, int count) {
        if (!entities.isEmpty()) {

            List<LivingEntity> livingEntities = entities.stream()
                    .filter(e -> e instanceof LivingEntity)
                    .map(e -> (LivingEntity) e)
                    .collect(Collectors.toList());

            //为实体排序
            Vec3d pos = owner.getPos();
            livingEntities.sort(Comparator.comparingDouble(e ->
                    e.getPos().squaredDistanceTo(pos)));

            if (livingEntities.size() > count) {
                livingEntities.subList(count, livingEntities.size()).clear();
            }

            //这里是特效部分
            List<Entity> chainOrder = new ArrayList<>(livingEntities);
            Entity current = null;
            double nearestDistance = Double.MAX_VALUE;
            for (Entity entity : chainOrder) {
                double distance = entity.getPos().squaredDistanceTo(pos);
                if (distance < nearestDistance) {
                    nearestDistance = distance;
                    current = entity;
                }
            }
            List<Entity> chain = new ArrayList<>();
            chain.add(current);
            chainOrder.remove(current);
            while (!chainOrder.isEmpty()) {
                if (current != null) {
                    Vec3d lastPos = current.getPos();
                    nearestDistance = Double.MAX_VALUE;
                    Entity next = null;
                    for (Entity entity : chainOrder) {
                        double distance = entity.getPos().squaredDistanceTo(lastPos);
                        if (distance < nearestDistance) {
                            nearestDistance = distance;
                            next = entity;
                        }
                    }
                    chain.add(next);
                    chainOrder.remove(next);
                    current = next;
                }
            }
            // 限制
            if (chain.size() > count) {
                chain.subList(count, chain.size()).clear();
            }
            spawnLightingParticle(world, owner, chain.get(0));
            for (int i = 0; i < chain.size() - 1; i++) {
                spawnLightingParticle(world, chain.get(i), chain.get(i + 1));
            }
        }
    }

    private void spawnLightingParticle(World world, Entity from, Entity to) {
        Vec3d start = from.getPos().add(0, from.getHeight() / 2, 0);
        Vec3d end = to.getPos().add(0, to.getHeight() / 2, 0);

        drawLine(world, start, end,
                ParticleTypes.ELECTRIC_SPARK, 15, 0.3, 0.1);
    }

    public static void drawLine(World world, Vec3d start, Vec3d end,
                                ParticleEffect particle, int points,
                                double speed, double offset) {
        Vec3d delta = end.subtract(start);

        for (int i = 0; i <= points; i++) {
            double progress = (double) i / points;

            double x = start.x + delta.x * progress;
            double y = start.y + delta.y * progress;
            double z = start.z + delta.z * progress;

            if (offset > 0) {
                x += (world.random.nextDouble() - 0.5) * offset;
                y += (world.random.nextDouble() - 0.5) * offset;
                z += (world.random.nextDouble() - 0.5) * offset;
            }

            spawnParticle(world, particle, x, y, z, speed);
        }
    }
    private static void spawnParticle(World world, ParticleEffect particle,
                                      double x, double y, double z, double speed) {
        if (world.isClient) {
            world.addParticle(particle, x, y, z, 0, 0, 0);
        } else {
            ((ServerWorld) world).spawnParticles(particle,
                    x, y, z, 1, 0, 0, 0, speed);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        Text moreText=Text.of("主攻击：对射程范围内至多8名生物造成一次\"力量\"/2伤害");
        Text moreText1=Text.of("次攻击：让距离最远的弹幕对4格范围内至多8名生物造成一次\"力量\"/2伤害");
        if (ShiftKeyHelper.isShiftDown()) {
            tooltip.add(moreText);
            tooltip.add(moreText1);
        }
    }
}

