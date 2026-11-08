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

package com.github.thedeathlycow.frostiful.registry.tag;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class FHasFeatureTags {

    public static final TagKey<Biome> SUN_LICHEN_COVERED_ROCK = FBiomeTags.register("has_feature/sun_lichen_covered_rock");
    public static final TagKey<Biome> ICICLE_CLUSTER = FBiomeTags.register("has_feature/icicle_cluster");
    public static final TagKey<Biome> BRITTLE_ICE = FBiomeTags.register("has_feature/brittle_ice");

    private FHasFeatureTags() {

    }

}
