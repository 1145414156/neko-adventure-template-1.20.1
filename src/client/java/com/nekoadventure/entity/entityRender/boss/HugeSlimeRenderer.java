package com.nekoadventure.entity.entityRender.boss;


import com.nekoadventure.NekoAdventure;
import com.nekoadventure.entity.ModEntityModelLayers;
import com.nekoadventure.entity.boss.HugeSlimeEntity;
import com.nekoadventure.entity.model.boss.HugeSlimeEntityModel;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class HugeSlimeRenderer extends LivingEntityRenderer<HugeSlimeEntity, HugeSlimeEntityModel> {
    private static final Identifier TEXTURE = new Identifier(NekoAdventure.MOD_ID, "textures/entity/boss/huge_slime.png");

    private final HugeSlimeEntityModel outerModel;

    public HugeSlimeRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new HugeSlimeEntityModel(ctx.getPart(ModEntityModelLayers.HUGE_SLIME_INNER), false), 0.25f);
        this.outerModel = new HugeSlimeEntityModel(ctx.getPart(ModEntityModelLayers.HUGE_SLIME_OUTER), true);
        this.addFeature(new HugeSlimeOuterFeatureRenderer(this));
    }

    @Override
    public void render(HugeSlimeEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(HugeSlimeEntity entity) {
        return TEXTURE;
    }

    private class HugeSlimeOuterFeatureRenderer extends FeatureRenderer<HugeSlimeEntity, HugeSlimeEntityModel> {
        public HugeSlimeOuterFeatureRenderer(FeatureRendererContext<HugeSlimeEntity, HugeSlimeEntityModel> context) {
            super(context);
        }

        @Override
        public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, HugeSlimeEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
            HugeSlimeEntityModel mainModel = this.getContextModel();
            mainModel.getPart().copyTransform(outerModel.getPart());
            outerModel.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(TEXTURE));
            matrices.push();
            float scale = 2.5f;
            matrices.scale(scale, scale, scale);
            outerModel.getPart().getChild("outer").render(matrices, vertexConsumer, light, getOverlay(entity, 0), 1.0f, 1.0f, 1.0f, 0.8f);
            matrices.pop();
        }
    }
}