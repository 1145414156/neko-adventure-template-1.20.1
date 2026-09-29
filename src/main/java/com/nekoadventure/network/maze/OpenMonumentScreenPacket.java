package com.nekoadventure.network.maze;

import com.nekoadventure.NekoAdventure;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

// 通知客户端打开纪念碑感谢名单 UI
public class OpenMonumentScreenPacket {
    public static final Identifier OPEN_CREDITS_SCREEN_ID = new Identifier(NekoAdventure.MOD_ID, "open_credits_screen");

    public static void send(ServerPlayerEntity player) {
        ServerPlayNetworking.send(player, OPEN_CREDITS_SCREEN_ID, PacketByteBufs.create());
    }
}
