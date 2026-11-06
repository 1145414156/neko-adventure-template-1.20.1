package com.nekoadventure.network.mob;

import com.nekoadventure.NekoAdventure;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

//视角晃动（相机大幅摆动）效果的网络包
public class ScreenSwayNetworking {
    public static final Identifier CHANNEL = new Identifier(NekoAdventure.MOD_ID, "screen_sway");

    //给单个玩家发送视角晃动：ticks 总时长，maxAngle 最大角度，riseTicks 到最大角度所需的 tick
    public static void sendToPlayer(ServerPlayerEntity player, int ticks, float maxAngle, int riseTicks) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(ticks);
        buf.writeFloat(maxAngle);
        buf.writeInt(riseTicks);
        ServerPlayNetworking.send(player, CHANNEL, buf);
    }

    //给一个世界里的所有在线玩家发送视角晃动
    public static void sendToAll(ServerWorld world, int ticks, float maxAngle, int riseTicks) {
        for (ServerPlayerEntity player : world.getPlayers()) {
            sendToPlayer(player, ticks, maxAngle, riseTicks);
        }
    }
}
