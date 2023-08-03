package com.github.thedeathlycow.frostiful.mixins.compat.colorfulhearts.present;

import com.github.thedeathlycow.frostiful.compat.FrostifulIntegrations;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terrails.colorfulhearts.heart.HeartType;

@Mixin(HeartType.class)
public class FreezingHearts {

    @Inject(
            method = "forPlayer",
            at = @At("TAIL"),
            cancellable = true
    )
    private static void setFreezingHeartsWhenFrozen(PlayerEntity player, CallbackInfoReturnable<HeartType> cir) {
        if (FrostifulIntegrations.isModLoaded(FrostifulIntegrations.OVERFLOWING_BARS_ID)) return;
        if (player.thermoo$getTemperatureScale() <= -0.99) {
            cir.setReturnValue(HeartType.FROZEN);
        }
    }

}
