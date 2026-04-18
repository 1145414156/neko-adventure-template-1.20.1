package com.nekoadventure.datagen.model;

import com.nekoadventure.block.ModBlocks;
import com.nekoadventure.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
     //简单的方块模型
     blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.GATE_BLOCK);
     blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SPAWN_MOB_BLOCK);
     blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.MAZE_BLOCK);
     blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.MAZE_STRUCTURE_BLOCK);
     blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.TREASURE_ROOM_BLOCK);
     blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.START_ROOM_BLOCK);
     blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SHOP_ROOM_BLOCK);
     blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.GAMBLE_ROOM_BLOCK);
     blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.BOSS_ROOM_BLOCK);
     blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.FORGE_ROOM_BLOCK);
     blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SOUL_ROOM_BLOCK);
     blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.FOOD_ROOM_BLOCK);
     blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CONCEAL_ROOM_BLOCK);

     //自定义的方块模型
     blockStateModelGenerator.registerSimpleState(ModBlocks.TP_NEXT_LEVEL_BLOCK);
     blockStateModelGenerator.registerSimpleState(ModBlocks.ITEM_BASE_BLOCK);
     blockStateModelGenerator.registerSimpleState(ModBlocks.WISH_POOL_BLOCK);
     blockStateModelGenerator.registerSimpleState(ModBlocks.SLOT_MACHINE_BLOCK);
     blockStateModelGenerator.registerSimpleState(ModBlocks.ROLL_ITEM_BLOCK);
     blockStateModelGenerator.registerSimpleState(ModBlocks.TP_DUNGEON_BLOCK);
     blockStateModelGenerator.registerSimpleState(ModBlocks.RESET_FURNACE_BLOCK);
     blockStateModelGenerator.registerSimpleState(ModBlocks.REROLL_FURNACE_BLOCK);
     blockStateModelGenerator.registerSimpleState(ModBlocks.MONUMENT_BLOCK);
    }
    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        //简单的物品模型
        itemModelGenerator.register(ModItems.EMERGENCY_FOOD, Models.GENERATED);
        itemModelGenerator.register(ModItems.PROP_PROTOTYPE, Models.GENERATED);
        itemModelGenerator.register(ModItems.NEKO_PACKAGE, Models.GENERATED);
        itemModelGenerator.register(ModItems.COIN, Models.GENERATED);
        itemModelGenerator.register(ModItems.NEKO_FOOD_CAN, Models.GENERATED);
        itemModelGenerator.register(ModItems.UNRIPE_APPLE, Models.GENERATED);
        itemModelGenerator.register(ModItems.CATNIP, Models.GENERATED);
        itemModelGenerator.register(ModItems.UMBRELLA, Models.GENERATED);
        itemModelGenerator.register(ModItems.BRANCH, Models.GENERATED);
        itemModelGenerator.register(ModItems.CROWN, Models.GENERATED);
        itemModelGenerator.register(ModItems.BROKEN_SWORD_HILT, Models.GENERATED);
        itemModelGenerator.register(ModItems.OIL_BOTTLE,Models.GENERATED);
        itemModelGenerator.register(ModItems.UNDERCOOKED_LONG_BEANS,Models.GENERATED);
        itemModelGenerator.register(ModItems.TOMATO,Models.GENERATED);
        itemModelGenerator.register(ModItems.GLOVE,Models.GENERATED);
        itemModelGenerator.register(ModItems.QUENCHING_CHAIN,Models.GENERATED);
        itemModelGenerator.register(ModItems.MILK,Models.GENERATED);
        itemModelGenerator.register(ModItems.HUGE_SNOWBALL,Models.GENERATED);
        itemModelGenerator.register(ModItems.BROKEN_SOUL,Models.GENERATED);
        itemModelGenerator.register(ModItems.SLASH,Models.GENERATED);
        itemModelGenerator.register(ModItems.SULFUR_BLOCK,Models.GENERATED);
        itemModelGenerator.register(ModItems.NAGA_SCALE,Models.GENERATED);
        itemModelGenerator.register(ModItems.BAMBOO_SHOOT,Models.GENERATED);
        itemModelGenerator.register(ModItems.WING,Models.GENERATED);
        itemModelGenerator.register(ModItems.SHIRT,Models.GENERATED);
        itemModelGenerator.register(ModItems.SILVER_ARMOR,Models.GENERATED);
        itemModelGenerator.register(ModItems.DAMAGED_BRACER,Models.GENERATED);
        itemModelGenerator.register(ModItems.TACTICAL_ARMOR,Models.GENERATED);
        itemModelGenerator.register(ModItems.TRAINING_DAGGER,Models.GENERATED);
        itemModelGenerator.register(ModItems.SKELETON_PRIEST_CHIN,Models.GENERATED);
        itemModelGenerator.register(ModItems.TREE_TRUNK,Models.GENERATED);
        itemModelGenerator.register(ModItems.CAT_TREAT,Models.GENERATED);
        itemModelGenerator.register(ModItems.DELICIOUS_DRIED_FISH,Models.GENERATED);
        itemModelGenerator.register(ModItems.MANDRAKE,Models.GENERATED);
        itemModelGenerator.register(ModItems.TOAST,Models.GENERATED);
        itemModelGenerator.register(ModItems.CAT_TEACUP,Models.GENERATED);
        itemModelGenerator.register(ModItems.OXIDIZED_COPPER_INGOT,Models.GENERATED);
        itemModelGenerator.register(ModItems.NATTO,Models.GENERATED);
        itemModelGenerator.register(ModItems.POPSICLE,Models.GENERATED);
        itemModelGenerator.register(ModItems.A_PIECE_OF_CAKE,Models.GENERATED);
        itemModelGenerator.register(ModItems.LUNCH_POX, Models.GENERATED);
        itemModelGenerator.register(ModItems.NIGHT_VISION, Models.GENERATED);
        itemModelGenerator.register(ModItems.CACTUS_BALL, Models.GENERATED);
        itemModelGenerator.register(ModItems.ENCHANTED_GOLDEN_APPLE_SLICE, Models.GENERATED);
        itemModelGenerator.register(ModItems.STRANGE_POTION, Models.GENERATED);
        itemModelGenerator.register(ModItems.IODOPHOR, Models.GENERATED);
        itemModelGenerator.register(ModItems.BANDAGE, Models.GENERATED);
        itemModelGenerator.register(ModItems.LAVA_WALKER, Models.GENERATED);
        itemModelGenerator.register(ModItems.BLOODSTAINED_CROSS, Models.GENERATED);
        itemModelGenerator.register(ModItems.RED_SCARF, Models.GENERATED);
        itemModelGenerator.register(ModItems.TACTICAL_VEST, Models.GENERATED);
        itemModelGenerator.register(ModItems.HANGER, Models.GENERATED);
        itemModelGenerator.register(ModItems.PIERCING_DIAMOND, Models.GENERATED);
        itemModelGenerator.register(ModItems.CAT_CLAW, Models.GENERATED);
        itemModelGenerator.register(ModItems.EXPOSED_WIRE, Models.GENERATED);
        itemModelGenerator.register(ModItems.BRIMSTONE, Models.GENERATED);
        itemModelGenerator.register(ModItems.JOYEUSE, Models.GENERATED);
        itemModelGenerator.register(ModItems.LANCE, Models.GENERATED);
        itemModelGenerator.register(ModItems.PARRY, Models.GENERATED);
        itemModelGenerator.register(ModItems.SWIRL, Models.GENERATED);
        itemModelGenerator.register(ModItems.TWIST, Models.GENERATED);
        itemModelGenerator.register(ModItems.BOOMERANG, Models.GENERATED);
        itemModelGenerator.register(ModItems.ACCELERATION, Models.GENERATED);
        itemModelGenerator.register(ModItems.DECELERATION, Models.GENERATED);
        itemModelGenerator.register(ModItems.ZENITH, Models.GENERATED);
        itemModelGenerator.register(ModItems.BULLET, Models.GENERATED);
        itemModelGenerator.register(ModItems.THE_THIRD_EYE,Models.GENERATED);
        itemModelGenerator.register(ModItems.BLADE, Models.GENERATED);
        itemModelGenerator.register(ModItems.SOUL_JAR, Models.GENERATED);
        itemModelGenerator.register(ModItems.PURENESS_SOUL, Models.GENERATED);
        itemModelGenerator.register(ModItems.FEVER,Models.GENERATED);
        itemModelGenerator.register(ModItems.SPIRITING_AWAY,Models.GENERATED);
        itemModelGenerator.register(ModItems.POKER,Models.GENERATED);
        itemModelGenerator.register(ModItems.BLINDER,Models.GENERATED);
        itemModelGenerator.register(ModItems.SODA,Models.GENERATED);
        itemModelGenerator.register(ModItems.CULLET,Models.GENERATED);
        itemModelGenerator.register(ModItems.A_BUNDLE_OF_TNT,Models.GENERATED);
        itemModelGenerator.register(ModItems.STRENGTH_POTION_INJECTOR,Models.GENERATED);
        itemModelGenerator.register(ModItems.STICKY_BALL,Models.GENERATED);
        itemModelGenerator.register(ModItems.WHITE_PHOSPHORUS,Models.GENERATED);
        itemModelGenerator.register(ModItems.CASTER_SUGAR,Models.GENERATED);
        itemModelGenerator.register(ModItems.VENOMOUS_FANG,Models.GENERATED);
        itemModelGenerator.register(ModItems.BLOOD_SACRIFICE,Models.GENERATED);
        itemModelGenerator.register(ModItems.SNOW_BOOTS,Models.GENERATED);
        itemModelGenerator.register(ModItems.BROKEN_MASK,Models.GENERATED);
        itemModelGenerator.register(ModItems.ASH,Models.GENERATED);
        itemModelGenerator.register(ModItems.WITHERED_ROSE_PETALS,Models.GENERATED);
        itemModelGenerator.register(ModItems.BEER,Models.GENERATED);
        itemModelGenerator.register(ModItems.STRANGE_BEER,Models.GENERATED);
        itemModelGenerator.register(ModItems.PURPLE_JUICE,Models.GENERATED);
        itemModelGenerator.register(ModItems.FLOATING_AHOGE,Models.GENERATED);
        itemModelGenerator.register(ModItems.SKELETON_PRIEST_JEWEL,Models.GENERATED);
        itemModelGenerator.register(ModItems.CRUSHED_ICE,Models.GENERATED);
        itemModelGenerator.register(ModItems.BANANA,Models.GENERATED);
        itemModelGenerator.register(ModItems.ONION,Models.GENERATED);
        itemModelGenerator.register(ModItems.COMPRESSED_IRON,Models.GENERATED);
        itemModelGenerator.register(ModItems.CHOCOLATE_BISCUIT,Models.GENERATED);
        itemModelGenerator.register(ModItems.THE_FOURTH_EYE,Models.GENERATED);

        itemModelGenerator.register(ModItems.INSTANCE_HEALTH_SOUL,Models.GENERATED);
        itemModelGenerator.register(ModItems.TP_START_ROOM_SOUL,Models.GENERATED);
        itemModelGenerator.register(ModItems.GET_LAST_POOL_SOUL,Models.GENERATED);
        itemModelGenerator.register(ModItems.GET_FIRST_POOL_SOUL,Models.GENERATED);
        itemModelGenerator.register(ModItems.GET_CONCEAL_POOL_SOUL,Models.GENERATED);
        itemModelGenerator.register(ModItems.GET_SHOP_POOL_SOUL,Models.GENERATED);
        itemModelGenerator.register(ModItems.GET_THIRD_POOL_SOUL,Models.GENERATED);
        itemModelGenerator.register(ModItems.GET_APART_POOL_SOUL,Models.GENERATED);
        itemModelGenerator.register(ModItems.GET_CHAO_POOL_SOUL,Models.GENERATED);
        itemModelGenerator.register(ModItems.ORIGIN_SOUL,Models.GENERATED);
        itemModelGenerator.register(ModItems.DESIRE_SOUL,Models.GENERATED);
        itemModelGenerator.register(ModItems.GREEDY_SOUL,Models.GENERATED);
        itemModelGenerator.register(ModItems.RESET_SOUL,Models.GENERATED);


        itemModelGenerator.register(ModItems.BROKEN_SWORD,Models.GENERATED);
        itemModelGenerator.register(ModItems.BINDING_SOUL_SUBSTANCE,Models.GENERATED);

        //复杂的物品模型
    }
}
