package com.nekoadventure.entity.entityRender.mob;

import com.nekoadventure.entity.mob.CoinMonsterEntity;
import com.nekoadventure.item.ModItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class CoinMonsterEntityRenderer extends EntityRenderer<CoinMonsterEntity> {
    private static final ItemStack COIN_STACK = new ItemStack(ModItems.COIN);

    public CoinMonsterEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.shadowRadius = 0.3f;
    }

    @Override
    public void render(CoinMonsterEntity entity, float yaw, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);

        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        BakedModel model = itemRenderer.getModel(COIN_STACK, entity.getWorld(), null, entity.getId());

        matrices.push();
        float spin = (entity.age + tickDelta) * 8.0f;
        double bob = Math.sin((entity.age + tickDelta) * 0.15) * 0.05;
        matrices.translate(0.0, 0.35 + bob, 0.0);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(spin));
        matrices.scale(0.6f, 0.6f, 0.6f);
        itemRenderer.renderItem(COIN_STACK, ModelTransformationMode.NONE, false, matrices, vertexConsumers,
                light, OverlayTexture.DEFAULT_UV, model);
        matrices.pop();
    }

    @Override
    public Identifier getTexture(CoinMonsterEntity entity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }
}
