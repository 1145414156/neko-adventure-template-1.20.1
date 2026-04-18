package com.nekoadventure.entity.entityRender.mob;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.entity.ModEntityModelLayers;
import com.nekoadventure.entity.mob.TwineSoulEntity;
import com.nekoadventure.entity.model.mob.TwineSoulEntityModel;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class TwineSoulEntityRenderer extends MobEntityRenderer<TwineSoulEntity, TwineSoulEntityModel> {

    private static final Identifier TEXTURE = new Identifier(NekoAdventure.MOD_ID, "textures/entity/mob/twine_soul.png");

    public TwineSoulEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new TwineSoulEntityModel(context.getPart(ModEntityModelLayers.TWINE_SOUL)), 0.5f);
    }

    @Override
    public void render(TwineSoulEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(TwineSoulEntity entity) {
        return TEXTURE;
    }
}