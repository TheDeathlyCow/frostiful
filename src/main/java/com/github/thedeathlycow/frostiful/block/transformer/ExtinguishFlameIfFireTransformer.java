package com.github.thedeathlycow.frostiful.block.transformer;

import com.github.thedeathlycow.frostiful.registry.FBlockTransformerTypes;
import com.github.thedeathlycow.frostiful.registry.tag.FBlockTags;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Optional;

public final class ExtinguishFlameIfFireTransformer implements BlockTransformer {
    private static final ExtinguishFlameIfFireTransformer INSTANCE = new ExtinguishFlameIfFireTransformer();

    public static final MapCodec<ExtinguishFlameIfFireTransformer> CODEC = MapCodec.unit(() -> INSTANCE);

    public static ExtinguishFlameIfFireTransformer of() {
        return INSTANCE;
    }

    @Override
    public Optional<BlockState> tryTransformBlockState(ServerLevel level, BlockPos pos, BlockState original) {
        if (original.is(FBlockTags.IS_OPEN_FLAME)) {
            return Optional.of(original.getFluidState().createLegacyBlock());
        } else if (
                original.is(FBlockTags.HAS_OPEN_FLAME)
                        && original.hasProperty(BlockStateProperties.LIT)
                        && original.getValue(BlockStateProperties.LIT)
        ) {
            return Optional.of(original.setValue(BlockStateProperties.LIT, false));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Type<ExtinguishFlameIfFireTransformer> getType() {
        return FBlockTransformerTypes.EXTINGUISH_FLAME_IF_FIRE;
    }

    private ExtinguishFlameIfFireTransformer() {

    }
}