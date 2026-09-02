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
import com.github.thedeathlycow.frostiful.entity.loot.IsChillagerLord;
import com.github.thedeathlycow.frostiful.entity.loot.LocationWarmthLootCondition;
import com.github.thedeathlycow.frostiful.entity.loot.RootedLootCondition;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class FLootConditionTypes {
    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful loot condition types");

        register("rooted", RootedLootCondition.CODEC);
        register("wearing_ice_like_item", IsChillagerLord.CODEC);
        register("location_warmth", LocationWarmthLootCondition.CODEC);
    }

    private static void register(String name, MapCodec<? extends LootItemCondition> codec) {
        Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, Frostiful.id(name), codec);
    }

    private FLootConditionTypes() {

    }
}
