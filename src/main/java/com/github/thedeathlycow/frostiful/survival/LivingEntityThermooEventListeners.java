package com.github.thedeathlycow.frostiful.survival;

import com.github.thedeathlycow.frostiful.config.group.FreezingConfigGroup;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.thermoo.api.temperature.EnvironmentController;
import com.github.thedeathlycow.thermoo.api.temperature.HeatingModes;
import com.github.thedeathlycow.thermoo.api.temperature.event.InitialSoakChangeResult;
import com.github.thedeathlycow.thermoo.api.temperature.event.InitialTemperatureChangeResult;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;

public final class LivingEntityThermooEventListeners {

    public void tickHeatSources(
            EnvironmentController controller,
            LivingEntity entity,
            InitialTemperatureChangeResult result
    ) {
        if (entity.thermoo$isCold()) {
            result.applyInitialChange();
        }
    }

    public void tickHeatEffects(
            EnvironmentController controller,
            LivingEntity entity,
            InitialTemperatureChangeResult result
    ) {

        // dont heat (much) beyond 0, but still allow heating if cold,
        // and always allow passive cooling
        if (result.getInitialChange() > 0 && entity.thermoo$isWarm()) {
            return;
        }

        // applied initial change
        result.applyInitialChange();

        // stop using fire resistance to get free warmth
        if (entity.isOnFire()) {
            boolean isImmuneToFire = entity.hasStatusEffect(StatusEffects.FIRE_RESISTANCE)
                    || entity.isFireImmune();

            if (isImmuneToFire) {
                entity.extinguish();
            }
        }

        // apply conduit power warmth
        boolean applyConduitPowerWarmth = entity.isSubmergedInWater()
                && entity.hasStatusEffect(StatusEffects.CONDUIT_POWER);
        if (applyConduitPowerWarmth) {
            FreezingConfigGroup config = Frostiful.getConfig().freezingConfig;
            int warmth = config.getConduitWarmthPerTick();
            entity.thermoo$addTemperature(warmth, HeatingModes.PASSIVE);
        }
    }

    public void tickWetChange(
            EnvironmentController controller,
            LivingEntity entity,
            InitialSoakChangeResult result
    ) {
        // applies initial change
        result.applyInitialChange();
    }
}
