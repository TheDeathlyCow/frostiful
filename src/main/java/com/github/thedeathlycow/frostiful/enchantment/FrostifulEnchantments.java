package com.github.thedeathlycow.frostiful.enchantment;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FrostifulEnchantments {

    public static final Enchantment HEAT_DRAIN = new HeatDrainEnchantment(Enchantment.Rarity.RARE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});

    private static Enchantment register(String name, Enchantment enchantment) {
        return Registry.register(Registry.ENCHANTMENT, new Identifier(Frostiful.MODID, name), enchantment);
    }

}
