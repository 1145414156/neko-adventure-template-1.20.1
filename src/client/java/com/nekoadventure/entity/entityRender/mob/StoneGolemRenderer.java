package com.nekoadventure.entity.entityRender.mob;

import com.nekoadventure.entity.ModEntityModelLayers;
import com.nekoadventure.entity.mob.StoneGolemEntity;
import com.nekoadventure.entity.model.mob.StoneGolemEntityModel;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class StoneGolemRenderer extends MobEntityRenderer<StoneGolemEntity, StoneGolemEntityModel> {

    private static final Identifier TEXTURE = new Identifier("neko-adventure", "textures/entity/mob/stone_golem.png");

    public StoneGolemRenderer(EntityRendererFactory.Context context) {
        super(context, new StoneGolemEntityModel(context.getPart(ModEntityModelLayers.STONE_GOLEM)), 0.5F);
    }

    @Override
    public void render(StoneGolemEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(StoneGolemEntity entity) {
        return TEXTURE;
    }
}