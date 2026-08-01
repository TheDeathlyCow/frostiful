package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

public final class FEnchantments {
    public static final ResourceKey<Enchantment> ENERVATION = key("enervation");
    public static final ResourceKey<Enchantment> FROZEN_TOUCH_CURSE = key("frozen_touch_curse");
    public static final ResourceKey<Enchantment> ICE_BREAKER = key("ice_breaker");
    public static final ResourceKey<Enchantment> ICE_SPEED = key("ice_speed");

    private static ResourceKey<Enchantment> key(String name) {
        return ResourceKey.create(Registries.ENCHANTMENT, Frostiful.id(name));
    }

    private FEnchantments() {

    }
}