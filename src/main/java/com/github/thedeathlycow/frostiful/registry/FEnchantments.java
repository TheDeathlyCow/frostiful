package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

public final class FEnchantments {
    public static final ResourceKey<Enchantment> FROZEN_TOUCH_CURSE = createKey("frozen_touch_curse");

    private static ResourceKey<Enchantment> createKey(String name) {
        return ResourceKey.create(Registries.ENCHANTMENT, Frostiful.id(name));
    }

    private FEnchantments() {

    }
}