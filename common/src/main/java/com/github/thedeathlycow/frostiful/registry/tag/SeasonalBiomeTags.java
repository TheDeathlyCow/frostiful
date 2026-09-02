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

import com.github.thedeathlycow.thermoo.api.season.v2.TemperateSeason;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public record SeasonalBiomeTags(
        TagKey<Biome> freezing,
        TagKey<Biome> cold,
        TagKey<Biome> cool,
        TagKey<Biome> normal
) {

    private static final SeasonalBiomeTags SPRING_TAGS = new SeasonalBiomeTags(
            FBiomeTags.register("temperature/spring/freezing"),
            FBiomeTags.register("temperature/spring/cold"),
            FBiomeTags.register("temperature/spring/cool"),
            FBiomeTags.register("temperature/spring/normal")
    );
    private static final SeasonalBiomeTags SUMMER_TAGS = new SeasonalBiomeTags(
            FBiomeTags.register("temperature/summer/freezing"),
            FBiomeTags.register("temperature/summer/cold"),
            FBiomeTags.register("temperature/summer/cool"),
            FBiomeTags.register("temperature/summer/normal")
    );
    private static final SeasonalBiomeTags AUTUMN_TAGS = new SeasonalBiomeTags(
            FBiomeTags.register("temperature/autumn/freezing"),
            FBiomeTags.register("temperature/autumn/cold"),
            FBiomeTags.register("temperature/autumn/cool"),
            FBiomeTags.register("temperature/autumn/normal")
    );
    private static final SeasonalBiomeTags WINTER_TAGS = new SeasonalBiomeTags(
            FBiomeTags.register("temperature/winter/freezing"),
            FBiomeTags.register("temperature/winter/cold"),
            FBiomeTags.register("temperature/winter/cool"),
            FBiomeTags.register("temperature/winter/normal")
    );

    /**
     * Returns the biome tags for each season. If no season is provided, then returns the tags for spring
     *
     * @param season The season
     * @return The tags of that season, or spring if no season is given
     */
    public static SeasonalBiomeTags forSeason(TemperateSeason season) {
        return switch (season) {
            case SUMMER -> SUMMER_TAGS;
            case WINTER -> WINTER_TAGS;
            case AUTUMN -> AUTUMN_TAGS;
            case null, default -> SPRING_TAGS;
        };
    }

}
