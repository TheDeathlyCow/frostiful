package com.github.thedeathlycow.frostiful.client.render.entity;

import com.github.thedeathlycow.frostiful.client.registry.FEntityModelLayers;
import com.github.thedeathlycow.frostiful.client.render.model.FrostWandItemModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import org.joml.Vector3f;

import java.util.Set;

@Environment(EnvType.CLIENT)
public class FrostWandItemRenderer implements NoDataSpecialModelRenderer {
    // packed lightmap coordinates are (block << 4) | (sky << 20)
    private static final int FULL_BRIGHTNESS = (15 << 4) | (15 << 20);

    private final FrostWandItemModel model;

    public FrostWandItemRenderer(FrostWandItemModel model) {
        this.model = model;
    }

    @Override
    public void getExtents(Set<Vector3f> vertices) {
        var matrixStack = new PoseStack();
        matrixStack.scale(1.0F, -1.0F, -1.0F);
        this.model.root().getExtentsForGui(matrixStack, vertices);
    }

    @Override
    public void submit(
            ItemDisplayContext displayContext,
            PoseStack matrices,
            SubmitNodeCollector queue,
            int light,
            int overlay,
            boolean glint,
            int outlineColor
    ) {
        matrices.pushPose();
        matrices.scale(1.0F, -1.0F, -1.0F);

        queue.submitModelPart(
                this.model.root(),
                matrices,
                this.model.renderType(FrostWandItemModel.TEXTURE),
                FULL_BRIGHTNESS,
                overlay,
                null,
                false,
                glint,
                -1,
                null,
                outlineColor
        );

        matrices.popPose();
    }

    @Environment(EnvType.CLIENT)
    public record Unbaked() implements SpecialModelRenderer.Unbaked {
        public static final MapCodec<FrostWandItemRenderer.Unbaked> CODEC = MapCodec.unit(new FrostWandItemRenderer.Unbaked());

        @Override
        public SpecialModelRenderer<?> bake(BakingContext context) {
            return new FrostWandItemRenderer(
                    new FrostWandItemModel(context.entityModelSet().bakeLayer(FEntityModelLayers.FROST_WAND))
            );
        }

        @Override
        public MapCodec<FrostWandItemRenderer.Unbaked> type() {
            return CODEC;
        }
    }
}
