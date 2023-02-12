package com.github.thedeathlycow.frostiful.survival;

import com.github.thedeathlycow.thermoo.api.temperature.EnvironmentController;
import com.github.thedeathlycow.thermoo.api.temperature.HeatingModes;
import com.github.thedeathlycow.thermoo.api.temperature.event.EnvironmentChangeResult;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;

public final class LivingEntityThermooEventListeners {

    public void tickHeatSources(
            EnvironmentController controller,
            LivingEntity entity,
            int temperatureChange,
            EnvironmentChangeResult result
    ) {

        if (result.isAppliedChange()) {
            return;
        }

        if (entity.thermoo$isCold()) {
            entity.thermoo$addTemperature(temperatureChange, HeatingModes.PASSIVE);
            result.setAppliedChange();
        }

    }

    public void tickHeatEffects(
            EnvironmentController controller,
            LivingEntity entity,
            int temperatureChange,
            EnvironmentChangeResult result
    ) {
        if (result.isAppliedChange()) {
            return;
        }

        entity.thermoo$addTemperature(temperatureChange, HeatingModes.PASSIVE);
        result.setAppliedChange();

        if (entity.isOnFire()) {

            boolean isImmuneToFire = entity.hasStatusEffect(StatusEffects.FIRE_RESISTANCE)
                    || entity.isFireImmune();

            if (isImmuneToFire && entity.thermoo$isCold()) {
                entity.extinguish();
            }

        }
    }

    public void tickWetChange(
            EnvironmentController controller,
            LivingEntity entity,
            int soakChange,
            EnvironmentChangeResult result
    ) {
        if (result.isAppliedChange()) {
            return;
        }

        int wetTicks = entity.thermoo$getWetTicks();
        entity.thermoo$setWetTicks(wetTicks + soakChange);
        result.setAppliedChange();
    }
}
