package net.soaringpromise.mxx;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.Identifier;
import net.soaringpromise.mxx.event.CropEvents;
import net.soaringpromise.mxx.event.LootEvents;
import net.soaringpromise.mxx.event.PlayerEvents;
import net.soaringpromise.mxx.registry.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Mxx implements ModInitializer {
	public static final String MOD_NAME = "Mia's XPerience XPanded";
	public static final String MOD_ID = "xperience-xpanded";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModConfigs.register();

		ModEffects.register();
		ModPotions.register();
		ModResourceConditions.register();
		ModPayloads.register();

		CropEvents.register();
		PlayerEvents.register();
		LootEvents.register();

		LOGGER.info("{} successfully initialized.", MOD_NAME);
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}