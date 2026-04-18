package com.nekoadventure.entity.entityRender.boss;

import com.nekoadventure.entity.ModEntityModelLayers;
import com.nekoadventure.entity.boss.PriestSkeletonEntity;
import com.nekoadventure.entity.model.boss.PriestSkeletonEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class PriestSkeletonRenderer extends MobEntityRenderer<PriestSkeletonEntity, PriestSkeletonEntityModel> {
    private static final Identifier TEXTURE = new Identifier("neko-adventure", "textures/entity/boss/priest_skeleton.png");

    public PriestSkeletonRenderer(EntityRendererFactory.Context context) {
        super(context, new PriestSkeletonEntityModel(context.getPart(ModEntityModelLayers.PRIEST_SKELETON)), 0.5F);
    }

    @Override
    public Identifier getTexture(PriestSkeletonEntity entity) {
        return TEXTURE;
    }
}