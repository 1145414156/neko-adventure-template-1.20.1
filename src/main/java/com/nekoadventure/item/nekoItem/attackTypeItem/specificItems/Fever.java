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
import java.util.Random;

public class Fever extends NekoAttackTypeItem {


    public Fever(Settings settings, double health, double strength, double speed, double attackSpeed, double attackRange, double attackMultiplier, double attackSpeedMultiplier, boolean isSpecific) {
        super(settings, health, strength, speed, attackSpeed, attackRange, attackMultiplier, attackSpeedMultiplier, isSpecific);
    }

    @Override
    public void applySpecificItem(PlayerEntity player, LivingEntity target) {
        super.applySpecificItem(player, target);
        Random random=new Random();
        if (random.nextInt(99)<25) {
            target.addStatusEffect(
                    new StatusEffectInstance(StatusEffects.POISON, 5*20, 1),player);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        Text moreText=Text.of("造成伤害时,有1/4的概率让该生物获得5秒1强度的中毒效果");
        if (ShiftKeyHelper.isShiftDown()) {
            tooltip.add(moreText);
        }
    }
}


