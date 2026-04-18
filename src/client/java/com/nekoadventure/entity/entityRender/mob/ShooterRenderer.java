package com.nekoadventure.entity.entityRender.mob;

import com.nekoadventure.entity.ModEntityModelLayers;
import com.nekoadventure.entity.mob.ShooterEntity;
import com.nekoadventure.entity.model.mob.ShooterEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class ShooterRenderer extends MobEntityRenderer<ShooterEntity, ShooterEntityModel> {

    private static final Identifier TEXTURE = new Identifier("neko-adventure", "textures/entity/mob/shooter.png");

    public ShooterRenderer(EntityRendererFactory.Context context) {
        super(context, new ShooterEntityModel(context.getPart(ModEntityModelLayers.SHOOTER)), 0.5F);
    }

    @Override
    public Identifier getTexture(ShooterEntity entity) {
        return TEXTURE;
    }
}