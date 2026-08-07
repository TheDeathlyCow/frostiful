/*
 * Frostiful: A Vanilla+ Freezing Temperature Mod. Also try Scorchful!
 * Copyright (C) 2026	TheDeathlyCow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.  If not, see
 * <https://www.gnu.org/licenses/>.
 */

package com.github.thedeathlycow.frostiful.mixins.powder_snow_effects;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.thermoo.api.core.v2.TemperatureAware;
import net.minecraft.world.entity.Entity;
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

    @Shadow public abstract int getTicksFrozen();

    @Inject(
            method = "setTicksFrozen",
            at = @At("HEAD"),
            cancellable = true
    )
    private void redirectPowderSnowTicksToTemperature(int frozenTicks, CallbackInfo ci) {
        int frozenTicksChange = frozenTicks - this.getTicksFrozen();

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

            temperatureAware.thermoo$addTemperature(-frozenTicksChange, instance.level().thermoo$temperatureSources().active());

            ci.cancel();
        }
    }

}
