package com.github.thedeathlycow.frostiful.particle;

import com.github.thedeathlycow.frostiful.registry.FParticleTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record WindParticleEffect(
        boolean flipped
) implements ParticleOptions {

    public static final MapCodec<WindParticleEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            Codec.BOOL
                                    .fieldOf("flipped")
                                    .forGetter(WindParticleEffect::flipped)
                    )
                    .apply(instance, WindParticleEffect::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, WindParticleEffect> PACKET_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            WindParticleEffect::flipped,
            WindParticleEffect::new
    );

    @Override
    public ParticleType<?> getType() {
        return this.flipped ? FParticleTypes.WIND_FLIPPED : FParticleTypes.WIND;
    }
}
