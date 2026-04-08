package com.github.thedeathlycow.frostiful.client.mixin.entity.render;

import com.github.thedeathlycow.frostiful.client.render.state.FHumanoidRenderState;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.state.ArmorStandRenderState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorStandRenderer.class)
public class ArmorStandRendererMixin {
    @Inject(
            method = "extractRenderState(Lnet/minecraft/world/entity/decoration/ArmorStand;Lnet/minecraft/client/renderer/entity/state/ArmorStandRenderState;F)V",
            at = @At("TAIL")
    )
    private void updateRenderState(ArmorStand entity, ArmorStandRenderState state, float tickDelta, CallbackInfo ci) {
        boolean wearingSkates = entity.getItemBySlot(EquipmentSlot.FEET).is(FItemTags.ICE_SKATES);

        FHumanoidRenderState bipedState = ((FHumanoidRenderState) state);

        bipedState.frostiful$wearingIceSkates(wearingSkates);
    }
}