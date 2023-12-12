package com.github.thedeathlycow.frostiful.survival;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.compat.AdaptedSeason;
import com.github.thedeathlycow.frostiful.config.group.EnvironmentConfigGroup;
import com.github.thedeathlycow.frostiful.tag.FBiomeTags;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToIntFunction;

public enum BiomeCategory {

    NORMAL(c -> 0, false),
    COLD_AT_NIGHT(c -> 0, true),
    COLD(EnvironmentConfigGroup::getColdBiomeTemperatureChange, true),
    FREEZING(EnvironmentConfigGroup::getFreezingBiomeTemperatureChange, true);

    private static final float SNOW_TEMPERATURE = 0.15f;
    private static final float TAIGA_TEMPERATURE = 0.3f;

    private final ToIntFunction<EnvironmentConfigGroup> temperatureChange;
    private final boolean coldAtNight;

    BiomeCategory(ToIntFunction<EnvironmentConfigGroup> temperatureChange, boolean coldAtNight) {
        this.temperatureChange = temperatureChange;
        this.coldAtNight = coldAtNight;
    }

    public static BiomeCategory fromBiome(RegistryEntry<Biome> biomeEntry, @Nullable AdaptedSeason season) {
        Biome biome = biomeEntry.value();

        EnvironmentConfigGroup config = Frostiful.getConfig().environmentConfig;

        // +inf means only tags will be considered
        float temperature = biome.getTemperature();

        BiomeCategory category;

        if (biomeEntry.isIn(FBiomeTags.FREEZING_BLACKLIST_BIOMES)) {
            category = NORMAL;
        } else if (biomeEntry.isIn(FBiomeTags.FREEZING_BIOMES)) {
            category = FREEZING;
        } else if (temperature <= SNOW_TEMPERATURE || biomeEntry.isIn(FBiomeTags.COLD_BIOMES)) {
            category = COLD;
        } else if (
                temperature <= TAIGA_TEMPERATURE
                        || (biomeEntry.isIn(FBiomeTags.DRY_BIOMES) && config.doDryBiomeNightFreezing())
                        || biomeEntry.isIn(FBiomeTags.COOL_BIOMES)
        ) {
            category = COLD_AT_NIGHT;
        } else {
            category = NORMAL;
        }

        return AdaptedSeason.getSeasonallyShiftedBiomeCategory(season, category);
    }

    public int getTemperatureChange(World world, @Nullable AdaptedSeason season) {
        EnvironmentConfigGroup config = Frostiful.getConfig().environmentConfig;
        int change = this.temperatureChange.applyAsInt(config);

        if (coldAtNight && world.isNight() && (!AdaptedSeason.isSummer(season) || config.isNightColdInSummer())) {
            change += config.getNightTemperatureShift();
        }

        if (change < 0 && season == AdaptedSeason.WINTER) {
            change += config.getWinterTemperatureShift();
        }


        return change;
    }
}
