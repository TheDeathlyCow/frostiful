package com.github.thedeathlycow.frostiful.util.survival;

import com.github.thedeathlycow.frostiful.config.group.FreezingConfigGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class PassiveFreezingHelper {

    public static int getPassiveFreezing(LivingEntity livingEntity) {

        if (!livingEntity.canFreeze()) {
            return 0;
        }

        int biomeFreezing = getBiomeFreezing(livingEntity);
        World world = livingEntity.getWorld();
        BlockPos pos = livingEntity.getBlockPos();
        Biome biome = world.getBiome(pos).value();
        // applies regardless of biome
        if (biome.isCold(pos) && livingEntity.isWet()) {
            biomeFreezing += FreezingConfigGroup.WET_FREEZE_RATE.getValue();
        }

        return biomeFreezing;
    }

    public static int getWarmth(LivingEntity livingEntity) {
        World world = livingEntity.getWorld();
        BlockPos pos = livingEntity.getBlockPos();
        int warmth = 0;

        int lightLevel = world.getLightLevel(LightType.BLOCK, pos);
        int minLightLevel = world.isDay() ?
                FreezingConfigGroup.MIN_WARMTH_LIGHT_LEVEL_DAY.getValue() :
                FreezingConfigGroup.MIN_WARMTH_LIGHT_LEVEL_NIGHT.getValue();

        if (lightLevel >= minLightLevel) {
            warmth += FreezingConfigGroup.WARMTH_PER_LIGHT_LEVEL.getValue() * (lightLevel - minLightLevel);
        }

        if (livingEntity.isOnFire()) {
            warmth += FreezingConfigGroup.ON_FIRE_THAW_RATE.getValue();
        }

        if (!livingEntity.canFreeze()) {
            warmth += FreezingConfigGroup.CANNOT_FREEZE_THAW_RATE.getValue();
        }

        return warmth;
    }

    public static int getPowderSnowFreezing(LivingEntity livingEntity) {
        return livingEntity.inPowderSnow ?
                FreezingConfigGroup.POWDER_SNOW_FREEZE_RATE.getValue() :
                0;
    }

    public static int getBiomeFreezing(LivingEntity livingEntity) {
        World world = livingEntity.getWorld();
        BlockPos pos = livingEntity.getBlockPos();
        boolean inNaturalDimension = world.getDimension().isNatural();
        if (inNaturalDimension) {
            RegistryEntry<Biome> biomeEntry = world.getBiome(pos);
            Biome biome = biomeEntry.value();
            float temperature = biome.getTemperature();
            return getPerTickFreezing(temperature);
        }
        return 0;
    }

    public static int getPerTickFreezing(float temperature) {
        double mul = FreezingConfigGroup.BIOME_TEMPERATURE_MULTIPLIER.getValue();
        double cutoff = FreezingConfigGroup.PASSIVE_FREEZING_START_TEMP.getValue();

        return MathHelper.floor(-mul * (temperature - cutoff) + 1);
    }

}
