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

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class FBiomeTags {
    public static final TagKey<Biome> FREEZING_WIND_NEVER_SPAWNS = FBiomeTags.register("freezing_wind_never_spawns");
    public static final TagKey<Biome> FREEZING_WIND_ALWAYS_SPAWNS = FBiomeTags.register("freezing_wind_always_spawns");
    public static final TagKey<Biome> FREEZING_WIND_SPAWNS_IN_STORMS = FBiomeTags.register("freezing_wind_spawns_in_storms");
    public static final TagKey<Biome> DRY_BIOMES = FBiomeTags.register("dry_biomes");
    public static final TagKey<Biome> FREEZING_BLACKLIST_BIOMES = FBiomeTags.register("freezing_blacklist_biomes");

    static TagKey<Biome> register(String id) {
        return TagKey.create(Registries.BIOME, Frostiful.id(id));
    }

    private FBiomeTags() {
    }

}
