package com.nekoadventure.entity.entityRender.mob;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.entity.ModEntityModelLayers;
import com.nekoadventure.entity.mob.JarGremlinBodyEntity;
import com.nekoadventure.entity.model.mob.JarGremlinBodyEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class JarGremlinBodyRenderer extends MobEntityRenderer<JarGremlinBodyEntity, JarGremlinBodyEntityModel> {

    private static final Identifier TEXTURE = new Identifier(
            NekoAdventure.MOD_ID, "textures/entity/mob/jar_gremlin.png");

    public JarGremlinBodyRenderer(EntityRendererFactory.Context context) {
        super(context, new JarGremlinBodyEntityModel(context.getPart(ModEntityModelLayers.JAR_GREMLIN_BODY)), 0.4F);
    }

    @Override
    public Identifier getTexture(JarGremlinBodyEntity entity) {
        return TEXTURE;
    }
}
