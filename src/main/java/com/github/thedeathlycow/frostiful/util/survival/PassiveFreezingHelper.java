package com.github.thedeathlycow.frostiful.util.survival;

import com.github.thedeathlycow.frostiful.config.FreezingConfig;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.tag.biome.FrostifulBiomeTemperatureTags;
import com.github.thedeathlycow.simple.config.Config;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
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
        if (lightLevel >= config.get(FreezingConfig.MIN_WARMTH_LIGHT_LEVEL)) {
            warmth += config.get(FreezingConfig.WARMTH_PER_LIGHT_LEVEL) * (lightLevel - config.get(FreezingConfig.MIN_WARMTH_LIGHT_LEVEL));
        }

        if (livingEntity.isOnFire()) {
            warmth += config.get(FreezingConfig.ON_FIRE_THAW_RATE);
        }

        if (!livingEntity.canFreeze()) {
            warmth += config.get(FreezingConfig.WARM_BIOME_THAW_RATE);
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
        Config config = FreezingConfig.CONFIG;

        if (!livingEntity.canFreeze()) {
            return 0;
        }

        RegistryEntry<Biome> biomeEntry = world.getBiome(pos);
        Biome biome = biomeEntry.value();
        float temperature = biome.getTemperature();
        boolean inNaturalDimension = world.getDimension().isNatural();

        boolean freezingBelowDamageThreshold = livingEntity.getFrozenTicks() < livingEntity.getMinFreezeDamageTicks();
        if (biomeEntry.isIn(FrostifulBiomeTemperatureTags.IS_CHILLY) && freezingBelowDamageThreshold) {
            return config.get(FreezingConfig.CHILLY_BIOME_FREEZE_RATE);
        } else if (biomeEntry.isIn(FrostifulBiomeTemperatureTags.IS_COLD) && freezingBelowDamageThreshold) {
            return config.get(FreezingConfig.COLD_BIOME_FREEZE_RATE);
        } else if (biomeEntry.isIn(FrostifulBiomeTemperatureTags.IS_FREEZING) && freezingBelowDamageThreshold) {
            return config.get(FreezingConfig.FREEZING_BIOME_FREEZE_RATE);
        } else if (!freezingBelowDamageThreshold) {
            return 0;
        } else {
            return -config.get(FreezingConfig.WARM_BIOME_THAW_RATE);
        }
    }

}
