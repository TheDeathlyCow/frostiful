package com.github.thedeathlycow.frostiful.init;

import com.github.thedeathlycow.frostiful.block.FCutouts;
import com.github.thedeathlycow.frostiful.client.render.FrostWandItemRenderer;
import com.github.thedeathlycow.frostiful.client.model.FrostWandItemModel;
import com.github.thedeathlycow.frostiful.client.render.entity.FEntityRenderers;
import com.github.thedeathlycow.frostiful.item.FItems;
import com.github.thedeathlycow.frostiful.particle.client.FParticleFactoryRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;

@Environment(EnvType.CLIENT)
public class FrostifulClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FCutouts.registerCutouts();
        FEntityRenderers.registerEntityRenderers();
        FParticleFactoryRegistry.registerFactories();

        EntityModelLayerRegistry.registerModelLayer(FrostWandItemModel.FROST_WAND_LAYER, FrostWandItemModel::getTexturedModelData);

        FrostWandItemRenderer frostWandRenderer = new FrostWandItemRenderer(FrostWandItemModel.FROST_WAND_LAYER);
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(frostWandRenderer);
        BuiltinItemRendererRegistry.INSTANCE.register(
                () -> FItems.FROST_WAND,
                frostWandRenderer
        );
        ModelLoadingRegistry.INSTANCE.registerModelProvider(
                (manager, out) -> {
                    out.accept(FrostWandItemRenderer.INVENTORY_MODEL_ID);
                }
        );

        FCutouts.registerCutouts();
        FEntityRenderers.registerEntityRenderers();
        FParticleFactoryRegistry.registerFactories();
        Frostiful.LOGGER.info("Initialized Frostiful client!");
    }
}
