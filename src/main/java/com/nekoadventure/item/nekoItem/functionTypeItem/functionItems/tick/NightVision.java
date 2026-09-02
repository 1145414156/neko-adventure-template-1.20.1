package com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.tick;

import com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.FunctionItem;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class NightVision extends FunctionItem {

    public NightVision(Settings settings) {
        super(settings);
        text= Text.of("自身获得夜视");
    }

    @Override
    public void applyTickFunctionItem(PlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION,301,1,true,false,false));
    }
}
