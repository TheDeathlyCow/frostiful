package com.github.thedeathlycow.frostiful.client.mixin.entity_renderer;

import com.github.thedeathlycow.frostiful.client.render.state.FLivingEntityRenderState;
import com.github.thedeathlycow.frostiful.registry.FComponents;
import com.github.thedeathlycow.frostiful.survival.SurvivalUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
@Environment(EnvType.CLIENT)
public class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> {
    @Unique
    private BlockRenderDispatcher frostiful$blockRenderManager;

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void initAddon(EntityRendererProvider.Context ctx, M model, float shadowRadius, CallbackInfo ci) {
        this.frostiful$blockRenderManager = ctx.getBlockRenderDispatcher();
    }

    @Inject(
            method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V",
            at = @At("TAIL")
    )
    private void updateRenderState(T entity, S state, float f, CallbackInfo ci) {
        boolean isRooted = FComponents.FROST_WAND_ROOT_COMPONENT.get(entity).isRooted();
        ((FLivingEntityRenderState) state).frostiful$isRooted(isRooted);

        boolean shaking = state.isFullyFrozen;
        if (SurvivalUtils.isShiveringRender(entity)) {
            state.isFullyFrozen = true;
        } else {
            state.isFullyFrozen = shaking;
        }
    }

    // TODO(Ravel): target method submit with the signature not found
// TODO(Ravel): target method submit with the signature not found
    @Inject(
            method = "submit(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V",
            at = @At(
                    value = "TAIL"
            )
    )
    private void renderIceOnEntity(
            S state,
            PoseStack matrices,
            SubmitNodeCollector queue,
            CameraRenderState cameraState,
            CallbackInfo ci
    ) {
        if (((FLivingEntityRenderState) state).frostiful$isRooted()) {
            matrices.pushPose();
            float blockSize = 1.75f;
            matrices.scale(
                    blockSize * state.boundingBoxWidth,
                    blockSize * state.boundingBoxHeight,
                    blockSize * state.boundingBoxWidth
            );
            matrices.translate(-0.5, -0.3, -0.5);

            queue.submitBlock(
                    matrices,
                    Blocks.ICE.defaultBlockState(),
                    state.lightCoords,
                    OverlayTexture.NO_OVERLAY,
                    state.outlineColor
            );

            matrices.popPose();
        }
    }
}
