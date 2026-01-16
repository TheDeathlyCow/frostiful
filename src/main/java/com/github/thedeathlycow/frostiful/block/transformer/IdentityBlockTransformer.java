package com.github.thedeathlycow.frostiful.block.transformer;

import com.github.thedeathlycow.frostiful.registry.FBlockTransformerTypes;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

public final class IdentityBlockTransformer implements BlockTransformer {
    public static final IdentityBlockTransformer INSTANCE = new IdentityBlockTransformer();

    public static final MapCodec<IdentityBlockTransformer> CODEC = MapCodec.unit(() -> INSTANCE);

    @Override
    public BlockState transformBlockState(ServerLevel level, BlockPos pos, BlockState original) {
        return original;
    }

    @Override
    public Type<IdentityBlockTransformer> getType() {
        return FBlockTransformerTypes.IDENTITY;
    }

    private IdentityBlockTransformer() {

    }
}