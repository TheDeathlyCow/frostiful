package com.github.thedeathlycow.frostiful.survival;

import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.config.section.EnvironmentSettings;
import com.github.thedeathlycow.frostiful.config.section.TemperatureSourceSettings;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import com.github.thedeathlycow.frostiful.registry.FItems;
import com.github.thedeathlycow.thermoo.api.core.v2.event.EnvironmentTickContext;
import com.github.thedeathlycow.thermoo.api.core.v2.event.LivingEntityTemperatureTickEvents;
import com.github.thedeathlycow.thermoo.api.core.v2.source.TemperatureSources;
import com.github.thedeathlycow.thermoo.api.entity.v1.ThermooEntityTypeTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public final class ActiveTemperatureEffects {
    public static void initialize() {
        LivingEntityTemperatureTickEvents.getTemperatureChange(TemperatureSources.ACTIVE).register(ActiveTemperatureEffects::getActiveChange);
    }

    private static int getActiveChange(EnvironmentTickContext<? extends LivingEntity> context) {
        LivingEntity entity = context.affected();

        // don't touch scorchful's effects
        if (entity.isSpectator() || entity.thermoo$getTemperature() > 0) {
            return 0;
        }

        int total = 0;
        EnvironmentSettings environmentSettings = FrostifulConfigYACL.environmentSettings();
        TemperatureSourceSettings temperatureSourceSettings = FrostifulConfigYACL.temperatureSourceSettings();

        total += getOnFireTemperatureChange(entity, temperatureSourceSettings);
        total += getPowderSnowTemperatureChange(entity, temperatureSourceSettings);
        total += getConduitPowerTemperatureChange(entity, temperatureSourceSettings);
        total += getShiveringTemperatureChange(entity, environmentSettings, temperatureSourceSettings);

        return total;
    }

    private static int getOnFireTemperatureChange(LivingEntity entity, TemperatureSourceSettings config) {
        if (entity.isOnFire()) {
            int onFireRate = config.onFireTemperatureChange();

            if (entity.getType() == FEntityTypes.FROSTOLOGER) {
                onFireRate /= 2;
            }

            return onFireRate;
        }
        return 0;
    }

    private static int getPowderSnowTemperatureChange(LivingEntity entity, TemperatureSourceSettings config) {
        if (entity.wasInPowderSnow) {
            return config.powderSnowTemperatureChange();
        }
        return 0;
    }

    private static int getConduitPowerTemperatureChange(LivingEntity entity, TemperatureSourceSettings config) {
        boolean applyConduitPowerWarmth = entity.isUnderWater()
                && entity.hasEffect(MobEffects.CONDUIT_POWER);

        if (applyConduitPowerWarmth) {
            return config.conduitPowerTemperatureChange();
        }
        return 0;
    }

    private static int getShiveringTemperatureChange(LivingEntity entity, EnvironmentSettings environmentSettings, TemperatureSourceSettings sourceSettings) {
        if (!SurvivalUtils.isShivering(entity)) {
            return 0;
        }

        boolean benefitsFromCold = entity.is(ThermooEntityTypeTags.BENEFITS_FROM_COLD_ENTITY_TYPE)
                || entity.getItemBySlot(EquipmentSlot.CHEST).is(FItems.FROSTOLOGY_CLOAK);

        if (benefitsFromCold) {
            return 0;
        }

        int shiverWarmth = environmentSettings.shiveringTemperatureChange(sourceSettings);
        if (entity instanceof Player player) {
            if (player.getFoodData().getFoodLevel() <= environmentSettings.stopShiverWarmingBelowFoodLevel()) {
                return 0;
            }

            player.causeFoodExhaustion(0.04f * shiverWarmth);
        }

        return shiverWarmth;
    }

    private ActiveTemperatureEffects() {

    }
}