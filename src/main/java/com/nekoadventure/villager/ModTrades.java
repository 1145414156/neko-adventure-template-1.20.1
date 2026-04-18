package com.nekoadventure.villager;

import com.nekoadventure.item.ModItems;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;

public class ModTrades {
    public static void registerTrades(){
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.WEAPONSMITH, 1, factories -> {
            factories.add(new TradeOffers.SellItemFactory(ModItems.BROKEN_SWORD, 3, 1, 16, 5));});
    }
}
