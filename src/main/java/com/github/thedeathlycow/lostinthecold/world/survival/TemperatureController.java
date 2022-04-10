package com.github.thedeathlycow.lostinthecold.world.survival;

import com.github.thedeathlycow.lostinthecold.config.LostInTheColdConfig;
import com.github.thedeathlycow.lostinthecold.config.ConfigKeys;
import com.github.thedeathlycow.lostinthecold.init.LostInTheCold;
import com.github.thedeathlycow.lostinthecold.tag.biome.BiomeTemperatureTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class TemperatureController {

    public static int getWarmth(LivingEntity livingEntity, World world, BlockPos pos) {
        LostInTheColdConfig config = LostInTheCold.getConfig();
        int warmth = 0;

        int lightLevel = world.getLightLevel(LightType.BLOCK, pos);
        if (lightLevel >= config.getInt(ConfigKeys.MIN_WARMTH_LIGHT_LEVEL)) {
            warmth += config.getInt(ConfigKeys.WARMTH_PER_LIGHT_LEVEL) * (lightLevel - config.getInt(ConfigKeys.MIN_WARMTH_LIGHT_LEVEL));
        }

        if (livingEntity.isOnFire()) {
            warmth += config.getInt(ConfigKeys.WARM_BIOME_THAW_RATE);
        }

        return warmth;
    }

    public static double getMultiplier(LivingEntity livingEntity) {
        LostInTheColdConfig config = LostInTheCold.getConfig();
        double multiplier = 1.0D;

        if (livingEntity.inPowderSnow) {
            multiplier += config.getDouble(ConfigKeys.POWDER_SNOW_FREEZE_RATE_MULTIPLIER);
        }

        if (livingEntity.isWet()) {
            multiplier += config.getDouble(ConfigKeys.WET_FREEZE_RATE_MULTIPLIER);
        }

        return multiplier;
    }

    public static int getBiomeFreezing(LivingEntity livingEntity, World world, BlockPos pos) {
        LostInTheColdConfig config = LostInTheCold.getConfig();
        RegistryEntry<Biome> biomeIn = world.getBiome(pos);

        if (!livingEntity.canFreeze()) {
            return -config.getInt(ConfigKeys.WARM_BIOME_THAW_RATE);
        }

        if (biomeIn.isIn(BiomeTemperatureTags.IS_CHILLY)) {
            return config.getInt(ConfigKeys.CHILLY_BIOME_FREEZE_RATE);
        } else if (biomeIn.isIn(BiomeTemperatureTags.IS_COLD)) {
            return config.getInt(ConfigKeys.COLD_BIOME_FREEZE_RATE);
        } else if (biomeIn.isIn(BiomeTemperatureTags.IS_FREEZING)) {
            return config.getInt(ConfigKeys.FREEZING_BIOME_FREEZE_RATE);
        } else {
            return -config.getInt(ConfigKeys.WARM_BIOME_THAW_RATE);
        }
    }

}
