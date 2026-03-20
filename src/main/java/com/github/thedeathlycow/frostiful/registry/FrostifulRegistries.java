package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.block.transformer.BlockTransformer;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public final class FrostifulRegistries {
    public static final ResourceKey<Registry<BlockTransformer>> BLOCK_TRANSFORMER_KEY = ResourceKey.createRegistryKey(Frostiful.id("block_transformer"));

    public static final ResourceKey<Registry<BlockTransformer.Type<?>>> BLOCK_TRANSFORMER_TYPE_KEY = ResourceKey.createRegistryKey(Frostiful.id("block_transformer_type"));

    public static final Registry<BlockTransformer.Type<?>> BLOCK_TRANSFORMER_TYPE = FabricRegistryBuilder.create(BLOCK_TRANSFORMER_TYPE_KEY).buildAndRegister();

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful registries");

        DynamicRegistries.register(BLOCK_TRANSFORMER_KEY, BlockTransformer.ELEMENT_CODEC);
    }

    private FrostifulRegistries() {

    }
}