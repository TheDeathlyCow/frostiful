package com.github.thedeathlycow.frostiful.item.enchantment;

import com.github.thedeathlycow.frostiful.item.FrostWandItem;
import com.github.thedeathlycow.frostiful.registry.tag.FEnchantmentTags;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;

public class EnchantmentEventListeners {

    public static TriState allowHeatDrainWeaponEnchanting(
            Enchantment enchantment,
            ItemStack target,
            EnchantingContext enchantingContext
    ) {
        boolean isWeapon = target.isIn(FItemTags.SUPPORTS_HEAT_DRAIN);

        boolean isFrostWandWeaponEnchantment = Registries.ENCHANTMENT
                .getEntry(enchantment)
                .isIn(FEnchantmentTags.HEAT_DRAIN);

        return isWeapon && isFrostWandWeaponEnchantment && enchantingContext != EnchantingContext.RANDOM_ENCHANTMENT
                ? TriState.TRUE
                : TriState.DEFAULT;
    }

    public static TriState allowFrostWandAnvilEnchanting(
            Enchantment enchantment,
            ItemStack target,
            EnchantingContext enchantingContext
    ) {
        boolean isFrostWand = target.getItem() instanceof FrostWandItem;

        boolean isFrostWandAnvilEnchantment = Registries.ENCHANTMENT
                .getEntry(enchantment)
                .isIn(FEnchantmentTags.FROST_WAND_ANVIL);

        return isFrostWand && isFrostWandAnvilEnchantment && enchantingContext != EnchantingContext.RANDOM_ENCHANTMENT
                ? TriState.TRUE
                : TriState.DEFAULT;
    }

    private EnchantmentEventListeners() {

    }

}
