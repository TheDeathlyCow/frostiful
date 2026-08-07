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

package com.github.thedeathlycow.frostiful.item.attribute;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.Util;
import net.minecraft.world.entity.EquipmentSlot;

import java.util.EnumMap;
import java.util.Map;

public enum FArmorType implements StringRepresentable {
    HELMET(EquipmentSlot.HEAD, 1.5, 0.25, "helmet"),
    CHESTPLATE(EquipmentSlot.CHEST, 2.0, 0.5, "chestplate"),
    LEGGINGS(EquipmentSlot.LEGS, 1.0, 0.125, "leggings"),
    BOOTS(EquipmentSlot.FEET, 0.5, 0.125, "boots"),
    BODY(EquipmentSlot.BODY, 4.0, 1.0, "body");

    public static final Codec<FArmorType> CODEC = StringRepresentable.fromValues(
            FArmorType::values
    );

    private static final Map<EquipmentSlot, FArmorType> ARMOR_TYPE_TO_FROST_RESISTANCE = Util.make(
            new EnumMap<>(EquipmentSlot.class),
            map -> {
                map.put(EquipmentSlot.HEAD, FArmorType.HELMET);
                map.put(EquipmentSlot.CHEST, FArmorType.CHESTPLATE);
                map.put(EquipmentSlot.LEGS, FArmorType.LEGGINGS);
                map.put(EquipmentSlot.FEET, FArmorType.BOOTS);
                map.put(EquipmentSlot.BODY, FArmorType.BODY);
            }
    );

    private final EquipmentSlot equipmentSlot;
    private final String name;
    private final double baseFrostResistance;
    private final double baseEnvironmentFrostResistance;

    FArmorType(final EquipmentSlot equipmentSlot, final double baseFrostResistance, final double baseEnvironmentFrostResistance, final String name) {
        this.equipmentSlot = equipmentSlot;
        this.name = name;
        this.baseFrostResistance = baseFrostResistance;
        this.baseEnvironmentFrostResistance = baseEnvironmentFrostResistance;
    }

    public static FArmorType forEquipmentSlot(EquipmentSlot armorType) {
        return ARMOR_TYPE_TO_FROST_RESISTANCE.getOrDefault(armorType, BODY);
    }

    public EquipmentSlot getEquipmentSlot() {
        return this.equipmentSlot;
    }

    public String getName() {
        return this.name;
    }

    public double getBaseFrostResistance() {
        return this.baseFrostResistance;
    }

    public double getBaseEnvironmentFrostResistance() {
        return baseEnvironmentFrostResistance;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
