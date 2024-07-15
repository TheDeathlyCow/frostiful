package com.github.thedeathlycow.frostiful.world.gen.feature.coveredrock;

import com.github.thedeathlycow.frostiful.registry.tag.FBlockTags;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Optional;


public class CoveredRockFeature extends Feature<CoveredRockFeatureConfig> {
    public CoveredRockFeature(Codec<CoveredRockFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<CoveredRockFeatureConfig> context) {
        Optional<BlockPos> origin = this.lookForGround(context);
        if (origin.isPresent()) {
            BlockPos placeAt = origin.get();
            Random random = context.getRandom();
            for (int i = 0; i < 3; i++) {
                this.placeRock(context, placeAt);
                int delta = 2;
                placeAt = placeAt.add(-1 + random.nextInt(delta), -random.nextInt(delta), -1 + random.nextInt(delta));
            }
            return true;
        }
        return false;
    }

    private void placeRock(FeatureContext<CoveredRockFeatureConfig> context, BlockPos origin) {
        StructureWorldAccess world = context.getWorld();
        Random random = context.getRandom();
        CoveredRockFeatureConfig config = context.getConfig();

        // generate rock
        int dx = config.size().sizeX().get(random);
        int dy = config.size().sizeY().get(random);
        int dz = config.size().sizeZ().get(random);
        final float maxSquareDistance = MathHelper.square((dx + dy + dz) / 3.f + 0.5f);
        BlockPos from = origin.add(-dx, -dy, -dz);
        BlockPos to = origin.add(dx, dy == 0 ? 1 : dy, dz);

        for (BlockPos pos : BlockPos.iterate(from, to)) {
            BlockState current = world.getBlockState(pos);
            if (pos.getSquaredDistance(origin) < maxSquareDistance && !current.isIn(FBlockTags.COVERED_ROCKS_CANNOT_REPLACE)) {
                BlockState baseState = config.base().get(random, pos);
                world.setBlockState(pos, baseState, Block.NOTIFY_ALL);
            }
        }

        // cover rock
        from = from.add(-1, -1, -1);
        to = to.add(1, 1, 1);

        for (BlockPos pos : BlockPos.iterate(from, to)) {
            BlockState current = world.getBlockState(pos);
            if (this.isCoveringReplaceable(current) && random.nextFloat() < config.placeCoveringChance()) {
                this.tryPlaceCovering(context, pos);
            }
        }
    }

    private void tryPlaceCovering(FeatureContext<CoveredRockFeatureConfig> context, BlockPos origin) {
        CoveredRockFeatureConfig config = context.getConfig();
        StructureWorldAccess world = context.getWorld();
        Random random = context.getRandom();

        config.coveringFeature().value()
                .generateUnregistered(
                        world,
                        context.getGenerator(),
                        random,
                        origin
                );
    }

    private Optional<BlockPos> lookForGround(FeatureContext<CoveredRockFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        for (BlockPos current = context.getOrigin(); current.getY() > world.getBottomY() + 3; current = current.down()) {
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

    private boolean isCoveringReplaceable(BlockState state) {
        return state.isAir() || state.isIn(FBlockTags.COVERED_ROCK_COVERING_REPLACEABLE);
    }
}
