package com.github.thedeathlycow.frostiful.block.transformer;

import com.github.thedeathlycow.frostiful.registry.FBlockTransformerTypes;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public final class IdentityBlockTransformer implements BlockTransformer {
    static final IdentityBlockTransformer INSTANCE = new IdentityBlockTransformer();

    public static final MapCodec<IdentityBlockTransformer> CODEC = MapCodec.unit(() -> INSTANCE);

    @Override
    public Optional<BlockState> transformBlockState(ServerLevel level, BlockPos pos, BlockState original) {
        return Optional.of(original);
    }

    @Override
    public Type<IdentityBlockTransformer> getType() {
        return FBlockTransformerTypes.IDENTITY;
    }

    private IdentityBlockTransformer() {

    }
}