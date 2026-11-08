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

package com.github.thedeathlycow.frostiful.registry.tag;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class FEntityTypeTags {

    public static final TagKey<EntityType<?>> ROOT_IMMUNE = register("root_immune");

    public static final TagKey<EntityType<?>> HEAVY_ENTITY_TYPES = register("heavy_entity_types");

    public static final TagKey<EntityType<?>> DOES_NOT_BREAK_BRITTLE_ICE = register("does_not_break_brittle_ice");

    public static final TagKey<EntityType<?>> IS_BRUSHABLE = register("is_brushable");

    public static final TagKey<EntityType<?>> BRUSHING_DROPS_POLAR_BEAR_FUR = register("brushing/drops_polar_bear_fur");

    public static final TagKey<EntityType<?>> BRUSHING_DROPS_WOLF_FUR = register("brushing/drops_wolf_fur");

    public static final TagKey<EntityType<?>> BRUSHING_DROPS_OCELOT_FUR = register("brushing/drops_ocelot_fur");

    public static final TagKey<EntityType<?>> HAS_PLAYER_TEMPERATURE_STATUSES = register("has_player_temperature_statuses");

    private static TagKey<EntityType<?>> register(String id) {
        return TagKey.create(Registries.ENTITY_TYPE, Frostiful.id(id));
    }

    private static TagKey<EntityType<?>> registerCommon(String id) {
        return TagKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath("c", id));
    }

    private FEntityTypeTags() {

    }
}
