package com.github.thedeathlycow.frostiful.block.transformer;

import com.github.thedeathlycow.frostiful.registry.FBlockTransformerTypes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.criterion.FluidPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public record IfFluidTransformer(
        FluidPredicate predicate,
        BlockTransformer whenTrue,
        Optional<BlockTransformer> whenFalse
) implements BlockTransformer {
    public static final MapCodec<IfFluidTransformer> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    FluidPredicate.CODEC
                            .fieldOf("predicate")
                            .forGetter(IfFluidTransformer::predicate),
                    BlockTransformer.ELEMENT_CODEC
                            .fieldOf("when_true")
                            .forGetter(IfFluidTransformer::whenTrue),
                    BlockTransformer.ELEMENT_CODEC
                            .optionalFieldOf("when_false")
                            .forGetter(IfFluidTransformer::whenFalse)
            ).apply(instance, IfFluidTransformer::new)
    );

    @Override
    public Optional<BlockState> transformBlockState(ServerLevel level, BlockPos pos, BlockState original) {
        if (this.predicate.matches(level, pos)) {
            return this.whenTrue.transformBlockState(level, pos, original);
        } else if (this.whenFalse.isPresent()) {
            return this.whenFalse.orElseThrow().transformBlockState(level, pos, original);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Type<IfFluidTransformer> getType() {
        return FBlockTransformerTypes.IF_FLUID;
    }

    public static IfFluidTransformer ifFluid(FluidPredicate.Builder predicate, BlockTransformer whenTrue) {
        return new IfFluidTransformer(
                predicate.build(),
                whenTrue,
                Optional.empty()
        );
    }

    public static IfFluidTransformer ifFluidOrElse(FluidPredicate.Builder predicate, BlockTransformer whenTrue, BlockTransformer whenFalse) {
        return new IfFluidTransformer(
                predicate.build(),
                whenTrue,
                Optional.of(whenFalse)
        );
    }
}