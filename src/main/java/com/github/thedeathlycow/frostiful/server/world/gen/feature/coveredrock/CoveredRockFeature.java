package com.github.thedeathlycow.frostiful.server.world.gen.feature.coveredrock;

import com.github.thedeathlycow.frostiful.registry.tag.FBlockTags;
import com.mojang.serialization.Codec;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;


public class CoveredRockFeature extends Feature<CoveredRockFeatureConfig> {
    public CoveredRockFeature(Codec<CoveredRockFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<CoveredRockFeatureConfig> context) {
        Optional<BlockPos> origin = this.lookForGround(context);
        if (origin.isPresent()) {
            BlockPos placeAt = origin.get();
            RandomSource random = context.random();
            for (int i = 0; i < 3; i++) {
                this.placeRock(context, placeAt);
                int delta = 2;
                placeAt = placeAt.offset(-1 + random.nextInt(delta), -random.nextInt(delta), -1 + random.nextInt(delta));
            }
            return true;
        }
        return false;
    }

    private void placeRock(FeaturePlaceContext<CoveredRockFeatureConfig> context, BlockPos origin) {
        WorldGenLevel world = context.level();
        RandomSource random = context.random();
        CoveredRockFeatureConfig config = context.config();

        // generate rock
        int dx = config.size().sizeX().sample(random);
        int dy = config.size().sizeY().sample(random);
        int dz = config.size().sizeZ().sample(random);
        final float maxSquareDistance = Mth.square((dx + dy + dz) / 3.f + 0.5f);
        BlockPos from = origin.offset(-dx, -dy, -dz);
        BlockPos to = origin.offset(dx, dy == 0 ? 1 : dy, dz);

        for (BlockPos pos : BlockPos.betweenClosed(from, to)) {
            BlockState current = world.getBlockState(pos);
            if (pos.distSqr(origin) < maxSquareDistance && !current.is(FBlockTags.COVERED_ROCKS_CANNOT_REPLACE)) {
                BlockState baseState = config.base().getState(random, pos);
                world.setBlock(pos, baseState, Block.UPDATE_ALL);
            }
        }

        // cover rock
        from = from.offset(-1, -1, -1);
        to = to.offset(1, 1, 1);

        for (BlockPos pos : BlockPos.betweenClosed(from, to)) {
            BlockState current = world.getBlockState(pos);
            if (this.isCoveringReplaceable(current) && random.nextFloat() < config.placeCoveringChance()) {
                this.tryPlaceCovering(context, pos);
            }
        }
    }

    private void tryPlaceCovering(FeaturePlaceContext<CoveredRockFeatureConfig> context, BlockPos origin) {
        CoveredRockFeatureConfig config = context.config();
        WorldGenLevel world = context.level();
        RandomSource random = context.random();

        config.coveringFeature().value()
                .place(
                        world,
                        context.chunkGenerator(),
                        random,
                        origin
                );
    }

    private Optional<BlockPos> lookForGround(FeaturePlaceContext<CoveredRockFeatureConfig> context) {
        WorldGenLevel world = context.level();
        for (BlockPos current = context.origin(); current.getY() > world.getMinBuildHeight() + 3; current = current.below()) {
            if (!world.isEmptyBlock(current.below()) && canPlaceAtPos(world, current)) {
                return Optional.of(current);
            }
        }
        return Optional.empty();
    }

    private boolean canPlaceAtPos(WorldGenLevel world, BlockPos pos) {
        BlockState below = world.getBlockState(pos);
        return isDirt(below) || isStone(below);
    }

    private boolean isCoveringReplaceable(BlockState state) {
        return state.isAir() || state.is(FBlockTags.COVERED_ROCK_COVERING_REPLACEABLE);
    }
}
