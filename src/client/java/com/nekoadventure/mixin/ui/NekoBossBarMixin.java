package com.nekoadventure.mixin.ui;

import com.nekoadventure.NekoAdventure;
import net.minecraft.client.font.TextRenderer;
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
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

/**制作这个类是为了:
 * 为指定的特殊生物渲染专属的 BossBar 血条：
 * 当 BossBar 名字命中特殊生物时，改用专属材质
 * assets/neko-adventure/textures/ui/boss_bar/grand_knight_boss_bar.png（186x48）绘制。
 * 材质上下两半各 186x24：上半为填充外观、下半为空槽外观；
 * 先整幅绘制下半幅作为底轨，再按血量百分比从左裁剪上半幅叠加为填充层；
 * 专属血条相对原版条整体放大 1.5 倍并保持水平居中，且隐藏条上方的 Boss 名字。
 * 名字不匹配时不介入，保持原版 BossBarHud 渲染逻辑。
 * 该类由AI辅助完成
 */
@Mixin(BossBarHud.class)
public class NekoBossBarMixin {
    @Unique
    private static final Map<String, Identifier> NEKO_SPECIAL_BOSS_BARS = Map.of(
            "entity.neko-adventure.grand_knight",
            new Identifier(NekoAdventure.MOD_ID, "textures/ui/boss_bar/grand_knight_boss_bar.png"));

    // 材质规格：186x48，上下两半各为一条 186x24 的条带
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

    // 渲染尺寸：相对原版 182 宽的条整体放大 1.5 倍（182*1.5=273），
    // 高度按材质条带 186x24 等比换算（24*273/186≈35），
    // 并以 (273-182)/2 的偏移保持水平居中
    @Unique
    private static final int NEKO_BAR_WIDTH = 273;
    @Unique
    private static final int NEKO_BAR_HEIGHT = Math.round(NEKO_STRIP_HEIGHT * ((float) NEKO_BAR_WIDTH / NEKO_TEXTURE_WIDTH));

    //相对屏幕位置
    @Unique
    private static final int NEKO_BAR_X_OFFSET = (NEKO_BAR_WIDTH - 182) / 2;
    @Unique
    private static final int NEKO_BAR_TOP_Y = 10;

    @Inject(method = "renderBossBar(Lnet/minecraft/client/gui/DrawContext;IILnet/minecraft/entity/boss/BossBar;)V",
            at = @At("HEAD"), cancellable = true)
    private void nekoRenderSpecialBossBar(DrawContext context, int x, int y, BossBar bossBar, CallbackInfo ci) {
        Identifier texture = nekoGetSpecialBossBarTexture(bossBar.getName());
        if (texture == null) {
            // 名字不匹配的时候，保持原有渲染逻辑
            return;
        }

        // 血量百分比换算为材质像素宽度，再按同比例映射到渲染宽度，保证填充与底轨像素对齐
        float percent = MathHelper.clamp(bossBar.getPercent(), 0.0F, 1.0F);
        int filledTextureWidth = Math.round(percent * NEKO_TEXTURE_WIDTH);
        int filledBarWidth = Math.round(filledTextureWidth * ((float) NEKO_BAR_WIDTH / NEKO_TEXTURE_WIDTH));
        int barX = x - NEKO_BAR_X_OFFSET;
        int barY = NEKO_BAR_TOP_Y;

        // 底轨：整幅绘制下半幅（空槽外观）
        context.drawTexture(texture, barX, barY, NEKO_BAR_WIDTH, NEKO_BAR_HEIGHT,
                0.0F, (float) NEKO_TRACK_STRIP_V, NEKO_TEXTURE_WIDTH, NEKO_STRIP_HEIGHT,
                NEKO_TEXTURE_WIDTH, NEKO_TEXTURE_HEIGHT);

        // 填充层：按百分比从左裁剪上半幅（填充外观）叠加在底轨上
        if (filledBarWidth > 0) {
            context.drawTexture(texture, barX, barY, filledBarWidth, NEKO_BAR_HEIGHT,
                    0.0F, (float) NEKO_FILL_STRIP_V, filledTextureWidth, NEKO_STRIP_HEIGHT,
                    NEKO_TEXTURE_WIDTH, NEKO_TEXTURE_HEIGHT);
        }

        ci.cancel();
    }

    // 特殊生物的血条不显示条上方的名字：拦截 render 循环中的名字绘制调用
//    @Redirect(method = "render(Lnet/minecraft/client/gui/DrawContext;)V",
//            at = @At(value = "INVOKE",
//                    target = "Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;III)I"))
//    private int nekoHideSpecialBossBarName(DrawContext context, TextRenderer textRenderer, Text text, int x, int y, int color) {
//        if (nekoGetSpecialBossBarTexture(text) != null) {
//            return 0;
//        }
//        return context.drawTextWithShadow(textRenderer, text, x, y, color);
//    }

    // 用 BossBar 名字（翻译后的显示文本）与特殊生物翻译键比对，命中则返回其专属材质
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
