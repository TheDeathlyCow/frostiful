package com.github.thedeathlycow.frostiful.mixins.client.gui;

import com.github.thedeathlycow.frostiful.client.gui.RootedOverlayRenderer;
import com.github.thedeathlycow.frostiful.entity.RootedEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    protected abstract void renderOverlay(DrawContext context, Identifier texture, float opacity);


    @Inject(
            method = "renderMiscOverlays",
            at = @At("TAIL")
    )
    private void renderRootedOverlay(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (this.client.player != null) {
            RootedOverlayRenderer.render(
                    (RootedEntity) this.client.player,
                    context, tickCounter,
                    this::renderOverlay
            );
        }
    }

}
