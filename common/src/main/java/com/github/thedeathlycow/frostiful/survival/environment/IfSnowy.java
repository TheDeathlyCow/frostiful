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

package com.github.thedeathlycow.frostiful.survival.environment;

import com.github.thedeathlycow.thermoo.api.environment.v2.provider.EnvironmentProvider;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

public record IfSnowy(
        Holder<EnvironmentProvider> whenTrue
) implements EnvironmentProvider {
    public static final MapCodec<IfSnowy> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    EnvironmentProvider.HOLDER_CODEC
                            .fieldOf("when_true")
                            .forGetter(IfSnowy::whenTrue)
            ).apply(instance, IfSnowy::new)
    );

    @Override
    public void buildCurrentComponents(Level level, BlockPos pos, Holder<Biome> biome, DataComponentMap.Builder builder) {
        if (isSnowy(level, pos, biome)) {
            whenTrue.value().buildCurrentComponents(level, pos, biome, builder);
        }
    }

    @Override
    public MapCodec<IfSnowy> codec() {
        return CODEC;
    }

    private static boolean isSnowy(Level level, BlockPos pos, Holder<Biome> biomeHolder) {
        Biome biome = biomeHolder.value();

        return biome.getBaseTemperature() < 0.15f
                || biome.getPrecipitationAt(pos, level.getSeaLevel()) == Biome.Precipitation.SNOW;
    }
}