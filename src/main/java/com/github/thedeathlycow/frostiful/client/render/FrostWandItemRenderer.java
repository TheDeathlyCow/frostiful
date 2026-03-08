package com.github.thedeathlycow.frostiful.client.render;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.client.model.FrostWandItemModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class FrostWandItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer, SimpleSynchronousResourceReloadListener {

    public static final ResourceLocation ID = Frostiful.id("frost_wand_renderer");
    public static final ModelResourceLocation INVENTORY_MODEL_ID = new ModelResourceLocation(
            Frostiful.id("item/frost_wand_in_inventory"),
            "main"
    );

    private final ModelLayerLocation modelLayer;
    private FrostWandItemModel model;
    private ItemRenderer itemRenderer;
    private BakedModel inventoryModel;

    public FrostWandItemRenderer(ModelLayerLocation modelLayer) {
        this.modelLayer = modelLayer;
    }

    /**
     * Code largely based on similar functionality in the mod
     * <a href="https://github.com/Ladysnake/Impaled/">'Impaled'</a>
     *
     * @param stack           the rendered item stack
     * @param mode            the model transformation mode
     * @param matrices        the matrix stack
     * @param vertexConsumers the vertex consumer provider
     * @param light           packed lightmap coordinates
     * @param overlay         the overlay UV passed to {@link VertexConsumer#setOverlay(int)}
     */
    @Override
    public void render(ItemStack stack, ItemDisplayContext mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        boolean renderAsItem = mode == ItemDisplayContext.GUI
                || mode == ItemDisplayContext.GROUND
                || mode == ItemDisplayContext.FIXED;

        if (renderAsItem) {
            matrices.popPose();
            matrices.pushPose();
            itemRenderer.render(stack, mode, false, matrices, vertexConsumers, light, overlay, this.inventoryModel);
        } else {
            matrices.pushPose();
            matrices.scale(0.6F, -0.6F, -0.6F);
            matrices.translate(0f, 1f, 0f);
            VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(
                    vertexConsumers, this.model.renderType(FrostWandItemModel.TEXTURE), false, stack.hasFoil()
            );

            this.model.renderToBuffer(matrices, vertexConsumer, light, overlay);
            matrices.popPose();
        }
    }

    @Override
    public ResourceLocation getFabricId() {
        return ID;
    }

    @Override
    public void onResourceManagerReload(ResourceManager manager) {
        Minecraft client = Minecraft.getInstance();
        this.model = new FrostWandItemModel(client.getEntityModels().bakeLayer(this.modelLayer));
        this.itemRenderer = client.getItemRenderer();
        this.inventoryModel = client.getModelManager().getModel(INVENTORY_MODEL_ID);
    }
}
