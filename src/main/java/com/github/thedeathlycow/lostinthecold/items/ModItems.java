package com.github.thedeathlycow.lostinthecold.items;

import com.github.thedeathlycow.lostinthecold.init.LostInTheCold;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final Item FUR_LINED_IRON_HELMET = register("fur_lined_iron_helmet", new ArmorItem(FurLinedArmorMaterials.FUR_LINED_IRON, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT)));
    public static final Item FUR_LINED_IRON_CHESTPLATE = register("fur_lined_iron_chestplate", new ArmorItem(FurLinedArmorMaterials.FUR_LINED_IRON, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT)));
    public static final Item FUR_LINED_IRON_LEGGINGS = register("fur_lined_iron_leggings", new ArmorItem(FurLinedArmorMaterials.FUR_LINED_IRON, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT)));
    public static final Item FUR_LINED_IRON_BOOTS = register("fur_lined_iron_boots", new ArmorItem(FurLinedArmorMaterials.FUR_LINED_IRON, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT)));

    public static final Item FUR_LINED_NETHERITE_HELMET = register("fur_lined_netherite_helmet", new ArmorItem(FurLinedArmorMaterials.FUR_LINED_NETHERITE, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT).fireproof()));
    public static final Item FUR_LINED_NETHERITE_CHESTPLATE = register("fur_lined_netherite_chestplate", new ArmorItem(FurLinedArmorMaterials.FUR_LINED_NETHERITE, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT).fireproof()));
    public static final Item FUR_LINED_NETHERITE_LEGGINGS = register("fur_lined_netherite_leggings", new ArmorItem(FurLinedArmorMaterials.FUR_LINED_NETHERITE, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT).fireproof()));
    public static final Item FUR_LINED_NETHERITE_BOOTS = register("fur_lined_netherite_boots", new ArmorItem(FurLinedArmorMaterials.FUR_LINED_NETHERITE, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT).fireproof()));

    private static Item register(String id, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(LostInTheCold.MODID, id), item);
    }

}
