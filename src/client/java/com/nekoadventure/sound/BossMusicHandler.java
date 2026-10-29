package com.nekoadventure.sound;

import com.nekoadventure.entity.boss.GrandKnightEntity;
import com.nekoadventure.entity.boss.HugeSlimeEntity;
import com.nekoadventure.entity.boss.PriestSkeletonEntity;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.MusicSound;

import java.util.List;

/**
 * Boss 战斗背景音乐：玩家处于 Boss 32 格范围内且非创造/旁观时循环播放 boss_fight。
 * 通过替换 {@link MinecraftClient#getMusicType()} 的返回值交给原版 MusicTracker 播放，
 * 音乐结束后原版逻辑会立即重播，从而实现循环。
 */
public class BossMusicHandler {
    private static final double BOSS_MUSIC_RANGE = 32.0D;
    private static final MusicSound BOSS_MUSIC =
            new MusicSound(RegistryEntry.of(ModSoundEvents.BOSS_FIGHT), 0, 0, true);
    private static boolean bossMusicActive;

    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(BossMusicHandler::tick);
    }

    public static MusicSound getBossMusic() {
        return BOSS_MUSIC;
    }

    public static boolean isBossMusicActive() {
        return bossMusicActive;
    }

    private static void tick(MinecraftClient client) {
        ClientPlayerEntity player = client.player;
        boolean shouldPlay = player != null && client.world != null
                && !player.isCreative() && !player.isSpectator()
                && isBossNearby(player);
        if (bossMusicActive && !shouldPlay) {
            MusicTracker musicTracker = client.getMusicTracker();
            if (musicTracker.isPlayingType(BOSS_MUSIC)) {
                musicTracker.stop();
            }
        }
        bossMusicActive = shouldPlay;
    }

    private static boolean isBossNearby(ClientPlayerEntity player) {
        List<HostileEntity> bosses = player.getWorld().getEntitiesByClass(
                HostileEntity.class,
                player.getBoundingBox().expand(BOSS_MUSIC_RANGE),
                BossMusicHandler::isBoss
        );
        for (HostileEntity boss : bosses) {
            if (boss.isAlive()
                    && player.squaredDistanceTo(boss) <= BOSS_MUSIC_RANGE * BOSS_MUSIC_RANGE) {
                return true;
            }
        }
        return false;
    }

    private static boolean isBoss(Entity entity) {
        return entity instanceof GrandKnightEntity
                || entity instanceof HugeSlimeEntity
                || entity instanceof PriestSkeletonEntity;
    }
}
