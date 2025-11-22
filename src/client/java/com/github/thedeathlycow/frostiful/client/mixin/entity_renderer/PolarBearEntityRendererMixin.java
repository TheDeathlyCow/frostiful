package com.github.thedeathlycow.frostiful.client.mixin.entity_renderer;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.client.BrushableTextures;
import com.github.thedeathlycow.frostiful.client.render.state.FPolarBearEntityRenderState;
import com.github.thedeathlycow.frostiful.registry.FComponents;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.renderer.entity.PolarBearRenderer;
import net.minecraft.client.renderer.entity.state.PolarBearRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.PolarBear;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PolarBearRenderer.class)
public class PolarBearEntityRendererMixin {
    @Inject(
            method = "extractRenderState(Lnet/minecraft/world/entity/animal/PolarBear;Lnet/minecraft/client/renderer/entity/state/PolarBearRenderState;F)V",
            at = @At("TAIL")
    )
    private void updateRenderState(PolarBear entity, PolarBearRenderState state, float tickDelta, CallbackInfo ci) {
        ((FPolarBearEntityRenderState) state).frostiful$wasSheared(FComponents.BRUSHABLE_COMPONENT.get(entity).wasBrushed());
    }

    @WrapMethod(
            method = "getTextureLocation(Lnet/minecraft/client/renderer/entity/state/PolarBearRenderState;)Lnet/minecraft/resources/ResourceLocation;"
    )
    private ResourceLocation setPolarBearHurtTexture(PolarBearRenderState state, Operation<ResourceLocation> original) {
        if (!Frostiful.getConfig().clientConfig.isDisableHurtPolarBearSkin() && ((FPolarBearEntityRenderState) state).frostiful$wasSheared()) {
            return BrushableTextures.POLAR_BEAR;
        }

        return original.call(state);
    }
}