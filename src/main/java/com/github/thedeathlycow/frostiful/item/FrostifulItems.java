package com.github.thedeathlycow.frostiful.item;

import com.github.thedeathlycow.frostiful.block.FrostifulBlocks;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FrostifulItems {

    public static final Item FUR_HELMET = new ArmorItem(FrostResistantArmorMaterials.FUR_ARMOR, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT));
    public static final Item FUR_CHESTPLATE = new ArmorItem(FrostResistantArmorMaterials.FUR_ARMOR, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT));
    public static final Item FUR_LEGGINGS = new ArmorItem(FrostResistantArmorMaterials.FUR_ARMOR, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT));
    public static final Item FUR_BOOTS = new ArmorItem(FrostResistantArmorMaterials.FUR_ARMOR, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT));

    public static final Item FUR_PADDED_CHAINMAIL_HELMET = new ArmorItem(FrostResistantArmorMaterials.FUR_LINED_CHAIN, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT));
    public static final Item FUR_PADDED_CHAINMAIL_CHESTPLATE = new ArmorItem(FrostResistantArmorMaterials.FUR_LINED_CHAIN, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT));
    public static final Item FUR_PADDED_CHAINMAIL_LEGGINGS = new ArmorItem(FrostResistantArmorMaterials.FUR_LINED_CHAIN, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT));
    public static final Item FUR_PADDED_CHAINMAIL_BOOTS = new ArmorItem(FrostResistantArmorMaterials.FUR_LINED_CHAIN, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT));

    public static final Item TUFT_OF_POLAR_BEAR_FUR = new Item(new FabricItemSettings().group(ItemGroup.MATERIALS));
    public static final Item ICICLE = new BlockItem(FrostifulBlocks.ICICLE, new FabricItemSettings().group(ItemGroup.DECORATIONS));
    public static final Item SUN_LICHEN = new BlockItem(FrostifulBlocks.SUN_LICHEN, new FabricItemSettings().group(ItemGroup.DECORATIONS));
    public static final Item FROST_TIPPED_ARROW = new FrostTippedArrowItem(new FabricItemSettings().group(ItemGroup.COMBAT));

    public static void registerItems() {
        register("fur_helmet", FUR_HELMET);
        register("fur_chestplate", FUR_CHESTPLATE);
        register("fur_leggings", FUR_LEGGINGS);
        register("fur_boots", FUR_BOOTS);

        register("fur_padded_chainmail_helmet", FUR_PADDED_CHAINMAIL_HELMET);
        register("fur_padded_chainmail_chestplate", FUR_PADDED_CHAINMAIL_CHESTPLATE);
        register("fur_padded_chainmail_leggings", FUR_PADDED_CHAINMAIL_LEGGINGS);
        register("fur_padded_chainmail_boots", FUR_PADDED_CHAINMAIL_BOOTS);

        register("tuft_of_polar_bear_fur", TUFT_OF_POLAR_BEAR_FUR);
        register("icicle", ICICLE);
        register("sun_lichen", SUN_LICHEN);
        register("frost_tipped_arrow", FROST_TIPPED_ARROW);
    }

    private static void register(String id, Item item) {
        Registry.register(Registry.ITEM, new Identifier(Frostiful.MODID, id), item);
    }

}
