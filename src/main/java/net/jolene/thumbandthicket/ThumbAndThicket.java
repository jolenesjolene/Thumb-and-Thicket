package net.jolene.thumbandthicket;

import net.fabricmc.api.ModInitializer;

import net.jolene.thumbandthicket.item.ModItemGroups;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThumbAndThicket implements ModInitializer {
	public static final String MOD_ID = "thumbandthicket";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();
		LOGGER.info("Very Green!");
	}
}