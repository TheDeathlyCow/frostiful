package com.github.thedeathlycow.lostinthecold.mixins.entity;

import com.github.thedeathlycow.lostinthecold.attributes.ModEntityAttributes;
import com.github.thedeathlycow.lostinthecold.config.FreezingValues;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
abstract class EntityMixin {

    @Inject(at = @At("HEAD"), method = "getMinFreezeDamageTicks()I", cancellable = true)
    private void getMinFreezeDamageTicks(CallbackInfoReturnable<Integer> cir) {
        Entity instance = (Entity) (Object) this;

        int freezeTickDamageThreshold = 0;
        if (instance instanceof LivingEntity livingEntity) {
            // add more time to freeze based on frost resistance
            if (livingEntity.getAttributes().hasAttribute(ModEntityAttributes.FROST_RESISTANCE)) {
                double frostResistance = livingEntity.getAttributes().getValue(ModEntityAttributes.FROST_RESISTANCE);
                freezeTickDamageThreshold += getTicksFromFrostResistance(frostResistance);
            }
        }

        cir.setReturnValue(freezeTickDamageThreshold);
    }

    private static double getTicksFromFrostResistance(final double frostResistance) {
        return 20 * FreezingValues.SECONDS_PER_FROST_RESIST * frostResistance;
    }
}
