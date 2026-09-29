package com.nekoadventure.network.mob;


import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.util.math.random.Random;

public class ClientScreenShakeHandler {

    private static final Random RANDOM = Random.create();

    private static int remainingTicks;
    private static int totalTicks;
    private static float intensity;

    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(ScreenShakeNetworking.CHANNEL,
                (client, handler, buf, responseSender) -> {
                    int ticks = buf.readInt();
                    float strength = buf.readFloat();
                    client.execute(() -> startShake(ticks, strength));
                });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (remainingTicks > 0) {
                remainingTicks--;
            }
        });
        WorldRenderEvents.START.register(context -> {
            float currentIntensity = getCurrentIntensity();
            if (currentIntensity <= 0) {
                return;
            }

            double offsetX = (RANDOM.nextDouble() - 0.5) * 2.0 * currentIntensity;
            double offsetY = (RANDOM.nextDouble() - 0.5) * 2.0 * currentIntensity;
            double offsetZ = (RANDOM.nextDouble() - 0.5) * 2.0 * currentIntensity;

            context.matrixStack().translate(offsetX, offsetY, offsetZ);
        });
    }
    public static void startShake(int ticks, float strength) {
        remainingTicks = Math.max(ticks, 0);
        totalTicks = remainingTicks;
        intensity = Math.max(strength, 0);
    }
    private static float getCurrentIntensity() {
        if (remainingTicks <= 0 || totalTicks <= 0) {
            return 0;
        }
        float progress = (float) remainingTicks / totalTicks;
        return intensity * progress;
    }
}