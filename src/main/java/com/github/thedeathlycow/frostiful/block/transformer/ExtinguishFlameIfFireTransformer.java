package com.github.thedeathlycow.frostiful.block.transformer;

import com.github.thedeathlycow.frostiful.registry.FBlockTransformerTypes;
import com.github.thedeathlycow.frostiful.registry.tag.FBlockTags;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public record ExtinguishFlameIfFireTransformer(
        BlockTransformer notFire
) implements BlockTransformer {
    public static final MapCodec<ExtinguishFlameIfFireTransformer> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    BlockTransformer.ELEMENT_CODEC
                            .fieldOf("not_fire")
                            .forGetter(ExtinguishFlameIfFireTransformer::notFire)
            ).apply(instance, ExtinguishFlameIfFireTransformer::new)
    );

    public static ExtinguishFlameIfFireTransformer of(BlockTransformer notFire) {
        return new ExtinguishFlameIfFireTransformer(notFire);
    }

    @Override
    public BlockState transformBlockState(ServerLevel level, BlockPos pos, BlockState original) {
        if (original.is(FBlockTags.IS_OPEN_FLAME)) {
            return original.getFluidState().createLegacyBlock();
        } else if (
                original.is(FBlockTags.HAS_OPEN_FLAME)
                        && original.hasProperty(BlockStateProperties.LIT)
                        && original.getValue(BlockStateProperties.LIT)
        ) {
            return original.setValue(BlockStateProperties.LIT, false);
        } else {
            return this.notFire().transformBlockState(level, pos, original);
        }
    }

    @Override
    public Type<ExtinguishFlameIfFireTransformer> getType() {
        return FBlockTransformerTypes.EXTINGUISH_FLAME_IF_FIRE;
    }
}