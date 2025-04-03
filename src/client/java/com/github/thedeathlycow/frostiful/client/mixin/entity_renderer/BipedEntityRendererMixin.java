package com.github.thedeathlycow.frostiful.client.mixin.entity_renderer;

import com.github.thedeathlycow.frostiful.client.render.state.FBipedRenderState;
import com.github.thedeathlycow.frostiful.item.component.CapeComponent;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityRenderer.class)
public abstract class BipedEntityRendererMixin<T extends MobEntity, S extends BipedEntityRenderState, M extends BipedEntityModel<S>> {
    @Inject(
            method = "updateBipedRenderState",
            at = @At("TAIL")
    )
    private static void updateIceSkateRenderState(
            LivingEntity entity,
            BipedEntityRenderState state,
            float tickDelta,
            ItemModelManager itemModelResolver,
            CallbackInfo ci
    ) {
        boolean wearingSkates = entity.getEquippedStack(EquipmentSlot.FEET).isIn(FItemTags.ICE_SKATES);

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