package com.nekoadventure.item.soulItem;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class GetThirdPoolSoul extends AbstractSoulPoolItem{
    public GetThirdPoolSoul(Settings settings, int maxCharge) {
        super(settings, maxCharge);
        text= Text.literal("使用时，提取出道具池中顺序为三的倍数的道具").formatted(Formatting.GOLD);
    }

    @Override
    public List<Item> getStoragePoolItems(List<Item> inputPools, PlayerEntity player) {
        List<Item> result = new ArrayList<>();
        for (int i = 0; i < inputPools.size(); i++) {
            boolean isThirdMultiple = ((i + 1) % 3 == 0);
            if (player.isSneaking()) {
                if (!isThirdMultiple) {
                    result.add(inputPools.get(i));
                }
            } else {
                if (isThirdMultiple) {
                    result.add(inputPools.get(i));
                }
            }
        }
        return result;
    }
}
