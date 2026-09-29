package com.nekoadventure.item.soulItem;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class GetApartPoolSoul extends AbstractSoulPoolItem{

    public GetApartPoolSoul(Settings settings, int maxCharge) {
        super(settings, maxCharge);
        text= Text.literal("使用时，提取出道具池中前一半的道具").formatted(Formatting.GOLD);

    }

    @Override
    public List<Item> getStoragePoolItems(List<Item> inputPools, PlayerEntity player) {
        int half = inputPools.size() / 2;
        if (player.isSneaking()) {
            return new ArrayList<>(inputPools.subList(half, inputPools.size()));
        } else {
            return new ArrayList<>(inputPools.subList(0, half));
        }
    }
}
