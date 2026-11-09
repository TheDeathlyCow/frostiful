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
import com.github.thedeathlycow.frostiful.entity.effect.SimpleMobEffect;
import com.github.thedeathlycow.frostiful.entity.effect.WarmingMobEffect;
import com.github.thedeathlycow.thermoo.api.entity.v1.ThermooAttributes;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public final class FMobEffects {

    public static final Holder<MobEffect> WARMING = registerReference(
            "warming",
            new WarmingMobEffect(
                    MobEffectCategory.BENEFICIAL, 0xE3963E
            )
    );

    public static final Holder<MobEffect> WARMHEARTED = registerReference(
            "warmhearted",
            new SimpleMobEffect(
                    MobEffectCategory.BENEFICIAL, 0xE3673E
            ).addAttributeModifier(
                    ThermooAttributes.FROST_RESISTANCE,
                    Frostiful.id("effect.warmhearted/frost_resistance"),
                    1.0,
                    AttributeModifier.Operation.ADD_VALUE
            ).addAttributeModifier(
                    ThermooAttributes.ENVIRONMENT_FROST_RESISTANCE,
                    Frostiful.id("effect.warmhearted/environment_frost_resistance"),
                    1.0,
                    AttributeModifier.Operation.ADD_VALUE
            )
    );

    public static final Holder<MobEffect> FROST_BITE = registerReference(
            "frost_bite",
            new SimpleMobEffect(
                    MobEffectCategory.HARMFUL, 0x4287F5
            ).addAttributeModifier(
                    ThermooAttributes.FROST_RESISTANCE,
                    Frostiful.id("effect.frost_base"),
                    -1.0,
                    AttributeModifier.Operation.ADD_VALUE
            )
    );

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful status effects");

        BuiltInRegistries.MOB_EFFECT.addAlias(Frostiful.id("warmth"), Frostiful.id("warmhearted"));
    }

    private static Holder<MobEffect> registerReference(String name, MobEffect statusEffect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Frostiful.id(name), statusEffect);
    }

    private FMobEffects() {
    }
}
