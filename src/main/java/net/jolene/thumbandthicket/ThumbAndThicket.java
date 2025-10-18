package net.jolene.thumbandthicket;

import net.fabricmc.api.ModInitializer;

import net.jolene.thumbandthicket.block.ModBlocks;
import net.jolene.thumbandthicket.item.ModItemGroups;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThumbAndThicket implements ModInitializer {
	public static final String MOD_ID = "thumbandthicket";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();
		ModBlocks.registerModBlocks();
		LOGGER.info("Muddy!");
	}


	public static Identifier id(String name) {
		return Identifier.of(MOD_ID, name);
	}
}