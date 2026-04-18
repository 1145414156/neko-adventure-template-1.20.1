package com.nekoadventure.entity.entityRender.mob;

import com.nekoadventure.entity.ModEntityModelLayers;
import com.nekoadventure.entity.mob.MinderEntity;
import com.nekoadventure.entity.model.mob.MinderEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class MinderRenderer extends MobEntityRenderer<MinderEntity, MinderEntityModel> {

    private static final Identifier TEXTURE = new Identifier("neko-adventure", "textures/entity/mob/minder.png");

    public MinderRenderer(EntityRendererFactory.Context context) {
        super(context, new MinderEntityModel(context.getPart(ModEntityModelLayers.MINDER)), 0.5F);
    }

    @Override
    public Identifier getTexture(MinderEntity entity) {
        return TEXTURE;
    }
}