package com.nekoadventure.entity.entityRender.mob;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.entity.ModEntityModelLayers;
import com.nekoadventure.entity.mob.ScarabBeetleEntity;
import com.nekoadventure.entity.model.mob.ScarabBeetleEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class ScarabBeetleRenderer extends MobEntityRenderer<ScarabBeetleEntity, ScarabBeetleEntityModel> {
    private static final Identifier TEXTURE = new Identifier(NekoAdventure.MOD_ID, "textures/entity/mob/scarab_beetle.png");

    public ScarabBeetleRenderer(EntityRendererFactory.Context context) {
        super(context, new ScarabBeetleEntityModel(context.getPart(ModEntityModelLayers.SCARAB_BEETLE)), 0.8F);
    }

    @Override
    public Identifier getTexture(ScarabBeetleEntity entity) {
        return TEXTURE;
    }
}
