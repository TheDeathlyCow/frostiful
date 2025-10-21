package com.github.thedeathlycow.frostiful.client.mixin.entity_renderer;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.client.BrushableTextures;
import com.github.thedeathlycow.frostiful.client.render.state.FPolarBearEntityRenderState;
import com.github.thedeathlycow.frostiful.registry.FComponents;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.render.entity.PolarBearEntityRenderer;
import net.minecraft.client.render.entity.state.PolarBearEntityRenderState;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PolarBearEntityRenderer.class)
public class PolarBearEntityRendererMixin {
    @Inject(
            method = "updateRenderState(Lnet/minecraft/entity/passive/PolarBearEntity;Lnet/minecraft/client/render/entity/state/PolarBearEntityRenderState;F)V",
            at = @At("TAIL")
    )
    private void updateRenderState(PolarBearEntity entity, PolarBearEntityRenderState state, float tickDelta, CallbackInfo ci) {
        ((FPolarBearEntityRenderState) state).frostiful$wasSheared(FComponents.BRUSHABLE_COMPONENT.get(entity).wasBrushed());
    }

    @WrapMethod(
            method = "getTexture(Lnet/minecraft/client/render/entity/state/PolarBearEntityRenderState;)Lnet/minecraft/util/Identifier;"
    )
    private Identifier setPolarBearHurtTexture(PolarBearEntityRenderState state, Operation<Identifier> original) {
        if (!Frostiful.getConfig().clientConfig.isDisableHurtPolarBearSkin() && ((FPolarBearEntityRenderState) state).frostiful$wasSheared()) {
            return BrushableTextures.POLAR_BEAR;
        }

        return original.call(state);
    }
}