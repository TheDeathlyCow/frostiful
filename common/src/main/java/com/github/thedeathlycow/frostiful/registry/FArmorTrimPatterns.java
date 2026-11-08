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
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.trim.TrimPattern;
import net.minecraft.world.item.equipment.trim.TrimPatterns;

public final class FArmorTrimPatterns {
    public static final ResourceKey<TrimPattern> FROSTY = key("frosty");
    public static final ResourceKey<TrimPattern> GLACIAL = key("glacial");
    public static final ResourceKey<TrimPattern> SNOW_MAN = key("snow_man");

    public static void bootstrap(BootstrapContext<TrimPattern> registry) {
        TrimPatterns.register(registry, FROSTY);
        TrimPatterns.register(registry, GLACIAL);
        TrimPatterns.register(registry, SNOW_MAN);
    }

    private static ResourceKey<TrimPattern> key(String id) {
        return ResourceKey.create(Registries.TRIM_PATTERN, Frostiful.id(id));
    }

    private FArmorTrimPatterns() {

    }
}