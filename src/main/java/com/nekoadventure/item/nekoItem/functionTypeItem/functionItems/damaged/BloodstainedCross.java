package com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.damaged;

import com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.AbstractNekoFunctionItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class BloodstainedCross extends AbstractNekoFunctionItem {
    private static final double MAX_PROBABILITY = 0.5;

    public BloodstainedCross(Settings settings) {
        super(settings);
        text= Text.of("受到伤害时，有当前已损生命值/100概率免疫此次伤害(最多1/2概率)");
    }

    @Override
    public boolean applyDamagedFunction(PlayerEntity player) {
        float currentHealth = player.getHealth();
        float maxHealth = player.getMaxHealth();
        float lostHealth = maxHealth - currentHealth;
        double probability = lostHealth / 100.0;
        probability = Math.max(0, Math.min(MAX_PROBABILITY, probability));
        return Math.random() < probability;
    }
}
