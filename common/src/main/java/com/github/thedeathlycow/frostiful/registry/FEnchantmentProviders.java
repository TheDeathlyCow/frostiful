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
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.providers.EnchantmentProvider;

public final class FEnchantmentProviders {

    public static final ResourceKey<EnchantmentProvider> FROSTOLOGER_SPAWN_FROST_WAND = key("frostologer_spawn_frost_wand");

    private static ResourceKey<EnchantmentProvider> key(String id) {
        return ResourceKey.create(Registries.ENCHANTMENT_PROVIDER, Frostiful.id(id));
    }

    private FEnchantmentProviders() {

    }
}