package com.github.thedeathlycow.frostiful.client.mixin;

import com.github.thedeathlycow.frostiful.client.render.state.FPlayerRendererState;
import com.github.thedeathlycow.frostiful.item.component.CapeComponent;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.entity.feature.CapeFeatureRenderer;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CapeFeatureRenderer.class)
public class CapeFeatureRendererMixin {
    @WrapOperation(
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/PlayerEntityRenderState;FF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/util/SkinTextures;capeTexture()Lnet/minecraft/util/Identifier;"
            )
    )
    private Identifier getFrostologyCloakTexture(
            SkinTextures instance,
            Operation<Identifier> original,
            @Local(argsOnly = true) PlayerEntityRenderState state
    ) {
        Identifier accountCape = original.call(instance);
        CapeComponent cape = ((FPlayerRendererState) state).frostiful$cape();

        if (cape != null && (accountCape == null || cape.overrideAccountCape())) {
            return cape.capeTexture();
        } else {
            return accountCape;
        }
    }
}
