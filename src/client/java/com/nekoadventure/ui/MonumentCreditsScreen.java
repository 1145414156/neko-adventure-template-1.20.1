package com.nekoadventure.ui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import java.util.List;

public class MonumentCreditsScreen extends Screen {
    private static final int PANEL_WIDTH = 260;

    // TODO: 感谢名单内容（当前为示例，请自行修改）
    private static final List<String> CREDITS = List.of(
            "目前版本：v0.0.1",
            "材质: 李云川YC/Old_Chen_/Infinitly_",
            "代码: Infinitly_",
            "音效: Infinitly_",
            "建筑：zjsfdx/LAOMAO_NG/Infinitly_",
            "测试: Infinitly_",
            "以及，感谢每一位游玩该模组的玩家"
    );

    private int panelHeight;

    public MonumentCreditsScreen() {
        super(Text.literal("感谢名单"));
    }

    @Override
    protected void init() {
        super.init();
        int lineHeight = textRenderer.fontHeight + 6;
        panelHeight = 48 + CREDITS.size() * lineHeight + 28;
        int panelY = (height - panelHeight) / 2;

        // 关闭按钮（面板底部居中）
        this.addDrawableChild(ButtonWidget.builder(Text.literal("关闭"), button -> this.close())
                .dimensions(width / 2 - 30, panelY + panelHeight - 24, 60, 20)
                .build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);

        int lineHeight = textRenderer.fontHeight + 6;
        int panelX = (width - PANEL_WIDTH) / 2;
        int panelY = (height - panelHeight) / 2;

        // 面板背景
        context.fill(panelX, panelY, panelX + PANEL_WIDTH, panelY + panelHeight, 0xCC000000);
        // 面板边框
        context.fill(panelX, panelY, panelX + PANEL_WIDTH, panelY + 1, 0xFFAAAAAA);
        context.fill(panelX, panelY + panelHeight - 1, panelX + PANEL_WIDTH, panelY + panelHeight, 0xFFAAAAAA);
        context.fill(panelX, panelY, panelX + 1, panelY + panelHeight, 0xFFAAAAAA);
        context.fill(panelX + PANEL_WIDTH - 1, panelY, panelX + PANEL_WIDTH, panelY + panelHeight, 0xFFAAAAAA);

        // 标题
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("§6§l感 谢 名 单"), width / 2, panelY + 14, 0xFFFFFF);

        // 分隔线
        context.fill(panelX + 12, panelY + 32, panelX + PANEL_WIDTH - 12, panelY + 33, 0xFF555555);

        // 名单内容
        for (int i = 0; i < CREDITS.size(); i++) {
            int lineY = panelY + 42 + i * lineHeight;
            context.drawCenteredTextWithShadow(textRenderer, Text.literal(CREDITS.get(i)), width / 2, lineY, 0xFFFFFF);
        }
    }
}
