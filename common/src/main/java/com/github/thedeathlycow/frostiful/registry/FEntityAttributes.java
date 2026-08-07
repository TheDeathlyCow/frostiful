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
import com.github.thedeathlycow.thermoo.api.entity.v1.ThermooAttributes;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public final class FEntityAttributes {
    public static final double BASE_MIN_TEMPERATURE = 45;

    public static final Holder<Attribute> ICE_BREAKER_DAMAGE = register(
            "ice_breaker_damage",
            new RangedAttribute(
                    "attribute.frostiful.ice_break_damage", 3.0, 0, 1024.0
            ).setSyncable(true)
    );

    // called from mixin
    public static void createLivingAttributes(AttributeSupplier.Builder builder) {
        builder.add(ICE_BREAKER_DAMAGE);
    }

    private static Holder<Attribute> register(String name, Attribute attribute) {
        return Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, Frostiful.id(name), attribute);
    }

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful attributes");

        ThermooAttributes.baseValueEvent(ThermooAttributes.MIN_TEMPERATURE).register((entity, baseValue) -> {
            if (entity.getType() == FEntityTypes.FROSTOLOGER) {
                return baseValue;
            } else {
                return baseValue + BASE_MIN_TEMPERATURE;
            }
        });
    }

    private FEntityAttributes() {

    }
}