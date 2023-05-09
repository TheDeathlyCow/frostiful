package com.github.thedeathlycow.frostiful.particle;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class FParticleTypes {

    public static final ParticleType<HeatDrainParticleEffect> HEAT_DRAIN = FabricParticleTypes.complex(HeatDrainParticleEffect.FACTORY);
    public static final ParticleType<WindParticleEffect> WIND = FabricParticleTypes.complex(WindParticleEffect.FACTORY);
    public static final ParticleType<WindParticleEffect> WIND_FLIPPED = FabricParticleTypes.complex(WindParticleEffect.FACTORY);

    public static void registerParticleTypes() {
        register("heat_drain", HEAT_DRAIN);
        register("wind", WIND);
        register("wind_flipped", WIND_FLIPPED);
    }

    private static void register(String name, ParticleType<?> particle) {
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(Frostiful.MODID, name), particle);
    }
}
