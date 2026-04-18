package com.nekoadventure.network;
import com.nekoadventure.NekoAdventure;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

public class ScreenShakeNetworking {

    // 改成你的 mod id
    public static final Identifier CHANNEL = new Identifier(NekoAdventure.MOD_ID, "screen_shake");

    /**
     * 给单个玩家发送屏幕震动
     *
     * @param player    服务端玩家
     * @param ticks     持续时间（20 tick = 1 秒）
     * @param intensity 初始强度（0.05 ~ 0.5 比较合适）
     */
    public static void sendToPlayer(ServerPlayerEntity player, int ticks, float intensity) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(ticks);
        buf.writeFloat(intensity);
        ServerPlayNetworking.send(player, CHANNEL, buf);
    }

    /**
     * 给一个世界里的所有在线玩家发送屏幕震动
     */
    public static void sendToAll(ServerWorld world, int ticks, float intensity) {
        for (ServerPlayerEntity player : world.getPlayers()) {
            sendToPlayer(player, ticks, intensity);
        }
    }
}