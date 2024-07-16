package com.github.thedeathlycow.frostiful.registry.tag;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class FEnchantmentTags {
    
    public static final TagKey<Enchantment> IS_FROSTY = register("is_frosty");

    private static TagKey<Enchantment> register(String id) {
        return TagKey.of(RegistryKeys.ENCHANTMENT, Frostiful.id(id));
    }


}
