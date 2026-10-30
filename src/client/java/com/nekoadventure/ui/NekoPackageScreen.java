package com.nekoadventure.ui;

import com.nekoadventure.item.other.NekoPackageItem;
import com.nekoadventure.other.itemApart.NekoPackageDataManager;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NekoPackageScreen implements HudRenderCallback {
    private static final int ITEMS_PER_ROW = 5;
    private static final int ITEM_SIZE = 16;
    private static final int ITEM_SPACING = 1;
    private static final int SLOT_SIZE = ITEM_SIZE + ITEM_SPACING;
    private static final String SEPARATOR = "|";
    private static final String[] ATTRIBUTE_NAMES = {
            "额外生命", "额外速度", "额外力量", "额外攻速", "额外范围"
    };

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;

        if (player == null || client.currentScreen != null) return;

        ItemStack offHand = player.getOffHandStack();
        if (offHand.isEmpty() || !(offHand.getItem() instanceof NekoPackageItem nekoPackageItem)) return;

        NbtCompound nbt = offHand.getNbt();
        if (nbt == null) return;
        renderRightPanel(drawContext, client, nbt);
        renderLeftPanel(drawContext, client, player, nekoPackageItem);
    }

    private void renderRightPanel(DrawContext drawContext, MinecraftClient client, NbtCompound nbt) {
        TextRenderer textRenderer = client.textRenderer;
        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();
        NbtList items = nbt.getList("Items", NbtCompound.COMPOUND_TYPE);
        List<ItemStack> itemStacks = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            itemStacks.add(ItemStack.fromNbt(items.getCompound(i)));
        }
        int totalItems = itemStacks.size();
        int rows = (int) Math.ceil((double) totalItems / ITEMS_PER_ROW);
        int cols = Math.min(totalItems, ITEMS_PER_ROW);

        int padding = 2;
        int separatorWidth = textRenderer.getWidth(SEPARATOR);
        int gridWidth = cols * SLOT_SIZE + (cols - 1) * separatorWidth + padding * 2;
        int gridHeight = rows * SLOT_SIZE + padding * 2;

        int x = screenWidth - gridWidth;
        int y = screenHeight / 2 - gridHeight / 2;

        for (int i = 0; i < totalItems; i++) {
            ItemStack itemStack = itemStacks.get(i);
            int row = i / ITEMS_PER_ROW;
            int col = i % ITEMS_PER_ROW;

            int itemX = x + padding + col * (SLOT_SIZE + separatorWidth);
            int itemY = y + padding + row * SLOT_SIZE;

            drawContext.drawItem(itemStack, itemX, itemY);

            if (col < ITEMS_PER_ROW - 1 && i < totalItems - 1) {
                int separatorX = itemX + ITEM_SIZE + ITEM_SPACING;
                int separatorY = itemY + ITEM_SIZE / 2 - textRenderer.fontHeight / 2;
                drawContext.drawText(textRenderer,
                        Text.literal("§8" + SEPARATOR),
                        separatorX, separatorY,
                        0xFFFFFF, true);
            }
        }
    }

    private void renderLeftPanel(DrawContext drawContext, MinecraftClient client, PlayerEntity player, NekoPackageItem nekoPackageItem) {
        TextRenderer textRenderer = client.textRenderer;
        int screenHeight = client.getWindow().getScaledHeight();

        double[] finalData = nekoPackageItem.getFinalData(player);
        int level = NekoPackageDataManager.getClientLevel();
        List<String> dataLines = getLines(finalData);
        int padding = 4;
        int lineHeight = textRenderer.fontHeight + 2;
        int panelWidth = 100;
        int panelHeight = (1 + 1 + 1 + dataLines.size()) * lineHeight + padding * 2;
        int x = 0;
        int y = screenHeight / 2 - panelHeight / 2;
        if (level>0) {
            int levelY = y + padding;
            drawContext.drawText(textRenderer,
                    Text.literal("§6楼层: §f" + level),
                    x + padding, levelY,
                    0xFFFFFF, true);
        }
        int titleY = y + padding + lineHeight;
        drawContext.drawText(textRenderer,
                Text.literal("§6属性"),
                x + padding, titleY,
                0xFFFFFF, true);
        int separatorY = titleY + textRenderer.fontHeight + 2;
        drawContext.fill(x + padding, separatorY,
                x + panelWidth - padding, separatorY + 1,
                0xFF555555);
        for (int i = 0; i < dataLines.size(); i++) {
            int dataY = separatorY + 2 + i * lineHeight;
            drawContext.drawText(textRenderer,
                    Text.literal(dataLines.get(i)),
                    x + padding, dataY,
                    0xFFFFFF, true);
        }
    }

    private @NotNull List<String> getLines(double[] finalData) {
        List<String> dataLines = new ArrayList<>();
        if (finalData != null && finalData.length >= 5) {
            for (int i = 0; i < 5; i++) {
                String name = ATTRIBUTE_NAMES[i];
                double value = finalData[i];
                if (i==2) {
                    dataLines.add("§7" + name + ": §f" + String.format("%.2f", value)+"(*"+ String.format("%.1f", finalData[5]) +")");
                }
                else if (i==3) {
                    dataLines.add("§7" + name + ": §f" + String.format("%.2f", value)+"(*"+ String.format("%.1f", finalData[6]) +")");
                }
                else {
                    dataLines.add("§7" + name + ": §f" + String.format("%.2f", value));
                }
            }
        } else {
            dataLines.add("§8No data");
        }
        return dataLines;
    }
}
