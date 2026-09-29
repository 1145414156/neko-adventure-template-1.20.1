package com.nekoadventure.other.itemApart;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.block.specialroomblock.*;
import com.nekoadventure.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class SpawnRandomNekoItems {
    public final int[] ALL_POOL_PROBABILITIES_HEIGHT = {70, 20, 8, 2};
    private final Random RANDOM = new Random();
    public static final TagKey<Item> ALL_POOL= TagKey.of(RegistryKeys.ITEM, new Identifier(NekoAdventure.MOD_ID, "all_item_pool"));
    public static final TagKey<Item> TREASURE_POOL=TagKey.of(RegistryKeys.ITEM, new Identifier(NekoAdventure.MOD_ID, "treasure_pool"));
    public static final TagKey<Item> SHOP_POOL=TagKey.of(RegistryKeys.ITEM, new Identifier(NekoAdventure.MOD_ID, "shop_pool"));
    public static final TagKey<Item> BOSS_POOL=TagKey.of(RegistryKeys.ITEM, new Identifier(NekoAdventure.MOD_ID, "boss_pool"));
    public static final TagKey<Item> FORGE_POOL=TagKey.of(RegistryKeys.ITEM, new Identifier(NekoAdventure.MOD_ID, "forge_pool"));
    public static final TagKey<Item> FOOD_POOL=TagKey.of(RegistryKeys.ITEM, new Identifier(NekoAdventure.MOD_ID, "food_pool"));
    public static final TagKey<Item> GAMBLE_POOL=TagKey.of(RegistryKeys.ITEM, new Identifier(NekoAdventure.MOD_ID, "gamble_pool"));
    public static final TagKey<Item> SOUL_POOL=TagKey.of(RegistryKeys.ITEM, new Identifier(NekoAdventure.MOD_ID, "soul_pool"));
    public static final TagKey<Item> CONCEAL_POOL=TagKey.of(RegistryKeys.ITEM, new Identifier(NekoAdventure.MOD_ID, "conceal_pool"));

    private Set<Item> getRandomItemFromTag(ServerWorld world, TagKey<Item> tagKey) {
        // 获取标签中的所有物品
        return world.getRegistryManager()
                .get(RegistryKeys.ITEM)
                .getEntryList(tagKey)
                .map(entryList -> entryList.stream()
                        .map(RegistryEntry::value)
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
    }

    public Set<Item> getRoomPool(ServerWorld world, AbstractRoomBlock roomBlock) {
        TagKey<Item> tagKey;
        if (roomBlock instanceof TreasureRoomBlock) {
            tagKey=TREASURE_POOL;
        }
        else if (roomBlock instanceof ShopRoomBlock){
            tagKey=SHOP_POOL;
        }
        else if (roomBlock instanceof BossRoomBlock){
            tagKey=BOSS_POOL;
        }
        else if (roomBlock instanceof ForgeRoomBlock){
            tagKey=FORGE_POOL;
        }
        else if (roomBlock instanceof SoulRoomBlock){
            tagKey=SOUL_POOL;
        }
        else if (roomBlock instanceof ConcealRoomBlock){
            tagKey=CONCEAL_POOL;
        }
        else if (roomBlock instanceof GambleRoomBlock){
            tagKey=GAMBLE_POOL;
        }
        else if (roomBlock instanceof FoodRoomBlock){
            tagKey=FOOD_POOL;
        }
        else {
            tagKey=ALL_POOL;
        }
        return getRandomItemFromTag(world, tagKey);
    }
    public ItemStack summonRandomItemFromPool(ServerWorld world, AbstractRoomBlock roomBlock
            ,int commonItem,int uncommonItem,int rareItem,int epicItem){
        validateProbabilities(commonItem,uncommonItem,rareItem,epicItem);
        Set<Item> pool=getRoomPool(world, roomBlock);
        int random=RANDOM.nextInt(100)+1;
        Rarity selectedRarity;
        if (random <= commonItem) {
            selectedRarity = Rarity.COMMON;
        } else if (random <= commonItem + uncommonItem) {
            selectedRarity = Rarity.UNCOMMON;
        } else if (random <= commonItem + uncommonItem + rareItem) {
            selectedRarity = Rarity.RARE;
        } else {
            selectedRarity = Rarity.EPIC;
        }
        List<Item> candidates = pool.stream()
                .filter(item -> new ItemStack(item).getRarity() == selectedRarity)
                .toList();
        if (candidates.isEmpty()) {
            return ModItems.BINDING_SOUL_SUBSTANCE.getDefaultStack();
        }

        // 在筛选后的集合中随机抽取一个
        Item selected = candidates.get(RANDOM.nextInt(candidates.size()));
        return new ItemStack(selected);
    }

    private void validateProbabilities(int common, int uncommon, int rare, int epic) {
        // 检查负数
        if (common < 0 || uncommon < 0 || rare < 0 || epic < 0) {
            throw new IllegalArgumentException(
                    String.format("概率不能为负数！普通: %d, 稀有: %d, 史诗: %d, 传说: %d",
                            common, uncommon, rare, epic)
            );
        }

        // 检查总和
        int total = common + uncommon + rare + epic;
        if (total != 100) {
            throw new IllegalArgumentException(
                    String.format("概率总和必须为100%%，当前为: %d%% (普通: %d%%, 稀有: %d%%, 史诗: %d%%, 传说: %d%%)",
                            total, common, uncommon, rare, epic)
            );
        }
    }

}
