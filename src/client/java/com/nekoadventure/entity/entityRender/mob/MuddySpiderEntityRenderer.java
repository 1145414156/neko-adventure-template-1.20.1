package com.nekoadventure.entity.entityRender.mob;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.entity.ModEntityModelLayers;
import com.nekoadventure.entity.mob.MuddySpiderEntity;
import com.nekoadventure.entity.model.mob.MuddySpiderEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class MuddySpiderEntityRenderer extends MobEntityRenderer<MuddySpiderEntity, MuddySpiderEntityModel> {
    private static final Identifier TEXTURE = new Identifier(NekoAdventure.MOD_ID, "textures/entity/mob/muddy_spider.png");

    public MuddySpiderEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new MuddySpiderEntityModel(context.getPart(ModEntityModelLayers.MUDDY_SPIDER)), 0.8F);
    }

    @Override
    public Identifier getTexture(MuddySpiderEntity entity) {
        return TEXTURE;
    }
}