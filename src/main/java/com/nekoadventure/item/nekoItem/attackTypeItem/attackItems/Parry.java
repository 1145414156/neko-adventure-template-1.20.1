package com.nekoadventure.item.nekoItem.attackTypeItem.attackItems;

import com.nekoadventure.client.ShiftKeyHelper;
import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.item.nekoItem.attackTypeItem.NekoAttackTypeItem;
import com.nekoadventure.item.other.NekoPackageItem;
import com.nekoadventure.other.itemApart.NekoPackageDataManager;
import com.nekoadventure.sound.ModSoundEvents;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Parry extends NekoAttackTypeItem {
    public Parry(Settings settings, double health, double strength, double speed, double attackSpeed, double attackRange, double attackMultiplier, double attackSpeedMultiplier, boolean isSpecific) {
        super(settings, health, strength, speed, attackSpeed, attackRange, attackMultiplier, attackSpeedMultiplier, isSpecific);
    }

    @Override
    public void changeMainAttackType(PlayerEntity player) {
        super.changeMainAttackType(player);
        if (player.getWorld().isClient) return;
        double damage = 1;
        double range = 1;
        if (player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackage) {
            damage = nekoPackage.getStrength(player);
            range = nekoPackage.getAttackRange(player);
        }
        player.addStatusEffect(new StatusEffectInstance(new StatusEffectInstance(StatusEffects.RESISTANCE, 3, 20, true, true)));
        Box playerBox = player.getBoundingBox().expand(0.2);
        List<MissileEntity> contactedMissiles = player.getWorld().getEntitiesByClass(
                MissileEntity.class,
                playerBox.expand(0.3 + range * 0.1),
                missile -> missile.getOwner() != player
        );
        if (!contactedMissiles.isEmpty()) {
            for (MissileEntity missile : contactedMissiles) {
                missile.setOwner(player);
                missile.setDamage(Math.max(missile.getDamage(), damage));
                missile.setVelocity(missile.getVelocity().x * -1, missile.getVelocity().y * -1, missile.getVelocity().z * -1);
                missile.move(MovementType.SELF, missile.getVelocity());
                missile.setYaw(missile.getYaw() + 180);
                missile.setPitch(missile.getPitch() * -1);
                missile.setAliveDuration(0);
            }
            player.getWorld().playSound(null, player.getBlockPos(), ModSoundEvents.PARRY_ATTACK,
                    SoundCategory.PLAYERS, 1.0F, 0.9F);
        }
        double[] finalData = NekoPackageDataManager.getFinalData(player);
        double attackSpeed = finalData != null ? finalData[3] : 0;
        int cooldown = (int) (100 - (attackSpeed / NekoPackageDataManager.MAX_ATTACK_SPEED) * 100);
        cooldown = Math.max(10, Math.min(100, cooldown));
        player.getItemCooldownManager().set(player.getOffHandStack().getItem(), cooldown);
    }

    @Override
    public void changeOffAttackType(PlayerEntity player) {
        super.changeOffAttackType(player);
        if (player.getWorld().isClient) return;
        if (!canExecute(player, 5)) {
        double damage = 1;
        double range = 1;
        if (player.getOffHandStack().getItem() instanceof NekoPackageItem nekoPackage) {
            damage = nekoPackage.getStrength(player);
            range = nekoPackage.getAttackRange(player);
        }
        List<MissileEntity> ownMissiles = player.getWorld().getEntitiesByClass(
                MissileEntity.class,
                player.getBoundingBox().expand(range * 4),
                missile -> missile.getOwner() == player
        );
        boolean isParried = false;
        for (MissileEntity ownMissile : ownMissiles) {
            List<MissileEntity> contactedMissiles = player.getWorld().getEntitiesByClass(
                    MissileEntity.class,
                    ownMissile.getBoundingBox().expand(0.2),
                    missile -> missile.getOwner() != player
            );
            for (MissileEntity missile : contactedMissiles) {
                missile.setOwner(player);
                missile.setDamage(Math.max(missile.getDamage(), damage));
                missile.setVelocity(missile.getVelocity().x * -1, missile.getVelocity().y * -1, missile.getVelocity().z * -1);
                missile.move(MovementType.SELF, missile.getVelocity());
                missile.setYaw(missile.getYaw() + 180);
                missile.setPitch(missile.getPitch() * -1);
                missile.setAliveDuration(0);
                isParried = true;
            }
        }
        if (isParried) {
            player.getWorld().playSound(null, player.getBlockPos(), ModSoundEvents.PARRY_ATTACK,
                    SoundCategory.PLAYERS, 1.0F, 0.9F);
        }
        };
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        Text moreText=Text.of("主攻击：进入短暂无敌，如果接触到了弹幕则将该弹幕转换为自身发射的弹幕(伤害取自身与该弹幕原发射者间的最大值)");
        Text moreText1=Text.of("次攻击：如果子弹接触到了弹幕则将该弹幕转换为自身发射的弹幕(伤害取自身与该弹幕原发射者间的最大值)");

        if (ShiftKeyHelper.isShiftDown()) {
            tooltip.add(moreText);
            tooltip.add(moreText1);
        }
    }
}
