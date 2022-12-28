package com.github.thedeathlycow.frostiful.util.survival;

import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.entity.FreezableEntity;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.mixins.entity.EntityInvoker;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
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

        int biomeFreezing = getBiomeFreezing(player);

        if (biomeFreezing > 0) {
            float modifier = SoakingHelper.getWetnessFreezeModifier(player);
            biomeFreezing = MathHelper.ceil(biomeFreezing * (1 + modifier));
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
        int minLightLevel = config.freezingConfig.getMinLightForWarmth();

        if (lightLevel >= minLightLevel) {
            warmth += config.freezingConfig.getWarmthPerLightLevel() * (lightLevel - minLightLevel);
        }

        if (livingEntity.isOnFire()) {
            warmth += config.freezingConfig.getOnFireThawRate();
        }

        if (!freezable.frostiful$canFreeze()) {
            warmth += config.freezingConfig.getCannotFreezeThawRate();
        }

        boolean isSubmerged = livingEntity.isSubmergedInWater()
                || ((EntityInvoker) livingEntity).frostiful$invokeIsInsideBubbleColumn();
        if (isSubmerged && livingEntity.hasStatusEffect(StatusEffects.CONDUIT_POWER)) {
            warmth += config.freezingConfig.getConduitPowerWarmthPerTick();
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
            return getPerTickFreezing(world, temperature, biome.getPrecipitation() == Biome.Precipitation.NONE);
        } else if (world.getDimension().ultrawarm()) {
            FrostifulConfig config = Frostiful.getConfig();
            return -config.freezingConfig.getUltrawarmThawRate();
        }
        return 0;
    }

    public static int getPerTickFreezing(World world, float temperature, boolean isDryBiome) {
        FrostifulConfig config = Frostiful.getConfig();
        double mul = config.freezingConfig.getBiomeTemperatureMultiplier();
        double cutoff = config.freezingConfig.getPassiveFreezingStartTemp();

        double tempShift = 0.0;
        if (world.isNight()) {
            if (isDryBiome) {
                temperature = Math.min(temperature, config.freezingConfig.getDryBiomeNightTemperature());
            } else {
                tempShift = config.freezingConfig.getNightTimeTemperatureDecrease();
            }
        }

        return MathHelper.floor(-mul * (temperature - cutoff - tempShift) + 1);
    }



}
