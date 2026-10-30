package com.nekoadventure.network.maze;

import com.nekoadventure.ui.MonumentCreditsScreen;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;

public class ClientMonumentCreditsHandler {

    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(OpenMonumentScreenPacket.OPEN_CREDITS_SCREEN_ID,
                (client, handler, buf, responseSender) ->
                        client.execute(() -> MinecraftClient.getInstance().setScreen(new MonumentCreditsScreen())));
    }
}
