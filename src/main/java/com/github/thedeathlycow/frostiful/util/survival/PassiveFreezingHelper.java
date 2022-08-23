package com.github.thedeathlycow.frostiful.util.survival;

import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.entity.FreezableEntity;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class PassiveFreezingHelper {

    public static int getPassiveFreezing(PlayerEntity player) {
        final FreezableEntity freezable = (FreezableEntity) player;
        if (!freezable.frostiful$canFreeze()) {
            return 0;
        }

        FrostifulConfig config = Frostiful.getConfig();

        int biomeFreezing = getBiomeFreezing(player);
        World world = player.getWorld();
        BlockPos pos = player.getBlockPos();
        Biome biome = world.getBiome(pos).value();
        if (biome.isCold(pos) && player.isWet()) {
            biomeFreezing += config.freezingConfig.getWetFreezeRate();
        }

        return biomeFreezing;
    }

    public static int getWarmth(LivingEntity livingEntity) {
        final FreezableEntity freezable = (FreezableEntity) livingEntity;
        World world = livingEntity.getWorld();
        BlockPos pos = livingEntity.getBlockPos();
        int warmth = 0;
        FrostifulConfig config = Frostiful.getConfig();

        int lightLevel = world.getLightLevel(LightType.BLOCK, pos);
        int minLightLevel = world.isDay() ?
                config.freezingConfig.getMinWarmthForLightDay() :
                config.freezingConfig.getMinWarmthForLightNight();

        if (lightLevel >= minLightLevel) {
            warmth += config.freezingConfig.getWarmthPerLightLevel() * (lightLevel - minLightLevel);
        }

        if (livingEntity.isOnFire()) {
            warmth += config.freezingConfig.getOnFireThawRate();
        }

        if (!freezable.frostiful$canFreeze()) {
            warmth += config.freezingConfig.getCannotFreezeThawRate();
        }

        return warmth;
    }

    public static int getBiomeFreezing(LivingEntity livingEntity) {
        World world = livingEntity.getWorld();
        BlockPos pos = livingEntity.getBlockPos();
        boolean inNaturalDimension = world.getDimension().natural();
        if (inNaturalDimension) {
            RegistryEntry<Biome> biomeEntry = world.getBiome(pos);
            Biome biome = biomeEntry.value();
            float temperature = biome.getTemperature();
            return getPerTickFreezing(temperature);
        }
        return 0;
    }

    public static int getPerTickFreezing(float temperature) {
        FrostifulConfig config = Frostiful.getConfig();
        double mul = config.freezingConfig.getBiomeTemperatureMultiplier();
        double cutoff =config.freezingConfig.getPassiveFreezingStartTemp();

        return MathHelper.floor(-mul * (temperature - cutoff) + 1);
    }

}
