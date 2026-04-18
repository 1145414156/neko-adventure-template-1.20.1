package com.nekoadventure.entity.entityRender.boss;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.entity.ModEntityModelLayers;
import com.nekoadventure.entity.boss.GrandKnightEntity;
import com.nekoadventure.entity.model.boss.GrandKnightEntityModel;
import com.nekoadventure.entity.model.boss.GrandKnightLevelTwoEntityModel;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class GrandKnightRenderer extends LivingEntityRenderer<GrandKnightEntity, EntityModel<GrandKnightEntity>> {

    private static final Identifier TEXTURE_STAGE_1 =
            new Identifier(NekoAdventure.MOD_ID, "textures/entity/boss/grand_knight_stage_1.png");
    private static final Identifier TEXTURE_STAGE_2 =
            new Identifier(NekoAdventure.MOD_ID, "textures/entity/boss/grand_knight_stage_2.png");

    private final GrandKnightEntityModel normalModel;
    private final GrandKnightLevelTwoEntityModel levelTwoModel;

    public GrandKnightRenderer(EntityRendererFactory.Context ctx) {
        this(ctx, new GrandKnightEntityModel(ctx.getPart(ModEntityModelLayers.GRAND_KNIGHT_1)),
                new GrandKnightLevelTwoEntityModel(ctx.getPart(ModEntityModelLayers.GRAND_KNIGHT_2)));
    }

    private GrandKnightRenderer(EntityRendererFactory.Context ctx,
                                GrandKnightEntityModel normalModel,
                                GrandKnightLevelTwoEntityModel levelTwoModel) {
        super(ctx, normalModel, 0.5F);
        this.normalModel = normalModel;
        this.levelTwoModel = levelTwoModel;
    }

    @Override
    public Identifier getTexture(GrandKnightEntity entity) {
        return entity.getStage() == 1 ? TEXTURE_STAGE_1 : TEXTURE_STAGE_2;
    }

    @Override
    public void render(GrandKnightEntity entity, float yaw, float tickDelta,
                       MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        this.model = entity.getStage() == 1 ? normalModel : levelTwoModel;
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }
}
