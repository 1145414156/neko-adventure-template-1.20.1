package com.nekoadventure.entity.entityRender.mob;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.entity.ModEntityModelLayers;
import com.nekoadventure.entity.mob.CobraEntity;
import com.nekoadventure.entity.model.mob.CobraEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class CobraEntityRenderer extends MobEntityRenderer<CobraEntity, CobraEntityModel> {

    private static final Identifier TEXTURE = new Identifier(
            NekoAdventure.MOD_ID, "textures/entity/mob/cobra.png");

    public CobraEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new CobraEntityModel(context.getPart(ModEntityModelLayers.COBRA)), 0.4f);
    }

    @Override
    public Identifier getTexture(CobraEntity entity) {
        return TEXTURE;
    }
}