package com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.tick;

import com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.FunctionItem;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class Poker extends FunctionItem {
    public Poker(Settings settings) {
        super(settings);
        text= Text.of("每隔10秒，随机给予玩家力量，抗性提升，速度，生命恢复的效果");
    }
    private final Map<UUID, Integer> playerTimers = new HashMap<>();
    private final Random random = new Random();

    @Override
    public void applyTickFunctionItem(PlayerEntity player) {
        UUID playerUuid = player.getUuid();
        int z = playerTimers.getOrDefault(playerUuid, 0);
        if (z < 400) {
            playerTimers.put(playerUuid, z + 1);
        } else {
            clearAllEffects(player);
            int i = random.nextInt(4);
            applyRandomEffect(player, i);
            playerTimers.put(playerUuid, 0);
        }
    }
    private void clearAllEffects(PlayerEntity player) {
        player.removeStatusEffect(StatusEffects.REGENERATION);
        player.removeStatusEffect(StatusEffects.RESISTANCE);
        player.removeStatusEffect(StatusEffects.SPEED);
        player.removeStatusEffect(StatusEffects.STRENGTH);
    }

    private void applyRandomEffect(PlayerEntity player, int i) {
        StatusEffectInstance effect = null;
        String message = "";

        effect = switch (i) {
            case 0 -> {
                message = "抽到的是红桃";
                yield effectInstance(StatusEffects.REGENERATION);
            }
            case 1 -> {
                message = "抽到的是方块";
                yield effectInstance(StatusEffects.RESISTANCE);
            }
            case 2 -> {
                message = "抽到的是黑桃";
                yield effectInstance(StatusEffects.SPEED);
            }
            case 3 -> {
                message = "抽到的是梅花";
                yield effectInstance(StatusEffects.STRENGTH);
            }
            default -> effect;
        };

        if (effect != null) {
            player.sendMessage(Text.of(message), true);
            player.addStatusEffect(effect);
        }
    }
    private StatusEffectInstance effectInstance(StatusEffect effects){
        return new StatusEffectInstance(
                effects,
                20 * 10,
                1,
                true,
                false,
                true
        );
    }
}
