package com.nekoadventure.entity;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.entity.boss.GrandKnightEntity;
import com.nekoadventure.entity.boss.HugeSlimeEntity;
import com.nekoadventure.entity.boss.PriestSkeletonEntity;
import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.entity.mob.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ModEntities  {
    public static final EntityType<MissileEntity> MISSILE = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(NekoAdventure.MOD_ID, "missile"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC,
                            (EntityType<MissileEntity> entityType, World world) -> new MissileEntity(entityType, world))
                    .dimensions(EntityDimensions.fixed(0.2f, 0.2f))
                    .build()
    );
    public static final EntityType<TwineSoulEntity> TWINE_SOUL = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(NekoAdventure.MOD_ID,"twine_soul"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER,
                            TwineSoulEntity::new)
                    .dimensions(EntityDimensions.fixed(0.8f,2.0f)).build()
    );

    public static final EntityType<TreasureHunterEntity> TREASURE_HUNTER = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(NekoAdventure.MOD_ID,"treasure_hunter"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER,
                            TreasureHunterEntity::new)
                    .dimensions(EntityDimensions.fixed(0.6f, 1.99f)).build()
    );

    public static final EntityType<MuddySpiderEntity> MUDDY_SPIDER = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(NekoAdventure.MOD_ID,"muddy_spider"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER,
                            MuddySpiderEntity::new)
                    .dimensions(EntityDimensions.fixed(0.75f, 0.6f)).build()
    );
    public static final EntityType<StoneGolemEntity> STONE_GOLEM = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(NekoAdventure.MOD_ID,"stone_golem"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER,
                            StoneGolemEntity::new)
                    .dimensions(EntityDimensions.fixed(1.2f, 2.5f)).build()
    );
    public static final EntityType<MinderEntity> MINDER = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(NekoAdventure.MOD_ID,"minder"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER,
                            MinderEntity::new)
                    .dimensions(EntityDimensions.fixed(0.6f, 1.99f)).build()
    );
    public static final EntityType<ShooterEntity> SHOOTER = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(NekoAdventure.MOD_ID,"shooter"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER,
                            ShooterEntity::new)
                    .dimensions(EntityDimensions.fixed(0.6f, 1.99f)).build()
    );
    public static final EntityType<HugeSlimeEntity> HUGE_SLIME=Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(NekoAdventure.MOD_ID,"huge_slime"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER,
                            HugeSlimeEntity::new)
                    .dimensions(EntityDimensions.fixed(4.0f, 4.0f)).build()
    );
    public static final EntityType<PriestSkeletonEntity> PRIEST_SKELETON=Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(NekoAdventure.MOD_ID,"priest_skeleton"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER,
                            PriestSkeletonEntity::new)
                    .dimensions(EntityDimensions.fixed(0.9f, 3.0f)).build()
    );
    public static final EntityType<GrandKnightEntity> GRAND_KNIGHT=Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(NekoAdventure.MOD_ID,"grand_knight"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER,
                            GrandKnightEntity::new)
                    .dimensions(EntityDimensions.fixed(1.4f, 3.6f)).build()
    );

    public static void initialize(){
        FabricDefaultAttributeRegistry.register(ModEntities.TWINE_SOUL, TwineSoulEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.TREASURE_HUNTER, TreasureHunterEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.MUDDY_SPIDER, MuddySpiderEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.STONE_GOLEM,StoneGolemEntity.createStoneGolemAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.MINDER,MinderEntity.createMinderAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.SHOOTER, ShooterEntity.createShooterAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.HUGE_SLIME,HugeSlimeEntity.createHugeSlimeAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.PRIEST_SKELETON,PriestSkeletonEntity.createPriestSkeletonAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.GRAND_KNIGHT,GrandKnightEntity.createGrandKnightAttributes());
    }

}
