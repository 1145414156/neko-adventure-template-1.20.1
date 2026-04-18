package com.nekoadventure.other.itemApart;

import com.nekoadventure.item.soulItem.AbstractSoulItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.world.World;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SpawnRandomSoulItems {
    public ItemStack summonRandomSoulItem(World world){
        Set<AbstractSoulItem> soulItems=getSoulItems();
        if (soulItems.isEmpty()){return ItemStack.EMPTY;}
        int random=world.getRandom().nextInt(soulItems.size());
        List<AbstractSoulItem> candidates = soulItems.stream()
                .toList();
        AbstractSoulItem soulItem=candidates.get(random);
        return soulItem.getDefaultStack();
    }

    private Set<AbstractSoulItem> getSoulItems(){
        return Registries.ITEM.stream()
                .filter(item -> item instanceof AbstractSoulItem)
                .map(item -> (AbstractSoulItem) item)
                .collect(Collectors.toSet());
    }
}
