package com.nekoadventure.entity.entityRender.mob;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.entity.ModEntityModelLayers;
import com.nekoadventure.entity.mob.JarGremlinEntity;
import com.nekoadventure.entity.model.mob.JarGremlinEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class JarGremlinRenderer extends MobEntityRenderer<JarGremlinEntity, JarGremlinEntityModel> {

    private static final Identifier TEXTURE = new Identifier(
            NekoAdventure.MOD_ID, "textures/entity/mob/jar_gremlin.png");

    public JarGremlinRenderer(EntityRendererFactory.Context context) {
        super(context, new JarGremlinEntityModel(context.getPart(ModEntityModelLayers.JAR_GREMLIN)), 0.5f);
    }

    @Override
    public Identifier getTexture(JarGremlinEntity entity) {
        return TEXTURE;
    }
}