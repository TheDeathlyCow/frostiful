package com.github.thedeathlycow.frostiful.world.gen.feature;

import com.github.thedeathlycow.frostiful.block.IcicleHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.jetbrains.annotations.Nullable;

public class IcicleFeature extends Feature<IcicleFeature.IcicleFeatureConfig> {

    public IcicleFeature(Codec<IcicleFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<IcicleFeatureConfig> context) {

        // set up variables
        StructureWorldAccess worldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        Random random = context.getRandom();
        IcicleFeatureConfig config = context.getConfig();

        // pick a random direction
        Direction direction = pickDirection(worldAccess, blockPos, random);
        if (direction == null) {
            return false;
        }

        // place base blocks
        BlockPos offset = blockPos.offset(direction.getOpposite());
        generatePackedIceBase(worldAccess, random, offset, config);
        int i = random.nextFloat() < config.chanceOfTallerIcicle
                && IcicleHelper.canGenerate(worldAccess.getBlockState(blockPos.offset(direction)))
                ? 2
                : 1;

        // place icicles
        IcicleHelper.generateIcicle(worldAccess, blockPos, direction, i, false);

        return true;
    }

    @Nullable
    private static Direction pickDirection(WorldAccess world, BlockPos pos, Random random) {
        boolean canPlaceUp = IcicleHelper.canReplace(world.getBlockState(pos.up()));
        boolean canPlaceDown = IcicleHelper.canReplace(world.getBlockState(pos.down()));
        if (canPlaceUp && canPlaceDown) {
            return random.nextBoolean() ? Direction.DOWN : Direction.UP;
        }
        if (canPlaceUp) {
            return Direction.DOWN;
        }
        if (canPlaceDown) {
            return Direction.UP;
        }
        return null;
    }

    private static void generatePackedIceBase(WorldAccess world, Random random, BlockPos pos, IcicleFeatureConfig config) {
        IcicleHelper.generateIceBaseBlock(world, pos);
        for (Direction direction : Direction.Type.HORIZONTAL) {
            if (random.nextFloat() > config.chanceOfDirectionalSpread) {
                continue;
            }

            BlockPos blockPos = pos.offset(direction);
            IcicleHelper.generateIceBaseBlock(world, blockPos);

            if (random.nextFloat() > config.chanceOfSpreadRadius2) {
                continue;
            }

            BlockPos blockPos2 = blockPos.offset(Direction.random(random));
            IcicleHelper.generateIceBaseBlock(world, blockPos2);

            if (random.nextFloat() > config.chanceOfSpreadRadius3) {
                continue;
            }

            BlockPos blockPos3 = blockPos2.offset(Direction.random(random));
            IcicleHelper.generateIceBaseBlock(world, blockPos3);
        }
    }

    public record IcicleFeatureConfig(
            float chanceOfTallerIcicle,
            float chanceOfDirectionalSpread,
            float chanceOfSpreadRadius2,
            float chanceOfSpreadRadius3
    ) implements FeatureConfig {
        public static final Codec<IcicleFeatureConfig> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        Codec.floatRange(0.0f, 1.0f)
                                .fieldOf("chance_of_taller_icicle")
                                .orElse(0.2f)
                                .forGetter(config -> config.chanceOfTallerIcicle),
                        Codec.floatRange(0.0f, 1.0f)
                                .fieldOf("chance_of_directional_spread")
                                .orElse(0.7f)
                                .forGetter(config -> config.chanceOfDirectionalSpread),
                        Codec.floatRange(0.0f, 1.0f)
                                .fieldOf("chance_of_spread_radius2")
                                .orElse(0.5f)
                                .forGetter(config -> config.chanceOfSpreadRadius2),
                        Codec.floatRange(0.0f, 1.0f).fieldOf("chance_of_spread_radius3")
                                .orElse(0.5f)
                                .forGetter(config -> config.chanceOfSpreadRadius3)
                ).apply(instance, IcicleFeatureConfig::new)
        );

    }

}
