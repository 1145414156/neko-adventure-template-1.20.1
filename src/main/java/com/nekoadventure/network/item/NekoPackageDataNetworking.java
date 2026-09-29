package com.nekoadventure.network.item;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.item.other.NekoPackageItem;
import com.nekoadventure.other.itemApart.NekoPackageDataManager;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class NekoPackageDataNetworking {
    // S2C：同步纸盒最终数据给持有者客户端用于HUD渲染
    public static final Identifier DATA_CHANNEL = new Identifier(NekoAdventure.MOD_ID, "neko_package_data");
    // C2S：客户端按键请求重新计算纸盒数据
    public static final Identifier RESET_CHANNEL = new Identifier(NekoAdventure.MOD_ID, "neko_package_reset");
    // C2S：客户端按键请求执行主攻击方式
    public static final Identifier MAIN_ATTACK_CHANNEL = new Identifier(NekoAdventure.MOD_ID, "neko_package_main_attack");

    // 给单个玩家同步最终数据（附带当前迷宫楼层）
    public static void sendToPlayer(ServerPlayerEntity player, double[] finalData, int level) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(finalData.length);
        for (double value : finalData) {
            buf.writeDouble(value);
        }
        buf.writeInt(level);
        ServerPlayNetworking.send(player, DATA_CHANNEL, buf);
    }

    // 服务端注册（主类调用）
    public static void registerServerReceivers() {
        // 客户端请求重置纸盒数据
        ServerPlayNetworking.registerGlobalReceiver(RESET_CHANNEL,
                (server, player, handler, buf, responseSender) -> server.execute(() -> NekoPackageDataManager.reset(player)));
        // 客户端请求执行主攻击方式
        ServerPlayNetworking.registerGlobalReceiver(MAIN_ATTACK_CHANNEL,
                (server, player, handler, buf, responseSender) -> server.execute(() -> NekoPackageItem.tryApplyMainAttackType(player)));
        // 玩家退出时清理其纸盒数据
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) ->
                NekoPackageDataManager.remove(handler.getPlayer().getUuid()));
    }
}
