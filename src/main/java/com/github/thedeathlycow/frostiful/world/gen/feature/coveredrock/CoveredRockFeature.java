package com.github.thedeathlycow.frostiful.world.gen.feature.coveredrock;

import com.github.thedeathlycow.frostiful.tag.blocks.FrostifulBlockTags;
import com.mojang.serialization.Codec;
import net.minecraft.block.AbstractLichenBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.UndergroundConfiguredFeatures;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Random;

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
        BlockPos from = origin.add(-dx, -dy, -dz);
        BlockPos to = origin.add(dx, dy, dz);

        for (BlockPos pos : BlockPos.iterate(from, to)) {
            BlockState current = world.getBlockState(pos);
            if (!current.isIn(FrostifulBlockTags.COVERED_ROCKS_CANNOT_REPLACE)) {
                BlockState baseState = config.base().getBlockState(random, pos);
                world.setBlockState(pos, baseState, Block.NOTIFY_ALL);
            }
        }

        // cover rock
        from = from.add(-1, -1, -1);
        to = to.add(1, 1, 1);

        for (BlockPos pos : BlockPos.iterate(from, to)) {
            BlockState current = world.getBlockState(pos);
            if (this.isAirOrWaterOrSnow(current) && random.nextFloat() < config.placeCoveringChance()) {
                this.placeCovering(context, pos);
            }
        }
    }

    private void placeCovering(FeatureContext<CoveredRockFeatureConfig> context, BlockPos origin) {
        BlockPos.Mutable placingOnPos = origin.mutableCopy();
        CoveredRockFeatureConfig config = context.getConfig();
        StructureWorldAccess world = context.getWorld();
        for (Direction direction : Direction.values()) {
            BlockState state = context.getWorld().getBlockState(placingOnPos.set(origin, direction));
            if (state.isIn(config.canPlaceOn())) {
                this.setCoveringBlockState(config, context.getRandom(), world, origin, direction);
            }
        }
    }

    private void setCoveringBlockState(CoveredRockFeatureConfig config, Random random, StructureWorldAccess world, BlockPos placeAt, Direction direction) {
        BlockState covering = config.covering().getBlockState(random, placeAt);
        AbstractLichenBlock lichenBlock = null;
        if (covering.getBlock() instanceof AbstractLichenBlock) {
            lichenBlock = (AbstractLichenBlock) covering.getBlock();
            covering = lichenBlock.withDirection(covering, world, placeAt, direction);
        }
        world.setBlockState(placeAt, covering, Block.NOTIFY_ALL);

        if (lichenBlock != null) {
            lichenBlock.trySpreadRandomly(covering, world, placeAt, direction, random, true);
        }
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

    private boolean isAirOrWaterOrSnow(BlockState state) {
        return state.isAir() || state.isOf(Blocks.WATER) || state.isOf(Blocks.SNOW);
    }
}
