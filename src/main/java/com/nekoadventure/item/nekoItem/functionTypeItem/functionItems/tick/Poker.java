package com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.tick;

import com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.NekoFunctionItem;
import com.nekoadventure.other.itemApart.NekoPackageDataManager;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

import java.util.Random;

public class Poker extends NekoFunctionItem {
    public Poker(Settings settings) {
        super(settings);
        text= Text.of("每隔10秒，随机给予玩家力量，抗性提升，速度，生命恢复的效果");
    }
    private final Random random = new Random();

    @Override
    public void applyTickFunctionItem(PlayerEntity player) {
        //只在服务端执行：客户端也会执行本逻辑，导致计时双倍消耗、随机结果不一致
        if (player.getWorld().isClient()) return;
        //状态按玩家存储，避免物品单例跨玩家共享状态
        int z = NekoPackageDataManager.getItemIntState(player, "poker");
        if (z < 400) {
            NekoPackageDataManager.setItemIntState(player, "poker", z + 1);
        } else {
            clearAllEffects(player);
            applyRandomEffect(player, random.nextInt(4));
            NekoPackageDataManager.setItemIntState(player, "poker", 0);
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
