package com.github.thedeathlycow.frostiful.init;

import com.github.thedeathlycow.frostiful.block.FCutouts;
import com.github.thedeathlycow.frostiful.client.render.entity.FEntityRenderers;
import com.github.thedeathlycow.frostiful.particle.client.FParticleFactoryRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class FrostifulClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FCutouts.registerCutouts();
        FEntityRenderers.registerEntityRenderers();
        FParticleFactoryRegistry.registerFactories();
        Frostiful.LOGGER.info("Initialized Frostiful client!");
    }
}
