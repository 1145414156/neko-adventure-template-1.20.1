package com.nekoadventure.screen;

import com.nekoadventure.network.NekoPackageDataManager;
import com.nekoadventure.network.NekoPackageDataNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;

public class ClientNekoPackageDataHandler {

    public static void init() {
        // 接收服务端同步过来的纸盒最终数据
        ClientPlayNetworking.registerGlobalReceiver(NekoPackageDataNetworking.DATA_CHANNEL,
                (client, handler, buf, responseSender) -> {
                    int length = buf.readInt();
                    double[] finalData = new double[length];
                    for (int i = 0; i < length; i++) {
                        finalData[i] = buf.readDouble();
                    }
                    client.execute(() -> NekoPackageDataManager.setClientFinalData(finalData));
                });
    }

    // 请求服务端重新计算纸盒数据
    public static void requestReset() {
        ClientPlayNetworking.send(NekoPackageDataNetworking.RESET_CHANNEL, PacketByteBufs.create());
    }
}
