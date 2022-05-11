package com.github.thedeathlycow.frostiful.item;

import com.github.thedeathlycow.frostiful.block.FrostifulBlocks;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FrostifulItems {

    public static final Item FUR_LINED_IRON_HELMET = new ArmorItem(FrostResistantArmorMaterials.FUR_LINED_IRON, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT));
    public static final Item FUR_LINED_IRON_CHESTPLATE = new ArmorItem(FrostResistantArmorMaterials.FUR_LINED_IRON, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT));
    public static final Item FUR_LINED_IRON_LEGGINGS = new ArmorItem(FrostResistantArmorMaterials.FUR_LINED_IRON, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT));
    public static final Item FUR_LINED_IRON_BOOTS = new ArmorItem(FrostResistantArmorMaterials.FUR_LINED_IRON, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT));

    public static final Item FUR_LINED_DIAMOND_HELMET = new ArmorItem(FrostResistantArmorMaterials.FUR_LINED_DIAMOND, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT));
    public static final Item FUR_LINED_DIAMOND_CHESTPLATE = new ArmorItem(FrostResistantArmorMaterials.FUR_LINED_DIAMOND, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT));
    public static final Item FUR_LINED_DIAMOND_LEGGINGS = new ArmorItem(FrostResistantArmorMaterials.FUR_LINED_DIAMOND, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT));
    public static final Item FUR_LINED_DIAMOND_BOOTS = new ArmorItem(FrostResistantArmorMaterials.FUR_LINED_DIAMOND, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT));

    public static final Item FUR_LINED_GOLDEN_HELMET = new ArmorItem(FrostResistantArmorMaterials.FUR_LINED_GOLD, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT));
    public static final Item FUR_LINED_GOLDEN_CHESTPLATE = new ArmorItem(FrostResistantArmorMaterials.FUR_LINED_GOLD, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT));
    public static final Item FUR_LINED_GOLDEN_LEGGINGS = new ArmorItem(FrostResistantArmorMaterials.FUR_LINED_GOLD, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT));
    public static final Item FUR_LINED_GOLDEN_BOOTS = new ArmorItem(FrostResistantArmorMaterials.FUR_LINED_GOLD, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT));

    public static final Item FUR_LINED_NETHERITE_HELMET = new ArmorItem(FrostResistantArmorMaterials.FUR_LINED_NETHERITE, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT).fireproof());
    public static final Item FUR_LINED_NETHERITE_CHESTPLATE = new ArmorItem(FrostResistantArmorMaterials.FUR_LINED_NETHERITE, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT).fireproof());
    public static final Item FUR_LINED_NETHERITE_LEGGINGS = new ArmorItem(FrostResistantArmorMaterials.FUR_LINED_NETHERITE, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT).fireproof());
    public static final Item FUR_LINED_NETHERITE_BOOTS = new ArmorItem(FrostResistantArmorMaterials.FUR_LINED_NETHERITE, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT).fireproof());

    public static final Item FUR_HELMET = new ArmorItem(FrostResistantArmorMaterials.FUR_ARMOR, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT));
    public static final Item FUR_CHESTPLATE = new ArmorItem(FrostResistantArmorMaterials.FUR_ARMOR, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT));
    public static final Item FUR_LEGGINGS = new ArmorItem(FrostResistantArmorMaterials.FUR_ARMOR, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT));
    public static final Item FUR_BOOTS = new ArmorItem(FrostResistantArmorMaterials.FUR_ARMOR, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT));

    public static final Item FUR_LINED_CHAINMAIL_HELMET = new ArmorItem(FrostResistantArmorMaterials.FUR_LINED_CHAIN, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT));
    public static final Item FUR_LINED_CHAINMAIL_CHESTPLATE = new ArmorItem(FrostResistantArmorMaterials.FUR_LINED_CHAIN, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT));
    public static final Item FUR_LINED_CHAINMAIL_LEGGINGS = new ArmorItem(FrostResistantArmorMaterials.FUR_LINED_CHAIN, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT));
    public static final Item FUR_LINED_CHAINMAIL_BOOTS = new ArmorItem(FrostResistantArmorMaterials.FUR_LINED_CHAIN, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT));

    public static final Item ICICLE = new BlockItem(FrostifulBlocks.ICICLE, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS));

    public static final Item FROST_TIPPED_ARROW = new FrostTippedArrowItem(new FabricItemSettings().group(ItemGroup.COMBAT));

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

        register("fur_helmet", FUR_HELMET);
        register("fur_chestplate", FUR_CHESTPLATE);
        register("fur_leggings", FUR_LEGGINGS);
        register("fur_boots", FUR_BOOTS);

        register("fur_lined_chainmail_helmet", FUR_LINED_CHAINMAIL_HELMET);
        register("fur_lined_chainmail_chestplate", FUR_LINED_CHAINMAIL_CHESTPLATE);
        register("fur_lined_chainmail_leggings", FUR_LINED_CHAINMAIL_LEGGINGS);
        register("fur_lined_chainmail_boots", FUR_LINED_CHAINMAIL_BOOTS);

        register("icicle", ICICLE);

        register("frost_tipped_arrow", FROST_TIPPED_ARROW);
    }

    private static void register(String id, Item item) {
        Registry.register(Registry.ITEM, new Identifier(Frostiful.MODID, id), item);
    }

}
