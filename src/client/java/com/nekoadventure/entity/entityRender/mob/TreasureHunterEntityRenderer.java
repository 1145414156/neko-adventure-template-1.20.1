package com.nekoadventure.entity.entityRender.mob;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.entity.ModEntityModelLayers;
import com.nekoadventure.entity.mob.TreasureHunterEntity;
import com.nekoadventure.entity.model.mob.TreasureHunterEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class TreasureHunterEntityRenderer extends MobEntityRenderer<TreasureHunterEntity, TreasureHunterEntityModel> {
    private static final Identifier TEXTURE = new Identifier(NekoAdventure.MOD_ID, "textures/entity/mob/treasure_hunter.png");

    public TreasureHunterEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new TreasureHunterEntityModel(context.getPart(ModEntityModelLayers.TREASURE_HUNTER)), 0.5f);
    }

    @Override
    public Identifier getTexture(TreasureHunterEntity entity) {
        return TEXTURE;
    }
}