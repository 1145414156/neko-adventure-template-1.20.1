package com.nekoadventure.item.nekoItem.attackTypeItem.attackItems;

import com.nekoadventure.client.ShiftKeyHelper;
import com.nekoadventure.entity.ModEntities;
import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.item.nekoItem.attackTypeItem.NekoAttackTypeItem;
import com.nekoadventure.item.other.NekoPackageItem;
import com.nekoadventure.other.itemApart.NekoPackageDataManager;
import com.nekoadventure.other.attackApart.AttackTypes;
import com.nekoadventure.sound.ModSoundEvents;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Lance extends NekoAttackTypeItem {
    public Lance(Settings settings, double health, double strength, double speed, double attackSpeed, double attackRange, double attackMultiplier, double attackSpeedMultiplier, boolean isSpecific) {
        super(settings, health, strength, speed, attackSpeed, attackRange, attackMultiplier, attackSpeedMultiplier, isSpecific);
    }

    @Override
    public void changeMainAttackType(PlayerEntity player) {
        super.changeMainAttackType(player);
        if (player.getWorld().isClient) return;
        double range = 4;
        double damage=1;
        if (player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackage) {
            range = nekoPackage.getAttackRange(player);
            damage= nekoPackage.getStrength(player);
        }
        double attackDistance = Math.min(range / 2.0, 8.0);
        Vec3d look = player.getRotationVec(1.0F);
        Vec3d horizontalLook = new Vec3d(look.x, 0, look.z).normalize();
        Vec3d startPos = player.getPos();
        List<LivingEntity> targets = getEntitiesOnLine(player, startPos, horizontalLook, attackDistance);
        if (!targets.isEmpty()) {
            for (LivingEntity target : targets) {
                MissileEntity attack=getMissileEntity(player,damage);
                attack.setPosition(target.getX(), target.getY(), target.getZ());
                player.getWorld().spawnEntity(attack);
            }
            player.getWorld().playSound(null, player.getBlockPos(), ModSoundEvents.LANCE_ATTACK,
                    SoundCategory.PLAYERS, 1.0F, 1.0F);
        }
        double[] finalData = NekoPackageDataManager.getFinalData(player);
        double attackSpeed = finalData != null ? finalData[3] : 0;
        int cooldown = (int) (100 - (attackSpeed / NekoPackageDataManager.MAX_ATTACK_SPEED) * 100);
        cooldown = Math.max(10, Math.min(60, cooldown));
        player.getItemCooldownManager().set(player.getOffHandStack().getItem(), cooldown);
    }

    private List<LivingEntity> getEntitiesOnLine(PlayerEntity player,
                                                 Vec3d start,
                                                 Vec3d horizontalDir,
                                                 double maxDistance) {
        Vec3d startXZ = new Vec3d(start.x, 0, start.z);
        Vec3d dir = horizontalDir.normalize();
        Box searchBox = new Box(
                startXZ.x - maxDistance, start.y - 1, startXZ.z - maxDistance,
                startXZ.x + maxDistance, start.y + 2, startXZ.z + maxDistance
        );

        return player.getWorld().getEntitiesByClass(
                LivingEntity.class,
                searchBox,
                entity -> entity != player && entity.isAlive()
        ).stream().filter(entity -> {
            Vec3d entityPos = entity.getPos();
            Vec3d entityXZ = new Vec3d(entityPos.x, 0, entityPos.z);
            Vec3d relative = entityXZ.subtract(startXZ);
            double projection = relative.dotProduct(dir);
            if (projection < 0 || projection > maxDistance) {
                return false;
            }
            Vec3d closestPoint = startXZ.add(dir.multiply(projection));
            return entityXZ.distanceTo(closestPoint) <= 0.7;
        }).collect(Collectors.toList());
    }

    @Override
    public void changeOffAttackType(PlayerEntity player) {
        super.changeOffAttackType(player);
        if (canExecute(player,10)) {
            World world = player.getWorld();
            double damage;
            double attackRange = 3;
            if (player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackage) {
                damage= nekoPackage.getStrength(player);
                attackRange = nekoPackage.getAttackRange(player);
            } else {
                damage = 1;
            }
            List<MissileEntity> missileEntities = world.getEntitiesByClass(
                    MissileEntity.class,
                    player.getBoundingBox().expand(attackRange * 4),
                    entity -> entity.getOwner() != null && entity.getOwner().equals(player)
            );

            if (missileEntities.isEmpty()) {
                return;
            }
            MissileEntity nearestMissile = missileEntities.stream()
                    .min(Comparator.comparingDouble(m -> m.squaredDistanceTo(player)))
                    .orElse(null);
            Optional<LivingEntity> nearestTarget = world.getEntitiesByClass(
                    LivingEntity.class,
                    nearestMissile.getBoundingBox().expand(5),
                    entity -> nearestMissile.getOwner() != entity && entity.isAlive()
            ).stream().min(Comparator.comparingDouble(entity -> entity.squaredDistanceTo(nearestMissile)));

            nearestTarget.ifPresent(target -> {
                MissileEntity missile = getMissileEntity(nearestMissile, damage);
                missile.setPosition(target.getEyePos());
                world.spawnEntity(missile);
                target.takeKnockback(0.5f,
                        nearestMissile.getX() - target.getX(),
                        nearestMissile.getZ() - target.getZ());
                player.getWorld().playSound(null, player.getBlockPos(), ModSoundEvents.LANCE_ATTACK,
                        SoundCategory.PLAYERS, 0.5F, 1.0F);
            });
        }
    }

    private @NotNull MissileEntity getMissileEntity(Entity owner, double damage) {
        AttackTypes brimstoneType = new AttackTypes(AttackTypes.AttackType.HOMING);
        return new MissileEntity(ModEntities.MISSILE,
                owner.getWorld(),
                owner,
                brimstoneType,
                false,
                40,
                3,
                damage);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        Text moreText=Text.of("主攻击：玩家朝自身视野方向的\"范围\"距离(最多8格)获取生物并造成一次\"力量\"伤害");
        Text moreText1=Text.of("次攻击：弹幕持续将周围5格范围内的生物造成\"力量\"的伤害并将该目标相对该弹幕位置击退(可触发特效)");
        if (ShiftKeyHelper.isShiftDown()) {
            tooltip.add(moreText);
            tooltip.add(moreText1);
        }
    }
}
