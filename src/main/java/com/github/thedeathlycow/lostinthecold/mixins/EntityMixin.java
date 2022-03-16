package com.github.thedeathlycow.lostinthecold.mixins;

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
        // start with a base time to freeze
        int freezeTickDamageThreshhold = FreezingValues.BASE_MIN_FREEZING_TICKS;
        if (((Entity) (Object) this) instanceof LivingEntity livingEntity) {
            // add more time to freeze based on current health
            freezeTickDamageThreshhold += FreezingValues.TICK_INCREASE_PER_HEALTH_POINT * livingEntity.getHealth();
        }
        cir.setReturnValue(freezeTickDamageThreshhold);
    }
}
