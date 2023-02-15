package com.github.thedeathlycow.frostiful.survival;

import com.github.thedeathlycow.frostiful.config.group.FreezingConfigGroup;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.thermoo.api.temperature.EnvironmentController;
import com.github.thedeathlycow.thermoo.api.temperature.HeatingModes;
import com.github.thedeathlycow.thermoo.api.temperature.event.EnvironmentChangeResult;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;

public final class LivingEntityThermooEventListeners {

    public void tickHeatSources(
            EnvironmentController controller,
            LivingEntity entity,
            EnvironmentChangeResult result
    ) {

        if (result.isInitialChangeApplied()) {
            return;
        }

        if (entity.thermoo$isCold()) {
            entity.thermoo$addTemperature(result.getInitialTemperatureChange(), HeatingModes.PASSIVE);
            result.setAppliedInitialChange();
        }

    }

    public void tickHeatEffects(
            EnvironmentController controller,
            LivingEntity entity,
            EnvironmentChangeResult result
    ) {
        // applied initial change
        if (!result.isInitialChangeApplied()) {
            // consider fire to be active
            entity.thermoo$addTemperature(result.getInitialTemperatureChange(), HeatingModes.ACTIVE);
            result.setAppliedInitialChange();
        }

        // stop using fire resistance to get free warmth
        if (entity.isOnFire() && entity.thermoo$isCold()) {
            boolean isImmuneToFire = entity.hasStatusEffect(StatusEffects.FIRE_RESISTANCE)
                    || entity.isFireImmune();

            if (isImmuneToFire) {
                entity.extinguish();
            }
        }

        // apply conduit power warmth
        FreezingConfigGroup config = Frostiful.getConfig().freezingConfig;

        boolean applyConduitPowerWarmth = entity.thermoo$isCold()
                && entity.isSubmergedInWater()
                && entity.hasStatusEffect(StatusEffects.CONDUIT_POWER);
        if (applyConduitPowerWarmth) {
            int warmth = config.getConduitWarmthPerTick();
            entity.thermoo$addTemperature(warmth, HeatingModes.PASSIVE);

            result.addAdditionalChange(warmth);
        }
    }

    public void tickWetChange(
            EnvironmentController controller,
            LivingEntity entity,
            EnvironmentChangeResult result
    ) {
        // applies initial change
        if (result.isInitialChangeApplied()) {
            return;
        }

        int wetTicks = entity.thermoo$getWetTicks();
        entity.thermoo$setWetTicks(wetTicks + result.getInitialTemperatureChange());
        result.setAppliedInitialChange();
    }
}
