package com.nekoadventure.network.mob;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.mixin.client.GameRendererAccessor;
import com.nekoadventure.mixin.client.PostEffectProcessorAccessor;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.PostEffectPass;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

/**这个类是：视觉效果黑闪（屏幕黑白反转）
 * 该类由AI完成
 */
public class ClientScreenFlashHandler {

    private static final Identifier BLACK_FLASH_EFFECT =
            new Identifier(NekoAdventure.MOD_ID, "shaders/post/black_flash.json");
    private static final String SATURATE_PASS = "neko_black_flash_saturate";
    private static final String INVERT_PASS = "neko_black_flash_invert";

    private static int totalTicks;
    private static int remainingTicks;
    private static boolean effectLoaded;

    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(ScreenFlashNetworking.CHANNEL,
                (client, handler, buf, responseSender) -> {
                    int ticks = buf.readInt();
                    client.execute(() -> startFlash(ticks));
                });
        ClientTickEvents.START_CLIENT_TICK.register(ClientScreenFlashHandler::tick);
        WorldRenderEvents.START.register(context -> applyFadeParams(context.tickDelta()));
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> clearEffect(client));
    }

    public static void startFlash(int ticks) {
        totalTicks = Math.max(ticks, 0);
        remainingTicks = totalTicks;
    }

    private static void tick(MinecraftClient client) {
        if (remainingTicks > 0) {
            if (!effectLoaded && client.world != null) {
                loadEffect(client);
            }
            remainingTicks--;
            return;
        }
        if (effectLoaded) {
            clearEffect(client);
        }
    }

    private static void loadEffect(MinecraftClient client) {
        ((GameRendererAccessor) client.gameRenderer).nekoLoadPostProcessor(BLACK_FLASH_EFFECT);
        effectLoaded = true;
    }

    private static void applyFadeParams(float tickDelta) {
        if (!effectLoaded) {
            return;
        }
        PostEffectProcessor processor = MinecraftClient.getInstance().gameRenderer.getPostProcessor();
        if (processor == null) {
            return;
        }
        float progress = getFadeProgress(tickDelta);
        setUniform(processor, SATURATE_PASS, "Saturation", progress);
        setUniform(processor, INVERT_PASS, "InverseAmount", 1.0F - progress);
    }

    private static float getFadeProgress(float tickDelta) {
        if (totalTicks <= 0) {
            return 1.0F;
        }
        int fadeTicks = Math.max(1, totalTicks / 4);
        float remain = remainingTicks - tickDelta;
        if (remain >= fadeTicks) {
            return 0.0F;
        }
        return MathHelper.clamp((fadeTicks - remain) / fadeTicks, 0.0F, 1.0F);
    }

    private static void setUniform(PostEffectProcessor processor, String passName, String uniformName, float value) {
        for (PostEffectPass pass : ((PostEffectProcessorAccessor) processor).nekoGetPasses()) {
            if (!passName.equals(pass.getName())) {
                continue;
            }
            GlUniform uniform = pass.getProgram().getUniformByName(uniformName);
            if (uniform != null) {
                uniform.set(value);
            }
        }
    }

    private static void clearEffect(MinecraftClient client) {
        totalTicks = 0;
        remainingTicks = 0;
        if (!effectLoaded) {
            return;
        }
        client.gameRenderer.disablePostProcessor();
        effectLoaded = false;
    }
}
