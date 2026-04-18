package com.nekoadventure;

import com.nekoadventure.block.ModBlocks;
import com.nekoadventure.block.blockentity.ModBlockEntityTypes;
import com.nekoadventure.effect.ModStatusEffects;
import com.nekoadventure.entity.ModEntities;
import com.nekoadventure.event.ModEvents;
import com.nekoadventure.item.ModItems;
import com.nekoadventure.item.itemGroup.ModItemGroups;
import com.nekoadventure.network.NekoPackageDataNetworking;
import com.nekoadventure.sound.ModSoundEvents;
import com.nekoadventure.villager.ModTrades;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NekoAdventure implements ModInitializer {
	public static final String MOD_ID = "neko-adventure";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.initialize();
		ModTrades.registerTrades();
		ModBlocks.initialize();
		ModStatusEffects.initialize();
		ModBlockEntityTypes.initialize();
		ModSoundEvents.initialize();
		ModItemGroups.registerGroups();
		ModEvents.initialize();
		NekoPackageDataNetworking.registerServerReceivers();

		ModEntities.initialize();

		LOGGER.info("create by inf...");
	}
}