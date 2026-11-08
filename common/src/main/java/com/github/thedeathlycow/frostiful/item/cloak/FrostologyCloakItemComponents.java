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

package com.github.thedeathlycow.frostiful.item.cloak;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.thermoo.api.entity.v1.ThermooAttributes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.equipment.Equippable;

public final class FrostologyCloakItemComponents {
    public static Equippable createEquippableComponent() {
        return Equippable.builder(EquipmentSlot.CHEST)
                .setDamageOnHurt(false)
                .build();
    }

    public static ItemAttributeModifiers createAttributeModifiers() {
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        builder.add(
                ThermooAttributes.FROST_RESISTANCE,
                new AttributeModifier(
                        Frostiful.id("cloak.frost_resistance_penalty"),
                        -3.0,
                        AttributeModifier.Operation.ADD_VALUE
                ),
                EquipmentSlotGroup.BODY
        );
        return builder.build();
    }

    private FrostologyCloakItemComponents() {

    }
}
