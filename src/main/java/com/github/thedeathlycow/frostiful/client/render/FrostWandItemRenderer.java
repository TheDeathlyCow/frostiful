package com.github.thedeathlycow.frostiful.client.render;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class FrostWandItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer, SimpleSynchronousResourceReloadListener {

    public static final Identifier ID = new Identifier(Frostiful.MODID, "frost_wand_renderer");
    public static final ModelIdentifier INVENTORY_MODEL_ID = new ModelIdentifier(new Identifier(Frostiful.MODID, "frost_wand_in_inventory"), "inventory");

    private final EntityModelLayer modelLayer;
    private FrostWandModel model;
    private ItemRenderer itemRenderer;
    private BakedModel inventoryModel;

    public FrostWandItemRenderer(EntityModelLayer modelLayer) {
        this.modelLayer = modelLayer;
    }

    @Override
    public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        boolean renderAsItem = mode == ModelTransformation.Mode.GUI
                || mode == ModelTransformation.Mode.GROUND
                || mode == ModelTransformation.Mode.FIXED;


        if (renderAsItem) {
            matrices.pop();
            matrices.push();
            itemRenderer.renderItem(stack, mode, false, matrices, vertexConsumers, light, overlay, this.inventoryModel);
        } else {
            matrices.push();
            matrices.scale(1.0F, -1.0F, -1.0F);
            VertexConsumer vertexConsumer = ItemRenderer.getDirectItemGlintConsumer(
                    vertexConsumers, this.model.getLayer(FrostWandModel.TEXTURE), false, stack.hasGlint()
            );
            this.model.render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
            matrices.pop();
        }
    }

    @Override
    public Identifier getFabricId() {
        return ID;
    }

    @Override
    public void reload(ResourceManager manager) {
        MinecraftClient client = MinecraftClient.getInstance();
        this.model = new FrostWandModel(client.getEntityModelLoader().getModelPart(this.modelLayer));
        this.itemRenderer = client.getItemRenderer();
        this.inventoryModel = client.getBakedModelManager().getModel(INVENTORY_MODEL_ID);
    }
}
