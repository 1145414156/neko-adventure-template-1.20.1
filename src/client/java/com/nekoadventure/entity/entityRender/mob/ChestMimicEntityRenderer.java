package com.nekoadventure.entity.entityRender.mob;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.entity.ModEntityModelLayers;
import com.nekoadventure.entity.mob.ChestMimicEntity;
import com.nekoadventure.entity.model.mob.ChestMimicEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class ChestMimicEntityRenderer extends MobEntityRenderer<ChestMimicEntity, ChestMimicEntityModel> {
    private static final Identifier TEXTURE =
            new Identifier(NekoAdventure.MOD_ID, "textures/entity/mob/chest_mimic.png");

    public ChestMimicEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new ChestMimicEntityModel(context.getPart(ModEntityModelLayers.CHEST_MIMIC)), 0.5f);
    }

    @Override
    public Identifier getTexture(ChestMimicEntity entity) {
        return TEXTURE;
    }
}
