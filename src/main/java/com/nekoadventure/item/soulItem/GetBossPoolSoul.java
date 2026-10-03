package com.nekoadventure.item.soulItem;

import com.nekoadventure.other.itemApart.SpawnRandomNekoItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GetBossPoolSoul extends AbstractSoulPoolItem{
    public GetBossPoolSoul(Settings settings, int maxCharge) {
        super(settings, maxCharge);
        text= Text.literal("使用时，提取出道具池中涉及到首领池的道具").formatted(Formatting.GOLD);
    }

    @Override
    public List<Item> getStoragePoolItems(List<Item> inputPools, PlayerEntity player) {
        if (inputPools.isEmpty()) {
            return player.getWorld().getRegistryManager()
                    .get(RegistryKeys.ITEM)
                    .getEntryList(SpawnRandomNekoItems.BOSS_POOL)
                    .map(entryList -> entryList.stream()
                            .map(RegistryEntry::value)
                            .collect(Collectors.toList()))
                    .orElse(Collections.emptyList());
        }
        if (player.isSneaking()) {
            return inputPools.stream()
                    .filter(item -> !Registries.ITEM.getEntry(item).isIn(SpawnRandomNekoItems.BOSS_POOL))
                    .toList();
        } else {
            return inputPools.stream()
                    .filter(item -> Registries.ITEM.getEntry(item).isIn(SpawnRandomNekoItems.BOSS_POOL))
                    .toList();
        }
    }
}
