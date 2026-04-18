package com.nekoadventure.item.itemGroup;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.item.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final RegistryKey<ItemGroup> NUM_ITEM_GROUP=register("num_item_group");
    public static final RegistryKey<ItemGroup> NEKO_GROUP=register("neko_group");

    private static RegistryKey<ItemGroup> register(String id){
        return RegistryKey.of(RegistryKeys.ITEM_GROUP,new Identifier(NekoAdventure.MOD_ID,id));
    }
    public static void registerGroups() {
        Registry.register(
                Registries.ITEM_GROUP,
                NUM_ITEM_GROUP,
                ItemGroup.create(ItemGroup.Row.TOP, 7)
                        .displayName(Text.translatable("itemgroup.num_item_group"))
                        .icon(() -> new ItemStack(ModItems.NEKO_FOOD_CAN))
                        .entries((displayContext, entries) -> {
                            entries.add(ModItems.NEKO_FOOD_CAN);
                            entries.add(ModItems.ERROR);
                            entries.add(ModItems.HANGER);

                        })
                        .build());
        Registry.register(
                Registries.ITEM_GROUP,
                NEKO_GROUP,
                ItemGroup.create(ItemGroup.Row.TOP, 8)
                        .displayName(Text.translatable("itemgroup.neko_group"))
                        .icon(() -> new ItemStack(ModItems.NEKO_PACKAGE))
                        .entries((displayContext, entries) -> {
                            entries.add(ModItems.NEKO_PACKAGE);
                        })
                        .build());
    }
}
