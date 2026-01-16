package com.github.thedeathlycow.frostiful.block.transformer;

import com.github.thedeathlycow.frostiful.registry.FBlockTransformerTypes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.criterion.BlockPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record SimpleBlockTransformer(
        BlockPredicate predicate,
        BlockStateProvider stateProvider
) implements BlockTransformer {
    public static final MapCodec<SimpleBlockTransformer> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    BlockPredicate.CODEC
                            .fieldOf("predicate")
                            .forGetter(SimpleBlockTransformer::predicate),
                    BlockStateProvider.CODEC
                            .fieldOf("state_provider")
                            .forGetter(SimpleBlockTransformer::stateProvider)
            ).apply(instance, SimpleBlockTransformer::new)
    );

    @Override
    public BlockState transformBlockState(ServerLevel level, BlockPos pos, BlockState original) {
        if (this.predicate.matches(level, pos)) {
            stateProvider.getState(level.getRandom(), pos);
        }

        return original;
    }

    @Override
    public Type<SimpleBlockTransformer> getType() {
        return FBlockTransformerTypes.SIMPLE_BLOCK;
    }
}