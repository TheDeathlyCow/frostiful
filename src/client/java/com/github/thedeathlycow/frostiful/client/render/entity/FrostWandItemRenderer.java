package com.github.thedeathlycow.frostiful.client.render.entity;

import com.github.thedeathlycow.frostiful.client.registry.FEntityModelLayers;
import com.github.thedeathlycow.frostiful.client.render.model.FrostWandItemModel;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.item.model.special.SimpleSpecialModelRenderer;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import org.joml.Vector3f;

import java.util.Set;

@Environment(EnvType.CLIENT)
public class FrostWandItemRenderer implements SimpleSpecialModelRenderer {
    // packed lightmap coordinates are (block << 4) | (sky << 20)
    private static final int FULL_BRIGHTNESS = (15 << 4) | (15 << 20);

    private final FrostWandItemModel model;

    public FrostWandItemRenderer(FrostWandItemModel model) {
        this.model = model;
    }

    @Override
    public void collectVertices(Set<Vector3f> vertices) {
        var matrixStack = new MatrixStack();
        matrixStack.scale(1.0F, -1.0F, -1.0F);
        this.model.getRootPart().collectVertices(matrixStack, vertices);
    }

    @Override
    public void render(
            ItemDisplayContext displayContext,
            MatrixStack matrices,
            OrderedRenderCommandQueue queue,
            int light,
            int overlay,
            boolean glint,
            int outlineColor
    ) {
        matrices.push();
        matrices.scale(1.0F, -1.0F, -1.0F);

        queue.submitModelPart(
                this.model.getRootPart(),
                matrices,
                this.model.getLayer(FrostWandItemModel.TEXTURE),
                FULL_BRIGHTNESS,
                overlay,
                null,
                false,
                glint,
                -1,
                null,
                outlineColor
        );

        matrices.pop();
    }

    @Environment(EnvType.CLIENT)
    public record Unbaked() implements SpecialModelRenderer.Unbaked {
        public static final MapCodec<FrostWandItemRenderer.Unbaked> CODEC = MapCodec.unit(new FrostWandItemRenderer.Unbaked());

        @Override
        public SpecialModelRenderer<?> bake(BakeContext context) {
            return new FrostWandItemRenderer(
                    new FrostWandItemModel(context.entityModelSet().getModelPart(FEntityModelLayers.FROST_WAND))
            );
        }

        @Override
        public MapCodec<FrostWandItemRenderer.Unbaked> getCodec() {
            return CODEC;
        }
    }
}
