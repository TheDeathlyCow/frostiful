package com.github.thedeathlycow.frostiful.client.mixin;

import com.github.thedeathlycow.frostiful.client.render.state.FBipedRenderState;
import com.github.thedeathlycow.frostiful.item.component.CapeComponent;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.core.ClientAsset;
import net.minecraft.world.entity.player.PlayerSkin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CapeLayer.class)
public class CapeFeatureRendererMixin {
    @WrapOperation(
            method = "submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/renderer/entity/state/AvatarRenderState;FF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/PlayerSkin;cape()Lnet/minecraft/core/ClientAsset$Texture;"
            )
    )
    private ClientAsset.Texture getFrostologyCloakTexture(
            PlayerSkin instance,
            Operation<ClientAsset.Texture> original,
            @Local(argsOnly = true) AvatarRenderState state
    ) {
        ClientAsset.Texture accountCape = original.call(instance);
        CapeComponent cape = ((FBipedRenderState) state).frostiful$cape();

        if (cape != null && (accountCape == null || cape.overrideAccountCape())) {
            return cape.capeAsset();
        } else {
            return accountCape;
        }
    }
}
