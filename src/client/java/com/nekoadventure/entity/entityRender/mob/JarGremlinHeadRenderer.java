package com.nekoadventure.entity.entityRender.mob;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.entity.ModEntityModelLayers;
import com.nekoadventure.entity.mob.JarGremlinHeadEntity;
import com.nekoadventure.entity.model.mob.JarGremlinHeadEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class JarGremlinHeadRenderer extends MobEntityRenderer<JarGremlinHeadEntity, JarGremlinHeadEntityModel> {

    private static final Identifier TEXTURE = new Identifier(
            NekoAdventure.MOD_ID, "textures/entity/mob/jar_gremlin.png");

    public JarGremlinHeadRenderer(EntityRendererFactory.Context context) {
        super(context, new JarGremlinHeadEntityModel(context.getPart(ModEntityModelLayers.JAR_GREMLIN_HEAD)), 0.3F);
    }

    @Override
    public Identifier getTexture(JarGremlinHeadEntity entity) {
        return TEXTURE;
    }
}
