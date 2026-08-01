package com.github.thedeathlycow.frostiful.client.mixin.gui;

import com.github.thedeathlycow.frostiful.client.config.FrostifulClientConfig;
import com.github.thedeathlycow.frostiful.client.config.section.AccessibilitySettings;
import com.github.thedeathlycow.frostiful.survival.SurvivalUtils;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Matrix4fc;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow
    @Final
    private RandomSource random;

    @Shadow
    @Final
    private Minecraft minecraft;

    @Unique
    private static final float frostiful_baseShakeSift = 0.5f;
    @Unique
    private static final float frostiful_baseIntensity = 0.01f;


    @Inject(
            method = "renderItemInHand",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/GameRenderer;bobHurt(Lnet/minecraft/client/renderer/state/level/CameraRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;)V",
                    shift = At.Shift.AFTER
            )
    )
    private void shakeViewWhenShivering(
            CameraRenderState cameraState,
            float deltaPartialTick,
            Matrix4fc modelViewMatrix,
            CallbackInfo ci,
            @Local(name = "poseStack") PoseStack poseStack
    ) {
        if (this.minecraft.getCameraEntity() instanceof LivingEntity livingEntity && SurvivalUtils.isShiveringRender(livingEntity)) {
            AccessibilitySettings config = FrostifulClientConfig.accessibilitySettings();

            if (config.shakeHandWhenShivering()) {
                final float intensity = config.handShakeIntensity();

                float shakeX = (this.random.nextFloat() - frostiful_baseShakeSift) * frostiful_baseIntensity;
                float shakeY = (this.random.nextFloat() - frostiful_baseShakeSift) * frostiful_baseIntensity;
                float shakeZ = (this.random.nextFloat() - frostiful_baseShakeSift) * frostiful_baseIntensity;

                poseStack.translate(intensity * shakeX, intensity * shakeY, intensity * shakeZ);
            }
        }
    }
}
