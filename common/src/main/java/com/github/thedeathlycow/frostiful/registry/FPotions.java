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
import net.fabricmc.fabric.api.registry.FabricPotionBrewingBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;

public class FPotions {

    public static final Holder<Potion> FREEZING = registerReference(
            "freezing",
            create("freezing", new MobEffectInstance(FStatusEffects.FROST_BITE, 180 * 20, 0))
    );
    public static final Holder<Potion> FREEZING_LONG = registerReference(
            "long_freezing",
            create("freezing", new MobEffectInstance(FStatusEffects.FROST_BITE, 2 * 180 * 20, 0))
    );
    public static final Holder<Potion> FREEZING_STRONG = registerReference(
            "strong_freezing",
            create("freezing", new MobEffectInstance(FStatusEffects.FROST_BITE, 90 * 20, 1))
    );

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful potions");
        FabricPotionBrewingBuilder.BUILD.register(
                builder -> {
                    builder.addMix(Potions.AWKWARD, FItems.FROZEN_ROD, FPotions.FREEZING);
                    builder.addMix(FPotions.FREEZING, Items.REDSTONE, FPotions.FREEZING_LONG);
                    builder.addMix(FPotions.FREEZING, Items.GLOWSTONE_DUST, FPotions.FREEZING_STRONG);
                }
        );
    }

    private static Potion create(String name, MobEffectInstance... effects) {
        return new Potion(String.format("%s.%s", Frostiful.MODID, name), effects);
    }

    private static Holder<Potion> registerReference(String name, Potion potion) {
        return Registry.registerForHolder(BuiltInRegistries.POTION, Frostiful.id(name), potion);
    }

    private FPotions() {

    }

}
