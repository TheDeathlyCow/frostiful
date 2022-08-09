package com.github.thedeathlycow.frostiful.mixins.client.gui;

import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.config.group.ClientConfigGroup;
import com.github.thedeathlycow.frostiful.entity.FreezableEntity;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class FrostVinetteOverlay {

    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    @Final
    private static Identifier POWDER_SNOW_OUTLINE;

    @Shadow
    protected abstract void renderOverlay(Identifier texture, float opacity);


//    @Redirect(
//            method = "render",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;getFrozenTicks()I"
//            )
//    )
//    private int doNotRenderPowderSnowOverlayNormally(ClientPlayerEntity instance) {
//        return 0;
//    }

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
    private void renderPowderSnowOverlayAtThreshold(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        assert this.client.player != null;
        final FreezableEntity entity = (FreezableEntity) this.client.player;
        float freezeScale = entity.frostiful$getFrostProgress();
        FrostifulConfig config = Frostiful.getConfig();
        float renderThreshold = config.clientConfig.getFrostOverlayStart();

        if (freezeScale >= renderThreshold) {
            Frostiful.LOGGER.info(String.format("Render frost vignette %.2f %.2f", freezeScale, renderThreshold));
            float opacity = renderThreshold == 1.0f ? 0.0f : (freezeScale - renderThreshold) / (1.0f - renderThreshold);
            this.renderOverlay(POWDER_SNOW_OUTLINE, opacity);
        }


    }
}
