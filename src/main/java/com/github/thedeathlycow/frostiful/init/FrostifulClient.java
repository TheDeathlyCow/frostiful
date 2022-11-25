package com.github.thedeathlycow.frostiful.init;

import com.github.thedeathlycow.frostiful.block.FrostifulCutouts;
import com.github.thedeathlycow.frostiful.client.render.FrostWandItemRenderer;
import com.github.thedeathlycow.frostiful.client.render.FrostWandModel;
import com.github.thedeathlycow.frostiful.client.render.entity.FrostifulEntityRenderers;
import com.github.thedeathlycow.frostiful.item.FrostifulItems;
import com.github.thedeathlycow.frostiful.particle.client.FrostifulParticleFactoryRegistry;
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
        FrostifulCutouts.registerCutouts();
        FrostifulEntityRenderers.registerEntityRenderers();
        FrostifulParticleFactoryRegistry.registerFactories();

        EntityModelLayerRegistry.registerModelLayer(FrostWandModel.FROST_WAND_LAYER, FrostWandModel::getTexturedModelData);

        FrostWandItemRenderer frostWandRenderer = new FrostWandItemRenderer(FrostWandModel.FROST_WAND_LAYER);
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(frostWandRenderer);
        BuiltinItemRendererRegistry.INSTANCE.register(
                () -> FrostifulItems.FROST_WAND,
                frostWandRenderer
        );
        ModelLoadingRegistry.INSTANCE.registerModelProvider(
                (manager, out) -> {
                    out.accept(FrostWandItemRenderer.INVENTORY_MODEL_ID);
                }
        );

        Frostiful.LOGGER.info("Initialized Frostiful client!");
    }
}
