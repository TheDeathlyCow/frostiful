package com.github.thedeathlycow.frostiful.world.gen.feature.coveredrock;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class CoveredRockFeature extends Feature<CoveredRockFeatureConfig> {
    public CoveredRockFeature(Codec<CoveredRockFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<CoveredRockFeatureConfig> context) {
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess world = context.getWorld();
        Random random = context.getRandom();
        CoveredRockFeatureConfig config = context.getConfig();

        int dx = config.size().get(random);
        int dz = config.size().get(random);
        int dy = config.size().get(random);
        BlockPos from = blockPos.add(-dx, -dy, -dz);
        BlockPos to = blockPos.add(dx, dy, dz);


        for (BlockPos current : BlockPos.iterate(from, to)) {
            BlockState baseState = config.base().getBlockState(random, current);
            world.setBlockState(current, baseState, Block.NOTIFY_ALL);
        }

        return true;
    }
}
