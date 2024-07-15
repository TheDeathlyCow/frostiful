package com.github.thedeathlycow.frostiful.registry.tag;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class FEnchantmentTags {

    public static final TagKey<Enchantment> FROST_WAND_ENCHANTING_TABLE = register("frost_wand/enchanting_table");
    public static final TagKey<Enchantment> FROST_WAND_ANVIL = register("frost_wand/anvil");
    public static final TagKey<Enchantment> HEAT_DRAIN = register("heat_drain");
    public static final TagKey<Enchantment> IS_FROSTY = register("is_frosty");

    private static TagKey<Enchantment> register(String id) {
        return TagKey.of(RegistryKeys.ENCHANTMENT, Frostiful.id(id));
    }


}
