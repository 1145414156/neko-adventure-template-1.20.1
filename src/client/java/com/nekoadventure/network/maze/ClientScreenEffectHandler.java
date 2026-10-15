package com.nekoadventure.network.maze;

import com.nekoadventure.other.mazeApart.PlayerBlackScreenState;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

//突然发现好像直接写一个失明效果就挺好的，如果说后续玩家有意见的话我就把这段改成自定义的效果啦（反正接口做好了）
public class ClientScreenEffectHandler {
    protected static boolean isScreenBlack = false;

    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(ScreenBlackEffectPacket.SCREEN_BLACK_EFFECT_ID,
                (client, handler,
                 buf, responseSender) -> {
            boolean isBlack = buf.readBoolean();

            client.execute(() -> {
                //玩家移动由客户端主导：锁定状态必须写进本客户端的JVM，PlayerEntityMixin的封锁判定才会在本地生效
                if (client.player != null) {
                    PlayerBlackScreenState.setBlackScreen(client.player.getUuid(), isBlack);
                }
                if (isBlack) {
                    if (client.player != null&&!client.player.isSpectator()&&!client.player.isCreative()) {
                        client.player.addStatusEffect(new StatusEffectInstance(
                                StatusEffects.BLINDNESS,
                                200,
                                255,
                                false,
                                false,
                                false
                        ));
                        isScreenBlack = true;
                    }
                } else {
                    if (client.player != null) {
                        client.player.removeStatusEffect(StatusEffects.BLINDNESS);
                    }
                    isScreenBlack = false;
                }
            });
        });

        //断线/退出世界时清理本地锁定，防止静态Map残留导致重进后依然无法移动
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) ->
                PlayerBlackScreenState.setBlackScreen(handler.getProfile().getId(), false));
    }

}
