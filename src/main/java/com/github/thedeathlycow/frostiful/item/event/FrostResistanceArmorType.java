package com.github.thedeathlycow.frostiful.item.event;

import com.mojang.serialization.Codec;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.Util;

import java.util.EnumMap;
import java.util.Map;

public enum FrostResistanceArmorType implements StringIdentifiable {
    HELMET(EquipmentSlot.HEAD, 1.5, "helmet"),
    CHESTPLATE(EquipmentSlot.CHEST, 2.0, "chestplate"),
    LEGGINGS(EquipmentSlot.LEGS, 1.0, "leggings"),
    BOOTS(EquipmentSlot.FEET, 0.5, "boots"),
    BODY(EquipmentSlot.BODY, 4.0, "body");

    public static final Codec<FrostResistanceArmorType> CODEC = StringIdentifiable.createBasicCodec(
            FrostResistanceArmorType::values
    );

    private static final Map<ArmorItem.Type, FrostResistanceArmorType> ARMOR_TYPE_TO_FROST_RESISTANCE = Util.make(
            new EnumMap<>(ArmorItem.Type.class),
            map -> {
                map.put(ArmorItem.Type.HELMET, FrostResistanceArmorType.HELMET);
                map.put(ArmorItem.Type.CHESTPLATE, FrostResistanceArmorType.CHESTPLATE);
                map.put(ArmorItem.Type.LEGGINGS, FrostResistanceArmorType.LEGGINGS);
                map.put(ArmorItem.Type.BOOTS, FrostResistanceArmorType.BOOTS);
                map.put(ArmorItem.Type.BODY, FrostResistanceArmorType.BODY);
            }
    );

    private final EquipmentSlot equipmentSlot;
    private final String name;
    private final double baseFrostResistance;

    FrostResistanceArmorType(final EquipmentSlot equipmentSlot, final double baseFrostResistance, final String name) {
        this.equipmentSlot = equipmentSlot;
        this.name = name;
        this.baseFrostResistance = baseFrostResistance;
    }

    public static FrostResistanceArmorType forArmorType(ArmorItem.Type armorType) {
        return ARMOR_TYPE_TO_FROST_RESISTANCE.getOrDefault(armorType, BODY);
    }

    public EquipmentSlot getEquipmentSlot() {
        return this.equipmentSlot;
    }

    public String getName() {
        return this.name;
    }

    public double getFrostResistance(double multiplier) {
        return this.baseFrostResistance * multiplier;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
