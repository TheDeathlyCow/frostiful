package com.github.thedeathlycow.frostiful.mixins.client.gui;

import com.github.thedeathlycow.frostiful.client.gui.FrostOverlayRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;

@Mixin(InGameHud.class)
public abstract class FrostVinetteOverlay {

    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    @Final
    private static Identifier POWDER_SNOW_OUTLINE;

    @Shadow
    protected abstract void renderOverlay(DrawContext context, Identifier texture, float opacity);

    @Unique
    private final BiConsumer<DrawContext, Float> frostiful$renderOverlayCallback = (context, opacity) -> {
        this.renderOverlay(context, POWDER_SNOW_OUTLINE, opacity);
    };

    @ModifyArg(
            method = "render",
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/client/network/ClientPlayerEntity;getFrozenTicks()I"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/hud/InGameHud;renderOverlay(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/util/Identifier;F)V",
                    ordinal = 0
            ),
            index = 2
    )
    private float setOpacityForDefaultOverlayToAlways0(float opacity) {
        return 0.0f;
    }

    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE_ASSIGN",
                    target = "Lnet/minecraft/util/math/MathHelper;lerp(FFF)F",
                    shift = At.Shift.BEFORE,
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/client/network/ClientPlayerEntity;getFrozenTicks()I"
                    )
            )
    )
    private void renderPowderSnowOverlayAtThreshold(DrawContext context, float tickDelta, CallbackInfo ci) {
        if (this.client.player != null) {
            FrostOverlayRenderer.renderFrostOverlay(context, this.client.player, this.frostiful$renderOverlayCallback);
        }
    }
}
