package com.github.thedeathlycow.frostiful.client.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.client.particle.HeatDrainParticle;
import com.github.thedeathlycow.frostiful.client.particle.WindParticle;
import com.github.thedeathlycow.frostiful.registry.FParticleTypes;

import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;


public class FParticleFactoryRegistry {

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful particle factories");
        registerFactory(FParticleTypes.HEAT_DRAIN, HeatDrainParticle.Factory::new);
        registerFactory(FParticleTypes.WIND, WindParticle.Factory::new);
        registerFactory(FParticleTypes.WIND_FLIPPED, WindParticle.Factory::new);
    }

    private static <T extends ParticleOptions> void registerFactory(ParticleType<T> particle, ParticleProviderRegistry.PendingParticleProvider<T> factory) {
        ParticleProviderRegistry.getInstance().register(particle, factory);
    }

    private FParticleFactoryRegistry() {

    }

}
