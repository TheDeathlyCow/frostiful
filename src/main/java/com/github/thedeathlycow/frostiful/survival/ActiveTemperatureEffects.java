package com.github.thedeathlycow.frostiful.survival;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import com.github.thedeathlycow.frostiful.registry.FItems;
import com.github.thedeathlycow.thermoo.api.ThermooTags;
import com.github.thedeathlycow.thermoo.api.temperature.event.EnvironmentTickContext;
import com.github.thedeathlycow.thermoo.api.temperature.event.LivingEntityTemperatureTickEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public final class ActiveTemperatureEffects {
    public static void initialize() {
        LivingEntityTemperatureTickEvents.GET_ACTIVE_TEMPERATURE_CHANGE.register(ActiveTemperatureEffects::getActiveChange);
    }

    private static int getActiveChange(EnvironmentTickContext<? extends LivingEntity> context) {
        LivingEntity entity = context.affected();

        // don't touch scorchful's effects
        if (entity.isSpectator() || entity.thermoo$getTemperature() > 0) {
            return 0;
        }

        int total = 0;
        FrostifulConfig config = Frostiful.getConfig();

        total += getOnFireTemperatureChange(entity, config);
        total += getPowderSnowTemperatureChange(entity, config);
        total += getConduitPowerTemperatureChange(entity, config);
        total += getShiveringTemperatureChange(entity, config);

        return total;
    }

    private static int getOnFireTemperatureChange(LivingEntity entity, FrostifulConfig config) {
        if (entity.isOnFire()) {
            int onFireRate = config.environmentConfig.getOnFireWarmRate();

            if (entity.getType() == FEntityTypes.FROSTOLOGER) {
                onFireRate /= 2;
            }

            return onFireRate;
        }
        return 0;
    }

    private static int getPowderSnowTemperatureChange(LivingEntity entity, FrostifulConfig config) {
        if (entity.wasInPowderSnow) {
            return -config.environmentConfig.getPowderSnowFreezeRate();
        }
        return 0;
    }

    private static int getConduitPowerTemperatureChange(LivingEntity entity, FrostifulConfig config) {
        boolean applyConduitPowerWarmth = entity.isUnderWater()
                && entity.hasEffect(MobEffects.CONDUIT_POWER);

        if (applyConduitPowerWarmth) {
            return config.freezingConfig.getConduitWarmthPerTick();
        }
        return 0;
    }

    private static int getShiveringTemperatureChange(LivingEntity entity, FrostifulConfig config) {
        if (!SurvivalUtils.isShivering(entity)) {
            return 0;
        }

        boolean benefitsFromCold = entity.getType().is(ThermooTags.BENEFITS_FROM_COLD_ENTITY_TYPE)
                || entity.getItemBySlot(EquipmentSlot.CHEST).is(FItems.FROSTOLOGY_CLOAK);

        if (benefitsFromCold) {
            return 0;
        }

        int shiverWarmth = config.freezingConfig.getShiverWarmth();
        if (entity instanceof Player player) {
            if (player.getFoodData().getFoodLevel() <= config.freezingConfig.getStopShiverWarmingBelowFoodLevel()) {
                return 0;
            }

            player.causeFoodExhaustion(0.04f * shiverWarmth);
        }

        return shiverWarmth;
    }

    private ActiveTemperatureEffects() {

    }
}