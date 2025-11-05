package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.providers.EnchantmentProvider;

public final class FEnchantmentProviders {

    public static final ResourceKey<EnchantmentProvider> FROSTOLOGER_SPAWN_FROST_WAND = key("frostologer_spawn_frost_wand");

    private static ResourceKey<EnchantmentProvider> key(String id) {
        return ResourceKey.create(Registries.ENCHANTMENT_PROVIDER, Frostiful.id(id));
    }

    private FEnchantmentProviders() {

    }
}