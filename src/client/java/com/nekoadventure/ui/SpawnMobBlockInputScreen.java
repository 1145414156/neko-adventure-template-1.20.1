package com.nekoadventure.ui;

import com.nekoadventure.network.maze.ClientMobIdInputHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.glfw.GLFW;

//创造模式玩家设置boss生物ID（命名空间:生物ID）的输入界面
public class SpawnMobBlockInputScreen extends Screen {
    private static final int PANEL_WIDTH = 260;
    private static final int PANEL_HEIGHT = 124;

    private final BlockPos pos;
    private final String currentId;
    private TextFieldWidget idField;

    public SpawnMobBlockInputScreen(BlockPos pos, String currentId) {
        super(Text.literal("设置boss生物ID"));
        this.pos = pos;
        this.currentId = currentId;
    }

    @Override
    protected void init() {
        super.init();
        int panelX = (width - PANEL_WIDTH) / 2;
        int panelY = (height - PANEL_HEIGHT) / 2;

        //生物ID输入框
        idField = new TextFieldWidget(textRenderer, panelX + 20, panelY + 48, PANEL_WIDTH - 40, 20, Text.literal("生物ID"));
        idField.setMaxLength(128);
        idField.setPlaceholder(Text.literal("命名空间:生物ID"));
        idField.setText(currentId);
        addDrawableChild(idField);
        setInitialFocus(idField);

        //确认与取消按钮
        addDrawableChild(ButtonWidget.builder(Text.literal("确认"), button -> submit())
                .dimensions(width / 2 - 62, panelY + 80, 60, 20)
                .build());
        addDrawableChild(ButtonWidget.builder(Text.literal("取消"), button -> close())
                .dimensions(width / 2 + 2, panelY + 80, 60, 20)
                .build());
    }

    //把输入的生物ID提交给服务端；留空表示恢复原有结构生成逻辑
    private void submit() {
        ClientMobIdInputHandler.sendSubmit(pos, idField.getText().trim());
        close();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER) {
            submit();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
    }
}
