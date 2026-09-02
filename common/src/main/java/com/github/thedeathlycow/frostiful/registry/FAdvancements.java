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
import net.minecraft.advancements.Advancement;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;

public final class FAdvancements {
    public static final ResourceKey<Advancement> ROOT = create("adventure/root");
    public static final ResourceKey<Advancement> ADD_LOG_TO_CAMPFIRE = create("adventure/add_log_to_campfire");
    public static final ResourceKey<Advancement> BRUSH_POLAR_BEAR = create("adventure/brush_polar_bear");
    public static final ResourceKey<Advancement> CHAIN_FUR_ARMOR = create("adventure/chain_fur_armor");
    public static final ResourceKey<Advancement> CRAFT_ANY_FUR_ARMOR = create("adventure/craft_any_fur_armor");
    public static final ResourceKey<Advancement> FIND_CHILLAGER_OUTPOST = create("adventure/find_chillager_outpost");
    public static final ResourceKey<Advancement> FIND_FROSTOLOGER_CASTLE = create("adventure/find_frostologer_castle");
    public static final ResourceKey<Advancement> FREEZE_CREEPERS = create("adventure/freeze_creepers");
    public static final ResourceKey<Advancement> KILL_FROSTOLOGER = create("adventure/kill_frostologer");
    public static final ResourceKey<Advancement> OBTAIN_FROSTOLOGY_CLOAK = create("adventure/obtain_frostology_cloak");
    public static final ResourceKey<Advancement> OBTAIN_ICE_SKATES = create("adventure/obtain_ice_skates");
    public static final ResourceKey<Advancement> STEP_ON_SUN_LICHEN = create("adventure/step_on_sun_lichen");
    public static final ResourceKey<Advancement> TRIM_WITH_GLACIAL_PATTERN = create("adventure/trim_with_glacial_pattern");
    public static final ResourceKey<Advancement> TRIM_WITH_PACKED_SNOW_PATTERNS = create("adventure/trim_with_packed_snow_patterns");
    public static final ResourceKey<Advancement> WARM_BY_LIGHT = create("adventure/warm_by_light");

    private static ResourceKey<Advancement> create(String name) {
        return ResourceKey.create(Registries.ADVANCEMENT, Frostiful.id(name));
    }

    private FAdvancements() {

    }
}