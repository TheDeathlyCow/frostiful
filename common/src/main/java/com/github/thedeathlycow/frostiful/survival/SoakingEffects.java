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

package com.github.thedeathlycow.frostiful.survival;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.compat.FrostifulIntegrations;
import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.config.section.SoakingSettings;
import com.github.thedeathlycow.frostiful.mixins.entity.EntityInvoker;
import com.github.thedeathlycow.thermoo.api.core.v2.event.EnvironmentTickContext;
import com.github.thedeathlycow.thermoo.api.core.v2.event.LivingEntitySoakingTickEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;

public final class SoakingEffects {
    public static void initialize() {
        // scorchful overrides these effects
        if (!FrostifulIntegrations.isModLoaded(FrostifulIntegrations.SCORCHFUL_ID)) {
            LivingEntitySoakingTickEvents.GET_SOAKING_CHANGE.register(SoakingEffects::getSoakingChange);
        } else {
            Frostiful.LOGGER.info("Scorchful has been detected. Frostiful will not affect soaking.");
        }
    }

    private static int getSoakingChange(EnvironmentTickContext<? extends LivingEntity> context) {
        if (context.affected().isSpectator()) {
            return 0;
        }

        EntityInvoker invoker = (EntityInvoker) context.affected();
        SoakingSettings soakingSettings = FrostifulConfigYACL.soakingSettings();
        int total = 0;

        // increase wetness
        total += getRainChange(invoker, soakingSettings);
        total += getTouchingWaterChange(context, soakingSettings);
        total += getSubmerged(context, invoker);

        // drying effects
        total -= getLightDrying(context, soakingSettings);
        total -= getOnFireDrying(context, soakingSettings);

        return total;
    }

    private static int getRainChange(EntityInvoker invoker, SoakingSettings settings) {
        return invoker.frostiful$invokeIsBeingRainedOn()
                ? settings.rainWetnessIncrease()
                : 0;
    }

    private static int getTouchingWaterChange(EnvironmentTickContext<? extends LivingEntity> context, SoakingSettings settings) {
        LivingEntity entity = context.affected();
        return entity.isInWater() || entity.getInBlockState().is(Blocks.WATER_CAULDRON)
                ? settings.touchingWaterWetnessIncrease()
                : 0;
    }

    private static int getSubmerged(EnvironmentTickContext<? extends LivingEntity> context, EntityInvoker invoker) {
        LivingEntity entity = context.affected();
        return entity.isUnderWater()
                ? entity.thermoo$getMaxWetTicks()
                : 0;
    }

    private static int getLightDrying(EnvironmentTickContext<? extends LivingEntity> context, SoakingSettings settings) {
        if (settings.enableLightDrying()) {
            int blockLightLevel = context.level().getBrightness(LightLayer.BLOCK, context.pos());
            return blockLightLevel / 4;
        } else {
            return 0;
        }
    }

    private static int getOnFireDrying(EnvironmentTickContext<? extends LivingEntity> context, SoakingSettings settings) {
        return context.affected().isOnFire()
                ? settings.onFireDryDate()
                : 0;
    }

    private SoakingEffects() {

    }
}