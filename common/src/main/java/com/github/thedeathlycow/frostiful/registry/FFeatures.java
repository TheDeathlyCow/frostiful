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

package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.server.world.gen.feature.IcicleFeature;
import com.github.thedeathlycow.frostiful.server.world.gen.feature.coveredrock.CoveredRockFeature;
import com.github.thedeathlycow.frostiful.server.world.gen.feature.coveredrock.CoveredRockFeatureConfig;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class FFeatures {

    public static final Feature<CoveredRockFeatureConfig> COVERED_ROCK = register(
            "covered_rock",
            new CoveredRockFeature(CoveredRockFeatureConfig.CODEC)
    );
    public static final Feature<IcicleFeature.IcicleFeatureConfig> ICICLE_PATCH = register(
            "icicle_patch",
            new IcicleFeature(IcicleFeature.IcicleFeatureConfig.CODEC)
    );

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful features");
    }

    private static <C extends FeatureConfiguration> Feature<C> register(String name, Feature<C> feature) {
        return Registry.register(BuiltInRegistries.FEATURE, Frostiful.id(name), feature);
    }

    private FFeatures() {

    }
}
