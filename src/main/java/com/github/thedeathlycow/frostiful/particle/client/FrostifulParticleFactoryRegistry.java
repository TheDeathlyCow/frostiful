package com.github.thedeathlycow.frostiful.particle.client;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.particle.FrostifulParticleTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.FabricSpriteProvider;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class FrostifulParticleFactoryRegistry {

    public static void registerFactories() {
        registerFactory("heat_drain", FrostifulParticleTypes.HEAT_DRAIN, HeatDrainParticle.Factory::new);
    }

    private static <T extends ParticleEffect> void registerFactory(String name, ParticleType<T> particle, ParticleFactoryRegistry.PendingParticleFactory<T> factory) {
        addParticleToResourceReload(name);
        ParticleFactoryRegistry.getInstance().register(particle, factory);
    }

    private static <T extends ParticleEffect> void registerFactory(String name, ParticleType<T> particle, ParticleFactory<T> factory) {
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
