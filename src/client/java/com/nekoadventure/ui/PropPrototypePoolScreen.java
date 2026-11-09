package com.nekoadventure.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

/**
 * 道具原胚储存道具池的预览界面
 * 只供查看：极简显示，物品按PoolPreviewComponent的网格排列，图标后标注其在池中的序号，无法取出物品
 * 物品较多时按行滚动，序号与悬停判定使用同一套行坐标，保证对齐
 * 这个类由AI制作
 */
public class PropPrototypePoolScreen extends Screen {
    private static final int ICON_SIZE = PoolPreviewComponent.ICON_SIZE;
    private static final int ICON_GAP = 4;
    private static final int LABEL_GAP = 2;
    private static final int ROW_STRIDE = ICON_SIZE + ICON_GAP;
    private static final int TOP_MARGIN = 8;
    private static final int BOTTOM_AREA = 34;
    private static final int SCROLLBAR_GAP = 4;
    private static final int SCROLLBAR_WIDTH = 2;

    private final List<ItemStack> items;
    private int gridX;
    private int gridY;
    private int gridWidth;
    private int viewportHeight;
    private int columnStride;
    private int totalRows;
    private int visibleRows;
    private int scrollRow;

    public PropPrototypePoolScreen(List<Item> pool) {
        super(Text.literal("道具原胚：储存的道具池（" + pool.size() + "）"));
        this.items = new ArrayList<>();
        for (Item item : pool) {
            this.items.add(new ItemStack(item));
        }
    }

    public static boolean isShowing() {
        return MinecraftClient.getInstance().currentScreen instanceof PropPrototypePoolScreen;
    }

    @Override
    protected void init() {
        super.init();
        int iconsPerRow = PoolPreviewComponent.ICONS_PER_ROW;
        int numberWidth = textRenderer.getWidth(String.valueOf(items.size()));
        int cellWidth = ICON_SIZE + LABEL_GAP + numberWidth;
        int columns = Math.min(items.size(), iconsPerRow);
        totalRows = (items.size() + iconsPerRow - 1) / iconsPerRow;
        gridWidth = columns * cellWidth + Math.max(0, columns - 1) * ICON_GAP;
        columnStride = cellWidth + ICON_GAP;
        //按可用高度决定能完整显示几行，超出部分通过滚动查看
        int maxVisibleRows = Math.max(1, (height - TOP_MARGIN - BOTTOM_AREA) / ROW_STRIDE);
        visibleRows = Math.min(totalRows, maxVisibleRows);
        viewportHeight = visibleRows * ICON_SIZE + Math.max(0, visibleRows - 1) * ICON_GAP;
        gridX = (width - gridWidth) / 2;
        int blockHeight = viewportHeight + 12 + 20;
        gridY = Math.max(TOP_MARGIN, (height - blockHeight) / 2);
        scrollRow = clampScrollRow(scrollRow);

        addDrawableChild(ButtonWidget.builder(Text.literal("关闭"), button -> close())
                .dimensions(width / 2 - 40, gridY + viewportHeight + 12, 80, 20)
                .build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);

        ItemStack hovered = null;
        if (viewportHeight > 0) {
            context.enableScissor(gridX, gridY, gridX + gridWidth, gridY + viewportHeight);
            int iconsPerRow = PoolPreviewComponent.ICONS_PER_ROW;
            int endRow = Math.min(totalRows, scrollRow + visibleRows);
            for (int row = scrollRow; row < endRow; row++) {
                int iconY = gridY + (row - scrollRow) * ROW_STRIDE;
                for (int column = 0; column < iconsPerRow; column++) {
                    int index = row * iconsPerRow + column;
                    if (index >= items.size()) {
                        break;
                    }
                    int iconX = gridX + column * columnStride;
                    context.drawItem(items.get(index), iconX, iconY);
                    context.drawText(textRenderer, String.valueOf(index + 1),
                            iconX + ICON_SIZE + LABEL_GAP,
                            iconY + (ICON_SIZE - textRenderer.fontHeight) / 2,
                            0xFFB0B0B0, true);
                    if (mouseX >= iconX && mouseX < iconX + columnStride - ICON_GAP
                            && mouseY >= iconY && mouseY < iconY + ICON_SIZE) {
                        hovered = items.get(index);
                    }
                }
            }
            context.disableScissor();
            renderScrollbar(context);
        }

        super.render(context, mouseX, mouseY, delta);
        if (hovered != null) {
            context.drawItemTooltip(textRenderer, hovered, mouseX, mouseY);
        }
    }

    private void renderScrollbar(DrawContext context) {
        int maxScroll = maxScrollRow();
        if (maxScroll <= 0) {
            return;
        }
        int x = Math.min(gridX + gridWidth + SCROLLBAR_GAP, width - SCROLLBAR_WIDTH - 2);
        context.fill(x, gridY, x + SCROLLBAR_WIDTH, gridY + viewportHeight, 0x40FFFFFF);
        int thumbHeight = Math.max(8, viewportHeight * visibleRows / totalRows);
        int thumbY = gridY + (viewportHeight - thumbHeight) * scrollRow / maxScroll;
        context.fill(x, thumbY, x + SCROLLBAR_WIDTH, thumbY + thumbHeight, 0xC0FFFFFF);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        if (maxScrollRow() > 0 && amount != 0) {
            scrollRow = clampScrollRow(scrollRow - (int) Math.signum(amount));
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, amount);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        int maxScroll = maxScrollRow();
        if (maxScroll > 0) {
            if (keyCode == GLFW.GLFW_KEY_PAGE_UP) {
                scrollRow = clampScrollRow(scrollRow - visibleRows);
                return true;
            }
            if (keyCode == GLFW.GLFW_KEY_PAGE_DOWN) {
                scrollRow = clampScrollRow(scrollRow + visibleRows);
                return true;
            }
            if (keyCode == GLFW.GLFW_KEY_HOME) {
                scrollRow = 0;
                return true;
            }
            if (keyCode == GLFW.GLFW_KEY_END) {
                scrollRow = maxScroll;
                return true;
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private int maxScrollRow() {
        return Math.max(0, totalRows - visibleRows);
    }

    private int clampScrollRow(int row) {
        return Math.max(0, Math.min(maxScrollRow(), row));
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
