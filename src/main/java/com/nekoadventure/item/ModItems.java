package com.nekoadventure.item;

import com.nekoadventure.item.nekoItem.AbstractNumItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item EMERGENCY_FOOD=register("emergency_food",
            new Item(new Item.Settings().food(ModFoodComponents.EMERGENCY_FOOD)));
    public static final Item NEKO_PACKAGE=register("neko_package",
            new NekoPackageItem(new Item.Settings()));
    public static final AbstractNumItem NEKO_FOOD_CAN=register("neko_food_can",
            new AbstractNumItem(new Item.Settings().maxCount(1),10,0,0,0));
    public static final AbstractNumItem HANGER=register("hanger",
            new AbstractNumItem(new Item.Settings().maxCount(1),0,0,0,2));
    public static final AbstractNumItem ERROR=register("error",
            new AbstractNumItem(new Item.Settings().maxCount(1),0,10,10,10));

    public static <T extends Item> T register(String path, T item) {
        return Registry.register(Registries.ITEM,new Identifier("neko-adventure", path), item);
    }
    public static void initialize() {}
}
