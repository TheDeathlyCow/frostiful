package com.github.thedeathlycow.frostiful.particle;

import com.github.thedeathlycow.frostiful.registry.FParticleTypes;
import com.github.thedeathlycow.frostiful.util.FPacketCodecs;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.math.Vec3d;

public record HeatDrainParticleEffect(
        Vec3d destination
) implements ParticleEffect {

    public static final MapCodec<HeatDrainParticleEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            Vec3d.CODEC
                                    .fieldOf("destination")
                                    .forGetter(HeatDrainParticleEffect::destination)
                    )
                    .apply(instance, HeatDrainParticleEffect::new)
    );

    public static final PacketCodec<RegistryByteBuf, HeatDrainParticleEffect> PACKET_CODEC = PacketCodec.tuple(
            FPacketCodecs.VEC3D,
            HeatDrainParticleEffect::destination,
            HeatDrainParticleEffect::new
    );

    @Override
    public ParticleType<?> getType() {
        return FParticleTypes.HEAT_DRAIN;
    }
}
