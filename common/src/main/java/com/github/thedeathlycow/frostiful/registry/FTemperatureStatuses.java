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
import com.github.thedeathlycow.thermoo.api.core.v2.registry.ThermooRegistries;
import com.github.thedeathlycow.thermoo.api.temperature.status.v2.TemperatureStatus;
import net.minecraft.resources.ResourceKey;

public final class FTemperatureStatuses {
    public static final ResourceKey<TemperatureStatus> FREEZE_DAMAGE = key("freeze_damage");

    public static final ResourceKey<TemperatureStatus> PLAYER_MOVEMENT_SPEED = key("player/movement_speed");
    public static final ResourceKey<TemperatureStatus> PLAYER_CHILLY = key("player/chilly");
    public static final ResourceKey<TemperatureStatus> PLAYER_COLD = key("player/cold");
    public static final ResourceKey<TemperatureStatus> PLAYER_FREEZING = key("player/freezing");

    public static final ResourceKey<TemperatureStatus> FROSTOLOGY_CLOAK_MOVEMENT_SPEED = key("player/frostology_cloak/movement_speed");
    public static final ResourceKey<TemperatureStatus> FROSTOLOGY_CLOAK_MELTING = key("player/frostology_cloak/melting");
    public static final ResourceKey<TemperatureStatus> FROSTOLOGY_CLOAK_WARM = key("player/frostology_cloak/warm");
    public static final ResourceKey<TemperatureStatus> FROSTOLOGY_CLOAK_COLD = key("player/frostology_cloak/cold");
    public static final ResourceKey<TemperatureStatus> FROSTOLOGY_CLOAK_FREEZING = key("player/frostology_cloak/freezing");

    public static final ResourceKey<TemperatureStatus> FROSTOLOGER_ATTACK_DAMAGE = key("frostologer/attack_damage");
    public static final ResourceKey<TemperatureStatus> FROSTOLOGER_CHILLY = key("frostologer/chilly");
    public static final ResourceKey<TemperatureStatus> FROSTOLOGER_FREEZING = key("frostologer/freezing");

    private static ResourceKey<TemperatureStatus> key(String name) {
        return ResourceKey.create(ThermooRegistries.TEMPERATURE_STATUS, Frostiful.id(name));
    }

    private FTemperatureStatuses() {

    }
}