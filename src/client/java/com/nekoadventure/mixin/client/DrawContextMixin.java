package com.nekoadventure.mixin.client;

import com.nekoadventure.client.ShiftKeyHelper;
import com.nekoadventure.item.other.PropPrototypeItem;
import com.nekoadventure.ui.PoolPreviewComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.gui.tooltip.TooltipPositioner;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

/**制作这个类是为了:
 * 在 Tooltip 真正绘制前，把道具池图标组件追加到行列表末尾：
 * 这样图标会像普通文本行一样参与 Tooltip 的宽高计算与逐行排布，
 * 紧跟在"按下Shift键了解更多"文字行下方对齐绘制。
 * 该类由AI完成
 */
@Mixin(DrawContext.class)
public abstract class DrawContextMixin {
    @Unique
    private static int nekoTooltipDepth;

    @Inject(method = "drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;IILnet/minecraft/client/gui/tooltip/TooltipPositioner;)V",
            at = @At("HEAD"))
    private void nekoAppendPoolPreview(TextRenderer textRenderer, List<TooltipComponent> components,
                                        int x, int y, TooltipPositioner positioner, CallbackInfo ci) {
        nekoTooltipDepth++;
        if (nekoTooltipDepth != 1 || !ShiftKeyHelper.isShiftDown()) {
            return;
        }
        Screen screen = MinecraftClient.getInstance().currentScreen;
        if (!(screen instanceof HandledScreen<?> handledScreen)) {
            return;
        }
        Slot slot = ((HandledScreenAccessor) handledScreen).nekoGetFocusedSlot();
        if (slot == null) {
            return;
        }
        ItemStack stack = slot.getStack();
        if (!(stack.getItem() instanceof PropPrototypeItem propPrototype)) {
            return;
        }
        List<Item> pool = propPrototype.getPoolItems(stack);
        if (pool.isEmpty()) {
            return;
        }
        components.add(new PoolPreviewComponent(pool));
    }

    @Inject(method = "drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;IILnet/minecraft/client/gui/tooltip/TooltipPositioner;)V",
            at = @At("TAIL"))
    private void neko$popTooltipDepth(CallbackInfo ci) {
        nekoTooltipDepth--;
    }
}
