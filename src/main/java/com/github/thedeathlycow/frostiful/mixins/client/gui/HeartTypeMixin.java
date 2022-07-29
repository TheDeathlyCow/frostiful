package com.github.thedeathlycow.frostiful.mixins.client.gui;

import com.github.thedeathlycow.frostiful.entity.FreezableEntity;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InGameHud.HeartType.class)
public class HeartTypeMixin {

    @Inject(
            method = "fromPlayerState",
            at = @At("TAIL"),
            cancellable = true
    )
    private static void showFrozenHeartsWhenAtMaxFrost(PlayerEntity player, CallbackInfoReturnable<InGameHud.HeartType> cir) {
        InGameHud.HeartType current = cir.getReturnValue();
        if (current == InGameHud.HeartType.NORMAL) {
            final FreezableEntity freezable = (FreezableEntity) player;
            if (freezable.frostiful$isFrozen()) {
                cir.setReturnValue(InGameHud.HeartType.FROZEN);
            }
        }
    }

}
