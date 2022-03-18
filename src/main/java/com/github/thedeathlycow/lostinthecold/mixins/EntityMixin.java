package com.github.thedeathlycow.lostinthecold.mixins;

import com.github.thedeathlycow.lostinthecold.attributes.ModEntityAttributes;
import com.github.thedeathlycow.lostinthecold.config.FreezingValues;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
abstract class EntityMixin {

    @Inject(at = @At("HEAD"), method = "getMinFreezeDamageTicks()I", cancellable = true)
    private void getMinFreezeDamageTicks(CallbackInfoReturnable<Integer> cir) {
        Entity instance = (Entity) (Object) this;
        // start with a base time to freeze
        int freezeTickDamageThreshold = FreezingValues.BASE_FROST_RESISTANCE;
        if (instance instanceof LivingEntity livingEntity) {
            // add more time to freeze based on frost resistance
            if (livingEntity.getAttributes().hasAttribute(ModEntityAttributes.FROST_RESISTANCE)) {
                freezeTickDamageThreshold += FreezingValues.FROST_RESISTANCE_MULTIPLIER * livingEntity.getAttributes().getValue(ModEntityAttributes.FROST_RESISTANCE);
            }
        }

        cir.setReturnValue(freezeTickDamageThreshold);

    }
}
