package com.github.thedeathlycow.frostiful.client.mixin.gui;

import com.github.thedeathlycow.frostiful.client.gui.FrostOverlayRenderer;
import com.github.thedeathlycow.frostiful.client.gui.RootedOverlayRenderer;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class InGameHudMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    protected abstract void extractTextureOverlay(GuiGraphicsExtractor graphics, Identifier texture, float alpha);

    @WrapOperation(
            method = "extractCameraOverlays",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/player/LocalPlayer;getTicksFrozen()I"
            )
    )
    private int blockVanillaFrozenOverlayRender(LocalPlayer instance, Operation<Integer> original) {
        return 0;
    }

    @Inject(
            method = "extractCameraOverlays",
            at = @At("TAIL")
    )
    private void renderRootedOverlay(GuiGraphicsExtractor extractor, DeltaTracker tickCounter, CallbackInfo ci) {
        if (this.minecraft.player != null) {
            RootedOverlayRenderer.render(
                    this.minecraft.player,
                    extractor,
                    tickCounter,
                    this::extractTextureOverlay
            );
            FrostOverlayRenderer.renderFrostOverlay(
                    extractor,
                    this.minecraft.player,
                    this::extractTextureOverlay
            );
        }
    }

}
