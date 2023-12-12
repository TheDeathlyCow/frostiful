package com.github.thedeathlycow.frostiful.compat;

import com.github.thedeathlycow.frostiful.survival.BiomeCategory;
import io.github.lucaargolo.seasons.FabricSeasons;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/**
 * Used as a mod-neutral adapter for different possible seasons from other mods.
 */
public enum AdaptedSeason {

    SPRING,
    SUMMER,
    AUTUMN,
    WINTER,
    WET_SEASON,
    DRY_SEASON;


    /**
     * Gets the current season in the world. Returns null if no season mod is enabled.
     *
     * @param world The world to get the season for.
     * @return Returns the current season. Returns null if no season mod is enabled.
     */
    @Nullable
    public static AdaptedSeason getCurrentSeason(World world) {
        if (FrostifulIntegrations.isModLoaded(FrostifulIntegrations.FABRIC_SEASONS_ID)) {
            return switch (FabricSeasons.getCurrentSeason(world)) {
                case SUMMER -> AdaptedSeason.SUMMER;
                case WINTER -> AdaptedSeason.WINTER;
                case FALL -> AdaptedSeason.AUTUMN;
                case SPRING -> AdaptedSeason.SPRING;
            };
        } else {
            return null;
        }
    }

    /**
     * Adjusts biome categories for their season.
     *
     * @param season The current season
     * @param normalCategory The normal category of the biome if no season mod was enabled.
     * @return Returns the biome category, adjusted for the season.
     */
    public static BiomeCategory getSeasonallyShiftedBiomeCategory(
            @Nullable AdaptedSeason season,
            BiomeCategory normalCategory
    ) {
        if (isSummer(season) && normalCategory == BiomeCategory.FREEZING) {
            return BiomeCategory.COLD;
        }
        return normalCategory;
    }

    /**
     * Checks if the current season is one of the two possible summer seasons.
     *
     * @param season The current season
     * @return Returns true if the season is {@link #SUMMER} or {@link #DRY_SEASON}
     */
    public static boolean isSummer(@Nullable AdaptedSeason season) {
        return season == SUMMER || season == DRY_SEASON;
    }
}
