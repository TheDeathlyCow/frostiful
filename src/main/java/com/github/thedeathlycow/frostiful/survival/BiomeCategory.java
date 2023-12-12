package com.github.thedeathlycow.frostiful.survival;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.group.EnvironmentConfigGroup;
import com.github.thedeathlycow.frostiful.tag.FBiomeTags;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.function.ToIntFunction;

public enum BiomeCategory {

    NORMAL(c -> 0, false),
    COLD_AT_NIGHT(c -> 0, true),
    COLD(EnvironmentConfigGroup::getColdBiomeTemperatureChange, true),
    FREEZING(EnvironmentConfigGroup::getFreezingBiomeTemperatureChange, true);

    private static final float SNOW_TEMPERATURE = 0.15f;
    private static final float TAIGA_TEMPERATURE = 0.25f;

    private final ToIntFunction<EnvironmentConfigGroup> temperatureChange;
    private final boolean coldAtNight;

    BiomeCategory(ToIntFunction<EnvironmentConfigGroup> temperatureChange, boolean coldAtNight) {
        this.temperatureChange = temperatureChange;
        this.coldAtNight = coldAtNight;
    }

    public static BiomeCategory fromBiome(RegistryEntry<Biome> biomeEntry) {
        Biome biome = biomeEntry.value();

        EnvironmentConfigGroup config = Frostiful.getConfig().environmentConfig;

        // +inf means only tags will be considered
        float temperature = config.isEnableDynamicTemperature() ? biome.getTemperature() : Float.POSITIVE_INFINITY;

        if (biomeEntry.isIn(FBiomeTags.FREEZING_BLACKLIST_BIOMES)) {
            return NORMAL;
        } else if (biomeEntry.isIn(FBiomeTags.FREEZING_BIOMES)) {
            return FREEZING;
        } else if (temperature <= SNOW_TEMPERATURE || biomeEntry.isIn(FBiomeTags.COLD_BIOMES)) {
            return COLD;
        } else if (
                temperature <= TAIGA_TEMPERATURE
                        || (biomeEntry.isIn(FBiomeTags.DRY_BIOMES) && config.doDryBiomeNightFreezing())
                        || biomeEntry.isIn(FBiomeTags.COOL_BIOMES)
        ) {
            return COLD_AT_NIGHT;
        } else {
            return NORMAL;
        }
    }

    public int getTemperatureChange(World world) {
        EnvironmentConfigGroup config = Frostiful.getConfig().environmentConfig;
        int change = this.temperatureChange.applyAsInt(config);
        if (coldAtNight && world.isNight()) {
            change += config.getNightTemperatureShift();
        }
        return change;
    }
}
