package com.github.thedeathlycow.frostiful.init;

import com.github.thedeathlycow.frostiful.block.FrostifulCutouts;
import net.fabricmc.api.ClientModInitializer;

public class FrostifulClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Frostiful.LOGGER.info("Initializing Frostiful client...");
        FrostifulCutouts.registerCutouts();
        Frostiful.LOGGER.info("Initialized Frostiful client!");
    }
}
