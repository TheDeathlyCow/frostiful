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
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;

public class FPotions {
    private static final int BASE_LONG_DURATION = 180 * 20;
    private static final int BASE_SHORT_DURATION = 5 * 20;

    public static final Holder<Potion> NUMBING = registerReference(
            "numbing",
            create(
                    "numbing",
                    new MobEffectInstance(FMobEffects.FROSTBITE, BASE_LONG_DURATION, 0),
                    new MobEffectInstance(MobEffects.SLOWNESS, BASE_LONG_DURATION, 0),
                    new MobEffectInstance(FMobEffects.FREEZING, BASE_SHORT_DURATION, 0)
            )
    );
    public static final Holder<Potion> NUMBING_LONG = registerReference(
            "long_numbing",
            create(
                    "numbing",
                    new MobEffectInstance(FMobEffects.FROSTBITE, 2 * BASE_LONG_DURATION, 0),
                    new MobEffectInstance(MobEffects.SLOWNESS, 2 * BASE_LONG_DURATION, 0),
                    new MobEffectInstance(FMobEffects.FREEZING, 2 * BASE_SHORT_DURATION, 0)
            )
    );
    public static final Holder<Potion> NUMBING_STRONG = registerReference(
            "strong_numbing",
            create(
                    "numbing",
                    new MobEffectInstance(FMobEffects.FROSTBITE, BASE_LONG_DURATION / 2, 1),
                    new MobEffectInstance(MobEffects.SLOWNESS, BASE_LONG_DURATION / 2, 2),
                    new MobEffectInstance(FMobEffects.FREEZING, BASE_SHORT_DURATION, 2)
            )
    );

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful potions");
        FabricPotionBrewingBuilder.BUILD.register(
                builder -> {
                    builder.addMix(Potions.AWKWARD, FItems.FROZEN_ROD, FPotions.NUMBING);
                    builder.addMix(FPotions.NUMBING, Items.REDSTONE, FPotions.NUMBING_LONG);
                    builder.addMix(FPotions.NUMBING, Items.GLOWSTONE_DUST, FPotions.NUMBING_STRONG);
                }
        );

        BuiltInRegistries.POTION.addAlias(Frostiful.id("freezing"), Frostiful.id("numbing"));
        BuiltInRegistries.POTION.addAlias(Frostiful.id("long_freezing"), Frostiful.id("long_numbing"));
        BuiltInRegistries.POTION.addAlias(Frostiful.id("strong_freezing"), Frostiful.id("strong_numbing"));
    }

    private static Potion create(String name, MobEffectInstance... effects) {
        return new Potion(String.format("%s.%s", Frostiful.MODID, name), effects);
    }

    private static Holder<Potion> registerReference(String id, Potion potion) {
        return Registry.registerForHolder(BuiltInRegistries.POTION, Frostiful.id(id), potion);
    }

    private FPotions() {

    }

}
