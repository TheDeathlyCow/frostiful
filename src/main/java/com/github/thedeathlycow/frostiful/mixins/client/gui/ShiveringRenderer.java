package com.github.thedeathlycow.frostiful.mixins.client.gui;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.group.ClientConfigGroup;
import com.github.thedeathlycow.frostiful.survival.SurvivalUtils;
import com.github.thedeathlycow.thermoo.api.temperature.TemperatureAware;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(GameRenderer.class)
public abstract class ShiveringRenderer {

    @Shadow
    @Final
    private Random random;

    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(
            method = "renderWorld",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/GameRenderer;tiltViewWhenHurt(Lnet/minecraft/client/util/math/MatrixStack;F)V"
            )
    )
    private void shakeViewWhenShivering(float tickDelta, long limitTime, MatrixStack matrices, CallbackInfo ci) {
        if (this.client.getCameraEntity() instanceof TemperatureAware temperatureAware && SurvivalUtils.isShivering(temperatureAware)) {
            ClientConfigGroup config = Frostiful.getConfig().clientConfig;
            if (config.isShakeCameraWhenShiveringEnabled()) {

                final float intensity = config.getCameraShakeIntensity();

                float shakeX = this.random.nextFloat() * 0.02f - 0.1f;
                float shakeY = this.random.nextFloat() * 0.02f - 0.1f;
                float shakeZRot = this.random.nextFloat() * 0.4f - 2;

                matrices.translate(intensity * shakeX, intensity * shakeY, 0.0f);
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(intensity * shakeZRot));
            }
        }
    }

}
