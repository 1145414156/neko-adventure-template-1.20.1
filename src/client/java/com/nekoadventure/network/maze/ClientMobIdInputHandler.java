package com.nekoadventure.network.maze;

import com.nekoadventure.ui.SpawnMobBlockInputScreen;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

//接收刷怪方块右键请求，打开boss生物ID输入界面
public class ClientMobIdInputHandler {

    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(MobIdInputNetworking.OPEN_INPUT_CHANNEL,
                (client, handler, buf, responseSender) -> {
                    BlockPos pos = buf.readBlockPos();
                    String currentId = buf.readString();
                    client.execute(() -> MinecraftClient.getInstance().setScreen(new SpawnMobBlockInputScreen(pos, currentId)));
                });
    }

    //提交输入的boss生物ID给服务端
    public static void sendSubmit(BlockPos pos, String input) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(pos);
        buf.writeString(input);
        ClientPlayNetworking.send(MobIdInputNetworking.SUBMIT_INPUT_CHANNEL, buf);
    }
}
