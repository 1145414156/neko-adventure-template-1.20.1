package com.nekoadventure.network;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.other.mazeApart.PlayerBlackScreenState;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

//通知客户端的特殊屏幕处理
public class ScreenBlackEffectPacket {
    public static final Identifier SCREEN_BLACK_EFFECT_ID = new Identifier(NekoAdventure.MOD_ID, "screen_black_effect");

    public static void send(ServerPlayerEntity player, boolean isBlack) {
        PlayerBlackScreenState.setBlackScreen(player.getUuid(), isBlack);

        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(isBlack);
        ServerPlayNetworking.send(player, SCREEN_BLACK_EFFECT_ID, buf);
    }
}