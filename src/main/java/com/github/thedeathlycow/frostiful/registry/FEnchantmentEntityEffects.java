package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.item.enchantment.HeatDrainEnchantmentEffect;
import com.github.thedeathlycow.frostiful.item.enchantment.SetItemCooldownEnchantmentEffect;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;

public class FEnchantmentEntityEffects {

    public static void registerAndGetDefault(Registry<MapCodec<? extends EnchantmentEntityEffect>> registry) {
        Frostiful.LOGGER.debug("Initialized Frostiful enchantment effects");

        Registry.register(registry, Frostiful.location("set_item_cooldown"), SetItemCooldownEnchantmentEffect.CODEC);
        Registry.register(registry, Frostiful.location("heat_drain"), HeatDrainEnchantmentEffect.CODEC);
    }

    private FEnchantmentEntityEffects() {
    }

}
