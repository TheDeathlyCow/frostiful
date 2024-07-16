package com.github.thedeathlycow.frostiful.mixins.item;

import com.github.thedeathlycow.frostiful.registry.FEnchantmentEntityEffects;
import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentEntityEffect.class)
public class EnchantmentEntityEffectMixin {

    @Inject(
            method = "registerAndGetDefault",
            at = @At("RETURN")
    )
    private static void registerCallback(
            Registry<MapCodec<? extends EnchantmentEntityEffect>> registry,
            CallbackInfoReturnable<MapCodec<? extends EnchantmentEntityEffect>> cir
    ) {
        FEnchantmentEntityEffects.registerAndGetDefault(registry);
    }

}
