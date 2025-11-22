package com.github.thedeathlycow.frostiful.particle;

import com.github.thedeathlycow.frostiful.registry.FParticleTypes;
import com.github.thedeathlycow.frostiful.util.FPacketCodecs;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.phys.Vec3;

public record HeatDrainParticleEffect(
        Vec3 destination
) implements ParticleOptions {

    public static final MapCodec<HeatDrainParticleEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            Vec3.CODEC
                                    .fieldOf("destination")
                                    .forGetter(HeatDrainParticleEffect::destination)
                    )
                    .apply(instance, HeatDrainParticleEffect::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, HeatDrainParticleEffect> PACKET_CODEC = StreamCodec.composite(
            FPacketCodecs.VEC3D,
            HeatDrainParticleEffect::destination,
            HeatDrainParticleEffect::new
    );

    @Override
    public ParticleType<?> getType() {
        return FParticleTypes.HEAT_DRAIN;
    }
}
