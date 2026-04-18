package com.nekoadventure.item.soulItem;

import com.nekoadventure.item.nekoItem.AbstractNekoItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Collections;
import java.util.List;

public class GetLastPoolSoul extends AbstractSoulPoolItem{
    public GetLastPoolSoul(Settings settings, int maxCharge) {
        super(settings, maxCharge);
        text= Text.literal("使用时，提取道具池中的最后一个物品").formatted(Formatting.GOLD);
    }

    @Override
    public List<Item> getStoragePoolItems(List<Item> inputPools, PlayerEntity player) {
        if (player.isSneaking()){
            inputPools.remove(inputPools.size()-1);
            return inputPools;
        }
        else {
            return Collections.singletonList(inputPools.get(inputPools.size()-1));
        }
    }
}
