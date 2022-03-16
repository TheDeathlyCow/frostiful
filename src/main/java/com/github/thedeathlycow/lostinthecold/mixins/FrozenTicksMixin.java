package com.github.thedeathlycow.lostinthecold.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class FrozenTicksMixin {

    private static final int BASE_MIN_FREEZING_TICKS = 140;
    private static final int TICKS_PER_HEART = 20;

    @Inject(at = @At("HEAD"), method = "getMinFreezeDamageTicks()I", cancellable = true)
    public void getMinFreezeDamageTicks(CallbackInfoReturnable<Integer> cir) {
        // start with a base time to freeze
        int returnValue = BASE_MIN_FREEZING_TICKS;
        if (((Entity)(Object)this) instanceof LivingEntity livingEntity) {
            // add more time to freeze based on current health
            returnValue += TICKS_PER_HEART * livingEntity.getHealth();
        }
        cir.setReturnValue(returnValue);
    }
}
