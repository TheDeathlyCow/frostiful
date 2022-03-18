package com.github.thedeathlycow.lostinthecold.items;

import com.github.thedeathlycow.lostinthecold.attributes.ModEntityAttributes;
import com.github.thedeathlycow.lostinthecold.init.LostInTheCold;
import com.github.thedeathlycow.lostinthecold.init.LostInTheColdClient;
import com.github.thedeathlycow.lostinthecold.init.OnInitializeListener;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final Item FUR_LINED_IRON_ARMOR = new ArmorItem(FurLinedArmorMaterials.FUR_LINED_IRON, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));

    public static void registerItems() {
        LostInTheCold.LOGGER.info("Registering items for " + LostInTheCold.MODID);
        register("fur_lined_iron_armor", FUR_LINED_IRON_ARMOR);
    }

    private static void register(String id, Item item) {
        Registry.register(Registry.ITEM, new Identifier(LostInTheCold.MODID, id), item);
    }

}
