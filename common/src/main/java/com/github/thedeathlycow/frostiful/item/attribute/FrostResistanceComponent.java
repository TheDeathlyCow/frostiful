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

package com.github.thedeathlycow.frostiful.item.attribute;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record FrostResistanceComponent(
    double frostResistanceMultiplier,
    double environmentFrostResistanceMultiplier
) {
    public static final Codec<FrostResistanceComponent> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.DOUBLE
                            .fieldOf("frost_resistance_multiplier")
                            .forGetter(FrostResistanceComponent::frostResistanceMultiplier),
                    Codec.DOUBLE
                            .fieldOf("environment_frost_resistance_multiplier")
                            .forGetter(FrostResistanceComponent::environmentFrostResistanceMultiplier)
            ).apply(instance, FrostResistanceComponent::new)
    );

    public static final StreamCodec<ByteBuf, FrostResistanceComponent> PACKET_CODEC = StreamCodec.composite(
            ByteBufCodecs.DOUBLE,
            FrostResistanceComponent::frostResistanceMultiplier,
            ByteBufCodecs.DOUBLE,
            FrostResistanceComponent::environmentFrostResistanceMultiplier,
            FrostResistanceComponent::new
    );

    public static final FrostResistanceComponent DEFAULT = new FrostResistanceComponent(0, 0);
    public static final FrostResistanceComponent VERY_PROTECTIVE = new FrostResistanceComponent(1, 1);
    public static final FrostResistanceComponent PROTECTIVE = new FrostResistanceComponent(0.5, 0.5);
    public static final FrostResistanceComponent HARMFUL = new FrostResistanceComponent(-0.5, -0.5);
    public static final FrostResistanceComponent VERY_HARMFUL = new FrostResistanceComponent(-1, -1);
}