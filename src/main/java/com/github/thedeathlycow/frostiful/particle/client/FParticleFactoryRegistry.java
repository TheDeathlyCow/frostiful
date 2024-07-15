package com.github.thedeathlycow.frostiful.particle.client;

import com.github.thedeathlycow.frostiful.registry.FParticleTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;

@Environment(EnvType.CLIENT)
public class FParticleFactoryRegistry {

    public static void registerFactories() {
        registerFactory(FParticleTypes.HEAT_DRAIN, HeatDrainParticle.Factory::new);
        registerFactory(FParticleTypes.WIND, WindParticle.Factory::new);
        registerFactory(FParticleTypes.WIND_FLIPPED, WindParticle.Factory::new);
    }

    private static <T extends ParticleEffect> void registerFactory(ParticleType<T> particle, ParticleFactoryRegistry.PendingParticleFactory<T> factory) {
        ParticleFactoryRegistry.getInstance().register(particle, factory);
    }

}
