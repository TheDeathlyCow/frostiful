package com.github.thedeathlycow.lostinthecold.world.survival;

import com.github.thedeathlycow.lostinthecold.config.Config;
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
        Config config = LostInTheCold.getConfig();
        int warmth = 0;

        int lightLevel = world.getLightLevel(LightType.BLOCK, pos);
        if (lightLevel >= config.getInt("min_warmth_light_level")) {
            warmth += config.getInt("warmth_per_light_level") * (lightLevel - config.getInt("min_warmth_light_level"));
        }

        if (livingEntity.isOnFire()) {
            warmth += config.getInt("on_fire_freeze_rate");
        }

        return warmth;
    }

    public static double getMultiplier(LivingEntity livingEntity) {
        Config config = LostInTheCold.getConfig();
        double multiplier = 1.0D;

        if (livingEntity.inPowderSnow) {
            multiplier += config.getDouble("powder_snow_freeze_rate_multiplier");
        }

        if (livingEntity.isWet()) {
            multiplier += config.getDouble("wet_freeze_rate_multiplier");
        }

        return multiplier;
    }

    public static int getBiomeFreezing(LivingEntity livingEntity, World world, BlockPos pos) {
        Config config = LostInTheCold.getConfig();
        RegistryEntry<Biome> biomeIn = world.getBiome(pos);

        if (!livingEntity.canFreeze()) {
            return config.getInt("warm_biome_freeze_rate");
        }

        if (biomeIn.isIn(BiomeTemperatureTags.IS_CHILLY)) {
            return config.getInt("chilly_biome_freeze_rate");
        } else if (biomeIn.isIn(BiomeTemperatureTags.IS_COLD)) {
            return config.getInt("cold_biome_freeze_rate");
        } else if (biomeIn.isIn(BiomeTemperatureTags.IS_FREEZING)) {
            return config.getInt("freezing_biome_freeze_rate");
        } else {
            return config.getInt("warm_biome_freeze_rate");
        }
    }

}
