package com.nekoadventure.item.soulItem;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Collections;
import java.util.List;

public class GetCentrosymmetrySoul extends AbstractSoulPoolItem{
    public GetCentrosymmetrySoul(Settings settings, int maxCharge) {
        super(settings, maxCharge);
        text= Text.literal("使用时，将道具池以中心对称的形式重新分布(左右两边的道具按照方向重新排列)").formatted(Formatting.GOLD);
    }

    @Override
    public List<Item> getStoragePoolItems(List<Item> inputPools, PlayerEntity player) {
        Collections.reverse(inputPools);
        return inputPools;
    }
}
