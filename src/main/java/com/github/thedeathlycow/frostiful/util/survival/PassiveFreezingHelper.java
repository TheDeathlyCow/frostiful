package com.github.thedeathlycow.frostiful.util.survival;

import com.github.thedeathlycow.frostiful.config.FreezingConfig;
import com.github.thedeathlycow.simple.config.Config;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class PassiveFreezingHelper {

    public static int getPassiveFreezing(LivingEntity livingEntity) {
        int biomeFreezing = getBiomeFreezing(livingEntity);
        if (biomeFreezing > 0) {
            biomeFreezing *= getPassiveFreezingMultiplier(livingEntity);
        }
        return biomeFreezing;
    }

    public static int getWarmth(LivingEntity livingEntity) {
        World world = livingEntity.getWorld();
        BlockPos pos = livingEntity.getBlockPos();
        Config config = FreezingConfig.CONFIG;
        int warmth = 0;

        int lightLevel = world.getLightLevel(LightType.BLOCK, pos);
        int minLightLevel = world.isDay() ?
                config.get(FreezingConfig.MIN_WARMTH_LIGHT_LEVEL_DAY) :
                config.get(FreezingConfig.MIN_WARMTH_LIGHT_LEVEL_NIGHT);

        if (lightLevel >= minLightLevel) {
            warmth += config.get(FreezingConfig.WARMTH_PER_LIGHT_LEVEL) * (lightLevel - minLightLevel);
        }

        if (livingEntity.isOnFire()) {
            warmth += config.get(FreezingConfig.ON_FIRE_THAW_RATE);
        }

        if (!livingEntity.canFreeze()) {
            warmth += config.get(FreezingConfig.CANNOT_FREEZE_WARM_RATE);
        }

        return warmth;
    }

    public static int getPowderSnowFreezing(LivingEntity livingEntity) {
        Config config = FreezingConfig.CONFIG;
        return livingEntity.inPowderSnow ?
                config.get(FreezingConfig.POWDER_SNOW_INCREASE_PER_TICK) :
                0;
    }

    public static double getPassiveFreezingMultiplier(LivingEntity livingEntity) {
        Config config = FreezingConfig.CONFIG;
        double multiplier = 0.0D;

        if (livingEntity.isWet()) {
            multiplier += config.get(FreezingConfig.WET_FREEZE_RATE_MULTIPLIER);
        }

        return multiplier == 0.0D ? 1 : multiplier;
    }

    public static int getBiomeFreezing(LivingEntity livingEntity) {
        World world = livingEntity.getWorld();
        BlockPos pos = livingEntity.getBlockPos();


        if (!livingEntity.canFreeze()) {
            return 0;
        }

        RegistryEntry<Biome> biomeEntry = world.getBiome(pos);

        boolean inNaturalDimension = world.getDimension().isNatural();
        if (inNaturalDimension) {
            Biome biome = biomeEntry.value();
            float temperature = biome.getTemperature();
            return getPerTickFreezing(temperature);
        }
        return 0;
    }

    public static int getPerTickFreezing(float temperature) {
        Config config = FreezingConfig.CONFIG;
        int mul = config.get(FreezingConfig.BIOME_TEMPERATURE_MULTIPLIER);
        float cutoff = config.get(FreezingConfig.PASSIVE_FREEZING_START_TEMP);

        return MathHelper.floor(-mul * (Math.pow(temperature - cutoff, 3)) + 1);
    }

}
