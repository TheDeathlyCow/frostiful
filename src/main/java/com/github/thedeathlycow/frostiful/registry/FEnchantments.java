package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.enchantment.EnervationEnchantment;
import com.github.thedeathlycow.frostiful.enchantment.FrozenTouchCurse;
import com.github.thedeathlycow.frostiful.enchantment.IceBreakerEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;

public class FEnchantments {

    public static final Enchantment ENERVATION = new EnervationEnchantment(Enchantment.Rarity.RARE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment ICE_BREAKER = new IceBreakerEnchantment(Enchantment.Rarity.RARE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment FROZEN_TOUCH_CURSE = new FrozenTouchCurse(Enchantment.Rarity.RARE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});

    public static void registerEnchantments() {
        register("enervation", ENERVATION);
        register("ice_breaker", ICE_BREAKER);
        register("frozen_touch_curse", FROZEN_TOUCH_CURSE);
    }

    private static void register(String name, Enchantment enchantment) {
        Registry.register(Registries.ENCHANTMENT, new Identifier(Frostiful.MODID, name), enchantment);
    }

}
