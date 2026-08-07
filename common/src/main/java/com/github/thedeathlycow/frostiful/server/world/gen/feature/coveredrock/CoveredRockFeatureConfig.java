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

package com.github.thedeathlycow.frostiful.server.world.gen.feature.coveredrock;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

/**
 * The config for covered rock features
 *
 * @param base            The base block of the rock, e.g. stone
 * @param size            The size of the rock.
 * @param coveringFeature The feature that will cover the rock
 */
public record CoveredRockFeatureConfig(
        BlockStateProvider base,
        CoveredRockSizeConfig size,
        Holder<PlacedFeature> coveringFeature,
        float placeCoveringChance,
        BlockPredicate canPlaceOn
) implements FeatureConfiguration {
    public static final Codec<CoveredRockFeatureConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    BlockStateProvider.CODEC
                            .fieldOf("base")
                            .forGetter(CoveredRockFeatureConfig::base),
                    CoveredRockSizeConfig.CODEC
                            .fieldOf("size")
                            .forGetter(CoveredRockFeatureConfig::size),
                    PlacedFeature.CODEC
                            .fieldOf("covering_feature")
                            .forGetter(CoveredRockFeatureConfig::coveringFeature),
                    Codec.floatRange(0.0f, 1.0f)
                            .fieldOf("place_covering_chance")
                            .forGetter(CoveredRockFeatureConfig::placeCoveringChance),
                    BlockPredicate.CODEC
                            .optionalFieldOf("can_place_on", BlockPredicate.alwaysTrue())
                            .forGetter(CoveredRockFeatureConfig::canPlaceOn)
            ).apply(instance, instance.stable(CoveredRockFeatureConfig::new))
    );
}
