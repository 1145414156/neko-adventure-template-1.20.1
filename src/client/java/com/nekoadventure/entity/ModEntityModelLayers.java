package com.nekoadventure.entity;

import com.nekoadventure.NekoAdventure;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModEntityModelLayers {
    //missile
    public static final EntityModelLayer BULLET =
            new EntityModelLayer(new Identifier(NekoAdventure.MOD_ID, "bullet"), "main");
    public static final EntityModelLayer BRIMSTONE=
            new EntityModelLayer(new Identifier(NekoAdventure.MOD_ID, "brimstone"), "main");
    public static final EntityModelLayer JOYEUSE=
            new EntityModelLayer(new Identifier(NekoAdventure.MOD_ID, "joyeuse"), "main");
    public static final EntityModelLayer BOOMERANG=
            new EntityModelLayer(new Identifier(NekoAdventure.MOD_ID, "boomerang"), "main");

    //mob
    public static final EntityModelLayer TWINE_SOUL=
            new EntityModelLayer(new Identifier(NekoAdventure.MOD_ID,"twine_soul"),"main");
    public static final EntityModelLayer TREASURE_HUNTER=
            new EntityModelLayer(new Identifier(NekoAdventure.MOD_ID,"treasure_hunter"),"main");
    public static final EntityModelLayer MUDDY_SPIDER=
            new EntityModelLayer(new Identifier(NekoAdventure.MOD_ID,"muddy_spider"),"main");
    public static final EntityModelLayer STONE_GOLEM=
            new EntityModelLayer(new Identifier(NekoAdventure.MOD_ID,"stone_golem"),"main");
    public static final EntityModelLayer MINDER=
            new EntityModelLayer(new Identifier(NekoAdventure.MOD_ID,"minder"),"main");
    public static final EntityModelLayer SHOOTER=
            new EntityModelLayer( new Identifier(NekoAdventure.MOD_ID,"shooter"),"main");

    //boss
    public static final EntityModelLayer HUGE_SLIME_INNER =
            new EntityModelLayer(new Identifier(NekoAdventure.MOD_ID, "huge_slime"), "inner");
    public static final EntityModelLayer HUGE_SLIME_OUTER=
            new EntityModelLayer(new Identifier(NekoAdventure.MOD_ID,"huge_slime"),"outer");
    public static final EntityModelLayer PRIEST_SKELETON=
            new EntityModelLayer(new Identifier(NekoAdventure.MOD_ID,"priest_skeleton"),"main");
    public static final EntityModelLayer GRAND_KNIGHT_1=
            new EntityModelLayer(new Identifier(NekoAdventure.MOD_ID,"grand_knight_stage_1"), "main");
    public static final EntityModelLayer GRAND_KNIGHT_2=
            new EntityModelLayer(new Identifier(NekoAdventure.MOD_ID,"grand_knight_stage_2"), "main");
}
