package com.nekoadventure.screen;

import com.nekoadventure.network.OpenCreditsScreenPacket;
import com.nekoadventure.ui.MonumentCreditsScreen;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;

// 接收纪念碑右键请求，打开感谢名单 UI
public class ClientMonumentCreditsHandler {

    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(OpenCreditsScreenPacket.OPEN_CREDITS_SCREEN_ID,
                (client, handler, buf, responseSender) ->
                        client.execute(() -> MinecraftClient.getInstance().setScreen(new MonumentCreditsScreen())));
    }
}
