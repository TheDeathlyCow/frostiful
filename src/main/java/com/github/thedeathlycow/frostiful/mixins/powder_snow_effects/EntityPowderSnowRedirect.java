package com.github.thedeathlycow.frostiful.mixins.powder_snow_effects;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.thermoo.api.temperature.HeatingModes;
import com.github.thedeathlycow.thermoo.api.temperature.TemperatureAware;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Exists for compatibility reasons with other mods that may want to change the TicksFrozen of an entity.
 * The change is assumed to be an active change, and is scaled up by the min temperature of the entity before
 * being applied to their temperature.
 */
@Mixin(Entity.class)
public abstract class EntityPowderSnowRedirect {

    @Shadow public abstract int getFrozenTicks();

    @Inject(
            method = "setFrozenTicks",
            at = @At("HEAD"),
            cancellable = true
    )
    private void redirectPowderSnowTicksToTemperature(int frozenTicks, CallbackInfo ci) {
        int frozenTicksChange = frozenTicks - this.getFrozenTicks();

        if (frozenTicksChange == 0) {
            return;
        }

        // scale change by min temp
        Entity instance = (Entity) (Object) this;
        if (instance instanceof TemperatureAware temperatureAware) {

            if (frozenTicksChange < 0 && temperatureAware.thermoo$isWarm()) {
                return;
            }

            Frostiful.LOGGER.debug(
                    "Original frozen ticks change of {} converted to a Thermoo active negative temperature change by Frostiful",
                    frozenTicksChange
            );

            temperatureAware.thermoo$addTemperature(-frozenTicksChange, HeatingModes.ACTIVE);

            ci.cancel();
        }
    }

}
