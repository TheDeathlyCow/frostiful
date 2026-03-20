package com.github.thedeathlycow.frostiful.client.render.entity;

import com.github.thedeathlycow.frostiful.client.render.state.FLivingEntityRenderState;
import com.github.thedeathlycow.frostiful.registry.FComponents;
import com.github.thedeathlycow.frostiful.survival.SurvivalUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelResolver;
import net.minecraft.client.renderer.block.model.BlockDisplayContext;
import net.minecraft.client.renderer.entity.DisplayRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public final class IceBlockRenderer<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> {
    public static final BlockDisplayContext BLOCK_DISPLAY_CONTEXT = BlockDisplayContext.create();

    private static final BlockState RENDERED_STATE = Blocks.ICE.defaultBlockState();

    private final BlockModelResolver blockModelResolver;

    public IceBlockRenderer(EntityRendererProvider.Context context) {
        this.blockModelResolver = context.getBlockModelResolver();
    }

    public void extractRenderState(final T entity, final S state, final float partialTicks) {
        boolean isRooted = FComponents.FROST_WAND_ROOT_COMPONENT.get(entity).isRooted();
        FLivingEntityRenderState frostifulState = (FLivingEntityRenderState) state;
        frostifulState.frostiful$isRooted(isRooted);

        boolean shaking = state.isFullyFrozen;

        if (SurvivalUtils.isShiveringRender(entity)) {
            state.isFullyFrozen = true;
        } else {
            state.isFullyFrozen = shaking;
        }

        if (isRooted) {
            this.blockModelResolver.update(frostifulState.frostiful$blockModel(), RENDERED_STATE, BLOCK_DISPLAY_CONTEXT);
        } else {
            frostifulState.frostiful$blockModel().clear();
        }
    }

    public void submit(final S state, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final CameraRenderState camera) {
        FLivingEntityRenderState frostifulState = (FLivingEntityRenderState) state;

        if (frostifulState.frostiful$isRooted()) {
            poseStack.pushPose();
            float blockSize = 1.75f;
            poseStack.scale(
                    blockSize * state.boundingBoxWidth,
                    blockSize * state.boundingBoxHeight,
                    blockSize * state.boundingBoxWidth
            );
            poseStack.translate(-0.5, -0.3, -0.5);

            frostifulState.frostiful$blockModel().submit(
                    poseStack,
                    submitNodeCollector,
                    state.lightCoords,
                    OverlayTexture.NO_OVERLAY,
                    state.outlineColor
            );

            poseStack.popPose();
        }
    }
}