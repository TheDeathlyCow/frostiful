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
import com.github.thedeathlycow.frostiful.survival.environment.EnsureTemperatureBelow;
import com.github.thedeathlycow.frostiful.survival.environment.IfSnowy;
import com.github.thedeathlycow.thermoo.api.core.v2.registry.ThermooBuiltInRegistries;
import com.github.thedeathlycow.thermoo.api.environment.v2.provider.EnvironmentProvider;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;

public final class FEnvironmentProviderTypes {
    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful environment provider types");
        register("if_snowy", IfSnowy.CODEC);
        register("ensure_temperature_below", EnsureTemperatureBelow.CODEC);
    }

    private static <T extends EnvironmentProvider> MapCodec<T> register(String name, MapCodec<T> codec) {
        return Registry.register(ThermooBuiltInRegistries.ENVIRONMENT_PROVIDER_TYPE, Frostiful.id(name), codec);
    }

    private FEnvironmentProviderTypes() {

    }
}