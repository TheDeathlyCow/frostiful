package com.github.thedeathlycow.frostiful.init;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.SERVER)
public class FrostifulServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        Frostiful.LOGGER.info("Its Morbin' time");
        Frostiful.LOGGER.info("Initialized Frostiful server!");
    }
}
