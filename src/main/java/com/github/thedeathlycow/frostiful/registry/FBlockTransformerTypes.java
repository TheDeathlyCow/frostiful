package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.block.transformer.SequenceBlockTransformer;
import com.github.thedeathlycow.frostiful.block.transformer.BlockTransformer;
import com.github.thedeathlycow.frostiful.block.transformer.SimpleBlockTransformer;
import net.minecraft.core.Registry;

public final class FBlockTransformerTypes {
    public static final BlockTransformer.Type<SequenceBlockTransformer> SEQUENCE = register("sequence", new BlockTransformer.Type<>(SequenceBlockTransformer.CODEC));
    public static final BlockTransformer.Type<SimpleBlockTransformer> SIMPLE_BLOCK = register("simple_block", new BlockTransformer.Type<>(SimpleBlockTransformer.CODEC));

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized frostiful block transformers");
    }

    private static <T extends BlockTransformer> BlockTransformer.Type<T> register(String name, BlockTransformer.Type<T> type) {
        return Registry.register(FrostifulRegistries.BLOCK_TRANSFORMER_TYPE, Frostiful.id(name), type);
    }

    private FBlockTransformerTypes() {

    }
}