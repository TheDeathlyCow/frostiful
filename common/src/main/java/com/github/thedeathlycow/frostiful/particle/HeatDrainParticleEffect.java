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
