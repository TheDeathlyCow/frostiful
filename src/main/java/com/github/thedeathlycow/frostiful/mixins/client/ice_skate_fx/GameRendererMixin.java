package com.github.thedeathlycow.frostiful.mixins.client.ice_skate_fx;

import com.github.thedeathlycow.frostiful.entity.IceSkater;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(GameRenderer.class)
public class GameRendererMixin {


    @Shadow @Final private MinecraftClient client;

    @Inject(
            method = "bobView",
            at = @At("HEAD"),
            cancellable = true
    )
    private void cancelBobIfSkating(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if (this.client.getCameraEntity() instanceof IceSkater iceSkater) {
            if (iceSkater.frostiful$isIceSkating() && iceSkater.frostiful$isGliding()) {
                ci.cancel();
            }
        }
    }

    @ModifyArg(
            method = "bobView",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V"
            ),
            index = 0
    )
    private float reduceBobXWhenSkating(float input) {
        if (this.client.getCameraEntity() instanceof IceSkater iceSkater) {
            if (iceSkater.frostiful$isIceSkating()) {
                input *= 0.75f;
            }
        }

        return input;
    }

    @ModifyArg(
            method = "bobView",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V"
            ),
            index = 1
    )
    private float reduceBobYWhenSkating(float input) {
        if (this.client.getCameraEntity() instanceof IceSkater iceSkater) {
            if (iceSkater.frostiful$isIceSkating()) {
                input *= 0.75f;
            }
        }

        return input;
    }

}
