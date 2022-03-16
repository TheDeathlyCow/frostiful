package com.github.thedeathlycow.lostinthecold.mixins;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net.minecraft.client.gui.hud.InGameHud$HeartType")
public abstract class HudHeartTypeMixin {

    @Redirect(method = "fromPlayerState",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;isFrozen()Z"
            ))
    private static boolean blockDefaultFrozenHeartRendering(PlayerEntity thisPlayer) {
        return false;
    }

}
