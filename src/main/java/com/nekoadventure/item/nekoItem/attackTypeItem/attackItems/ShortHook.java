package com.nekoadventure.item.nekoItem.attackTypeItem.attackItems;

import com.nekoadventure.client.ShiftKeyHelper;
import com.nekoadventure.entity.ModEntities;
import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.item.nekoItem.attackTypeItem.NekoAttackTypeItem;
import com.nekoadventure.item.other.NekoPackageItem;
import com.nekoadventure.other.attackApart.AttackTypes;
import com.nekoadventure.other.itemApart.NekoPackageDataManager;
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

public class ShortHook extends NekoAttackTypeItem {
    public ShortHook(Settings settings, double health, double strength, double speed, double attackSpeed, double attackRange, double attackMultiplier, double attackSpeedMultiplier, boolean isSpecific) {
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

                Vec3d pullDirection = player.getEyePos().subtract(target.getEyePos()).normalize();
                double pullStrength = 1.0;
                target.setVelocity(pullDirection.multiply(pullStrength));
                target.velocityModified = true;

                attack.setPosition(target.getEyePos().getX(), target.getEyePos().getY(), target.getEyePos().getZ());
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
        if (canExecute(player,5)) {
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
            if (missileEntities.isEmpty()) {return;}
            MissileEntity farthestMissile = missileEntities.stream()
                    .max(Comparator.comparingDouble(m -> m.squaredDistanceTo(player)))
                    .orElse(null);
            Optional<LivingEntity> farthestTarget = world.getEntitiesByClass(
                    LivingEntity.class,
                    farthestMissile.getBoundingBox().expand(5),
                    entity -> farthestMissile.getOwner() != entity && entity.isAlive()
            ).stream().max(Comparator.comparingDouble(entity -> entity.squaredDistanceTo(farthestMissile)));

            farthestTarget.ifPresent(target -> {
                MissileEntity missile = getMissileEntity(farthestMissile, damage);
                missile.setPosition(target.getEyePos());
                world.spawnEntity(missile);

                Vec3d pullDirection = farthestMissile.getPos().subtract(target.getPos()).normalize();
                double pullStrength = 1.0;
                target.setVelocity(pullDirection.multiply(pullStrength));
                target.velocityModified = true;

                player.getWorld().playSound(null, player.getBlockPos(), ModSoundEvents.LANCE_ATTACK,
                        SoundCategory.PLAYERS, 0.5F, 1.0F);
            });
        }
    }

    private @NotNull MissileEntity getMissileEntity(Entity owner, double damage) {
        AttackTypes attackTypes = new AttackTypes(AttackTypes.AttackType.HOMING);
        return new MissileEntity(ModEntities.MISSILE,
                owner.getWorld(),
                owner,
                attackTypes,
                false,
                40,
                3,
                damage);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        Text moreText=Text.of("主攻击：对面前\"射程\"/2(最多8格)的生物造成一次\"力量\"的伤害并且拉向身前");
        Text moreText1=Text.of("次攻击：弹幕会持续对\"射程\"/2(最多5格)范围中最远的生物造成一次\"力量\"/2的伤害并将该生物拉向身前");

        if (ShiftKeyHelper.isShiftDown()) {
            tooltip.add(moreText);
            tooltip.add(moreText1);
        }
    }
}
