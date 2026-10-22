package com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.tick;

import com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.AbstractNekoFunctionItem;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class LavaWalker extends AbstractNekoFunctionItem {
    public LavaWalker(Settings settings) {
        super(settings);
        text= Text.of("自身获得防火");
    }

    @Override
    public void applyTickFunction(PlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.FIRE_RESISTANCE,
                20,
                0,
                true,
                false,
                false
        ));
    }
}
