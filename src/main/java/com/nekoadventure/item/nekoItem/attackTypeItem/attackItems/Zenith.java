package com.nekoadventure.item.nekoItem.attackTypeItem.attackItems;

import com.nekoadventure.client.ShiftKeyHelper;
import com.nekoadventure.entity.ModEntities;
import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.item.nekoItem.attackTypeItem.AttackTypeItem;
import com.nekoadventure.item.other.NekoPackageItem;
import com.nekoadventure.network.NekoPackageDataManager;
import com.nekoadventure.other.attackApart.AttackTypes;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Zenith extends AttackTypeItem {
    public Zenith(Settings settings, double health, double strength, double speed, double attackSpeed, double attackRange, double attackMultiplier, double attackSpeedMultiplier, boolean isSpecific) {
        super(settings, health, strength, speed, attackSpeed, attackRange, attackMultiplier, attackSpeedMultiplier, isSpecific);
    }

    @Override
    public void changeMainAttackType(PlayerEntity player) {
        super.changeMainAttackType(player);
        double range = 2;
        double damage = 2;
        if (player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackage){
            range= nekoPackage.getAttackRange(player);
            damage=nekoPackage.getStrength(player);
        }
        for (float yawOffset : new float[]{-60f, -30f, 0f, 30f, 60f}) {
            MissileEntity missileEntity = getMissileEntity(player, range*2, damage);
            missileEntity.setYaw(player.getYaw() + yawOffset);
            player.getWorld().spawnEntity(missileEntity);
        }
        double[] finalData = NekoPackageDataManager.getFinalData(player);
        double attackSpeed = finalData != null ? finalData[3] : 0;
        int cooldown = (int) (120 - (attackSpeed / 12.0) * 120);
        cooldown = Math.max(12, Math.min(120, cooldown));
        player.getItemCooldownManager().set(player.getOffHandStack().getItem(), cooldown);
    }

    @Override
    public void changeOffAttackType(PlayerEntity player) {
        super.changeOffAttackType(player);
        if (canExecute(player, 20)) {
            double range = 2;
            double damage = 2;
            if (player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackage) {
                range = nekoPackage.getAttackRange(player);
                damage = nekoPackage.getStrength(player);
            }
            List<MissileEntity> missileEntities = player.getWorld().getEntitiesByClass(
                    MissileEntity.class,
                    player.getBoundingBox().expand(range*2),
                    entity -> entity.getOwner()!=null&&entity.getOwner().equals(player)
            );
            if (!missileEntities.isEmpty()) {
                for (MissileEntity e : missileEntities) {
                    for (float yawOffset : new float[]{-30f, 0f, 30f}) {
                        MissileEntity missileEntity = getMissileEntity(e, range, damage);
                        missileEntity.setYaw(e.getYaw() + yawOffset);
                        e.getWorld().spawnEntity(missileEntity);
                    }
                }
            }
        }
    }

    private @NotNull MissileEntity getMissileEntity(Entity owner, double range, double damage) {
        AttackTypes bulletType = new AttackTypes(AttackTypes.AttackType.BULLET);
        MissileEntity missileEntity = new MissileEntity(ModEntities.MISSILE,
                owner.getWorld(),
                owner,
                bulletType,
                false,
                range,
                60,
                damage);
        missileEntity.setPos(owner.getX(), owner.getEyeY(), owner.getZ());
        missileEntity.setYaw(owner.getYaw());
        missileEntity.setPitch(owner.getPitch());
        return missileEntity;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        Text moreText=Text.of("主攻击：朝前方五个方向(共120度)同时发射弹幕");
        Text moreText1=Text.of("次攻击：弹幕会朝面前持续发射三个方向(共60度)同时发射弹幕");
        if (ShiftKeyHelper.isShiftDown()) {
            tooltip.add(moreText);
            tooltip.add(moreText1);
        }
    }
}
