package com.github.thedeathlycow.frostiful.client.render.entity;

import com.github.thedeathlycow.frostiful.client.registry.FEntityModelLayers;
import com.github.thedeathlycow.frostiful.client.render.model.FrostWandItemModel;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.item.model.special.SimpleSpecialModelRenderer;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;

@Environment(EnvType.CLIENT)
public class FrostWandItemRenderer implements SimpleSpecialModelRenderer {
    // packed lightmap coordinates are (block << 4) | (sky << 20)
    private static final int FULL_BRIGHTNESS = (15 << 4) | (15 << 20);

    private final FrostWandItemModel model;

    public FrostWandItemRenderer(FrostWandItemModel model) {
        this.model = model;
    }

    @Override
    public void render(
            ItemDisplayContext itemDisplayContext,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            int overlay,
            boolean glint
    ) {
        matrices.push();
        matrices.scale(1.0F, -1.0F, -1.0F);
        VertexConsumer vertexConsumer = ItemRenderer.getItemGlintConsumer(
                vertexConsumers,
                this.model.getLayer(FrostWandItemModel.TEXTURE),
                false,
                glint
        );
        this.model.render(matrices, vertexConsumer, FULL_BRIGHTNESS, overlay);
        matrices.pop();
    }

    @Environment(EnvType.CLIENT)
    public record Unbaked() implements SpecialModelRenderer.Unbaked {
        public static final MapCodec<FrostWandItemRenderer.Unbaked> CODEC = MapCodec.unit(new FrostWandItemRenderer.Unbaked());

        @Override
        public MapCodec<FrostWandItemRenderer.Unbaked> getCodec() {
            return CODEC;
        }

        @Override
        public SpecialModelRenderer<?> bake(LoadedEntityModels entityModels) {
            return new FrostWandItemRenderer(new FrostWandItemModel(entityModels.getModelPart(FEntityModelLayers.FROST_WAND)));
        }
    }
}
