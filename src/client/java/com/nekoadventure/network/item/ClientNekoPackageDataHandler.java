package com.nekoadventure.network.item;

import com.nekoadventure.other.itemApart.NekoPackageDataManager;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;

public class ClientNekoPackageDataHandler {

    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(NekoPackageDataNetworking.DATA_CHANNEL,
                (client, handler, buf, responseSender) -> {
                    int length = buf.readInt();
                    double[] finalData = new double[length];
                    for (int i = 0; i < length; i++) {
                        finalData[i] = buf.readDouble();
                    }
                    int level = buf.readInt();
                    client.execute(() -> {
                        NekoPackageDataManager.setClientFinalData(finalData);
                        NekoPackageDataManager.setClientLevel(level);
                    });
                });
    }
    public static void requestReset() {
        ClientPlayNetworking.send(NekoPackageDataNetworking.RESET_CHANNEL, PacketByteBufs.create());
    }
    public static void requestMainAttackType() {
        ClientPlayNetworking.send(NekoPackageDataNetworking.MAIN_ATTACK_CHANNEL, PacketByteBufs.create());
    }
}
