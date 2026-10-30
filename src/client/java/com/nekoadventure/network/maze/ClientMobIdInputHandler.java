package com.nekoadventure.network.maze;

import com.nekoadventure.ui.SpawnMobBlockInputScreen;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

public class ClientMobIdInputHandler {
    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(SpawnMobBlockIdInputNetworking.OPEN_INPUT_CHANNEL,
                (client, handler, buf, responseSender) -> {
                    BlockPos pos = buf.readBlockPos();
                    String currentId = buf.readString();
                    client.execute(() -> MinecraftClient.getInstance().setScreen(new SpawnMobBlockInputScreen(pos, currentId)));
                });
    }
    public static void sendSubmit(BlockPos pos, String input) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(pos);
        buf.writeString(input);
        ClientPlayNetworking.send(SpawnMobBlockIdInputNetworking.SUBMIT_INPUT_CHANNEL, buf);
    }
}
