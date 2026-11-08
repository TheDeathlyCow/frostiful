/*
 * Frostiful: A Vanilla+ Freezing Temperature Mod. Also try Scorchful!
 * Copyright (C) 2026	TheDeathlyCow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.  If not, see
 * <https://www.gnu.org/licenses/>.
 */

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
