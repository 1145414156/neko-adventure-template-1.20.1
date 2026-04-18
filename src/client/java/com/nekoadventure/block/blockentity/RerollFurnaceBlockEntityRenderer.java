package com.nekoadventure.block.blockentity;

import com.nekoadventure.block.blockentity.other.RerollFurnaceBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.RotationAxis;

public class RerollFurnaceBlockEntityRenderer implements BlockEntityRenderer<RerollFurnaceBlockEntity> {
    public RerollFurnaceBlockEntityRenderer(){}
    @Override
    public void render(RerollFurnaceBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        int requiredCoins =entity.getUseCount()*10+5;
        if (entity.getWorld() != null) {
            Text text = Text.literal(String.valueOf(requiredCoins));
            matrices.translate(0.5, 1.3, 0.5);

            MinecraftClient client = MinecraftClient.getInstance();
            Camera camera = client.gameRenderer.getCamera();

            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-camera.getYaw()));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));

            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180));

            float scale = 0.025f;
            matrices.scale(scale, scale, scale);

            TextRenderer textRenderer = client.textRenderer;
            int textWidth = textRenderer.getWidth(text);

            textRenderer.draw(
                    text,
                    -textWidth / 2.0f,
                    0,
                    0xFFFFFF,
                    false,
                    matrices.peek().getPositionMatrix(),
                    vertexConsumers,
                    TextRenderer.TextLayerType.NORMAL,
                    0x40000000,
                    0xF000F0
            );
        }
        matrices.pop();
    }
}
