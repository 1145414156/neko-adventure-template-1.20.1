package com.nekoadventure.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

//效果是
//禁用牛奶的清除效果（这个在mixin）
//只能让玩家攻击生物
public class BossFightStatusEffect extends StatusEffect {
    public BossFightStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 4866583);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {}
}
