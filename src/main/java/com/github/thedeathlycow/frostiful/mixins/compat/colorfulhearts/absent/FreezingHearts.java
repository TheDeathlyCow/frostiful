package com.github.thedeathlycow.frostiful.mixins.compat.colorfulhearts.absent;

import com.github.thedeathlycow.frostiful.compat.FrostifulIntegrations;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InGameHud.HeartType.class)
public abstract class FreezingHearts {

    @Inject(
            method = "fromPlayerState",
            at = @At("TAIL"),
            cancellable = true
    )
    private static void setFreezingHeartsWhenFrozen(PlayerEntity player, CallbackInfoReturnable<InGameHud.HeartType> cir) {
        if (FrostifulIntegrations.isModLoaded(FrostifulIntegrations.OVERFLOWING_BARS_ID)) return;
        if (player.thermoo$getTemperatureScale() <= -0.99) {
            cir.setReturnValue(InGameHud.HeartType.FROZEN);
        }
    }

}
