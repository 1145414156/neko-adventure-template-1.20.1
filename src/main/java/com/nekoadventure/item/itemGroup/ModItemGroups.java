package com.nekoadventure.item.itemGroup;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.block.ModBlocks;
import com.nekoadventure.item.ModItems;
import com.nekoadventure.item.nekoItem.AbstractNekoItem;
import com.nekoadventure.item.other.NekoPackageItem;
import com.nekoadventure.item.soulItem.AbstractSoulItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Comparator;

public class ModItemGroups {
    public static final RegistryKey<ItemGroup> NUM_ITEM_GROUP=register("num_item_group");
    public static final RegistryKey<ItemGroup> NEKO_GROUP=register("neko_group");
    public static final RegistryKey<ItemGroup> SOUL_ITEM_GROUP =register("soul_item_group");
    public static final RegistryKey<ItemGroup> MAZE_GROUP =register("maze_group");

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
                        .entries((displayContext, entries) ->

                                Registries.ITEM.stream()
                                .filter(item -> item instanceof AbstractNekoItem)
                                .map(ItemStack::new)
                                .sorted(Comparator
                                        .<ItemStack, Integer>comparing(
                                                stack -> stack.getItem().getRarity(stack).ordinal(),
                                                Comparator.reverseOrder()
                                        )
                                        .thenComparingInt(
                                                stack -> Registries.ITEM.getRawId(stack.getItem())
                                        )
                                )
                                .forEach(entries::add))

                        .build());

        Registry.register(
                Registries.ITEM_GROUP,
                NEKO_GROUP,
                ItemGroup.create(ItemGroup.Row.TOP, 8)
                        .displayName(Text.translatable("itemgroup.neko_group"))
                        .icon(() -> new ItemStack(ModItems.NEKO_PACKAGE))
                        .entries((displayContext, entries) -> {
                            ItemStack itemStack=new ItemStack(ModItems.NEKO_PACKAGE);
                            itemStack.getOrCreateNbt().getInt(NekoPackageItem.FINISHED_KEY);
                            entries.add(itemStack);
                            entries.add(ModItems.BINDING_SOUL_SUBSTANCE);
                            entries.add(ModItems.EMERGENCY_FOOD);
                            entries.add(ModItems.PROP_PROTOTYPE);
                            entries.add(ModItems.COIN);
                            entries.add(ModBlocks.GATE_BLOCK);
                            entries.add(ModBlocks.MAZE_BLOCK);
                            entries.add(ModBlocks.SPAWN_MOB_BLOCK);
                            entries.add(ModBlocks.MAZE_STRUCTURE_BLOCK);
                            entries.add(ModBlocks.TP_NEXT_LEVEL_BLOCK);
                            entries.add(ModBlocks.MAZE_ROOM_STAGE_BLOCK);
                            entries.add(ModBlocks.ITEM_BASE_BLOCK);
                            entries.add(ModBlocks.TREASURE_ROOM_BLOCK);
                            entries.add(ModBlocks.START_ROOM_BLOCK);
                            entries.add(ModBlocks.SHOP_ROOM_BLOCK);
                            entries.add(ModBlocks.GAMBLE_ROOM_BLOCK);
                            entries.add(ModBlocks.BOSS_ROOM_BLOCK);
                            entries.add(ModBlocks.FORGE_ROOM_BLOCK);
                            entries.add(ModBlocks.SOUL_ROOM_BLOCK);
                            entries.add(ModBlocks.FOOD_ROOM_BLOCK);
                            entries.add(ModBlocks.CONCEAL_ROOM_BLOCK);
                            entries.add(ModBlocks.WISH_POOL_BLOCK);
                            entries.add(ModBlocks.SLOT_MACHINE_BLOCK);
                            entries.add(ModBlocks.ROLL_ITEM_BLOCK);
                            entries.add(ModBlocks.REROLL_FURNACE_BLOCK);
                            entries.add(ModBlocks.MONUMENT_BLOCK);
                            entries.add(ModBlocks.RESET_FURNACE_BLOCK);
                        })
                        .build());
        Registry.register(
                Registries.ITEM_GROUP,
                SOUL_ITEM_GROUP,
                ItemGroup.create(ItemGroup.Row.TOP, 8)
                        .displayName(Text.translatable("itemgroup.soul_item_group"))
                        .icon(() -> new ItemStack(ModItems.INSTANCE_HEALTH_SOUL))
                        .entries((displayContext, entries) ->
                                Registries.ITEM.stream()
                                        .filter(item -> item instanceof AbstractSoulItem)
                                        .map(ItemStack::new)
                                        .sorted(Comparator
                                                .<ItemStack, Integer>comparing(
                                                        stack -> stack.getItem().getRarity(stack).ordinal(),
                                                        Comparator.reverseOrder()
                                                )
                                                .thenComparingInt(
                                                        stack -> Registries.ITEM.getRawId(stack.getItem())
                                                )
                                        )
                                        .forEach(entries::add))
                        .build());

        Registry.register(
                Registries.ITEM_GROUP,
                MAZE_GROUP,
                ItemGroup.create(ItemGroup.Row.TOP, 8)
                        .displayName(Text.translatable("itemgroup.maze_group"))
                        .icon(() -> new ItemStack(ModItems.BROKEN_SWORD))
                        .entries((displayContext, entries) -> {
                            entries.add(ModBlocks.TP_DUNGEON_BLOCK);
                            entries.add(ModItems.BROKEN_SWORD);
                        })
                        .build());

    }
}
