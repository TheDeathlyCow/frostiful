package com.github.thedeathlycow.frostiful.client.mixin;

import com.github.thedeathlycow.frostiful.entity.IceSkater;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Environment(EnvType.CLIENT)
@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @WrapMethod(
            method = "bobView"
    )
    private void cancelBobIfSkating(CameraRenderState cameraState, PoseStack poseStack, Operation<Void> original) {
        if (this.minecraft.getCameraEntity() instanceof IceSkater iceSkater) {
            if (iceSkater.frostiful$isIceSkating()) {
                return;
            }
        }

        original.call(cameraState, poseStack);
    }
}
