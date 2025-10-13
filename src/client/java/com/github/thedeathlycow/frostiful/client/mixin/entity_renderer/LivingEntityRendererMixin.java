package com.github.thedeathlycow.frostiful.client.mixin.entity_renderer;

import com.github.thedeathlycow.frostiful.client.render.state.FLivingEntityRenderState;
import com.github.thedeathlycow.frostiful.registry.FComponents;
import com.github.thedeathlycow.frostiful.survival.SurvivalUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
@Environment(EnvType.CLIENT)
public class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> {
    @Unique
    private BlockRenderManager frostiful$blockRenderManager;

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void initAddon(EntityRendererFactory.Context ctx, M model, float shadowRadius, CallbackInfo ci) {
        this.frostiful$blockRenderManager = ctx.getBlockRenderManager();
    }

    @Inject(
            method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V",
            at = @At("TAIL")
    )
    private void updateRenderState(T entity, S state, float f, CallbackInfo ci) {
        boolean isRooted = FComponents.FROST_WAND_ROOT_COMPONENT.get(entity).isRooted();
        ((FLivingEntityRenderState) state).frostiful$isRooted(isRooted);

        boolean shaking = state.shaking;
        if (SurvivalUtils.isShiveringRender(entity)) {
            state.shaking = true;
        } else {
            state.shaking = shaking;
        }
    }

    @Inject(
            method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V",
            at = @At(
                    value = "TAIL"
            )
    )
    private void renderIceOnEntity(
            S state,
            MatrixStack matrices,
            OrderedRenderCommandQueue queue,
            CameraRenderState cameraState,
            CallbackInfo ci
    ) {
        if (((FLivingEntityRenderState) state).frostiful$isRooted()) {
            matrices.push();
            float blockSize = 1.75f;
            matrices.scale(
                    blockSize * state.width,
                    blockSize * state.height,
                    blockSize * state.width
            );
            matrices.translate(-0.5, -0.3, -0.5);

            queue.submitBlock(
                    matrices,
                    Blocks.ICE.getDefaultState(),
                    state.light,
                    OverlayTexture.DEFAULT_UV,
                    state.outlineColor
            );

            matrices.pop();
        }
    }
}
