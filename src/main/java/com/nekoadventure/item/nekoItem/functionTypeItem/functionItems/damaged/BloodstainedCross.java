package com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.damaged;

import com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.FunctionItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class BloodstainedCross extends FunctionItem {
    public BloodstainedCross(Settings settings) {
        super(settings);
        text= Text.of("受到伤害时，有当前已损生命值/100概率免疫此次伤害");
    }

    @Override
    public boolean applyDamagedFunctionItem(PlayerEntity player) {
        float currentHealth = player.getHealth();
        float maxHealth = player.getMaxHealth();
        float lostHealth = maxHealth - currentHealth;
        double probability = lostHealth / 100.0;
        probability = Math.max(0, Math.min(1, probability));
        return Math.random() < probability;
    }
}
