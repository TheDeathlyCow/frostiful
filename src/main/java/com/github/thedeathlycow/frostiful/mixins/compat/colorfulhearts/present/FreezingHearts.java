package com.github.thedeathlycow.frostiful.mixins.compat.colorfulhearts.present;

import com.github.thedeathlycow.frostiful.compat.FrostifulIntegrations;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terrails.colorfulhearts.heart.CHeartType;

@Mixin(CHeartType.class)
public abstract class FreezingHearts {

    @Inject(
            method = "forPlayer",
            at = @At("TAIL"),
            cancellable = true
    )
    private static void setFreezingHeartsWhenFrozen(
            @NotNull PlayerEntity player,
            boolean health,
            CallbackInfoReturnable<CHeartType> cir
    ) {
        if (player.thermoo$getTemperatureScale() <= -0.99) {
            if (FrostifulIntegrations.isModLoaded(FrostifulIntegrations.OVERFLOWING_BARS_ID)) {
                return;
            }
            var type = health ? CHeartType.HEALTH_FROZEN : CHeartType.ABSORBING_FROZEN;
            cir.setReturnValue(type);
        }
    }

}