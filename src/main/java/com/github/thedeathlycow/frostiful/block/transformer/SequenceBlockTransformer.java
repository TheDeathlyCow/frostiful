package com.github.thedeathlycow.frostiful.block.transformer;

import com.github.thedeathlycow.frostiful.registry.FBlockTransformerTypes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public record SequenceBlockTransformer(
        List<BlockTransformer> transformers
) implements BlockTransformer {
    public static final MapCodec<SequenceBlockTransformer> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    BlockTransformer.ELEMENT_CODEC
                            .listOf()
                            .fieldOf("transformers")
                            .forGetter(SequenceBlockTransformer::transformers)
            ).apply(instance, SequenceBlockTransformer::new)
    );

    @Override
    public BlockState transformBlockState(ServerLevel level, BlockPos pos, BlockState original) {
        BlockState transformed = original;

        for (BlockTransformer transformer : transformers) {
            transformed = transformer.transformBlockState(level, pos, transformed);
        }

        return transformed;
    }

    @Override
    public Type<SequenceBlockTransformer> getType() {
        return FBlockTransformerTypes.SEQUENCE;
    }
}