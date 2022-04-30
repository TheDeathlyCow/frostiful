package com.github.thedeathlycow.frostiful.items;

import com.github.thedeathlycow.frostiful.block.FrostifulBlocks;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FrostifulItems {

    public static final Item FUR_LINED_IRON_HELMET = new ArmorItem(FurLinedArmorMaterials.FUR_LINED_IRON, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item FUR_LINED_IRON_CHESTPLATE = new ArmorItem(FurLinedArmorMaterials.FUR_LINED_IRON, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item FUR_LINED_IRON_LEGGINGS = new ArmorItem(FurLinedArmorMaterials.FUR_LINED_IRON, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item FUR_LINED_IRON_BOOTS = new ArmorItem(FurLinedArmorMaterials.FUR_LINED_IRON, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));

    public static final Item FUR_LINED_DIAMOND_HELMET = new ArmorItem(FurLinedArmorMaterials.FUR_LINED_DIAMOND, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item FUR_LINED_DIAMOND_CHESTPLATE = new ArmorItem(FurLinedArmorMaterials.FUR_LINED_DIAMOND, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item FUR_LINED_DIAMOND_LEGGINGS = new ArmorItem(FurLinedArmorMaterials.FUR_LINED_DIAMOND, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item FUR_LINED_DIAMOND_BOOTS = new ArmorItem(FurLinedArmorMaterials.FUR_LINED_DIAMOND, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));

    public static final Item FUR_LINED_GOLDEN_HELMET = new ArmorItem(FurLinedArmorMaterials.FUR_LINED_GOLD, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item FUR_LINED_GOLDEN_CHESTPLATE = new ArmorItem(FurLinedArmorMaterials.FUR_LINED_GOLD, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item FUR_LINED_GOLDEN_LEGGINGS = new ArmorItem(FurLinedArmorMaterials.FUR_LINED_GOLD, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item FUR_LINED_GOLDEN_BOOTS = new ArmorItem(FurLinedArmorMaterials.FUR_LINED_GOLD, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));

    public static final Item FUR_LINED_NETHERITE_HELMET = new ArmorItem(FurLinedArmorMaterials.FUR_LINED_NETHERITE, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT).fireproof());
    public static final Item FUR_LINED_NETHERITE_CHESTPLATE = new ArmorItem(FurLinedArmorMaterials.FUR_LINED_NETHERITE, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT).fireproof());
    public static final Item FUR_LINED_NETHERITE_LEGGINGS = new ArmorItem(FurLinedArmorMaterials.FUR_LINED_NETHERITE, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT).fireproof());
    public static final Item FUR_LINED_NETHERITE_BOOTS = new ArmorItem(FurLinedArmorMaterials.FUR_LINED_NETHERITE, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT).fireproof());

    public static final Item ICICLE = new BlockItem(FrostifulBlocks.ICICLE, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

    public static void registerItems() {
        register("fur_lined_iron_helmet", FUR_LINED_IRON_HELMET);
        register("fur_lined_iron_chestplate", FUR_LINED_IRON_CHESTPLATE);
        register("fur_lined_iron_leggings", FUR_LINED_IRON_LEGGINGS);
        register("fur_lined_iron_boots", FUR_LINED_IRON_BOOTS);

        register("fur_lined_diamond_helmet", FUR_LINED_DIAMOND_HELMET);
        register("fur_lined_diamond_chestplate", FUR_LINED_DIAMOND_CHESTPLATE);
        register("fur_lined_diamond_leggings", FUR_LINED_DIAMOND_LEGGINGS);
        register("fur_lined_diamond_boots", FUR_LINED_DIAMOND_BOOTS);

        register("fur_lined_golden_helmet", FUR_LINED_GOLDEN_HELMET);
        register("fur_lined_golden_chestplate", FUR_LINED_GOLDEN_CHESTPLATE);
        register("fur_lined_golden_leggings", FUR_LINED_GOLDEN_LEGGINGS);
        register("fur_lined_golden_boots", FUR_LINED_GOLDEN_BOOTS);

        register("fur_lined_netherite_helmet", FUR_LINED_NETHERITE_HELMET);
        register("fur_lined_netherite_chestplate", FUR_LINED_NETHERITE_CHESTPLATE);
        register("fur_lined_netherite_leggings", FUR_LINED_NETHERITE_LEGGINGS);
        register("fur_lined_netherite_boots", FUR_LINED_NETHERITE_BOOTS);

        register("icicle_block", ICICLE);
    }

    private static void register(String id, Item item) {
        Registry.register(Registry.ITEM, new Identifier(Frostiful.MODID, id), item);
    }

}
