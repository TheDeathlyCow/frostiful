package com.github.thedeathlycow.frostiful.mixins.entity;

import com.github.thedeathlycow.frostiful.entity.FreezableEntity;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityFreezeMixin {


    @Inject(
            method = "getMinFreezeDamageTicks",
            at = @At("HEAD"),
            cancellable = true
    )
    private void setMinFreezeTicksToMaxFrost(CallbackInfoReturnable<Integer> cir) {
        Entity instance = (Entity) (Object) this;
        if (instance instanceof FreezableEntity freezable) {
            cir.setReturnValue(freezable.frostiful$getMaxFrost());
        }
    }

}
