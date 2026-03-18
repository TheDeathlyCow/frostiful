package com.github.thedeathlycow.frostiful.client.mixin.gui;

import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.config.section.ClientConfig;
import com.github.thedeathlycow.frostiful.survival.SurvivalUtils;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(GameRenderer.class)
public abstract class ShiveringRenderer {

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


    @WrapOperation(
            method = "renderItemInHand",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/GameRenderer;bobView(Lcom/mojang/blaze3d/vertex/PoseStack;F)V"
            )
    )
    private void shakeViewWhenShivering(
            GameRenderer instance,
            PoseStack matrices,
            float tickDelta,
            Operation<Void> original
    ) {
        if (this.minecraft.getCameraEntity() instanceof LivingEntity livingEntity && SurvivalUtils.isShiveringRender(livingEntity)) {
            ClientConfig config = FrostifulConfigYACL.clientConfig();
            if (config.isShakeCameraWhenShiveringEnabled()) {

                final float intensity = config.getHandShakeIntensity();

                float shakeX = (this.random.nextFloat() - frostiful_baseShakeSift) * frostiful_baseIntensity;
                float shakeY = (this.random.nextFloat() - frostiful_baseShakeSift) * frostiful_baseIntensity;
                float shakeZ = (this.random.nextFloat() - frostiful_baseShakeSift) * frostiful_baseIntensity;

                matrices.translate(intensity * shakeX, intensity * shakeY, intensity * shakeZ);
            }
        }

        original.call(instance, matrices, tickDelta);
    }


}
