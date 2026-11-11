package com.nekoadventure.entity.entityRender.boss;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.entity.ModEntityModelLayers;
import com.nekoadventure.entity.boss.SandWormBodyEntity;
import com.nekoadventure.entity.model.boss.SandWormBodyEntityModel;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class SandWormEntityBodyEntityRenderer extends EntityRenderer<SandWormBodyEntity> {
    private static final Identifier TEXTURE = new Identifier(
            NekoAdventure.MOD_ID, "textures/entity/boss/sand_worm_body.png");

    private final SandWormBodyEntityModel model;

    public SandWormEntityBodyEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new SandWormBodyEntityModel(context.getPart(ModEntityModelLayers.SAND_WORM_BODY));
        this.shadowRadius = 0.0f;
    }

    @Override
    public void render(SandWormBodyEntity entity, float yaw, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light) {
        float bodyYaw = MathHelper.lerpAngleDegrees(tickDelta, entity.prevYaw, entity.getYaw());
        float bodyPitch = MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch());

        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0f - bodyYaw));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-bodyPitch));
        matrices.scale(-1.0f, -1.0f, 1.0f);
        matrices.translate(0.0f, -1.501f, 0.0f);

        this.model.setAngles(entity, 0.0f, 0.0f, entity.age + tickDelta, 0.0f, 0.0f);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.model.getLayer(TEXTURE));
        this.model.render(matrices, vertexConsumer, light,
                OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        matrices.pop();

        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(SandWormBodyEntity entity) {
        return TEXTURE;
    }
}
