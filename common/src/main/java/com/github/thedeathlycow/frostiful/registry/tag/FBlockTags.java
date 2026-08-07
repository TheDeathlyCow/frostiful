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
import net.minecraft.world.level.block.Block;

public class FBlockTags {
    public static final TagKey<Block> C_ICICLES = convention("icicles");
    public static final TagKey<Block> COVERED_ROCK_COVERING_REPLACEABLE = register("covered_rock_covering_replaceable");
    public static final TagKey<Block> COVERED_ROCKS_CANNOT_REPLACE = register("covered_rocks_cannot_replace");
    public static final TagKey<Block> SUN_LICHEN_CAN_PLACE_ON = register("sun_lichen_can_place_on");
    public static final TagKey<Block> FROSTOLOGER_CANNOT_FREEZE = register("frostologer_cannot_freeze");
    public static final TagKey<Block> FROZEN_TORCHES = register("frozen_torches");
    public static final TagKey<Block> HAS_OPEN_FLAME = register("has_open_flame");
    public static final TagKey<Block> HOT_FLOOR = register("hot_floor");
    public static final TagKey<Block> ICE_SPEED_BLOCKS = register("ice_speed_blocks");
    public static final TagKey<Block> ICICLE_GROWABLE = register("icicle_growable");
    public static final TagKey<Block> ICICLE_REPLACEABLE_BLOCKS = register("icicle_replaceable_blocks");
    public static final TagKey<Block> IS_OPEN_FLAME = register("is_open_flame");
    public static final TagKey<Block> SUN_LICHENS = register("sun_lichens");

    private static TagKey<Block> register(String id) {
        return TagKey.create(Registries.BLOCK, Frostiful.id(id));
    }

    private static TagKey<Block> convention(String id) {
        return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("c", id));
    }
}
