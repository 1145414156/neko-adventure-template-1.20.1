package com.nekoadventure.item;

import com.nekoadventure.item.nekoItem.NumItem;
import com.nekoadventure.item.nekoItem.attackTypeItem.attackItems.*;
import com.nekoadventure.item.nekoItem.attackTypeItem.specificItems.*;
import com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.damaged.*;
import com.nekoadventure.item.nekoItem.functionTypeItem.functionItems.tick.*;
import com.nekoadventure.item.other.NekoPackageItem;
import com.nekoadventure.item.other.PropPrototypeItem;
import com.nekoadventure.item.soulItem.*;
import net.minecraft.item.FoodComponents;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {

    //其他物品
    public static final Item EMERGENCY_FOOD=register("emergency_food",
            new Item(new Item.Settings().food(ModFoodComponents.EMERGENCY_FOOD)));
    public static final NekoPackageItem NEKO_PACKAGE=register("neko_package",
            new NekoPackageItem(new Item.Settings()));
    public static final Item COIN =register("coin",
            new Item(new Item.Settings().maxCount(16)));
    public static final Item BINDING_SOUL_SUBSTANCE=register("binding_soul_substance",
            new Item(new Item.Settings().maxCount(16)));
    public static final PropPrototypeItem PROP_PROTOTYPE=register("prop_prototype",
            new PropPrototypeItem(new Item.Settings().rarity(Rarity.RARE).maxCount(1)));

    //数值类道具
    public static final NumItem PIERCING_DIAMOND=register("piercing_diamond",
            new NumItem(new Item.Settings().rarity(Rarity.COMMON),0,4,0,0,0,0,0));
    public static final NumItem HANGER=register("hanger",
            new NumItem(new Item.Settings().rarity(Rarity.COMMON),0,0,0,0.7,0,0,0));
    public static final NumItem CAT_CLAW=register("cat_claw",
            new NumItem(new Item.Settings().rarity(Rarity.COMMON),0,0,0,0,4,0,0));
    public static final NumItem PURENESS_SOUL=register("pureness_soul",
            new NumItem(new Item.Settings().rarity(Rarity.RARE),0,5,0.2,2.5,0.5,0,0));
    public static final NumItem SODA=register("soda",
            new NumItem(new Item.Settings().rarity(Rarity.COMMON),0,0,0,0.6,4,0,0));
    public static final NumItem ONION=register("onion",
            new NumItem(new Item.Settings().rarity(Rarity.RARE).food(FoodComponents.APPLE),0,1,2,1.0,0,0,0.3));
    public static final NumItem CHOCOLATE_BISCUIT=register("chocolate_biscuit",
            new NumItem(new Item.Settings().rarity(Rarity.UNCOMMON),3,0,1.5,0.6,0,0,0));
    public static final NumItem COMPRESSED_IRON=register("compress_iron",
            new NumItem(new Item.Settings().rarity(Rarity.RARE),0,4,-0.3,0,0,0.5,-0.3));
    public static final NumItem THE_FOURTH_EYE=register("the_fourth_eye",
            new NumItem(new Item.Settings().rarity(Rarity.RARE), 0, 0, 0, 0, 0, 1, -1));
    public static final NumItem THE_THIRD_EYE=register("the_third_eye",
            new NumItem(new Item.Settings().rarity(Rarity.RARE), 0, 0, 0, 0, 0, 0.5, -0.5));
    public static final NumItem BLINDER=register("blinder",
            new NumItem(new Item.Settings().rarity(Rarity.UNCOMMON), 0, 0, 0, 0, 0, -0.5, 0.5));
    public static final NumItem NEKO_FOOD_CAN=register("neko_food_can",
            new NumItem(new Item.Settings().rarity(Rarity.COMMON).food(FoodComponents.COOKED_BEEF),6,0,0,0,0,0,0));
    public static final NumItem UNRIPE_APPLE=register("unripe_apple",
            new NumItem(new Item.Settings().rarity(Rarity.COMMON).food(FoodComponents.APPLE),0,-0.5,0,0.5,2,0,0));
    public static final NumItem CATNIP=register("catnip",
            new NumItem(new Item.Settings().rarity(Rarity.RARE),0,0.5,0,1.0,2,0,0.3));
    public static final NumItem UMBRELLA=register("umbrella",
            new NumItem(new Item.Settings().rarity(Rarity.COMMON),0,3.0,0,0,5,0,-0.2));
    public static final NumItem BRANCH=register("branch",
            new NumItem(new Item.Settings().rarity(Rarity.UNCOMMON),0,2.0,0,-0.1,4,0,-0.2));
    public static final NumItem CROWN=register("crown",
            new NumItem(new Item.Settings().rarity(Rarity.EPIC),0,5.0,1.0,1.0,5,0.5,0.5));
    public static final NumItem BROKEN_SWORD_HILT=register("broken_sword_hilt",
            new NumItem(new Item.Settings().rarity(Rarity.COMMON),0,-0.5,1.5,0.7,0,0,0));
    public static final NumItem OIL_BOTTLE=register("oil_bottle",
            new NumItem(new Item.Settings().rarity(Rarity.COMMON),0,-0.2,0,-0.2,0,0.2,0.2));
    public static final NumItem UNDERCOOKED_LONG_BEANS=register("undercooked_long_beans",
            new NumItem(new Item.Settings().rarity(Rarity.UNCOMMON).food(FoodComponents.POISONOUS_POTATO),4,-1.0,0,0.5,0,-0.2,0.2));
    public static final NumItem TOMATO=register("tomato",
            new NumItem(new Item.Settings().rarity(Rarity.COMMON).food(FoodComponents.POTATO),2,1.5,0.3,0.5,3,0,0));
    public static final NumItem GLOVE=register("glove",
            new NumItem(new Item.Settings().rarity(Rarity.UNCOMMON),0,1,0,0.2,5,0.3,0));
    public static final NumItem QUENCHING_CHAIN=register("quenching_chain",
            new NumItem(new Item.Settings().rarity(Rarity.COMMON),0,3.0,0,-0.4,3,0,0));
    public static final NumItem MILK=register("milk",
            new NumItem(new Item.Settings().food(FoodComponents.GLOW_BERRIES),0,-10.0,0,2,0,0,0.4));
    public static final NumItem HUGE_SNOWBALL=register("huge_snowball",
            new NumItem(new Item.Settings().rarity(Rarity.COMMON),0,2.0,-0.5,-0.3,0,0,0));
    public static final NumItem BROKEN_SOUL=register("broken_soul",
            new NumItem(new Item.Settings().rarity(Rarity.UNCOMMON),2,0.3,0.5,0.3,2,0.2,0.2));
    public static final NumItem SLASH=register("slash",
            new NumItem(new Item.Settings().rarity(Rarity.EPIC),0,7,0.7,7,7,0.7,0.7));
    public static final NumItem SULFUR_BLOCK=register("sulfur_block",
            new NumItem(new Item.Settings().rarity(Rarity.COMMON),-1.0,2,1.5,0,0,0,0));
    public static final NumItem NAGA_SCALE =register("naga_scale",
            new NumItem(new Item.Settings().rarity(Rarity.UNCOMMON),2,2,1.0,-0.5,3,0,0));
    public static final NumItem BAMBOO_SHOOT=register("bamboo_shoot",
            new NumItem(new Item.Settings().rarity(Rarity.COMMON).food(FoodComponents.CARROT),2.0,1.0,0,0.5,3,0,0));
    public static final NumItem WING=register("wing",
            new NumItem(new Item.Settings().rarity(Rarity.COMMON),0,0,3,0,2,0,0));
    public static final NumItem SHIRT=register("shirt",
            new NumItem(new Item.Settings().rarity(Rarity.UNCOMMON),2,1.5,0,0,3.0,0,0));
    public static final NumItem SILVER_ARMOR=register("silver_armor",
            new NumItem(new Item.Settings().rarity(Rarity.COMMON),5,-0.2,-0.2,0.3,0,0,0));
    public static final NumItem DAMAGED_BRACER=register("damaged_bracer",
            new NumItem(new Item.Settings().rarity(Rarity.COMMON),2.0,0,0,-0.2,0,0,0));
    public static final NumItem TACTICAL_ARMOR=register("tactical_armor",
            new NumItem(new Item.Settings().rarity(Rarity.RARE),10.0,-5,0,-1.5,0,0,0));
    public static final NumItem TRAINING_DAGGER=register("training_dagger",
            new NumItem(new Item.Settings().rarity(Rarity.COMMON),0,1.0,0,0,0,0.2,0));
    public static final NumItem SKELETON_PRIEST_CHIN=register("skeleton_priest_chin",
            new NumItem(new Item.Settings().rarity(Rarity.UNCOMMON),-1.0,2.0,0,1.0,0,0,0));
    public static final NumItem TREE_TRUNK=register("tree_trunk",
            new NumItem(new Item.Settings().rarity(Rarity.UNCOMMON),0,2.0,-0.5,0,4,0.2,0));
    public static final NumItem CAT_TREAT=register("cat_treat",
            new NumItem(new Item.Settings().rarity(Rarity.UNCOMMON).food(FoodComponents.COOKED_SALMON),2.0,1.5,0,0.3,0,0,0.2));
    public static final NumItem DELICIOUS_DRIED_FISH=register("delicious_dried_fish",
            new NumItem(new Item.Settings().rarity(Rarity.UNCOMMON).food(FoodComponents.COOKED_COD),4,0,0.3,0,0,0,0));
    public static final NumItem MANDRAKE=register("mandrake",
            new NumItem(new Item.Settings().rarity(Rarity.COMMON).food(FoodComponents.CARROT),2.0,-0.3,0,0,2,0,0));
    public static final NumItem TOAST=register("toast",
            new NumItem(new Item.Settings().rarity(Rarity.UNCOMMON).food(FoodComponents.BREAD),3.0,0,0,0,3,0,0));
    public static final NumItem CAT_TEACUP=register("cat_teacup",
            new NumItem(new Item.Settings().rarity(Rarity.UNCOMMON),0,0,0,0.5,4,0,0));
    public static final NumItem OXIDIZED_COPPER_INGOT=register("oxidized_copper_ingot",
            new NumItem(new Item.Settings().rarity(Rarity.COMMON),0,1.5,0,-0.1,-1.0,0.3,0));
    public static final NumItem NATTO=register("natto",
            new NumItem(new Item.Settings().rarity(Rarity.EPIC).food(FoodComponents.GOLDEN_APPLE),10.0,0,0.3,0,8,0.2,0));
    public static final NumItem POPSICLE=register("popsicle",
            new NumItem(new Item.Settings().rarity(Rarity.UNCOMMON).food(FoodComponents.MELON_SLICE),3.0,0,0,0.5,0,0,0));
    public static final NumItem A_PIECE_OF_CAKE=register("a_piece_of_cake",
            new NumItem(new Item.Settings().rarity(Rarity.COMMON).food(FoodComponents.COOKIE),1.0,0.2,0,0,0,0,0));


    //功能类道具
    public static final SpiritingAway SPIRITING_AWAY=register("spiriting_away",
            new SpiritingAway(new Item.Settings().rarity(Rarity.RARE)));
    public static final SoulJar SOUL_JAR=register("soul_jar",
            new SoulJar(new Item.Settings().rarity(Rarity.EPIC)));
    public static final Poker POKER=register("poker",
            new Poker(new Item.Settings().rarity(Rarity.RARE)));
    public static final LunchBox LUNCH_POX=register("lunch_box",
            new LunchBox(new Item.Settings().rarity(Rarity.RARE)));
    public static final NightVision NIGHT_VISION=register("night_vision",
            new NightVision(new Item.Settings().rarity(Rarity.UNCOMMON)));
    public static final CactusBall CACTUS_BALL=register("cactus_ball",
            new CactusBall(new Item.Settings().rarity(Rarity.COMMON)));
    public static final EnchantedGoldenAppleSlice ENCHANTED_GOLDEN_APPLE_SLICE=register("enchanted_golden_apple_slice",
            new EnchantedGoldenAppleSlice(new Item.Settings().rarity(Rarity.RARE)));
    public static final StrangePotion STRANGE_POTION=register("strange_potion",
            new StrangePotion(new Item.Settings().rarity(Rarity.COMMON)));
    public static final Iodophor IODOPHOR=register("iodophor",
            new Iodophor(new Item.Settings().rarity(Rarity.UNCOMMON)));
    public static final Bandage BANDAGE=register("bandage",
            new Bandage(new Item.Settings().rarity(Rarity.COMMON)));
    public static final LavaWalker LAVA_WALKER=register("lava_walker",
            new LavaWalker(new Item.Settings().rarity(Rarity.UNCOMMON)));
    public static final BloodstainedCross BLOODSTAINED_CROSS=register("bloodstained_cross",
            new BloodstainedCross(new Item.Settings().rarity(Rarity.RARE)));
    public static final RedScarf RED_SCARF=register("red_scarf",
            new RedScarf(new Item.Settings().rarity(Rarity.RARE)));
    public static final TacticalVest TACTICAL_VEST=register("tactical_vest",
            new TacticalVest(new Item.Settings().rarity(Rarity.UNCOMMON)));
    public static final Restock RESTOCK=register("restock",
            new Restock(new Item.Settings().rarity(Rarity.RARE)));
    public static final AbundantItemBase ABUNDANT_ITEM_BASE=register("abundant_item_base",
            new  AbundantItemBase(new Item.Settings().rarity(Rarity.RARE)));
    public static final CloudBoots CLOUD_BOOTS=register("cloud_boots",
            new CloudBoots(new Item.Settings().rarity(Rarity.UNCOMMON)));
    public static final Adrenaline ADRENALINE=register("adrenaline",
            new Adrenaline(new Item.Settings().rarity(Rarity.RARE)));
    public static final Battery BATTERY=register("battery",
            new Battery(new Item.Settings().rarity(Rarity.UNCOMMON)));

    //攻击方式类道具
    public static final ExposedWire EXPOSED_WIRE=register("exposed_wire",
            new ExposedWire(new Item.Settings().rarity(Rarity.EPIC),0,1,0,0,4,0.2,-0.3,false));
    public static final Bullet BULLET=register("bullet",
            new Bullet(new Item.Settings().rarity(Rarity.RARE),0,0,0,0,4,0,0,false));
    public static final BrimStone BRIMSTONE=register("brimstone",
            new BrimStone(new Item.Settings().rarity(Rarity.EPIC),0,-3,0,0,5,0.5,0,false));
    public static final Joyeuse JOYEUSE=register("joyeuse",
            new Joyeuse(new Item.Settings().rarity(Rarity.EPIC),0,2,0,0,3,0.3,0.1,false));
    public static final Lance LANCE=register("lance",
            new Lance(new Item.Settings().rarity(Rarity.RARE),0,2,0,-0.3,4,0,-0.2,false));
    public static final Parry PARRY=register("parry",
            new Parry(new Item.Settings().rarity(Rarity.UNCOMMON),0,0.5,1.3,0.2,0,0,0,false));
    public static final Swirl SWIRL=register("swirl",
            new Swirl(new Item.Settings().rarity(Rarity.COMMON),0,0,0,0,0,0,0,false));
    public static final Twist TWIST=register("twist",
            new Twist(new Item.Settings().rarity(Rarity.COMMON),0,0,0,0,0,0,0,false));
    public static final Boomerang BOOMERANG=register("boomerang",
            new Boomerang(new Item.Settings().rarity(Rarity.RARE),0,0,0.5,1.0,5,0,0,false));
    public static final Acceleration ACCELERATION=register("acceleration",
            new Acceleration(new Item.Settings().rarity(Rarity.COMMON),0,0,0,0,0,0,0,false));
    public static final Deceleration DECELERATION=register("deceleration",
            new Deceleration(new Item.Settings().rarity(Rarity.COMMON),0,0,0,0,0,0,0,false));
    public static final Zenith ZENITH=register("zenith",
            new Zenith(new Item.Settings().rarity(Rarity.EPIC),0,2.0,0.5,0,0,0.4,0,false));
    public static final ShortHook SHORT_HOOK=register("short_hook",
            new ShortHook(new Item.Settings().rarity(Rarity.UNCOMMON),0,0.3,0.5,-0.3,-1,0,0,false));

    //特效类道具
    public static final Fever FEVER=register("fever",
            new Fever(new Item.Settings().rarity(Rarity.UNCOMMON),0,0.5,0,0.2,0,0,0,true));
    public static final Blade BLADE=register("blade",
            new Blade(new Item.Settings().rarity(Rarity.RARE),0,2.5,0.5,0,0,0.3,0,true));
    public static final Cullet CULLET=register("cullet",
            new Cullet(new Item.Settings().rarity(Rarity.UNCOMMON),0,2,0,0.5,0,0,0,true));
    public static final ABundleOfTNT A_BUNDLE_OF_TNT=register("a_bundle_of_tnt",
            new ABundleOfTNT(new Item.Settings().rarity(Rarity.UNCOMMON),0,0.2,0,0,0,0,0,true));
    public static final StrengthPotionInjector STRENGTH_POTION_INJECTOR=register("strength_potion_injector",
            new StrengthPotionInjector(new Item.Settings().rarity(Rarity.UNCOMMON),0,4.0,0,0,0,0.3,0,true));
    public static final StickyBall STICKY_BALL=register("sticky_ball",
            new StickyBall(new Item.Settings().rarity(Rarity.UNCOMMON),0,0,-0.5,0,2,0,-0.2,true));
    public static final WhitePhosphorus WHITE_PHOSPHORUS=register("white_phosphorus",
            new WhitePhosphorus(new Item.Settings().rarity(Rarity.COMMON),-1,1.5,0,0.3,0,0,0,true));
    public static final CasterSugar CASTER_SUGAR=register("caster_sugar",
            new CasterSugar(new Item.Settings().rarity(Rarity.COMMON),0,0,0,0.5,1.0,0,0,true));
    public static final VenomousFang VENOMOUS_FANG=register("venomous_fang",
            new VenomousFang(new Item.Settings().rarity(Rarity.UNCOMMON),-2,-0.1,0,0.2,0,0,0,true));
    public static final BloodSacrifice BLOOD_SACRIFICE=register("blood_sacrifice",
            new BloodSacrifice(new Item.Settings().rarity(Rarity.COMMON),-5,1,0,0,0,0,0,true));
    public static final SnowBoots SNOW_BOOTS=register("snow_boots",
            new SnowBoots(new Item.Settings().rarity(Rarity.COMMON),0,0,1.5,0,0,0,0,true));
    public static final BrokenMask BROKEN_MASK=register("broken_mask",
            new BrokenMask(new Item.Settings().rarity(Rarity.UNCOMMON),0,1.5,0,0,0,0,0,true));
    public static final Ash ASH=register("ash",
            new Ash(new Item.Settings().rarity(Rarity.COMMON),0,0.7,1,0,0,0,0,true));
    public static final WitheredRosePetals WITHERED_ROSE_PETALS=register("withered_rose_petals",
            new WitheredRosePetals(new Item.Settings().rarity(Rarity.COMMON),-5,2,0,0.5,2,0,0,true));
    public static final Beer BEER=register("beer",
            new Beer(new Item.Settings().rarity(Rarity.UNCOMMON).food(FoodComponents.SPIDER_EYE),1.0,-0.5,0,0.5,2,0,0,true));
    public static final StrangeBeer STRANGE_BEER=register("strange_beer",
            new StrangeBeer(new Item.Settings().rarity(Rarity.UNCOMMON).food(FoodComponents.POISONOUS_POTATO),3,-2,0,0,0,0,0,true));
    public static final PurpleJuice PURPLE_JUICE=register("purple_juice",
            new PurpleJuice(new Item.Settings().rarity(Rarity.UNCOMMON).food(FoodComponents.COOKED_CHICKEN),1.5,-0.5,0,0,0,0,0,true));
    public static final FloatingAhoge FLOATING_AHOGE=register("floating_ahoge",
            new FloatingAhoge(new Item.Settings().rarity(Rarity.COMMON),0,0,1.0,0,0,0,0,true));
    public static final SkeletonPriestJewel SKELETON_PRIEST_JEWEL=register("skeleton_priest_jewel",
            new SkeletonPriestJewel(new Item.Settings().rarity(Rarity.EPIC),3.0,3,0,0,5,0.3,0.3,true));
    public static final CrushedIce CRUSHED_ICE=register("crushed_ice",
            new CrushedIce(new Item.Settings().rarity(Rarity.RARE),0,0,0,0.3,0,-0.1,0,true));
    public static final Banana BANANA=register("banana",
            new Banana(new Item.Settings().rarity(Rarity.UNCOMMON),3,1.5,0,0.1,0,0,0,true));
    public static final BrokenCrown BROKEN_CROWN=register("broken_crown",
            new BrokenCrown(new Item.Settings().rarity(Rarity.RARE),2.0,0,0.3,0,0,0,0,true));

    //魂石物品
    public static final InstanceHealthSoul INSTANCE_HEALTH_SOUL =register("instance_health_soul",
            new InstanceHealthSoul(new Item.Settings().rarity(Rarity.RARE),3));
    public static final TPStartRoomSoul TP_START_ROOM_SOUL=register("tp_start_room_soul",
            new TPStartRoomSoul(new Item.Settings().rarity(Rarity.RARE),2));
    public static final GetFirstPoolSoul GET_FIRST_POOL_SOUL=register("get_first_pool_soul",
            new GetFirstPoolSoul(new Item.Settings().rarity(Rarity.RARE),1));
    public static final GetLastPoolSoul GET_LAST_POOL_SOUL=register("get_last_pool_soul",
            new GetLastPoolSoul(new Item.Settings().rarity(Rarity.RARE),1));
    public static final GetConcealPoolSoul GET_CONCEAL_POOL_SOUL=register("get_conceal_pool_soul",
            new GetConcealPoolSoul(new Item.Settings().rarity(Rarity.RARE),6));
    public static final GetShopPoolSoul GET_SHOP_POOL_SOUL=register("get_shop_pool_soul",
            new GetShopPoolSoul(new Item.Settings().rarity(Rarity.RARE),1));
    public static final GetThirdPoolSoul GET_THIRD_POOL_SOUL=register("get_third_pool_soul",
            new GetThirdPoolSoul(new Item.Settings().rarity(Rarity.RARE),3));
    public static final GetApartPoolSoul GET_APART_POOL_SOUL=register("get_apart_pool_soul",
            new GetApartPoolSoul(new Item.Settings().rarity(Rarity.RARE),2));
    public static final GetChaoPoolSoul GET_CHAO_POOL_SOUL=register("get_chao_pool_soul",
            new GetChaoPoolSoul(new Item.Settings().rarity(Rarity.RARE),1));
    public static final OriginSoul ORIGIN_SOUL=register("origin_soul",
            new OriginSoul(new Item.Settings().rarity(Rarity.RARE),3));
    public static final DesireSoul DESIRE_SOUL=register("desire_soul",
            new DesireSoul(new Item.Settings().rarity(Rarity.RARE),6));
    public static final ResetSoul RESET_SOUL=register("reset_soul",
            new ResetSoul(new Item.Settings().rarity(Rarity.RARE),4));
    public static final GreedySoul GREEDY_SOUL=register("greedy_soul",
            new GreedySoul(new Item.Settings().rarity(Rarity.RARE),6));

    //传送物品
    public static final SwordItem BROKEN_SWORD =register("broken_sword",
            new SwordItem(ToolMaterials.IRON, 2, -2.4F, new Item.Settings()));

    public static <T extends Item> T register(String path, T item) {
        return Registry.register(Registries.ITEM,new Identifier("neko-adventure", path), item);
    }
    public static void initialize() {}
}
