package com.github.thedeathlycow.frostiful.survival;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.compat.SeasonHelper;
import com.github.thedeathlycow.frostiful.config.group.EnvironmentConfigGroup;
import com.github.thedeathlycow.frostiful.registry.tag.FBiomeTags;
import com.github.thedeathlycow.thermoo.api.season.ThermooSeason;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
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

    private static final int NIGHT_TIME_SURFACE_LIGHT = 4;

    private final ToIntFunction<EnvironmentConfigGroup> temperatureChange;
    private final boolean coldAtNight;

    BiomeCategory(ToIntFunction<EnvironmentConfigGroup> temperatureChange, boolean coldAtNight) {
        this.temperatureChange = temperatureChange;
        this.coldAtNight = coldAtNight;
    }

    public static BiomeCategory fromBiome(RegistryEntry<Biome> biomeEntry, @Nullable ThermooSeason season) {
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

        return SeasonHelper.getSeasonallyShiftedBiomeCategory(season, category);
    }

    public int getTemperatureChange(World world, BlockPos pos, @Nullable ThermooSeason season) {
        EnvironmentConfigGroup config = Frostiful.getConfig().environmentConfig;
        int change = this.temperatureChange.applyAsInt(config);

        int sunlight = world.getLightLevel(LightType.SKY, pos) - world.getAmbientDarkness();
        if (coldAtNight && sunlight <= NIGHT_TIME_SURFACE_LIGHT && (season != ThermooSeason.SUMMER || config.isNightColdInSummer())) {
            change += config.getNightTemperatureShift();
        }

        if (change < 0 && season == ThermooSeason.WINTER) {
            change += config.getWinterTemperatureShift();
        }


        return change;
    }
}
