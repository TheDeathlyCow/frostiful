package com.github.thedeathlycow.frostiful.particle;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FrostifulParticleTypes {

    public static final DefaultParticleType HEAT_DRAIN = FabricParticleTypes.simple();

    public static void registerParticleTypes() {
        register("heat_drain", HEAT_DRAIN);
    }

    private static void register(String name, ParticleType<?> particle) {
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(Frostiful.MODID, name), particle);
    }
}
