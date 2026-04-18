package com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.tick;

import com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.FunctionItem;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class StrangePotion extends FunctionItem {
    int tick=0;
    public StrangePotion(Settings settings) {
        super(settings);
        text= Text.of("自身随机获得药水1效果(无论好坏)，持续2秒(除去瞬间伤害)");
    }

    @Override
    public void applyTickFunctionItem(PlayerEntity player) {

        if (tick <= 0) {
            applyRandomPotionEffect(player);
        } else {
           tick--;
        }
    }

    private void applyRandomPotionEffect(PlayerEntity player) {
        List<StatusEffect> effects = Arrays.asList(
                StatusEffects.SPEED,
                StatusEffects.HASTE,
                StatusEffects.STRENGTH,
                StatusEffects.INSTANT_HEALTH,
                StatusEffects.JUMP_BOOST,
                StatusEffects.REGENERATION,
                StatusEffects.RESISTANCE,
                StatusEffects.FIRE_RESISTANCE,
                StatusEffects.WATER_BREATHING,
                StatusEffects.INVISIBILITY,
                StatusEffects.NIGHT_VISION,
                StatusEffects.HEALTH_BOOST,
                StatusEffects.ABSORPTION,
                StatusEffects.SATURATION,

                StatusEffects.SLOWNESS,
                StatusEffects.MINING_FATIGUE,
                StatusEffects.WEAKNESS,
                StatusEffects.POISON,
                StatusEffects.WITHER,
                StatusEffects.HUNGER,
                StatusEffects.NAUSEA,
                StatusEffects.BLINDNESS,
                StatusEffects.DARKNESS,
                StatusEffects.LEVITATION
        );
        Random random = new Random();
        StatusEffect selectedEffect = effects.get(random.nextInt(effects.size()));
        int amplifier = random.nextInt(3);
        int duration = 40;
        player.addStatusEffect(new StatusEffectInstance(
                selectedEffect,
                duration,
                amplifier,
                false,
                true,
                true
        ));

    }
}
