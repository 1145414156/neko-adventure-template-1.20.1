package com.nekoadventure.entity;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.entity.boss.*;
import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.entity.mob.*;
import com.nekoadventure.entity.particle.AbstractParticleEntity;
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

    //other
    public static final EntityType<MissileEntity> MISSILE = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(NekoAdventure.MOD_ID, "missile"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC,
                            (EntityType<MissileEntity> entityType, World world) -> new MissileEntity(entityType, world))
                    .dimensions(EntityDimensions.fixed(0.2f, 0.2f))
                    .disableSummon()
                    .build()
    );
    public static final EntityType<AbstractParticleEntity> NEKO_PARTICLE_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(NekoAdventure.MOD_ID, "neko_particle"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC,
                            (EntityType<AbstractParticleEntity> entityType, World world) -> new AbstractParticleEntity(entityType, world))
                    .dimensions(EntityDimensions.fixed(0.0f, 0.0f))
                    .disableSummon()
                    .build()
    );

    //mob
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
    public static final EntityType<ScarabBeetleEntity> SCARAB_BEETLE=Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(NekoAdventure.MOD_ID,"scarab_beetle"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER,
                    ScarabBeetleEntity::new)
                    .dimensions(EntityDimensions.fixed(0.99f,0.5f)).build()
    );
    public static final EntityType<JarGremlinEntity> JAR_GREMLIN=Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(NekoAdventure.MOD_ID,"jar_gremlin"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER,
                    JarGremlinEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f,1.01f)).build()
    );
    public static final EntityType<JarGremlinHeadEntity> JAR_GREMLIN_HEAD =
            Registry.register(Registries.ENTITY_TYPE,
                    new Identifier(NekoAdventure.MOD_ID, "jar_gremlin_head"),
                    FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, JarGremlinHeadEntity::new)
                            .dimensions(EntityDimensions.fixed(0.51f, 0.49f))
                            .disableSummon()
                            .build());
    public static final EntityType<JarGremlinBodyEntity> JAR_GREMLIN_BODY =
            Registry.register(Registries.ENTITY_TYPE,
                    new Identifier(NekoAdventure.MOD_ID, "jar_gremlin_body"),
                    FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, JarGremlinBodyEntity::new)
                            .dimensions(EntityDimensions.fixed(0.51f, 0.75f))
                            .disableSummon()
                            .build());

    public static final EntityType<CobraEntity> COBRA =Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(NekoAdventure.MOD_ID,"cobra"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER,
                            CobraEntity::new)
                    .dimensions(EntityDimensions.fixed(0.99f,0.99f)).build()
    );
    public static final EntityType<ChestMimicEntity> CHEST_MIMIC=Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(NekoAdventure.MOD_ID,"chest_mimic"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER,
                    ChestMimicEntity::new)
                    .dimensions(EntityDimensions.fixed(0.99f,0.99f)).build()
    );
    public static final EntityType<CoinMonsterEntity> COIN_MONSTER=Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(NekoAdventure.MOD_ID,"coin_monster"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER,
                            CoinMonsterEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f,0.75f)).build()
    );


    //boss
    public static final EntityType<HugeSlimeEntity> HUGE_SLIME=Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(NekoAdventure.MOD_ID,"huge_slime"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER,
                            HugeSlimeEntity::new)
                    .dimensions(EntityDimensions.fixed(3.99f, 3.99f)).build()
    );
    public static final EntityType<PriestSkeletonEntity> PRIEST_SKELETON=Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(NekoAdventure.MOD_ID,"priest_skeleton"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER,
                            PriestSkeletonEntity::new)
                    .dimensions(EntityDimensions.fixed(0.9f, 2.99f)).build()
    );
    public static final EntityType<GrandKnightEntity> GRAND_KNIGHT=Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(NekoAdventure.MOD_ID,"grand_knight"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER,
                            GrandKnightEntity::new)
                    .dimensions(EntityDimensions.fixed(1.4f, 3.6f)).build()
    );
    public static final EntityType<SandWormEntity> SAND_WORM=Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(NekoAdventure.MOD_ID,"sand_worm"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER,
                            SandWormEntity::new)
                    .dimensions(EntityDimensions.fixed(2.79f, 2.99f))
                    .build()
    );
    public static final EntityType<SandWormBodyEntity> SAND_WORM_BODY=Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(NekoAdventure.MOD_ID,"sand_worm_body"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER,
                            SandWormBodyEntity::new)
                    .dimensions(EntityDimensions.fixed(2.99f, 2.49f))
                    .disableSummon()
                    .build()
    );

    public static void initialize(){
        FabricDefaultAttributeRegistry.register(ModEntities.TWINE_SOUL, TwineSoulEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.TREASURE_HUNTER, TreasureHunterEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.MUDDY_SPIDER, MuddySpiderEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.STONE_GOLEM,StoneGolemEntity.createStoneGolemAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.MINDER,MinderEntity.createMinderAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.SHOOTER, ShooterEntity.createShooterAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.SCARAB_BEETLE,ScarabBeetleEntity.createScarabBeetleAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.JAR_GREMLIN,JarGremlinEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.COBRA,CobraEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.JAR_GREMLIN_HEAD,JarGremlinHeadEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.JAR_GREMLIN_BODY,JarGremlinBodyEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.CHEST_MIMIC,ChestMimicEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.COIN_MONSTER,CoinMonsterEntity.createAttributes());

        FabricDefaultAttributeRegistry.register(ModEntities.HUGE_SLIME,HugeSlimeEntity.createHugeSlimeAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.PRIEST_SKELETON,PriestSkeletonEntity.createPriestSkeletonAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.GRAND_KNIGHT,GrandKnightEntity.createGrandKnightAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.SAND_WORM,SandWormEntity.createAttributes());
    }

}
