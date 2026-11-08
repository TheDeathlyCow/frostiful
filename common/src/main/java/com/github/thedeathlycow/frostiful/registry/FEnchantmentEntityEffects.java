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
import com.github.thedeathlycow.frostiful.item.enchantment.HeatDrainEnchantmentEffect;
import com.github.thedeathlycow.frostiful.item.enchantment.SetItemCooldownEnchantmentEffect;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;

public class FEnchantmentEntityEffects {

    public static void registerAndGetDefault(Registry<MapCodec<? extends EnchantmentEntityEffect>> registry) {
        Frostiful.LOGGER.debug("Initialized Frostiful enchantment effects");

        Registry.register(registry, Frostiful.id("set_item_cooldown"), SetItemCooldownEnchantmentEffect.CODEC);
        Registry.register(registry, Frostiful.id("heat_drain"), HeatDrainEnchantmentEffect.CODEC);
    }

    private FEnchantmentEntityEffects() {
    }

}
