package com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.tick;

import com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.AbstractNekoFunctionItem;
import com.nekoadventure.other.itemApart.NekoPackageDataManager;
import com.nekoadventure.sound.ModSoundEvents;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;

import java.util.Random;

public class Poker extends AbstractNekoFunctionItem {
    public Poker(Settings settings) {
        super(settings);
        text= Text.of("每隔10秒，随机给予玩家力量，抗性提升，速度，生命恢复的效果");
    }
    private final Random random = new Random();

    @Override
    public void applyTickFunction(PlayerEntity player) {
        if (player.getWorld().isClient()) return;
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
        StatusEffectInstance effect;
        DefaultParticleType particleType=null;
        effect = switch (i) {
            case 0 -> {
                particleType=ParticleTypes.HEART;
                yield effectInstance(StatusEffects.REGENERATION);
            }
            case 1 -> {
                particleType=ParticleTypes.FIREWORK;
                yield effectInstance(StatusEffects.RESISTANCE);
            }
            case 2 -> {
                particleType=ParticleTypes.CLOUD;
                yield effectInstance(StatusEffects.SPEED);
            }
            case 3 -> {
                particleType=ParticleTypes.FLAME;
                yield effectInstance(StatusEffects.STRENGTH);
            }
            default -> null;
        };
        if (player.getWorld() instanceof ServerWorld serverWorld){
            serverWorld.spawnParticles(particleType,
                    player.getX(), player.getY()+1, player.getZ(), 10, 0.5, 0.5, 0.5, 0.01);
        }
        player.getWorld().playSound(null, player.getBlockPos(), ModSoundEvents.POKER_EXTRACT,
                SoundCategory.PLAYERS, 1.0F, 1.0F);
        if (effect != null) {
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
