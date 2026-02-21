package com.github.thedeathlycow.frostiful.registry.tag;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;

public final class FEnchantmentTags {
    public static final TagKey<Enchantment> IS_FROSTY = register("is_frosty");
    public static final TagKey<Enchantment> HEAT_DRAIN_EXCLUSIVE_SET = register("exclusive_set/heat_drain");

    private static TagKey<Enchantment> register(String id) {
        return TagKey.create(Registries.ENCHANTMENT, Frostiful.id(id));
    }

    private FEnchantmentTags() {

    }
}
