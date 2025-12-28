package com.github.thedeathlycow.frostiful.item.attribute;

import com.mojang.serialization.Codec;
import java.util.EnumMap;
import java.util.Map;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.Util;
import net.minecraft.world.entity.EquipmentSlot;

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
