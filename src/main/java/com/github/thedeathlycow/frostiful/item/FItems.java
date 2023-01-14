package com.github.thedeathlycow.frostiful.item;

import com.github.thedeathlycow.frostiful.block.FBlocks;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FItems {

    public static final Item FUR_HELMET = new ArmorItem(FrostResistantArmorMaterials.FUR_ARMOR, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT));
    public static final Item FUR_CHESTPLATE = new ArmorItem(FrostResistantArmorMaterials.FUR_ARMOR, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT));
    public static final Item FUR_LEGGINGS = new ArmorItem(FrostResistantArmorMaterials.FUR_ARMOR, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT));
    public static final Item FUR_BOOTS = new ArmorItem(FrostResistantArmorMaterials.FUR_ARMOR, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT));

    public static final Item FUR_PADDED_CHAINMAIL_HELMET = new ArmorItem(FrostResistantArmorMaterials.FUR_LINED_CHAIN, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT));
    public static final Item FUR_PADDED_CHAINMAIL_CHESTPLATE = new ArmorItem(FrostResistantArmorMaterials.FUR_LINED_CHAIN, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT));
    public static final Item FUR_PADDED_CHAINMAIL_LEGGINGS = new ArmorItem(FrostResistantArmorMaterials.FUR_LINED_CHAIN, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT));
    public static final Item FUR_PADDED_CHAINMAIL_BOOTS = new ArmorItem(FrostResistantArmorMaterials.FUR_LINED_CHAIN, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT));

    public static final Item POLAR_BEAR_FUR_TUFT = new Item(new FabricItemSettings().group(ItemGroup.MATERIALS));
    public static final Item WOLF_FUR_TUFT = new Item(new FabricItemSettings().group(ItemGroup.MATERIALS));
    public static final Item ICICLE = new IcicleItem(FBlocks.ICICLE, new FabricItemSettings().group(ItemGroup.DECORATIONS));
    public static final Item COLD_SUN_LICHEN = new BlockItem(FBlocks.COLD_SUN_LICHEN, new FabricItemSettings().group(ItemGroup.DECORATIONS));
    public static final Item COOL_SUN_LICHEN = new BlockItem(FBlocks.COOL_SUN_LICHEN, new FabricItemSettings().group(ItemGroup.DECORATIONS));
    public static final Item WARM_SUN_LICHEN = new BlockItem(FBlocks.WARM_SUN_LICHEN, new FabricItemSettings().group(ItemGroup.DECORATIONS));
    public static final Item HOT_SUN_LICHEN = new BlockItem(FBlocks.HOT_SUN_LICHEN, new FabricItemSettings().group(ItemGroup.DECORATIONS));
    public static final Item FROST_WAND = new FrostWandItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(250));
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

        register("polar_bear_fur_tuft", POLAR_BEAR_FUR_TUFT);
        register("wolf_fur_tuft", WOLF_FUR_TUFT);
        register("icicle", ICICLE);
        register("cold_sun_lichen", COLD_SUN_LICHEN);
        register("cool_sun_lichen", COOL_SUN_LICHEN);
        register("warm_sun_lichen", WARM_SUN_LICHEN);
        register("hot_sun_lichen", HOT_SUN_LICHEN);
        register("frost_wand", FROST_WAND);
        register("frost_tipped_arrow", FROST_TIPPED_ARROW);
    }

    private static void register(String id, Item item) {
        Registry.register(Registry.ITEM, new Identifier(Frostiful.MODID, id), item);
    }

}
