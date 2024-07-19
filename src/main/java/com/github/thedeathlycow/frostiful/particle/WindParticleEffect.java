package com.github.thedeathlycow.frostiful.particle;

import com.github.thedeathlycow.frostiful.registry.FParticleTypes;
import com.github.thedeathlycow.frostiful.util.FPacketCodecs;
import com.mojang.brigadier.StringReader;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.util.Locale;

public record WindParticleEffect(
        boolean flipped
) implements ParticleEffect {

    public static final MapCodec<WindParticleEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            Codec.BOOL
                                    .fieldOf("flipped")
                                    .forGetter(WindParticleEffect::flipped)
                    )
                    .apply(instance, WindParticleEffect::new)
    );

    public static final PacketCodec<RegistryByteBuf, WindParticleEffect> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL,
            WindParticleEffect::flipped,
            WindParticleEffect::new
    );

    @Override
    public ParticleType<?> getType() {
        return this.flipped ? FParticleTypes.WIND_FLIPPED : FParticleTypes.WIND;
    }
}
