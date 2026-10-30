package com.nekoadventure.mixin.ui;

import com.nekoadventure.NekoAdventure;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

/**制作这个类是为了:
 * 为指定的特殊生物渲染专属的 BossBar 血条：
 * 该类由AI辅助完成
 */
@Mixin(BossBarHud.class)
public class NekoBossBarMixin {
    @Unique
    private static final Map<String, Identifier> NEKO_SPECIAL_BOSS_BARS = Map.of(
            "entity.neko-adventure.grand_knight",
            new Identifier(NekoAdventure.MOD_ID, "textures/ui/boss_bar/grand_knight_boss_bar.png"));

    // 单一个血条：186x48
    @Unique
    private static final int NEKO_TEXTURE_WIDTH = 186;
    @Unique
    private static final int NEKO_TEXTURE_HEIGHT = 48;
    @Unique
    private static final int NEKO_STRIP_HEIGHT = 24;
    @Unique
    private static final int NEKO_FILL_STRIP_V = 0;
    @Unique
    private static final int NEKO_TRACK_STRIP_V = 24;
    @Unique
    private static final int NEKO_BAR_WIDTH = 273;
    @Unique
    private static final int NEKO_BAR_HEIGHT = Math.round(NEKO_STRIP_HEIGHT * ((float) NEKO_BAR_WIDTH / NEKO_TEXTURE_WIDTH));

    @Unique
    private static final int NEKO_BAR_X_OFFSET = (NEKO_BAR_WIDTH - 182) / 2;
    @Unique
    private static final int NEKO_BAR_TOP_Y = 8;

    @Inject(method = "renderBossBar(Lnet/minecraft/client/gui/DrawContext;IILnet/minecraft/entity/boss/BossBar;)V",
            at = @At("HEAD"), cancellable = true)
    private void nekoRenderSpecialBossBar(DrawContext context, int x, int y, BossBar bossBar, CallbackInfo ci) {
        Identifier texture = nekoGetSpecialBossBarTexture(bossBar.getName());
        if (texture == null) {
            return;
        }
        float percent = MathHelper.clamp(bossBar.getPercent(), 0.0F, 1.0F);
        int filledTextureWidth = Math.round(percent * NEKO_TEXTURE_WIDTH);
        int filledBarWidth = Math.round(filledTextureWidth * ((float) NEKO_BAR_WIDTH / NEKO_TEXTURE_WIDTH));
        int barX = x - NEKO_BAR_X_OFFSET;
        int barY = NEKO_BAR_TOP_Y;
        context.drawTexture(texture, barX, barY, NEKO_BAR_WIDTH, NEKO_BAR_HEIGHT,
                0.0F, (float) NEKO_TRACK_STRIP_V, NEKO_TEXTURE_WIDTH, NEKO_STRIP_HEIGHT,
                NEKO_TEXTURE_WIDTH, NEKO_TEXTURE_HEIGHT);
        if (filledBarWidth > 0) {
            context.drawTexture(texture, barX, barY, filledBarWidth, NEKO_BAR_HEIGHT,
                    0.0F, (float) NEKO_FILL_STRIP_V, filledTextureWidth, NEKO_STRIP_HEIGHT,
                    NEKO_TEXTURE_WIDTH, NEKO_TEXTURE_HEIGHT);
        }

        ci.cancel();
    }
    @Unique
    private static Identifier nekoGetSpecialBossBarTexture(Text name) {
        String nameString = name.getString();
        for (Map.Entry<String, Identifier> entry : NEKO_SPECIAL_BOSS_BARS.entrySet()) {
            if (nameString.equals(Text.translatable(entry.getKey()).getString())) {
                return entry.getValue();
            }
        }
        return null;
    }
}
