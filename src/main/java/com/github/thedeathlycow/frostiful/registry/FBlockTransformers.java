package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.block.transformer.BlockTransformer;
import net.minecraft.resources.ResourceKey;

public final class FBlockTransformers {
    public static final ResourceKey<BlockTransformer> FROSTOLOGER_BLIZZARD_FREEZE = createKey("frostologer_blizzard_freeze");

    private static ResourceKey<BlockTransformer> createKey(String name) {
        return ResourceKey.create(FrostifulRegistries.BLOCK_TRANSFORMER_KEY, Frostiful.id(name));
    }

    private FBlockTransformers() {

    }
}