package com.github.thedeathlycow.frostiful.init;

import com.github.thedeathlycow.frostiful.block.FrostifulCutouts;
import com.github.thedeathlycow.frostiful.client.render.entity.FrostifulEntityRenderers;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.oroarmor.config.Config;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;

public class FrostifulClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Frostiful.LOGGER.info("Initializing Frostiful client...");
        FrostifulCutouts.registerCutouts();
        FrostifulEntityRenderers.registerEntityRenderers();
        Frostiful.LOGGER.info("Initialized Frostiful client!");
    }
}
