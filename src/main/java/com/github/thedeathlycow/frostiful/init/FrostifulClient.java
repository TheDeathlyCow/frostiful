package com.github.thedeathlycow.frostiful.init;

import com.github.thedeathlycow.frostiful.block.FrostifulCutouts;
import com.github.thedeathlycow.frostiful.client.render.entity.FrostifulEntityRenderers;
import com.github.thedeathlycow.frostiful.particle.FrostifulParticleTypesClient;
import net.fabricmc.api.ClientModInitializer;

public class FrostifulClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Frostiful.LOGGER.info("Initializing Frostiful client...");
        FrostifulCutouts.registerCutouts();
        FrostifulEntityRenderers.registerEntityRenderers();
        FrostifulParticleTypesClient.registerParticleTypes();
        Frostiful.LOGGER.info("Initialized Frostiful client!");
    }
}
