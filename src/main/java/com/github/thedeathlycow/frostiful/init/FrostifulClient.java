package com.github.thedeathlycow.frostiful.init;

import com.github.thedeathlycow.frostiful.block.FrostifulCutouts;
import com.github.thedeathlycow.frostiful.client.render.entity.FrostifulEntityRenderers;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.oroarmor.config.Config;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;

public class FrostifulClient implements ClientModInitializer {

    public static final Config CONFIG = new FrostifulConfig();

    @Override
    public void onInitializeClient() {
        Frostiful.LOGGER.info("Initializing Frostiful client...");

        CONFIG.readConfigFromFile();
        CONFIG.saveConfigToFile();
        ClientLifecycleEvents.CLIENT_STOPPING.register(instance -> CONFIG.saveConfigToFile());

        FrostifulCutouts.registerCutouts();
        FrostifulEntityRenderers.registerEntityRenderers();
        Frostiful.LOGGER.info("Initialized Frostiful client!");
    }
}
