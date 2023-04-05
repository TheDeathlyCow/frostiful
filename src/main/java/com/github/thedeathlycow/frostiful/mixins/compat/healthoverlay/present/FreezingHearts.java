package com.github.thedeathlycow.frostiful.mixins.compat.healthoverlay.present;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terrails.healthoverlay.heart.HeartType;

@Mixin(HeartType.class)
public class FreezingHearts {

    @Inject(
            method = "forPlayer",
            at = @At("TAIL"),
            cancellable = true
    )
    private static void setFreezingHeartsWhenFrozen(PlayerEntity player, CallbackInfoReturnable<HeartType> cir) {
        if (player.thermoo$getTemperatureScale() <= -0.99) {
            cir.setReturnValue(HeartType.FROZEN);
        }
    }

}
