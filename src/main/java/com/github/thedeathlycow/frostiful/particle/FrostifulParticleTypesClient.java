package com.github.thedeathlycow.frostiful.particle;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class FrostifulParticleTypesClient {

    public static void registerParticleTypes() {
        register("heat_drain", FrostifulParticleTypes.HEAT_DRAIN, HeatDrainParticleFactory::new, true);
    }

    private static <T extends ParticleEffect> void register(String name, ParticleType<T> particle, ParticleFactoryRegistry.PendingParticleFactory<T> factory, boolean hasCustomTexture) {
        if (hasCustomTexture) {
            ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(
                    (atlasTexture, registry) -> {
                        registry.register(new Identifier(Frostiful.MODID, "particle/" + name));
                    }
            );
        }

        ParticleFactoryRegistry.getInstance().register(particle, factory);
    }
}
