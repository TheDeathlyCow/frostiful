package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.EnumMap;

public class FArmorMaterials {
    public static final ResourceKey<EquipmentAsset> FUR_ASSET = ResourceKey.create(EquipmentAssets.ROOT_ID, Frostiful.id("fur"));
    public static final ResourceKey<EquipmentAsset> FUR_LINED_CHAINMAIL_ASSET = ResourceKey.create(EquipmentAssets.ROOT_ID, Frostiful.id("fur_lined_chainmail"));

    public static final ArmorMaterial FUR = new ArmorMaterial(
            5,
            Util.make(new EnumMap<>(ArmorType.class), map -> {
                map.put(ArmorType.BOOTS, 1);
                map.put(ArmorType.LEGGINGS, 2);
                map.put(ArmorType.CHESTPLATE, 3);
                map.put(ArmorType.HELMET, 1);
                map.put(ArmorType.BODY, 3);
            }),
            15,
            SoundEvents.ARMOR_EQUIP_GENERIC,
            0.0f, 0.0f,
            FItemTags.REPAIRS_FUR_ARMOR,
            FUR_ASSET
    );

    public static final ArmorMaterial FUR_LINED_CHAINMAIL = new ArmorMaterial(
            5,
            Util.make(new EnumMap<>(ArmorType.class), map -> {
                map.put(ArmorType.BOOTS, 2);
                map.put(ArmorType.LEGGINGS, 5);
                map.put(ArmorType.CHESTPLATE, 6);
                map.put(ArmorType.HELMET, 2);
                map.put(ArmorType.BODY, 5);
            }),
            12,
            SoundEvents.ARMOR_EQUIP_CHAIN,
            0.0f,
            0.0f,
            FItemTags.REPAIRS_FUR_LINED_CHAINMAIL_ARMOR,
            FUR_LINED_CHAINMAIL_ASSET
    );

    private FArmorMaterials() {

    }
}
