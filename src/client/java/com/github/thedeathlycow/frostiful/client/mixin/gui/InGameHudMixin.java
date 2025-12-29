package com.github.thedeathlycow.frostiful.client.mixin.gui;

import com.github.thedeathlycow.frostiful.client.gui.FrostOverlayRenderer;
import com.github.thedeathlycow.frostiful.client.gui.RootedOverlayRenderer;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class InGameHudMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    protected abstract void renderTextureOverlay(GuiGraphics context, Identifier texture, float opacity);


    @WrapWithCondition(
            method = "renderCameraOverlays",
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/client/player/LocalPlayer;getTicksFrozen()I"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Gui;renderTextureOverlay(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/resources/Identifier;F)V",
                    ordinal = 0
            )
    )
    private boolean blockVanillaFrozenOverlayRender(
            Gui instance,
            GuiGraphics context, Identifier texture, float opacity
    ) {
        return false;
    }


    @Inject(
            method = "renderCameraOverlays",
            at = @At("TAIL")
    )
    private void renderRootedOverlay(GuiGraphics context, DeltaTracker tickCounter, CallbackInfo ci) {
        if (this.minecraft.player != null) {
            RootedOverlayRenderer.render(
                    this.minecraft.player,
                    context, tickCounter,
                    this::renderTextureOverlay
            );
            FrostOverlayRenderer.renderFrostOverlay(
                    context,
                    this.minecraft.player,
                    this::renderTextureOverlay
            );
        }
    }

}
