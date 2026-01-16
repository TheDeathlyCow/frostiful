package com.github.thedeathlycow.frostiful.block.transformer;

import com.github.thedeathlycow.frostiful.registry.FBlockTransformerTypes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.Optional;

public record SimpleBlockTransformer(
        BlockStateProvider state
) implements BlockTransformer{
    public static final MapCodec<SimpleBlockTransformer> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    BlockStateProvider.CODEC
                            .fieldOf("state")
                            .forGetter(SimpleBlockTransformer::state)
            ).apply(instance, SimpleBlockTransformer::new)
    );

    @Override
    public Optional<BlockState> tryTransformBlockState(ServerLevel level, BlockPos pos, BlockState original) {
        return Optional.of(this.state.getState(level.getRandom(), pos));
    }

    @Override
    public Type<SimpleBlockTransformer> getType() {
        return FBlockTransformerTypes.SIMPLE_BLOCK;
    }
}