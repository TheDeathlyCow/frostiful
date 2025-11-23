package com.github.thedeathlycow.frostiful.item.attribute;

import com.mojang.serialization.Codec;
import java.util.EnumMap;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;

public enum FArmorType implements StringRepresentable {
    HELMET(EquipmentSlot.HEAD, 1.5, 0.25, "helmet"),
    CHESTPLATE(EquipmentSlot.CHEST, 2.0, 0.5, "chestplate"),
    LEGGINGS(EquipmentSlot.LEGS, 1.0, 0.125, "leggings"),
    BOOTS(EquipmentSlot.FEET, 0.5, 0.125, "boots"),
    BODY(EquipmentSlot.BODY, 4.0, 1.0, "body");

    public static final Codec<FArmorType> CODEC = StringRepresentable.fromValues(
            FArmorType::values
    );

    private static final Map<ArmorItem.Type, FArmorType> ARMOR_TYPE_TO_FROST_RESISTANCE = Util.make(
            new EnumMap<>(ArmorItem.Type.class),
            map -> {
                map.put(ArmorItem.Type.HELMET, FArmorType.HELMET);
                map.put(ArmorItem.Type.CHESTPLATE, FArmorType.CHESTPLATE);
                map.put(ArmorItem.Type.LEGGINGS, FArmorType.LEGGINGS);
                map.put(ArmorItem.Type.BOOTS, FArmorType.BOOTS);
                map.put(ArmorItem.Type.BODY, FArmorType.BODY);
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

    public static FArmorType forArmorType(ArmorItem.Type armorType) {
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
