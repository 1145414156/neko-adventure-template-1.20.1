package com.nekoadventure.ui;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

//作为一行 Tooltip 组件平铺显示道具池中的所有图标
//人话说就是物品图标

public class PoolPreviewComponent implements TooltipComponent {
    public static final int ICON_SIZE = 16;

    private static final int ICON_GAP = 4;

    public static final int ICONS_PER_ROW = 10;

    public static final int MAX_ICONS = 80;

    private final List<Item> pool;

    public PoolPreviewComponent(List<Item> pool) {
        this.pool = pool;
    }

    @Override
    public int getHeight() {
        int rows = rows();
        return rows * ICON_SIZE + Math.max(0, rows - 1) * ICON_GAP;
    }

    @Override
    public int getWidth(TextRenderer textRenderer) {
        int columns = columns();
        return columns * ICON_SIZE + Math.max(0, columns - 1) * ICON_GAP;
    }

    @Override
    public void drawItems(TextRenderer textRenderer, int x, int y, DrawContext context) {
        int drawn = 0;
        for (Item item : pool) {
            if (drawn >= MAX_ICONS) {
                break;
            }
            int spacing = ICON_SIZE + ICON_GAP;

            int iconX = x + (drawn % ICONS_PER_ROW) * spacing;
            int iconY = y + (drawn / ICONS_PER_ROW) * spacing;

            context.drawItem(new ItemStack(item), iconX, iconY);
            drawn++;
        }
    }

    private int columns() {
        return Math.min(pool.size(), ICONS_PER_ROW);
    }

    private int rows() {
        return (Math.min(pool.size(), MAX_ICONS) + ICONS_PER_ROW - 1) / ICONS_PER_ROW;
    }
}
