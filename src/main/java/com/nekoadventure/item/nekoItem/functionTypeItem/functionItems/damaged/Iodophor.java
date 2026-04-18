package com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.damaged;

import com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.FunctionItem;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

import java.util.Random;

public class Iodophor extends FunctionItem {
    public Iodophor(Settings settings) {
        super(settings);
        text= Text.of("受到伤害时，有1/2概率获得抗性提升2，持续5秒");
    }

    @Override
    public boolean applyDamagedFunctionItem(PlayerEntity player) {
        Random random = new Random();
        if (random.nextBoolean()) {
            player.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.RESISTANCE,
                    100,
                    1,
                    false,
                    true,
                    true
            ));
        }
        return false;
    }
}
