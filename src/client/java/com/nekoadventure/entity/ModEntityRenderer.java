package com.nekoadventure.entity;

import com.nekoadventure.entity.entityRender.boss.GrandKnightRenderer;
import com.nekoadventure.entity.entityRender.boss.HugeSlimeRenderer;
import com.nekoadventure.entity.entityRender.boss.PriestSkeletonRenderer;
import com.nekoadventure.entity.entityRender.missile.MissileEntityRenderer;
import com.nekoadventure.entity.entityRender.mob.*;
import com.nekoadventure.entity.model.boss.GrandKnightEntityModel;
import com.nekoadventure.entity.model.boss.GrandKnightLevelTwoEntityModel;
import com.nekoadventure.entity.model.boss.HugeSlimeEntityModel;
import com.nekoadventure.entity.model.boss.PriestSkeletonEntityModel;
import com.nekoadventure.entity.model.missile.BoomerangEntityModel;
import com.nekoadventure.entity.model.missile.BrimstoneEntityModel;
import com.nekoadventure.entity.model.missile.JoyeuseEntityModel;
import com.nekoadventure.entity.model.mob.*;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class ModEntityRenderer {
    public static void register() {
        //missile
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.BRIMSTONE, BrimstoneEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.JOYEUSE, JoyeuseEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.BOOMERANG, BoomerangEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.MISSILE, MissileEntityRenderer::new);

        //mob
        EntityRendererRegistry.register(ModEntities.TWINE_SOUL, TwineSoulEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.TWINE_SOUL, TwineSoulEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.TREASURE_HUNTER, TreasureHunterEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.TREASURE_HUNTER, TreasureHunterEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.MUDDY_SPIDER, MuddySpiderEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.MUDDY_SPIDER, MuddySpiderEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.STONE_GOLEM, StoneGolemRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.STONE_GOLEM, StoneGolemEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.MINDER, MinderRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.MINDER, MinderEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.SHOOTER, ShooterRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.SHOOTER, ShooterEntityModel::getTexturedModelData);

        //boss
        EntityRendererRegistry.register(ModEntities.HUGE_SLIME, HugeSlimeRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.HUGE_SLIME_INNER, HugeSlimeEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.HUGE_SLIME_OUTER, HugeSlimeEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.PRIEST_SKELETON, PriestSkeletonRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.PRIEST_SKELETON, PriestSkeletonEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.GRAND_KNIGHT, GrandKnightRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.GRAND_KNIGHT_1, GrandKnightEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.GRAND_KNIGHT_2, GrandKnightLevelTwoEntityModel::getTexturedModelData);
    }
}
