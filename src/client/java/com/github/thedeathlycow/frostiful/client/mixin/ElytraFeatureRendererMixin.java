package com.github.thedeathlycow.frostiful.client.mixin;

import com.github.thedeathlycow.frostiful.client.render.state.FBipedRenderState;
import com.github.thedeathlycow.frostiful.item.component.CapeComponent;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ElytraFeatureRenderer.class)
public class ElytraFeatureRendererMixin {
    @WrapOperation(
            method = "getTexture",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/util/SkinTextures;capeTexture()Lnet/minecraft/util/Identifier;"
            )
    )
    private static Identifier getFrostologyCloakTexture(
            SkinTextures instance,
            Operation<Identifier> original,
            @Local(argsOnly = true) BipedEntityRenderState state
    ) {
        Identifier accountCape = original.call(instance);
        CapeComponent cape = ((FBipedRenderState) state).frostiful$cape();

        if (cape != null && (accountCape == null || cape.overrideAccountCape())) {
            return cape.capeTexture();
        } else {
            return accountCape;
        }
    }
}