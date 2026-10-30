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

//这个类是给道具原胚用的。用来画道具对应的材质贴图
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
