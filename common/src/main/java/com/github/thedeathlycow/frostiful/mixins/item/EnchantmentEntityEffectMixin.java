package com.github.thedeathlycow.frostiful.mixins.item;

import com.github.thedeathlycow.frostiful.registry.FEnchantmentEntityEffects;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentEntityEffect.class)
public interface EnchantmentEntityEffectMixin {

    @Inject(
            method = "bootstrap",
            at = @At("TAIL")
    )
    private static void registerCallback(
            Registry<MapCodec<? extends EnchantmentEntityEffect>> registry,
            CallbackInfoReturnable<MapCodec<? extends EnchantmentEntityEffect>> cir
    ) {
        FEnchantmentEntityEffects.registerAndGetDefault(registry);
    }

}
