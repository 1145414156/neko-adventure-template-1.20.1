package com.nekoadventure.item.soulItem;

import com.nekoadventure.item.nekoItem.AbstractNekoItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GetChaoPoolSoul extends AbstractSoulPoolItem{
    public GetChaoPoolSoul(Settings settings, int maxCharge) {
        super(settings, maxCharge);
        text= Text.literal("使用时，打乱道具池顺序").formatted(Formatting.GOLD);

    }

    @Override
    public List<Item> getStoragePoolItems(List<Item> inputPools, PlayerEntity player) {
        List<Item> result = new ArrayList<>(inputPools);
        if (!player.isSneaking()) {
            Collections.shuffle(result);
        }
        return result;
    }
}
