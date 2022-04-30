package com.github.thedeathlycow.frostiful.init;

import net.fabricmc.api.DedicatedServerModInitializer;

public class FrostifulServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        Frostiful.LOGGER.info("Initializing Frostiful server...");
        Frostiful.LOGGER.info("Initialized Frostiful server!");
    }
}
