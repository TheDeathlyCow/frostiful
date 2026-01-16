package com.github.thedeathlycow.frostiful.block.transformer;

import com.github.thedeathlycow.frostiful.registry.FBlockTransformerTypes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public record FirstOfBlockTransformer(
        List<BlockTransformer> transformers
) implements BlockTransformer {
    public static final MapCodec<FirstOfBlockTransformer> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    BlockTransformer.ELEMENT_CODEC
                            .listOf()
                            .fieldOf("transformers")
                            .forGetter(FirstOfBlockTransformer::transformers)
            ).apply(instance, FirstOfBlockTransformer::new)
    );

    public static FirstOfBlockTransformer of(List<BlockTransformer> transformers) {
        return new FirstOfBlockTransformer(transformers);
    }

    public static FirstOfBlockTransformer of(BlockTransformer... transformers) {
        return of(Arrays.asList(transformers));
    }

    @Override
    public Optional<BlockState> transformBlockState(ServerLevel level, BlockPos pos, BlockState original) {
        for (BlockTransformer transformer : transformers) {
            Optional<BlockState> result = transformer.transformBlockState(level, pos, original);
            if (result.isPresent()) {
                return result;
            }
        }

        return Optional.empty();
    }

    @Override
    public Type<FirstOfBlockTransformer> getType() {
        return FBlockTransformerTypes.FIRST_OF;
    }
}