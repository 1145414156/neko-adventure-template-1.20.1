package com.nekoadventure.entity.entityRender.boss;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.entity.ModEntityModelLayers;
import com.nekoadventure.entity.boss.SandWormEntity;
import com.nekoadventure.entity.model.boss.SandWormEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class SandWormEntityRenderer extends MobEntityRenderer<SandWormEntity, SandWormEntityModel> {
    private static final Identifier TEXTURE = new Identifier(
            NekoAdventure.MOD_ID, "textures/entity/boss/sand_worm.png");

    public SandWormEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new SandWormEntityModel(context.getPart(ModEntityModelLayers.SAND_WORM)), 0.5F);
    }

    @Override
    public Identifier getTexture(SandWormEntity entity) {
        return TEXTURE;
    }
}
