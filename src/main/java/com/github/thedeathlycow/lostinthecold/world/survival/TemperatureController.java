package com.github.thedeathlycow.lostinthecold.world.survival;

import com.github.thedeathlycow.lostinthecold.config.HypothermiaConfig;
import com.github.thedeathlycow.lostinthecold.tag.biome.BiomeTemperatureTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class TemperatureController {

    public static int getWarmth(LivingEntity livingEntity, World world, BlockPos pos, HypothermiaConfig config) {
        int warmth = 0;

        int lightLevel = world.getLightLevel(LightType.BLOCK, pos);
        if (lightLevel >= config.getMinWarmthLightLevel()) {
            warmth += config.getWarmthPerLightLevel() * (lightLevel - config.getMinWarmthLightLevel());
        }

        if (livingEntity.isOnFire()) {
            warmth += config.getOnFireFreezeRate();
        }

        return warmth;
    }

    public static double getMultiplier(LivingEntity livingEntity, HypothermiaConfig config) {

        double multiplier = 1.0D;

        if (livingEntity.inPowderSnow) {
            multiplier += config.getPowderSnowFreezeRateMultiplier();
        }

        if (livingEntity.isWet()) {
            multiplier += config.getWetFreezeRateMultiplier();
        }

        return multiplier;
    }

    public static int getBiomeFreezing(LivingEntity livingEntity, World world, BlockPos pos, HypothermiaConfig config) {
        RegistryEntry<Biome> biomeIn = world.getBiome(pos);

        if (!livingEntity.canFreeze()) {
            return config.getWarmBiomeFreezeRate();
        }

        if (biomeIn.isIn(BiomeTemperatureTags.IS_CHILLY)) {
            return config.getChillyBiomeFreezeRate();
        } else if (biomeIn.isIn(BiomeTemperatureTags.IS_COLD)) {
            return config.getColdBiomeFreezeRate();
        } else if (biomeIn.isIn(BiomeTemperatureTags.IS_FREEZING)) {
            return config.getFreezingBiomeFreezeRate();
        } else {
            return config.getWarmBiomeFreezeRate();
        }
    }

}
