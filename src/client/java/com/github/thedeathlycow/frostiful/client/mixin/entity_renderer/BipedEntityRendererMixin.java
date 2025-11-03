package com.github.thedeathlycow.frostiful.client.mixin.entity_renderer;

import com.github.thedeathlycow.frostiful.client.render.state.FBipedRenderState;
import com.github.thedeathlycow.frostiful.item.component.CapeComponent;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidMobRenderer.class)
public abstract class BipedEntityRendererMixin<T extends Mob, S extends HumanoidRenderState, M extends HumanoidModel<S>> {
    @Inject(
            method = "extractHumanoidRenderState",
            at = @At("TAIL")
    )
    private static void updateIceSkateRenderState(
            LivingEntity entity,
            HumanoidRenderState state,
            float tickDelta,
            ItemModelResolver itemModelResolver,
            CallbackInfo ci
    ) {
        boolean wearingSkates = entity.getItemBySlot(EquipmentSlot.FEET).is(FItemTags.ICE_SKATES);

        FBipedRenderState fState = ((FBipedRenderState) state);

        fState.frostiful$wearingIceSkates(wearingSkates);

        CapeComponent component = CapeComponent.getCapeOrChest(entity);
        if (component != null) {
            fState.frostiful$cape(component);
        } else {
            fState.frostiful$cape(null);
        }
    }
}