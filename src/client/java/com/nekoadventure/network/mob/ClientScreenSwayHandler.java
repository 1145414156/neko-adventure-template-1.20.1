package com.nekoadventure.network.mob;

import com.nekoadventure.mixin.client.CameraAccessor;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.render.Camera;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;

/**这个类是：视觉效果视角晃动（相机大幅摆动）
 * 该类由AI完成
 */
public class ClientScreenSwayHandler {

    private static final Random RANDOM = Random.create();

    private static int totalTicks;
    private static int remainingTicks;
    private static int riseTicks;
    private static float maxAngle;
    private static float yawDirection = 1.0F;
    private static float pitchDirection;

    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(ScreenSwayNetworking.CHANNEL,
                (client, handler, buf, responseSender) -> {
                    int ticks = buf.readInt();
                    float angle = buf.readFloat();
                    int rise = buf.readInt();
                    client.execute(() -> startSway(ticks, angle, rise));
                });
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (remainingTicks > 0) {
                remainingTicks--;
            }
        });
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> stopSway());
    }

    public static void startSway(int ticks, float maxAngle, int rise) {
        if (remainingTicks > 0) {
            return;
        }
        totalTicks = Math.max(ticks, 0);
        remainingTicks = totalTicks;
        riseTicks = MathHelper.clamp(rise, 1, Math.max(1, totalTicks));
        ClientScreenSwayHandler.maxAngle = Math.max(maxAngle, 0.0F);
        float yaw = RANDOM.nextBoolean() ? 1.0F : -1.0F;
        float pitch = (RANDOM.nextFloat() * 2.0F - 1.0F) * 0.6F;
        float length = MathHelper.sqrt(yaw * yaw + pitch * pitch);
        yawDirection = yaw / length;
        pitchDirection = pitch / length;
    }

    private static void stopSway() {
        totalTicks = 0;
        remainingTicks = 0;
        maxAngle = 0.0F;
    }

    public static void applySway(Camera camera, float tickDelta) {
        if (remainingTicks <= 0 || maxAngle <= 0.0F) {
            return;
        }
        float angle = getCurrentAngle(tickDelta);
        if (angle <= 0.0F) {
            return;
        }
        ((CameraAccessor) camera).nekoSetRotation(
                camera.getYaw() + yawDirection * angle,
                MathHelper.clamp(camera.getPitch() + pitchDirection * angle, -90.0F, 90.0F));
    }

    private static float getCurrentAngle(float tickDelta) {
        float elapsed = (totalTicks - remainingTicks) + tickDelta;
        if (elapsed <= 0.0F) {
            return 0.0F;
        }
        if (elapsed < riseTicks) {
            float progress = elapsed / riseTicks;
            float inv = 1.0F - progress;
            return maxAngle * (1.0F - inv * inv);
        }
        int recoverTicks = Math.max(1, totalTicks - riseTicks);
        float progress = MathHelper.clamp((elapsed - riseTicks) / recoverTicks, 0.0F, 1.0F);
        return maxAngle * (1.0F - progress * progress * (3.0F - 2.0F * progress));
    }
}
