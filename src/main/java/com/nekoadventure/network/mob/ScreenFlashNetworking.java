package com.nekoadventure.network.mob;

import com.nekoadventure.NekoAdventure;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
public class ScreenFlashNetworking {
    public static final Identifier CHANNEL = new Identifier(NekoAdventure.MOD_ID, "screen_flash");

    public static void sendToPlayer(ServerPlayerEntity player, int ticks) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(ticks);
        ServerPlayNetworking.send(player, CHANNEL, buf);
    }
    public static void sendToAll(ServerWorld world, int ticks) {
        for (ServerPlayerEntity player : world.getPlayers()) {
            sendToPlayer(player, ticks);
        }
    }
}
