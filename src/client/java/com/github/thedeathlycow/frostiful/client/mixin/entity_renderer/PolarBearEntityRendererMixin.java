package com.github.thedeathlycow.frostiful.client.mixin.entity_renderer;

import com.github.thedeathlycow.frostiful.client.BrushableTextures;
import com.github.thedeathlycow.frostiful.client.render.state.FPolarBearEntityRenderState;
import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.registry.FCardinalComponents;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.renderer.entity.PolarBearRenderer;
import net.minecraft.client.renderer.entity.state.PolarBearRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.polarbear.PolarBear;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PolarBearRenderer.class)
public class PolarBearEntityRendererMixin {
    @Inject(
            method = "extractRenderState(Lnet/minecraft/world/entity/animal/polarbear/PolarBear;Lnet/minecraft/client/renderer/entity/state/PolarBearRenderState;F)V",
            at = @At("TAIL")
    )
    private void updateRenderState(PolarBear entity, PolarBearRenderState state, float tickDelta, CallbackInfo ci) {
        ((FPolarBearEntityRenderState) state).frostiful$wasSheared(FCardinalComponents.BRUSHABLE_COMPONENT.get(entity).wasBrushed());
    }

    @WrapMethod(
            method = "getTextureLocation(Lnet/minecraft/client/renderer/entity/state/PolarBearRenderState;)Lnet/minecraft/resources/Identifier;"
    )
    private Identifier setPolarBearHurtTexture(PolarBearRenderState state, Operation<Identifier> original) {
        if (!FrostifulConfigYACL.clientConfig().isDisableHurtPolarBearSkin() && ((FPolarBearEntityRenderState) state).frostiful$wasSheared()) {
            return BrushableTextures.POLAR_BEAR;
        }

        return original.call(state);
    }
}