package com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.tick;

import com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.FunctionItem;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class EnchantedGoldenAppleSlice extends FunctionItem {
    public EnchantedGoldenAppleSlice(Settings settings) {
        super(settings);
        text= Text.of("自身获得生命恢复1效果");
    }

    @Override
    public void applyTickFunctionItem(PlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.REGENERATION,
                20,
                0,
                false,
                true,
                true
        ));
    }
}
