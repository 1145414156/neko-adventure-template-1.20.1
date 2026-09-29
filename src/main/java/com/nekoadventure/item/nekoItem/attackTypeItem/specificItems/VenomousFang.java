package com.nekoadventure.item.nekoItem.attackTypeItem.specificItems;

import com.nekoadventure.client.ShiftKeyHelper;
import com.nekoadventure.item.nekoItem.attackTypeItem.NekoAttackTypeItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class VenomousFang extends NekoAttackTypeItem {
    public VenomousFang(Settings settings, double health, double strength, double speed, double attackSpeed, double attackRange, double attackMultiplier, double attackSpeedMultiplier, boolean isSpecific) {
        super(settings, health, strength, speed, attackSpeed, attackRange, attackMultiplier, attackSpeedMultiplier, isSpecific);
    }

    @Override
    public void applySpecificItem(PlayerEntity player, LivingEntity target) {
        int currentAmplifier = target.hasStatusEffect(StatusEffects.POISON)
                ? Objects.requireNonNull(target.getStatusEffect(StatusEffects.POISON)).getAmplifier()
                : -1;
        int newAmplifier = Math.min(currentAmplifier + 1, 9);
        target.removeStatusEffect(StatusEffects.POISON);
        target.addStatusEffect(new StatusEffectInstance(
                StatusEffects.POISON,
                100, newAmplifier, false, true, true
        ),player);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        Text moreText=Text.of("造成伤害时，使目标获得当前中毒效果+1的中毒效果(上限为10)，持续5秒");
        if (ShiftKeyHelper.isShiftDown()) {
            tooltip.add(moreText);
        }
    }
}
