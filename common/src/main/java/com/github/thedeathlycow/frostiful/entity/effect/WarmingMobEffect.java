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

package com.github.thedeathlycow.frostiful.entity.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public final class WarmingMobEffect extends SimpleMobEffect {
    private static final double PERCENT_PER_TICK = 0.05 / 20.0;

    public WarmingMobEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    public boolean applyEffectTick(final ServerLevel level, final LivingEntity mob, final int amplification) {
        if (mob.thermoo$getTemperatureScale() < 0.01f) {
            int minTemp = -mob.thermoo$getMinTemperature();
            int tempChange = Mth.ceil(minTemp * PERCENT_PER_TICK * (amplification + 1));

            mob.thermoo$addTemperature(tempChange, level.thermoo$temperatureSources().active());
        }

        return true;
    }

    public boolean shouldApplyEffectTickThisTick(final int tickCount, final int amplification) {
        return true;
    }
}