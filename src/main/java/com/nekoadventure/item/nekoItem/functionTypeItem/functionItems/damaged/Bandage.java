package com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.damaged;

import com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.FunctionItem;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class Bandage extends FunctionItem {
    public Bandage(Settings settings) {
        super(settings);
        text= Text.of("受到伤害时，有1/2概率获得生命恢复2，持续3秒");
    }

    @Override
    public boolean applyDamagedFunctionItem(PlayerEntity player) {
        if (Math.random() < 0.5) {
            player.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.REGENERATION,
                    60,
                    1,
                    false,
                    true,
                    true
            ));
        }
        return false;
    }
}
