package com.nekoadventure.item.soulItem;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class RestoreSoul extends AbstractSoulItem{
    public RestoreSoul(Settings settings, int maxCharge) {
        super(settings, maxCharge);
        text= Text.of("使用时，获得生命恢复3，持续10秒");
    }

    @Override
    public void onUseEffect(World world, PlayerEntity player, ItemStack stack) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION,10,2));
    }
}
