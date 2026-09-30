package com.nekoadventure.datagen.language;

import com.nekoadventure.block.ModBlocks;
import com.nekoadventure.effect.ModStatusEffects;
import com.nekoadventure.entity.ModEntities;
import com.nekoadventure.item.ModItems;
import com.nekoadventure.item.itemGroup.ModItemGroups;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class ModENLanguageProvider extends FabricLanguageProvider {
    public ModENLanguageProvider(FabricDataOutput dataOutput) {
        super(dataOutput,"en_us");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(ModItems.EMERGENCY_FOOD,"emergency_food");
        translationBuilder.add(ModItems.NEKO_PACKAGE,"neko_package");
        translationBuilder.add(ModItems.PROP_PROTOTYPE,"prop_prototype");
        translationBuilder.add(ModItems.COIN,"coin");
        translationBuilder.add(ModItems.NEKO_FOOD_CAN,"neko_food_can");
        translationBuilder.add(ModItems.UNRIPE_APPLE,"unripe_apple");
        translationBuilder.add(ModItems.CATNIP,"catnip");
        translationBuilder.add(ModItems.UMBRELLA,"umbrella");
        translationBuilder.add(ModItems.BRANCH,"branch");
        translationBuilder.add(ModItems.CROWN,"crown");
        translationBuilder.add(ModItems.BROKEN_SWORD_HILT,"broken_sword_hilt");
        translationBuilder.add(ModItems.OIL_BOTTLE,"oil_bottle");
        translationBuilder.add(ModItems.UNDERCOOKED_LONG_BEANS,"undercooked_long_beans");
        translationBuilder.add(ModItems.TOMATO,"tomato");
        translationBuilder.add(ModItems.GLOVE,"glove");
        translationBuilder.add(ModItems.QUENCHING_CHAIN,"quenching_chain");
        translationBuilder.add(ModItems.MILK,"milk");
        translationBuilder.add(ModItems.HUGE_SNOWBALL,"huge_snowball");
        translationBuilder.add(ModItems.BROKEN_SOUL,"broken_soul");
        translationBuilder.add(ModItems.SLASH,"/");
        translationBuilder.add(ModItems.SULFUR_BLOCK,"sulfur_block");
        translationBuilder.add(ModItems.NAGA_SCALE,"naga_scale");
        translationBuilder.add(ModItems.BAMBOO_SHOOT,"bamboo_shoot");
        translationBuilder.add(ModItems.WING,"wing");
        translationBuilder.add(ModItems.SHIRT,"shirt");
        translationBuilder.add(ModItems.SILVER_ARMOR,"silver_armor");
        translationBuilder.add(ModItems.DAMAGED_BRACER,"damaged_bracer");
        translationBuilder.add(ModItems.TACTICAL_ARMOR,"tactical_armor");
        translationBuilder.add(ModItems.TRAINING_DAGGER,"training_dagger");
        translationBuilder.add(ModItems.SKELETON_PRIEST_CHIN,"skeleton_priest_chin");
        translationBuilder.add(ModItems.TREE_TRUNK,"tree_trunk");
        translationBuilder.add(ModItems.CAT_TREAT,"cat_treat");
        translationBuilder.add(ModItems.DELICIOUS_DRIED_FISH,"delicious_dried_fish");
        translationBuilder.add(ModItems.MANDRAKE,"mandrake");
        translationBuilder.add(ModItems.TOAST,"toast");
        translationBuilder.add(ModItems.CAT_TEACUP,"cat_teacup");
        translationBuilder.add(ModItems.OXIDIZED_COPPER_INGOT,"oxidized_copper_ingot");
        translationBuilder.add(ModItems.NATTO,"natto");
        translationBuilder.add(ModItems.POPSICLE,"popsicle");
        translationBuilder.add(ModItems.A_PIECE_OF_CAKE,"a_piece_of_cake");
        translationBuilder.add(ModItems.JUG,"jug");
        translationBuilder.add(ModItems.LUNCH_POX,"lunch_pox");
        translationBuilder.add(ModItems.NIGHT_VISION,"night_vision");
        translationBuilder.add(ModItems.CACTUS_BALL,"cactus_ball");
        translationBuilder.add(ModItems.ENCHANTED_GOLDEN_APPLE_SLICE,"enchanted_golden_apple_slice");
        translationBuilder.add(ModItems.STRANGE_POTION,"strange_potion");
        translationBuilder.add(ModItems.IODOPHOR,"iodophor");
        translationBuilder.add(ModItems.BANDAGE,"bandage");
        translationBuilder.add(ModItems.LAVA_WALKER,"lava_walker");
        translationBuilder.add(ModItems.BLOODSTAINED_CROSS,"bloodstained_cross");
        translationBuilder.add(ModItems.RED_SCARF,"red_scarf");
        translationBuilder.add(ModItems.TACTICAL_VEST,"tactical_vest");
        translationBuilder.add(ModItems.HANGER,"hanger");
        translationBuilder.add(ModItems.EXPOSED_WIRE,"exposed_wire");
        translationBuilder.add(ModItems.BULLET,"bullet");
        translationBuilder.add(ModItems.PIERCING_DIAMOND,"piercing_diamond");
        translationBuilder.add(ModItems.CAT_CLAW,"cat_claw");
        translationBuilder.add(ModItems.THE_THIRD_EYE,"the_third_eye");
        translationBuilder.add(ModItems.FEVER,"fever");
        translationBuilder.add(ModItems.BRIMSTONE,"brimstone");
        translationBuilder.add(ModItems.JOYEUSE,"joyeuse");
        translationBuilder.add(ModItems.LANCE,"lance");
        translationBuilder.add(ModItems.PARRY,"parry");
        translationBuilder.add(ModItems.SWIRL,"swirl");
        translationBuilder.add(ModItems.TWIST,"twist");
        translationBuilder.add(ModItems.BOOMERANG,"boomerang");
        translationBuilder.add(ModItems.ACCELERATION,"acceleration");
        translationBuilder.add(ModItems.DECELERATION,"deceleration");
        translationBuilder.add(ModItems.ZENITH,"zenith");
        translationBuilder.add(ModItems.SHORT_HOOK,"short_hook");
        translationBuilder.add(ModItems.SPIRITING_AWAY,"spiriting_away");
        translationBuilder.add(ModItems.BLADE,"blade");
        translationBuilder.add(ModItems.SOUL_JAR,"soul_jar");
        translationBuilder.add(ModItems.PURENESS_SOUL,"pureness_soul");
        translationBuilder.add(ModItems.POKER,"poker");
        translationBuilder.add(ModItems.BLINDER,"blinder");
        translationBuilder.add(ModItems.SODA,"soda");
        translationBuilder.add(ModItems.CULLET,"cullet");
        translationBuilder.add(ModItems.A_BUNDLE_OF_TNT,"a_bundle_of_tnt");
        translationBuilder.add(ModItems.STRENGTH_POTION_INJECTOR,"strength_potion_injection");
        translationBuilder.add(ModItems.STICKY_BALL,"sticky_ball");
        translationBuilder.add(ModItems.WHITE_PHOSPHORUS,"white_phosphorus");
        translationBuilder.add(ModItems.CASTER_SUGAR,"caster_sugar");
        translationBuilder.add(ModItems.VENOMOUS_FANG,"venomous_fang");
        translationBuilder.add(ModItems.BLOOD_SACRIFICE,"blood_sacrifice");
        translationBuilder.add(ModItems.SNOW_BOOTS,"snow_boots");
        translationBuilder.add(ModItems.BROKEN_MASK,"broken_mask");
        translationBuilder.add(ModItems.ASH,"ash");
        translationBuilder.add(ModItems.WITHERED_ROSE_PETALS,"withered_rose_petals");
        translationBuilder.add(ModItems.BEER,"beer");
        translationBuilder.add(ModItems.STRANGE_BEER,"strange_beer");
        translationBuilder.add(ModItems.PURPLE_JUICE,"purple_juice");
        translationBuilder.add(ModItems.FLOATING_AHOGE,"floating_ahoge");
        translationBuilder.add(ModItems.SKELETON_PRIEST_JEWEL,"skeleton_priest_jewel");
        translationBuilder.add(ModItems.CRUSHED_ICE,"crushed_ice");
        translationBuilder.add(ModItems.BANANA,"banana");
        translationBuilder.add(ModItems.ONION,"onion");
        translationBuilder.add(ModItems.CHOCOLATE_BISCUIT,"chocolate_biscuit");
        translationBuilder.add(ModItems.COMPRESSED_IRON,"compressed_iron");
        translationBuilder.add(ModItems.THE_FOURTH_EYE,"the_fourth_eye");
        translationBuilder.add(ModItems.BINDING_SOUL_SUBSTANCE,"binding_soul_substance");
        translationBuilder.add(ModItems.BROKEN_SWORD,"broken_sword");
        translationBuilder.add(ModItems.RESTOCK,"restock");
        translationBuilder.add(ModItems.ABUNDANT_ITEM_BASE,"abundant_item_base");
        translationBuilder.add(ModItems.CLOUD_BOOTS,"cloud_boots");
        translationBuilder.add(ModItems.ADRENALINE,"adrenaline");
        translationBuilder.add(ModItems.BATTERY,"battery");
        translationBuilder.add(ModItems.BROKEN_CROWN,"broken_crown");

        translationBuilder.add(ModItems.INSTANCE_HEALTH_SOUL,"instance_health_soul");
        translationBuilder.add(ModItems.TP_START_ROOM_SOUL,"tp_start_room_soul");
        translationBuilder.add(ModItems.GET_CONCEAL_POOL_SOUL,"get_conceal_pool_soul");
        translationBuilder.add(ModItems.GET_SHOP_POOL_SOUL,"get_shop_pool_soul");
        translationBuilder.add(ModItems.GET_THIRD_POOL_SOUL,"get_third_pool_soul");
        translationBuilder.add(ModItems.GET_APART_POOL_SOUL,"get_apart_pool_soul");
        translationBuilder.add(ModItems.GET_CHAO_POOL_SOUL,"get_chao_pool_soul");
        translationBuilder.add(ModItems.ORIGIN_SOUL,"origin_soul");
        translationBuilder.add(ModItems.DESIRE_SOUL,"desire_soul");
        translationBuilder.add(ModItems.GREEDY_SOUL,"greedy_soul");
        translationBuilder.add(ModItems.RESET_SOUL,"reset_soul");
        translationBuilder.add(ModItems.GET_FIRST_POOL_SOUL,"get_first_pool_soul");
        translationBuilder.add(ModItems.GET_LAST_POOL_SOUL,"get_last_pool_soul");

        translationBuilder.add("sounds.neko_adventure.electricity_run_through","electricity_run_through");

        translationBuilder.add(ModStatusEffects.MAZE_CURSE,"maze_curse");
        translationBuilder.add(ModStatusEffects.BOSS_FIGHT,"boss_fight");

        translationBuilder.add(ModBlocks.GATE_BLOCK,"gate_block");
        translationBuilder.add(ModBlocks.MAZE_BLOCK,"maze_block");
        translationBuilder.add(ModBlocks.SPAWN_MOB_BLOCK,"spawn_mob_block");
        translationBuilder.add(ModBlocks.ITEM_BASE_BLOCK,"item_base_block");
        translationBuilder.add(ModBlocks.TP_NEXT_LEVEL_BLOCK,"tp_next_level_block");
        translationBuilder.add(ModBlocks.MAZE_ROOM_STAGE_BLOCK,"maze_room_stage_block");
        translationBuilder.add(ModBlocks.TREASURE_ROOM_BLOCK,"treasure_room_block");
        translationBuilder.add(ModBlocks.START_ROOM_BLOCK,"start_room_block");
        translationBuilder.add(ModBlocks.SHOP_ROOM_BLOCK,"shop_room_block");
        translationBuilder.add(ModBlocks.GAMBLE_ROOM_BLOCK,"gamble_room_block");
        translationBuilder.add(ModBlocks.BOSS_ROOM_BLOCK,"boss_room_block");
        translationBuilder.add(ModBlocks.FORGE_ROOM_BLOCK,"forge_room_block");
        translationBuilder.add(ModBlocks.SOUL_ROOM_BLOCK,"soul_room_block");
        translationBuilder.add(ModBlocks.FOOD_ROOM_BLOCK,"food_room_block");
        translationBuilder.add(ModBlocks.CONCEAL_ROOM_BLOCK,"conceal_room_block");
        translationBuilder.add(ModBlocks.WISH_POOL_BLOCK,"wish_pool_block");
        translationBuilder.add(ModBlocks.SLOT_MACHINE_BLOCK,"slot_machine_block");
        translationBuilder.add(ModBlocks.ROLL_ITEM_BLOCK,"roll_item_block");
        translationBuilder.add(ModBlocks.RESET_FURNACE_BLOCK,"reset_furnace_block");
        translationBuilder.add(ModBlocks.REROLL_FURNACE_BLOCK,"reroll_furnace_block");
        translationBuilder.add(ModBlocks.MONUMENT_BLOCK,"monument_block");

        translationBuilder.add(ModItemGroups.NEKO_GROUP,"neko_group");
        translationBuilder.add(ModItemGroups.NUM_ITEM_GROUP,"num_item_group");
        translationBuilder.add(ModItemGroups.SOUL_ITEM_GROUP,"soul_item_group");
        translationBuilder.add(ModItemGroups.MAZE_GROUP,"maze_group");

        translationBuilder.add(ModEntities.HUGE_SLIME,"huge_slime");
        translationBuilder.add(ModEntities.PRIEST_SKELETON,"priest_skeleton");

        //按键绑定
        translationBuilder.add("key.neko-adventure.main_attack_type","Apply Main Attack");
    }
}
