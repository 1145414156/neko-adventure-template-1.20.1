package com.nekoadventure.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.Difficulty;


//效果是
//将玩家的饱和度上限为5
//禁用牛奶的清除效果（这个在mixin）
//只能让玩家攻击生物
    public class MazeCurseStatusEffect extends StatusEffect {
        public MazeCurseStatusEffect() {
            super(StatusEffectCategory.HARMFUL, 0xFF4444);
        }

        @Override
        public boolean canApplyUpdateEffect(int duration, int amplifier) {
            return true;
        }

        @Override
        public void applyUpdateEffect(LivingEntity entity, int amplifier) {
            if (entity instanceof PlayerEntity player) {
                if (player.getWorld().getDifficulty() != Difficulty.PEACEFUL) {
                    if (player.getHungerManager().getSaturationLevel()> 5.0F) {
                        player.getHungerManager().setSaturationLevel(5);
                    }
                }
            }
        }
}
