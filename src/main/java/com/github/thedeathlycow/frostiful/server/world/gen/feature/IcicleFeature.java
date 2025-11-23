package com.github.thedeathlycow.frostiful.server.world.gen.feature;

import com.github.thedeathlycow.frostiful.block.IcicleHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.OptionalInt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ClampedNormalFloat;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class IcicleFeature extends Feature<IcicleFeature.IcicleFeatureConfig> {

    public IcicleFeature(Codec<IcicleFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<IcicleFeatureConfig> context) {

        // set up variables
        WorldGenLevel worldAccess = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();
        IcicleFeatureConfig config = context.config();

        // read radii from config
        int xRadius = config.radius.sample(random);
        int zRadius = config.radius.sample(random);

        float density = config.density.sample(random);
        int icicleHeight = config.icicleHeight.sample(random);

        // generate
        generate(worldAccess, random, origin, xRadius, zRadius, density, icicleHeight, config);

        return true;
    }

    private void generate(
            LevelAccessor world,
            RandomSource random,
            BlockPos origin,
            int xRadius,
            int zRadius,
            float density,
            int icicleHeight,
            IcicleFeatureConfig config
    ) {
        BlockPos pos;

        for (int x = -xRadius; x < xRadius; x++) {
            for (int z = -zRadius; z < zRadius; z++) {
                pos = origin.offset(x, 0, z);
                double icicleChance = this.icicleChance(xRadius, zRadius, x, z, config);
                generateColumn(world, random, pos, x, z, density, icicleChance, icicleHeight, config);
            }
        }
    }

    private void generateColumn(
            LevelAccessor world,
            RandomSource random,
            BlockPos position,
            int localX, int localZ,
            float density,
            double icicleChance,
            int icicleHeight,
            IcicleFeatureConfig config
    ) {

        // get surface heights
        Optional<Column> caveSurfaceResult = Column.scan(
                world,
                position,
                config.floorToCeilingSearchRange,
                IcicleHelper::canGenerate,
                IcicleHelper::canReplace
        );
        if (caveSurfaceResult.isEmpty()) {
            return;
        }
        Column surface = caveSurfaceResult.get();
        OptionalInt ceilingHeight = surface.getCeiling();
        OptionalInt floorHeight = surface.getFloor();
        if (ceilingHeight.isEmpty() && floorHeight.isEmpty()) {
            return;
        }

        boolean shouldGenerateCeiling = random.nextDouble() < icicleChance;
        boolean shouldGenerateFloor = random.nextDouble() < icicleChance;


        int floorIcicleLength = 0;
        int ceilingIcicleLength = 0;


        // build ice layers
        if (ceilingHeight.isPresent() && shouldGenerateCeiling && !this.isLava(world, position.atY(ceilingHeight.getAsInt()))) {
            int thickness = config.packedIceBlockLayerThickness.sample(random);
            this.placePackedIceBlocks(world, position.atY(ceilingHeight.getAsInt()), thickness, Direction.UP);
            ceilingIcicleLength = getHeight(
                    random,
                    localX, localZ,
                    density,
                    icicleHeight,
                    config
            );
        }

        if (floorHeight.isPresent() && shouldGenerateFloor && !this.isLava(world, position.atY(floorHeight.getAsInt()))) {
            int thickness = config.packedIceBlockLayerThickness.sample(random);
            this.placePackedIceBlocks(world, position.atY(floorHeight.getAsInt()), thickness, Direction.DOWN);
            floorIcicleLength = getHeight(
                    random,
                    localX, localZ,
                    density,
                    icicleHeight,
                    config
            );
        }

        // place icicles
        boolean merge = false;

        // adjust icicle heights if they would intersect with each other or terrain
        if (floorHeight.isPresent() && ceilingHeight.isPresent()) {
            int floorY = floorHeight.getAsInt();
            int ceilY = ceilingHeight.getAsInt();

            // number of air blocks between floor and ceiling
            // the -1 removes the stone blocks at the extrema
            int span = ceilY - floorY - 1;

            int iciclesLength = floorIcicleLength + ceilingIcicleLength;
            // icicles will intersect if and only if their summed lengths exceed the available space
            if (iciclesLength > span) {
                floorIcicleLength = span / 2;
                ceilingIcicleLength = floorIcicleLength + (span % 2);
                merge = floorIcicleLength > 0 && ceilingIcicleLength > 0 && random.nextBoolean();
            }
        }

        // place icicles
        if (ceilingHeight.isPresent() && shouldGenerateCeiling && ceilingIcicleLength > 0) {
            IcicleHelper.generateIcicle(
                    world,
                    position.atY(ceilingHeight.getAsInt() - 1),
                    Direction.DOWN,
                    ceilingIcicleLength,
                    merge
            );
        }
        if (floorHeight.isPresent() && shouldGenerateFloor && floorIcicleLength > 0) {
            IcicleHelper.generateIcicle(
                    world,
                    position.atY(floorHeight.getAsInt() + 1),
                    Direction.UP,
                    floorIcicleLength,
                    merge
            );
        }
    }

    private boolean isLava(LevelReader world, BlockPos pos) {
        return world.getBlockState(pos).is(Blocks.LAVA);
    }

    private void placePackedIceBlocks(LevelAccessor world, BlockPos pos, int thickness, Direction direction) {
        BlockPos.MutableBlockPos position = pos.mutable();
        for (int i = 0; i < thickness; i++) {
            if (!IcicleHelper.generateIceBaseBlock(world, position)) {
                return;
            }
            position.move(direction);
        }
    }

    private int getHeight(RandomSource random, int localX, int localZ, float density, int maxHeight, IcicleFeatureConfig config) {
        if (random.nextFloat() > density) {
            return 0;
        }
        int absoluteDistanceManhattan = Math.abs(localX) + Math.abs(localZ);
        float mean = Mth.clampedMap(
                absoluteDistanceManhattan,
                1.0f,
                config.maxDistanceFromCenterAffectingHeightBias,
                maxHeight / 2.0f,
                1.0f
        );
        return (int) clampedGaussian(random, 1.0f, maxHeight, mean, config.heightDeviation);
    }

    private static float clampedGaussian(RandomSource random, float min, float max, float mean, float deviation) {
        return ClampedNormalFloat.sample(random, mean, deviation, min, max);
    }

    private double icicleChance(int radiusX, int radiusZ, int localX, int localZ, IcicleFeatureConfig config) {
        int distanceToEdgeX = radiusX - Math.abs(localX);
        int distanceToEdgeZ = radiusZ - Math.abs(localZ);
        int minDistanceToEdge = Math.min(distanceToEdgeX, distanceToEdgeZ);
        return Mth.clampedMap(
                minDistanceToEdge,
                0.0, IcicleFeatureConfig.MAX_DIST_FROM_CENTER_AFFECTING_COLUMN_CHANCE,
                IcicleFeatureConfig.CHANCE_OF_COLUMN_AT_MAX_DISTANCE, 1.0
        );
    }

    public record IcicleFeatureConfig(
            IntProvider icicleHeight,
            IntProvider radius,
            FloatProvider density,
            int floorToCeilingSearchRange,
            IntProvider packedIceBlockLayerThickness,
            int heightDeviation,
            int maxDistanceFromCenterAffectingHeightBias
    ) implements FeatureConfiguration {

        private static final double MAX_DIST_FROM_CENTER_AFFECTING_COLUMN_CHANCE = 3.0;
        private static final double CHANCE_OF_COLUMN_AT_MAX_DISTANCE = 0.1;

        public static final Codec<IcicleFeatureConfig> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        IntProvider.codec(0, 10)
                                .fieldOf("icicle_height")
                                .orElse(UniformInt.of(1, 6))
                                .forGetter(config -> config.icicleHeight),
                        IntProvider.codec(1, 32)
                                .fieldOf("radius")
                                .orElse(UniformInt.of(2, 8))
                                .forGetter(config -> config.radius),
                        FloatProvider.codec(0f, 1f)
                                .fieldOf("density")
                                .orElse(UniformFloat.of(0.1f, 0.4f))
                                .forGetter(config -> config.density),
                        Codec.intRange(1, 32)
                                .fieldOf("floor_to_ceiling_search_range")
                                .orElse(12)
                                .forGetter(config -> config.floorToCeilingSearchRange),
                        IntProvider.codec(1, 32)
                                .fieldOf("packed_ice_layer_thickness")
                                .orElse(UniformInt.of(1, 4))
                                .forGetter(config -> config.packedIceBlockLayerThickness),
                        Codec.intRange(1, 64)
                                .fieldOf("height_deviation")
                                .orElse(3)
                                .forGetter(config -> config.heightDeviation),
                        Codec.intRange(1, 64)
                                .fieldOf("max_distance_from_center_affecting_height_bias")
                                .orElse(8)
                                .forGetter(config -> config.maxDistanceFromCenterAffectingHeightBias)
                ).apply(instance, IcicleFeatureConfig::new)
        );

    }

}
