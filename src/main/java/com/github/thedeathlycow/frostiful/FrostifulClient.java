package com.github.thedeathlycow.frostiful;

import com.github.thedeathlycow.frostiful.block.FCutouts;
import com.github.thedeathlycow.frostiful.client.model.FEntityModelLayers;
import com.github.thedeathlycow.frostiful.client.render.FrostWandItemRenderer;
import com.github.thedeathlycow.frostiful.client.render.entity.FEntityRenderers;
import com.github.thedeathlycow.frostiful.particle.client.FParticleFactoryRegistry;
import com.github.thedeathlycow.frostiful.registry.FItems;
import com.github.thedeathlycow.frostiful.server.network.PointWindSpawnPacket;
import com.github.thedeathlycow.frostiful.server.network.PointWindSpawnPacketListener;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;

@Environment(EnvType.CLIENT)
public class FrostifulClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FCutouts.registerCutouts();
        FParticleFactoryRegistry.registerFactories();

        FEntityModelLayers.register();
        FEntityRenderers.registerEntityRenderers();

        FrostWandItemRenderer frostWandRenderer = new FrostWandItemRenderer(FEntityModelLayers.FROST_WAND);
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
        FParticleFactoryRegistry.registerFactories();
        ClientPlayNetworking.registerGlobalReceiver(
                PointWindSpawnPacket.ID,
                PointWindSpawnPacketListener::receive
        );


        Frostiful.LOGGER.info("Initialized Frostiful client!");
    }
}
