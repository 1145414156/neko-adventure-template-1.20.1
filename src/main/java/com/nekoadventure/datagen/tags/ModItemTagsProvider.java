package com.nekoadventure.datagen.tags;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.item.ModItems;
import com.nekoadventure.item.nekoItem.AbstractNekoItem;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ModItemTagsProvider extends FabricTagProvider<Item> {
    public static final TagKey<Item> ALL_POOL= TagKey.of(RegistryKeys.ITEM, new Identifier(NekoAdventure.MOD_ID, "all_item_pool"));
    public static final TagKey<Item> TREASURE_POOL=TagKey.of(RegistryKeys.ITEM, new Identifier(NekoAdventure.MOD_ID, "treasure_pool"));
    public static final TagKey<Item> SHOP_POOL=TagKey.of(RegistryKeys.ITEM, new Identifier(NekoAdventure.MOD_ID, "shop_pool"));
    public static final TagKey<Item> BOSS_POOL=TagKey.of(RegistryKeys.ITEM, new Identifier(NekoAdventure.MOD_ID, "boss_pool"));
    public static final TagKey<Item> FORGE_POOL=TagKey.of(RegistryKeys.ITEM, new Identifier(NekoAdventure.MOD_ID, "forge_pool"));
    public static final TagKey<Item> FOOD_POOL=TagKey.of(RegistryKeys.ITEM, new Identifier(NekoAdventure.MOD_ID, "food_pool"));
    public static final TagKey<Item> GAMBLE_POOL=TagKey.of(RegistryKeys.ITEM, new Identifier(NekoAdventure.MOD_ID, "gamble_pool"));
    public static final TagKey<Item> SOUL_POOL=TagKey.of(RegistryKeys.ITEM, new Identifier(NekoAdventure.MOD_ID, "soul_pool"));
    public static final TagKey<Item> CONCEAL_POOL=TagKey.of(RegistryKeys.ITEM, new Identifier(NekoAdventure.MOD_ID, "conceal_pool"));

    public ModItemTagsProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.ITEM, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        //通用池
        Set<AbstractNekoItem> allNekoItem=getAllNekoItems(arg);
        for (AbstractNekoItem item : allNekoItem) {
            getOrCreateTagBuilder(ALL_POOL).add(item);
        }

        //宝箱池
        Set<AbstractNekoItem> treasurePoolItem=getTreasurePoolItems();
        for (AbstractNekoItem item : treasurePoolItem) {
            getOrCreateTagBuilder(TREASURE_POOL).add(item);
        }

        //商店池
        Set<AbstractNekoItem> shopPoolItem=getShopPoolItems();
        for (AbstractNekoItem item : shopPoolItem) {
            getOrCreateTagBuilder(SHOP_POOL).add(item);
        }

        //boss池
        Set<AbstractNekoItem> bossPoolItem=getBossPoolItems();
        for (AbstractNekoItem item : bossPoolItem) {
            getOrCreateTagBuilder(BOSS_POOL).add(item);
        }

        //锻造池
        Set<AbstractNekoItem> forgePoolItem=getForgePoolItems();
        for (AbstractNekoItem item : forgePoolItem) {
            getOrCreateTagBuilder(FORGE_POOL).add(item);
        }

        //食物池
        Set<AbstractNekoItem> foodPoolItem=getFoodItems();
        for (AbstractNekoItem item : foodPoolItem) {
            getOrCreateTagBuilder(FOOD_POOL).add(item);
        }

        //赌博池
        Set<AbstractNekoItem> gamblePoolItem=getGamblePoolItems();
        for (AbstractNekoItem item : gamblePoolItem) {
            getOrCreateTagBuilder(GAMBLE_POOL).add(item);
        }

        //灵魂池
        Set<AbstractNekoItem> soulPoolItem=getSoulPoolItems();
        for (AbstractNekoItem item : soulPoolItem) {
            getOrCreateTagBuilder(SOUL_POOL).add(item);
        }

        //隐藏池
        Set<AbstractNekoItem> concealPoolItem=getConcealPoolItems();
        for (AbstractNekoItem item : concealPoolItem) {
            getOrCreateTagBuilder(CONCEAL_POOL).add(item);
        }
    }

    private Set<AbstractNekoItem> getAllNekoItems(RegistryWrapper.WrapperLookup registries){
        RegistryWrapper<Item> itemRegistry = registries.getWrapperOrThrow(RegistryKeys.ITEM);
        return itemRegistry.streamEntries()
                .map(RegistryEntry.Reference::value)
                .filter(item -> item instanceof AbstractNekoItem)
                .map(item -> (AbstractNekoItem) item)
                .collect(Collectors.toSet());
    }
    private Set<AbstractNekoItem> getTreasurePoolItems(){
        Set<AbstractNekoItem> treasurePoolItems=new HashSet<>();
        treasurePoolItems.add(ModItems.SOUL_JAR);
        treasurePoolItems.add(ModItems.EXPOSED_WIRE);
        treasurePoolItems.add(ModItems.JOYEUSE);
        treasurePoolItems.add(ModItems.PURENESS_SOUL);
        treasurePoolItems.add(ModItems.ONION);
        treasurePoolItems.add(ModItems.COMPRESSED_IRON);
        treasurePoolItems.add(ModItems.BULLET);
        treasurePoolItems.add(ModItems.CHOCOLATE_BISCUIT);
        treasurePoolItems.add(ModItems.FEVER);
        treasurePoolItems.add(ModItems.CULLET);
        treasurePoolItems.add(ModItems.PIERCING_DIAMOND);
        treasurePoolItems.add(ModItems.CAT_CLAW);
        treasurePoolItems.add(ModItems.SODA);
        treasurePoolItems.add(ModItems.NEKO_FOOD_CAN);
        treasurePoolItems.add(ModItems.LUNCH_POX);
        treasurePoolItems.add(ModItems.LANCE);
        treasurePoolItems.add(ModItems.A_BUNDLE_OF_TNT);
        treasurePoolItems.add(ModItems.BOOMERANG);
        treasurePoolItems.add(ModItems.BRANCH);
        treasurePoolItems.add(ModItems.NIGHT_VISION);
        treasurePoolItems.add(ModItems.BROKEN_SWORD_HILT);
        treasurePoolItems.add(ModItems.STICKY_BALL);
        treasurePoolItems.add(ModItems.WHITE_PHOSPHORUS);
        treasurePoolItems.add(ModItems.UNDERCOOKED_LONG_BEANS);
        treasurePoolItems.add(ModItems.VENOMOUS_FANG);
        treasurePoolItems.add(ModItems.TOMATO);
        treasurePoolItems.add(ModItems.ENCHANTED_GOLDEN_APPLE_SLICE);
        treasurePoolItems.add(ModItems.GLOVE);
        treasurePoolItems.add(ModItems.QUENCHING_CHAIN);
        treasurePoolItems.add(ModItems.MILK);
        treasurePoolItems.add(ModItems.HUGE_SNOWBALL);
        treasurePoolItems.add(ModItems.IODOPHOR);
        treasurePoolItems.add(ModItems.BANDAGE);
        treasurePoolItems.add(ModItems.LAVA_WALKER);
        treasurePoolItems.add(ModItems.SULFUR_BLOCK);
        treasurePoolItems.add(ModItems.NAGA_SCALE);
        treasurePoolItems.add(ModItems.BAMBOO_SHOOT);
        treasurePoolItems.add(ModItems.RED_SCARF);
        treasurePoolItems.add(ModItems.TACTICAL_VEST);
        treasurePoolItems.add(ModItems.BROKEN_MASK);
        treasurePoolItems.add(ModItems.ASH);
        treasurePoolItems.add(ModItems.WITHERED_ROSE_PETALS);
        treasurePoolItems.add(ModItems.TRAINING_DAGGER);
        treasurePoolItems.add(ModItems.TREE_TRUNK);
        treasurePoolItems.add(ModItems.CRUSHED_ICE);
        treasurePoolItems.add(ModItems.CAT_TREAT);
        treasurePoolItems.add(ModItems.DELICIOUS_DRIED_FISH);
        treasurePoolItems.add(ModItems.MANDRAKE);
        treasurePoolItems.add(ModItems.BANANA);
        treasurePoolItems.add(ModItems.OXIDIZED_COPPER_INGOT);


        return treasurePoolItems;
    }
    private Set<AbstractNekoItem> getShopPoolItems(){
        Set<AbstractNekoItem> shopPoolItems =new HashSet<>();
        shopPoolItems.add(ModItems.JOYEUSE);
        shopPoolItems.add(ModItems.COMPRESSED_IRON);
        shopPoolItems.add(ModItems.SPIRITING_AWAY);
        shopPoolItems.add(ModItems.POKER);
        shopPoolItems.add(ModItems.BULLET);
        shopPoolItems.add(ModItems.BLADE);
        shopPoolItems.add(ModItems.THE_FOURTH_EYE);
        shopPoolItems.add(ModItems.THE_THIRD_EYE);
        shopPoolItems.add(ModItems.BLINDER);
        shopPoolItems.add(ModItems.PIERCING_DIAMOND);
        shopPoolItems.add(ModItems.LUNCH_POX);
        shopPoolItems.add(ModItems.CATNIP);
        shopPoolItems.add(ModItems.LANCE);
        shopPoolItems.add(ModItems.A_BUNDLE_OF_TNT);
        shopPoolItems.add(ModItems.PARRY);
        shopPoolItems.add(ModItems.UMBRELLA);
        shopPoolItems.add(ModItems.SWIRL);
        shopPoolItems.add(ModItems.BOOMERANG);
        shopPoolItems.add(ModItems.ACCELERATION);
        shopPoolItems.add(ModItems.DECELERATION);
        shopPoolItems.add(ModItems.STRENGTH_POTION_INJECTOR);
        shopPoolItems.add(ModItems.STICKY_BALL);
        shopPoolItems.add(ModItems.OIL_BOTTLE);
        shopPoolItems.add(ModItems.WHITE_PHOSPHORUS);
        shopPoolItems.add(ModItems.CASTER_SUGAR);
        shopPoolItems.add(ModItems.CACTUS_BALL);
        shopPoolItems.add(ModItems.ENCHANTED_GOLDEN_APPLE_SLICE);
        shopPoolItems.add(ModItems.GLOVE);
        shopPoolItems.add(ModItems.QUENCHING_CHAIN);
        shopPoolItems.add(ModItems.STRANGE_POTION);
        shopPoolItems.add(ModItems.IODOPHOR);
        shopPoolItems.add(ModItems.BANDAGE);
        shopPoolItems.add(ModItems.BROKEN_SOUL);
        shopPoolItems.add(ModItems.LAVA_WALKER);
        shopPoolItems.add(ModItems.SULFUR_BLOCK);
        shopPoolItems.add(ModItems.SNOW_BOOTS);
        shopPoolItems.add(ModItems.RED_SCARF);
        shopPoolItems.add(ModItems.TACTICAL_VEST);
        shopPoolItems.add(ModItems.BROKEN_MASK);
        shopPoolItems.add(ModItems.SHIRT);
        shopPoolItems.add(ModItems.SILVER_ARMOR);
        shopPoolItems.add(ModItems.DAMAGED_BRACER);
        shopPoolItems.add(ModItems.TACTICAL_ARMOR);
        shopPoolItems.add(ModItems.STRANGE_BEER);
        shopPoolItems.add(ModItems.PURPLE_JUICE);
        shopPoolItems.add(ModItems.CRUSHED_ICE);
        shopPoolItems.add(ModItems.CAT_TREAT);
        shopPoolItems.add(ModItems.BANANA);
        shopPoolItems.add(ModItems.TOAST);
        shopPoolItems.add(ModItems.ZENITH);
        shopPoolItems.add(ModItems.CAT_TEACUP);
        shopPoolItems.add(ModItems.NATTO);
        shopPoolItems.add(ModItems.POPSICLE);
        shopPoolItems.add(ModItems.A_PIECE_OF_CAKE);

        return shopPoolItems;
    }

    private Set<AbstractNekoItem> getBossPoolItems(){
        Set<AbstractNekoItem> bossPoolItems =new HashSet<>();
        bossPoolItems.add(ModItems.PURENESS_SOUL);
        bossPoolItems.add(ModItems.ONION);
        bossPoolItems.add(ModItems.CHOCOLATE_BISCUIT);
        bossPoolItems.add(ModItems.PIERCING_DIAMOND);
        bossPoolItems.add(ModItems.HANGER);
        bossPoolItems.add(ModItems.CAT_CLAW);
        bossPoolItems.add(ModItems.SODA);
        bossPoolItems.add(ModItems.NEKO_FOOD_CAN);
        bossPoolItems.add(ModItems.UNRIPE_APPLE);
        bossPoolItems.add(ModItems.CATNIP);
        bossPoolItems.add(ModItems.UMBRELLA);
        bossPoolItems.add(ModItems.BRANCH);
        bossPoolItems.add(ModItems.CROWN);
        bossPoolItems.add(ModItems.STICKY_BALL);
        bossPoolItems.add(ModItems.UNDERCOOKED_LONG_BEANS);
        bossPoolItems.add(ModItems.VENOMOUS_FANG);
        bossPoolItems.add(ModItems.TOMATO);
        bossPoolItems.add(ModItems.GLOVE);
        bossPoolItems.add(ModItems.MILK);
        bossPoolItems.add(ModItems.HUGE_SNOWBALL);
        bossPoolItems.add(ModItems.BROKEN_SOUL);
        bossPoolItems.add(ModItems.NAGA_SCALE);
        bossPoolItems.add(ModItems.BAMBOO_SHOOT);
        bossPoolItems.add(ModItems.WING);
        bossPoolItems.add(ModItems.SHIRT);
        bossPoolItems.add(ModItems.TRAINING_DAGGER);
        bossPoolItems.add(ModItems.SKELETON_PRIEST_CHIN);
        bossPoolItems.add(ModItems.SKELETON_PRIEST_JEWEL);
        bossPoolItems.add(ModItems.TREE_TRUNK);
        bossPoolItems.add(ModItems.NATTO);

        return bossPoolItems;
    }

    private Set<AbstractNekoItem> getForgePoolItems(){
        Set<AbstractNekoItem> forgePoolItems =new HashSet<>();
        forgePoolItems.add(ModItems.COMPRESSED_IRON);
        forgePoolItems.add(ModItems.BLADE);
        forgePoolItems.add(ModItems.PIERCING_DIAMOND);
        forgePoolItems.add(ModItems.LANCE);
        forgePoolItems.add(ModItems.UMBRELLA);
        forgePoolItems.add(ModItems.BOOMERANG);
        forgePoolItems.add(ModItems.BROKEN_SWORD_HILT);
        forgePoolItems.add(ModItems.QUENCHING_CHAIN);
        forgePoolItems.add(ModItems.LAVA_WALKER);
        forgePoolItems.add(ModItems.TACTICAL_VEST);
        forgePoolItems.add(ModItems.SILVER_ARMOR);
        forgePoolItems.add(ModItems.DAMAGED_BRACER);
        forgePoolItems.add(ModItems.TACTICAL_ARMOR);
        forgePoolItems.add(ModItems.SKELETON_PRIEST_JEWEL);
        forgePoolItems.add(ModItems.ZENITH);
        forgePoolItems.add(ModItems.CAT_TEACUP);
        forgePoolItems.add(ModItems.OXIDIZED_COPPER_INGOT);

        return forgePoolItems;
    }
    private Set<AbstractNekoItem> getFoodItems(){
        Set<AbstractNekoItem> foodPoolItems =new HashSet<>();
        foodPoolItems.add(ModItems.ONION);
        foodPoolItems.add(ModItems.CHOCOLATE_BISCUIT);
        foodPoolItems.add(ModItems.SODA);
        foodPoolItems.add(ModItems.NEKO_FOOD_CAN);
        foodPoolItems.add(ModItems.UNRIPE_APPLE);
        foodPoolItems.add(ModItems.LUNCH_POX);
        foodPoolItems.add(ModItems.OIL_BOTTLE);
        foodPoolItems.add(ModItems.UNDERCOOKED_LONG_BEANS);
        foodPoolItems.add(ModItems.CASTER_SUGAR);
        foodPoolItems.add(ModItems.TOMATO);
        foodPoolItems.add(ModItems.ENCHANTED_GOLDEN_APPLE_SLICE);
        foodPoolItems.add(ModItems.MILK);
        foodPoolItems.add(ModItems.STRANGE_POTION);
        foodPoolItems.add(ModItems.BAMBOO_SHOOT);
        foodPoolItems.add(ModItems.CAT_TREAT);
        foodPoolItems.add(ModItems.DELICIOUS_DRIED_FISH);
        foodPoolItems.add(ModItems.MANDRAKE);
        foodPoolItems.add(ModItems.BANANA);
        foodPoolItems.add(ModItems.TOAST);
        foodPoolItems.add(ModItems.NATTO);
        foodPoolItems.add(ModItems.POPSICLE);
        foodPoolItems.add(ModItems.A_PIECE_OF_CAKE);

        return foodPoolItems;
    }

    private Set<AbstractNekoItem> getGamblePoolItems(){
        Set<AbstractNekoItem> gamblePoolItems =new HashSet<>();
        gamblePoolItems.add(ModItems.POKER);
        gamblePoolItems.add(ModItems.BLADE);
        gamblePoolItems.add(ModItems.FEVER);
        gamblePoolItems.add(ModItems.CULLET);
        gamblePoolItems.add(ModItems.STRENGTH_POTION_INJECTOR);
        gamblePoolItems.add(ModItems.STICKY_BALL);
        gamblePoolItems.add(ModItems.CASTER_SUGAR);
        gamblePoolItems.add(ModItems.VENOMOUS_FANG);
        gamblePoolItems.add(ModItems.BLOOD_SACRIFICE);
        gamblePoolItems.add(ModItems.RED_SCARF);
        gamblePoolItems.add(ModItems.BROKEN_MASK);
        gamblePoolItems.add(ModItems.ASH);
        gamblePoolItems.add(ModItems.WITHERED_ROSE_PETALS);
        gamblePoolItems.add(ModItems.BEER);
        gamblePoolItems.add(ModItems.STRANGE_BEER);
        gamblePoolItems.add(ModItems.PURPLE_JUICE);
        gamblePoolItems.add(ModItems.FLOATING_AHOGE);
        gamblePoolItems.add(ModItems.CRUSHED_ICE);
        gamblePoolItems.add(ModItems.BANANA);
        gamblePoolItems.add(ModItems.SKELETON_PRIEST_JEWEL);

        return gamblePoolItems;
    }

    private Set<AbstractNekoItem> getSoulPoolItems(){
        Set<AbstractNekoItem> soulPoolItems =new HashSet<>();
        soulPoolItems.add(ModItems.SOUL_JAR);
        soulPoolItems.add(ModItems.PURENESS_SOUL);
        soulPoolItems.add(ModItems.SPIRITING_AWAY);
        soulPoolItems.add(ModItems.NIGHT_VISION);
        soulPoolItems.add(ModItems.STRENGTH_POTION_INJECTOR);
        soulPoolItems.add(ModItems.ENCHANTED_GOLDEN_APPLE_SLICE);
        soulPoolItems.add(ModItems.STRANGE_POTION);
        soulPoolItems.add(ModItems.BROKEN_SOUL);
        soulPoolItems.add(ModItems.BLOODSTAINED_CROSS);

        return soulPoolItems;
    }
    private Set<AbstractNekoItem> getConcealPoolItems(){
        Set<AbstractNekoItem> concealPoolItems =new HashSet<>();
        concealPoolItems.add(ModItems.SOUL_JAR);
        concealPoolItems.add(ModItems.PURENESS_SOUL);
        concealPoolItems.add(ModItems.SPIRITING_AWAY);
        concealPoolItems.add(ModItems.BULLET);
        concealPoolItems.add(ModItems.BRIMSTONE);
        concealPoolItems.add(ModItems.A_BUNDLE_OF_TNT);
        concealPoolItems.add(ModItems.PARRY);
        concealPoolItems.add(ModItems.BOOMERANG);
        concealPoolItems.add(ModItems.NIGHT_VISION);
        concealPoolItems.add(ModItems.STRENGTH_POTION_INJECTOR);
        concealPoolItems.add(ModItems.CROWN);
        concealPoolItems.add(ModItems.WHITE_PHOSPHORUS);
        concealPoolItems.add(ModItems.BLOOD_SACRIFICE);
        concealPoolItems.add(ModItems.ENCHANTED_GOLDEN_APPLE_SLICE);
        concealPoolItems.add(ModItems.HUGE_SNOWBALL);
        concealPoolItems.add(ModItems.IODOPHOR);
        concealPoolItems.add(ModItems.BANDAGE);
        concealPoolItems.add(ModItems.SLASH);
        concealPoolItems.add(ModItems.BLOODSTAINED_CROSS);
        concealPoolItems.add(ModItems.NAGA_SCALE);
        concealPoolItems.add(ModItems.WING);
        concealPoolItems.add(ModItems.RED_SCARF);
        concealPoolItems.add(ModItems.FLOATING_AHOGE);
        concealPoolItems.add(ModItems.MANDRAKE);
        concealPoolItems.add(ModItems.ZENITH);
        concealPoolItems.add(ModItems.NATTO);

        return concealPoolItems;
    }
}
