package com.nekoadventure;

import com.nekoadventure.item.ModItems;
import com.nekoadventure.item.itemGroup.ModItemGroups;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NekoAdventure implements ModInitializer {
	public static final String MOD_ID = "neko-adventure";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModItemGroups.registerGroups();
		ModItems.initialize();
		LOGGER.info("Hello Fabric world!");
	}
}