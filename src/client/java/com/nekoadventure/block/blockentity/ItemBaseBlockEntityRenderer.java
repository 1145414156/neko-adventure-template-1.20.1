package com.nekoadventure.block.blockentity;

import com.nekoadventure.block.blockentity.other.ItemBaseBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.RotationAxis;

public class ItemBaseBlockEntityRenderer implements BlockEntityRenderer<ItemBaseBlockEntity> {
    public ItemBaseBlockEntityRenderer() {}

    @Override
    public void render(ItemBaseBlockEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemStack item = entity.getItem();
        matrices.push();
        if (entity.getWorld() != null) {
            matrices.translate(0.5, 1.0, 0.5);

            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((entity.getWorld().getTime() + tickDelta) * 4));

            int lightAbove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().up());

            MinecraftClient.getInstance().getItemRenderer().renderItem(
                    item,
                    ModelTransformationMode.GROUND,
                    lightAbove,
                    OverlayTexture.DEFAULT_UV,
                    matrices,
                    vertexConsumers,
                    entity.getWorld(),
                    0
            );
        }
        matrices.pop();
        if (!item.isEmpty()) {
            renderCoinText(entity, matrices, vertexConsumers, tickDelta);
        }
    }
    private void renderCoinText(ItemBaseBlockEntity entity, MatrixStack matrices,
                                VertexConsumerProvider vertexConsumers, float tickDelta) {
        int requiredCoins = entity.getRequiredCoins();
        if (entity.getWorld() != null) {
            Text text = Text.literal(String.valueOf(requiredCoins));
            if (requiredCoins == 0) {
                text = Text.literal("");
            }
            double textOffset = Math.sin((entity.getWorld().getTime() + tickDelta) / 8.0) / 7.0;
            matrices.push();
            matrices.translate(0.5, 1.5 + textOffset, 0.5);

            MinecraftClient client = MinecraftClient.getInstance();
            Camera camera = client.gameRenderer.getCamera();

            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-camera.getYaw()));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180));

            float scale = 0.025f;
            matrices.scale(scale, scale, scale);

            TextRenderer textRenderer = client.textRenderer;

            // 绘制金币数量
            int textWidth = textRenderer.getWidth(text);
            textRenderer.draw(
                    text,
                    -textWidth / 2.0f,
                    -10,
                    0xFFFFFF,
                    false,
                    matrices.peek().getPositionMatrix(),
                    vertexConsumers,
                    TextRenderer.TextLayerType.NORMAL,
                    0x40000000,
                    0xF000F0
            );

            // 绘制物品名称（在金币数量下方）
            ItemStack displayItem = entity.getItem();
            if (!displayItem.isEmpty()) {
                Text itemNameText = displayItem.getName();
                int nameWidth = textRenderer.getWidth(itemNameText);
                textRenderer.draw(
                        itemNameText,
                        -nameWidth / 2.0f,
                        -20,
                        0xFFFFFF,
                        false,
                        matrices.peek().getPositionMatrix(),
                        vertexConsumers,
                        TextRenderer.TextLayerType.NORMAL,
                        0x40000000,
                        0xF000F0
                );
            }
        }
        matrices.pop();
    }
}
