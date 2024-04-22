package com.github.thedeathlycow.frostiful.tag;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class FEnchantmentTags {

    public static final TagKey<Enchantment> FROST_WAND_ENCHANTMENTS = register("frost_wand_enchantments");
    public static final TagKey<Enchantment> FROST_WAND_ANVIL_ENCHANTMENTS = register("frost_wand_anvil_enchantments");
    public static final TagKey<Enchantment> HEAT_DRAIN = register("heat_drain");

    private static TagKey<Enchantment> register(String id) {
        return TagKey.of(RegistryKeys.ENCHANTMENT, Frostiful.id(id));
    }


}
