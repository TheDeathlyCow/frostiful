package com.github.thedeathlycow.lostinthecold.items;

import com.github.thedeathlycow.lostinthecold.attributes.ModEntityAttributes;
import com.github.thedeathlycow.lostinthecold.init.LostInTheCold;
import com.github.thedeathlycow.lostinthecold.init.LostInTheColdClient;
import com.github.thedeathlycow.lostinthecold.init.OnInitializeListener;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final Item FUR_LINED_IRON_HELMET = new ArmorItem(FurLinedArmorMaterials.FUR_LINED_IRON, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item FUR_LINED_IRON_CHESTPLATE = new ArmorItem(FurLinedArmorMaterials.FUR_LINED_IRON, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item FUR_LINED_IRON_LEGGINGS = new ArmorItem(FurLinedArmorMaterials.FUR_LINED_IRON, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item FUR_LINED_IRON_BOOTS = new ArmorItem(FurLinedArmorMaterials.FUR_LINED_IRON, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));


    public static void registerItems() {
        register("fur_lined_iron_helmet", FUR_LINED_IRON_HELMET);
        register("fur_lined_iron_chestplate", FUR_LINED_IRON_CHESTPLATE);
        register("fur_lined_iron_leggings", FUR_LINED_IRON_LEGGINGS);
        register("fur_lined_iron_boots", FUR_LINED_IRON_BOOTS);
    }

    private static void register(String id, Item item) {
        Registry.register(Registry.ITEM, new Identifier(LostInTheCold.MODID, id), item);
    }

}
