package com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.tick;

import com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.NekoFunctionItem;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class TacticalVest extends NekoFunctionItem {
    public TacticalVest(Settings settings) {
        super(settings);
        text= Text.of("自身获得抗性提升1效果");
    }

    @Override
    public void applyTickFunctionItem(PlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE,20,0,true,false,false));
    }
}
