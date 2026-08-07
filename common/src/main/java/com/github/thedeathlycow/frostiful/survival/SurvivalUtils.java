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

import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.thermoo.api.entity.v1.ThermooEntityTypeTags;
import net.minecraft.world.entity.LivingEntity;

public class SurvivalUtils {

    public static boolean isShivering(LivingEntity entity) {
        if (entity.is(ThermooEntityTypeTags.BENEFITS_FROM_COLD_ENTITY_TYPE)) {
            return false;
        }

        return entity.thermoo$getTemperatureScale() < FrostifulConfigYACL.environmentSettings().shiverBelowTemperatureScale();
    }

    
    public static boolean isShiveringRender(LivingEntity entity) {
        if (entity.is(ThermooEntityTypeTags.BENEFITS_FROM_COLD_ENTITY_TYPE)) {
            return false;
        }

        // start showing shivering slightly before actually applying it
        return entity.thermoo$getTemperatureScale() <= FrostifulConfigYACL.environmentSettings().shiverBelowTemperatureScale();
    }

    private SurvivalUtils() {
    }
}
