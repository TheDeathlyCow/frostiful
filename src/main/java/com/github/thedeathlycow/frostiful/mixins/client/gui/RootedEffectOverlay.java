package com.github.thedeathlycow.frostiful.mixins.client.gui;

import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.entity.RootedEntity;
import com.github.thedeathlycow.frostiful.Frostiful;
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
public abstract class RootedEffectOverlay {

    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    protected abstract void renderOverlay(Identifier texture, float opacity);

    private static final Identifier ROOTED_OVERLAY = new Identifier("textures/block/ice.png");

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
    private void renderRootedOverlay(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        assert this.client.player != null;
        final RootedEntity entity = (RootedEntity) this.client.player;

        if (entity.frostiful$isRooted()) {
            FrostifulConfig config = Frostiful.getConfig();
            float opacity = ((float)entity.frostiful$getRootedTicks()) / config.combatConfig.getFrostWandRootTime();
            this.renderOverlay(ROOTED_OVERLAY, opacity);
        }
    }
}
