package com.github.thedeathlycow.frostiful.particle.client;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.particle.FParticleTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class FParticleFactoryRegistry {

    public static void registerFactories() {
        registerFactory(FParticleTypes.HEAT_DRAIN, HeatDrainParticle.Factory::new);
        registerFactory(FParticleTypes.WIND, WindParticle.Factory::new);
    }

    private static <T extends ParticleEffect> void registerFactory(ParticleType<T> particle, ParticleFactoryRegistry.PendingParticleFactory<T> factory) {
        ParticleFactoryRegistry.getInstance().register(particle, factory);
    }

    private static <T extends ParticleEffect> void registerFactory(String name, ParticleType<T> particle, ParticleFactoryRegistry.PendingParticleFactory<T> factory) {
        addParticleToResourceReload(name);
        ParticleFactoryRegistry.getInstance().register(particle, factory);
    }

    private static void addParticleToResourceReload(String name) {
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(
                (atlasTexture, registry) -> {
                    registry.register(new Identifier(Frostiful.MODID, "particle/" + name));
                }
        );
    }
}
