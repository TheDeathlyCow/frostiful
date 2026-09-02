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

package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.entity.advancement.FrozenByFrostWandTrigger;
import com.github.thedeathlycow.frostiful.entity.advancement.SunLichenDischargeCriterion;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public final class FCriteria {
    public static final SunLichenDischargeCriterion SUN_LICHEN_DISCHARGE = register(
            "sun_lichen_discharge",
            new SunLichenDischargeCriterion()
    );

    public static final FrozenByFrostWandTrigger FROZEN_BY_FROST_WAND = register(
            "frozen_by_frost_wand",
            new FrozenByFrostWandTrigger()
    );

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful advancement criteria");
    }

    public static <T extends CriterionTrigger<?>> T register(String name, T criterion) {
        return Registry.register(BuiltInRegistries.TRIGGER_TYPES, Frostiful.id(name), criterion);
    }

    private FCriteria() {

    }
}