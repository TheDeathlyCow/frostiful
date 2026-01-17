package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.block.transformer.*;
import net.minecraft.core.Registry;

public final class FBlockTransformerTypes {
    public static final BlockTransformer.Type<FirstOfBlockTransformer> FIRST_OF = register("first_of", new BlockTransformer.Type<>(FirstOfBlockTransformer.CODEC));
    public static final BlockTransformer.Type<IdentityBlockTransformer> IDENTITY = register("identity", new BlockTransformer.Type<>(IdentityBlockTransformer.CODEC));
    public static final BlockTransformer.Type<IfBlockTransformer> IF_BLOCK = register("if_block", new BlockTransformer.Type<>(IfBlockTransformer.CODEC));
    public static final BlockTransformer.Type<IfFluidTransformer> IF_FLUID = register("if_fluid", new BlockTransformer.Type<>(IfFluidTransformer.CODEC));
    public static final BlockTransformer.Type<SimpleBlockTransformer> SIMPLE_BLOCK = register("simple_block", new BlockTransformer.Type<>(SimpleBlockTransformer.CODEC));
    public static final BlockTransformer.Type<FreezeTorchTransformer> FREEZE_TORCH = register("freeze_torch", new BlockTransformer.Type<>(FreezeTorchTransformer.CODEC));
    public static final BlockTransformer.Type<ExtinguishFlameIfFireTransformer> EXTINGUISH_FLAME_IF_FIRE = register("extinguish_flame_if_fire", new BlockTransformer.Type<>(ExtinguishFlameIfFireTransformer.CODEC));

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized frostiful block transformers");
    }

    private static <T extends BlockTransformer> BlockTransformer.Type<T> register(String name, BlockTransformer.Type<T> type) {
        return Registry.register(FrostifulRegistries.BLOCK_TRANSFORMER_TYPE, Frostiful.id(name), type);
    }

    private FBlockTransformerTypes() {

    }
}