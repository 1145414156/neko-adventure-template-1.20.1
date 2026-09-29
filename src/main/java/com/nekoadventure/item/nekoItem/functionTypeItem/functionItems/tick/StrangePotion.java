package com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.tick;

import com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.NekoFunctionItem;
import com.nekoadventure.other.itemApart.NekoPackageDataManager;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class StrangePotion extends NekoFunctionItem {
    public StrangePotion(Settings settings) {
        super(settings);
        text= Text.of("自身随机获得药水1效果(无论好坏)，持续2秒(除去瞬间伤害)");
    }

    @Override
    public void applyTickFunctionItem(PlayerEntity player) {
        //只在服务端执行：客户端也会执行本逻辑，导致计时双倍消耗、效果随机不一致
        if (player.getWorld().isClient()) return;
        //状态按玩家存储，避免物品单例跨玩家共享状态
        int tick = NekoPackageDataManager.getItemIntState(player, "strangePotion");
        if (tick <= 0) {
            applyRandomPotionEffect(player);
            NekoPackageDataManager.setItemIntState(player, "strangePotion", 200);
        } else {
            NekoPackageDataManager.setItemIntState(player, "strangePotion", tick - 1);
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
        int amplifier = random.nextInt(5);
        int duration = 40;
        player.addStatusEffect(new StatusEffectInstance(
                selectedEffect,
                duration,
                amplifier,
                true,
                false,
                false
        ));

    }
}
