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
        // applied initial change
        result.applyInitialChange();

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
