package com.nekoadventure.item.soulItem;

import com.nekoadventure.item.nekoItem.AbstractNekoItem;
import com.nekoadventure.other.itemApart.SpawnRandomNekoItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class GetConcealPoolSoul extends AbstractSoulPoolItem{
    public GetConcealPoolSoul(Settings settings, int maxCharge) {
        super(settings, maxCharge);
        text= Text.literal("使用时，提取出道具池中涉及到隐藏房的道具").formatted(Formatting.GOLD);
    }

    @Override
    public List<Item> getStoragePoolItems(List<Item> inputPools, PlayerEntity player) {
        if (player.isSneaking()) {
            return inputPools.stream()
                    .filter(item -> !Registries.ITEM.getEntry(item).isIn(SpawnRandomNekoItems.CONCEAL_POOL))
                    .toList();
        } else {
            return inputPools.stream()
                    .filter(item -> Registries.ITEM.getEntry(item).isIn(SpawnRandomNekoItems.CONCEAL_POOL))
                    .toList();
        }
    }
}
