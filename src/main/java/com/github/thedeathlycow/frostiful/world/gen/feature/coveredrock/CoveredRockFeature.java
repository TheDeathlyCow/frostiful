package com.github.thedeathlycow.frostiful.world.gen.feature.coveredrock;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.ForestRockFeature;
import net.minecraft.world.gen.feature.SingleStateFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.Random;

public class CoveredRockFeature extends Feature<CoveredRockFeatureConfig> {
    public CoveredRockFeature(Codec<CoveredRockFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<CoveredRockFeatureConfig> context) {
        Optional<BlockPos> origin = scan(context);
        if (origin.isPresent()) {
            return place(context, origin.get());
        }
        return false;
    }

    private boolean place(FeatureContext<CoveredRockFeatureConfig> context, BlockPos origin) {
        StructureWorldAccess world = context.getWorld();
        Random random = context.getRandom();
        CoveredRockFeatureConfig config = context.getConfig();

        int dx = config.size().get(random);
        int dz = config.size().get(random);
        int dy = config.size().get(random);
        BlockPos from = origin.add(-dx, -1, -dz);
        BlockPos to = origin.add(dx, dy, dz);

        for (BlockPos pos : BlockPos.iterate(from, to)) {

            BlockState current = world.getBlockState(pos);
            if (!current.isIn(BlockTags.FEATURES_CANNOT_REPLACE)) {
                BlockState baseState = config.base().getBlockState(random, pos);
                world.setBlockState(pos, baseState, Block.NOTIFY_ALL);
            }
        }

        return true;
    }

    private Optional<BlockPos> scan(FeatureContext<CoveredRockFeatureConfig> context) {

        StructureWorldAccess world = context.getWorld();
        for(BlockPos current = context.getOrigin(); current.getY() > world.getBottomY() + 3; current = current.down()) {
            if (!world.isAir(current.down()) && canPlaceAtPos(world, current)) {
                return Optional.of(current);
            }
        }

        return Optional.empty();
    }


    private boolean canPlaceAtPos(StructureWorldAccess world, BlockPos pos) {
        BlockState below = world.getBlockState(pos);
        return isSoil(below) || isStone(below);
    }
}
