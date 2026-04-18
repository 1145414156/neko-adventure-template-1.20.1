package com.nekoadventure.item.soulItem;

import com.nekoadventure.item.nekoItem.AbstractNekoItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Collections;
import java.util.List;

public class GetFirstPoolSoul extends AbstractSoulPoolItem{
    public GetFirstPoolSoul(Settings settings, int maxCharge) {
        super(settings, maxCharge);
        text= Text.literal("使用时，提取道具池中的第一个物品").formatted(Formatting.GOLD);
    }

    @Override
    public List<Item> getStoragePoolItems(List<Item> inputPools, PlayerEntity player) {

        if (player.isSneaking()){
            inputPools.remove(0);
            return inputPools;
        }
        else {
            return Collections.singletonList(inputPools.get(0));
        }
    }
}
