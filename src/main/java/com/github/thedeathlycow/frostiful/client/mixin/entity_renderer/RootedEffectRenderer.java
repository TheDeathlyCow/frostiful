package com.github.thedeathlycow.frostiful.client.mixin.entity_renderer;

import com.github.thedeathlycow.frostiful.entity.attachment.FrostWandRootComponent;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
@Environment(EnvType.CLIENT)
public class RootedEffectRenderer<T extends LivingEntity, M extends EntityModel<T>> {

    private BlockRenderDispatcher frostiful$blockRenderManager;
    private final BlockState frostiful$BLOCK_STATE = Blocks.ICE.defaultBlockState();

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void initAddon(EntityRendererProvider.Context ctx, M model, float shadowRadius, CallbackInfo ci) {
        this.frostiful$blockRenderManager = ctx.getBlockRenderDispatcher();
    }


    @Inject(
            method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(
                    value = "TAIL"
            )
    )
    private void renderIceOnEntity(T livingEntity, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, CallbackInfo ci) {
        if (FrostWandRootComponent.get(livingEntity).isRooted()) {
            matrixStack.pushPose();
            float blockSize = 1.75f;
            AABB boundingBox = livingEntity.getBoundingBox();
            BlockPos blockPos = BlockPos.containing(livingEntity.getX(), boundingBox.minY, livingEntity.getZ());
            matrixStack.scale(
                    blockSize * (float) boundingBox.getXsize(),
                    blockSize * (float) boundingBox.getYsize(),
                    blockSize * (float) boundingBox.getZsize()
            );
            matrixStack.translate(-0.5, -0.3, -0.5);
            this.frostiful$renderBlock(livingEntity, matrixStack, vertexConsumerProvider, blockPos);
            matrixStack.popPose();
        }
    }

    private void frostiful$renderBlock(LivingEntity livingEntity, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, BlockPos blockPos) {
        this.frostiful$blockRenderManager
                .getModelRenderer()
                .tesselateBlock(
                        livingEntity.level(),
                        this.frostiful$blockRenderManager.getBlockModel(frostiful$BLOCK_STATE),
                        frostiful$BLOCK_STATE,
                        blockPos,
                        matrixStack,
                        vertexConsumerProvider.getBuffer(
                                ItemBlockRenderTypes.getMovingBlockRenderType(frostiful$BLOCK_STATE)
                        ),
                        false,
                        RandomSource.create(),
                        frostiful$BLOCK_STATE.getSeed(blockPos),
                        OverlayTexture.NO_OVERLAY
                );
    }

}
