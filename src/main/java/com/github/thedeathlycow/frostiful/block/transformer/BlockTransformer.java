package com.github.thedeathlycow.frostiful.block.transformer;

import com.github.thedeathlycow.frostiful.registry.FrostifulRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import org.jetbrains.annotations.NotNull;

public interface BlockTransformer {
    Codec<BlockTransformer> ELEMENT_CODEC = FrostifulRegistries.BLOCK_TRANSFORMER_TYPE.byNameCodec()
            .dispatch("type", BlockTransformer::getType, Type::codec);

    Codec<Holder<@NotNull BlockTransformer>> HOLDER_CODEC = RegistryFileCodec.create(
            FrostifulRegistries.BLOCK_TRANSFORMER_KEY,
            ELEMENT_CODEC
    );

    BlockState transformBlockState(ServerLevel level, BlockPos pos, BlockState original);

    Type<? extends BlockTransformer> getType();

    static BlockTransformer identity() {
        return IdentityBlockTransformer.INSTANCE;
    }

    static BlockTransformer simple(Block block) {
        return new SimpleBlockTransformer(BlockStateProvider.simple(block));
    }

    record Type<T extends BlockTransformer>(MapCodec<T> codec) {

    }
}