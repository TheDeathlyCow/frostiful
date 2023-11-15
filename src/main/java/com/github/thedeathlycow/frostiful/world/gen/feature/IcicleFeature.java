package com.github.thedeathlycow.frostiful.world.gen.feature;

import com.github.thedeathlycow.frostiful.block.IcicleHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.floatprovider.FloatProvider;
import net.minecraft.util.math.floatprovider.UniformFloatProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.util.CaveSurface;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Optional;
import java.util.OptionalInt;

public class IcicleFeature extends Feature<IcicleFeature.IcicleFeatureConfig> {

    public IcicleFeature(Codec<IcicleFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<IcicleFeatureConfig> context) {

        // set up variables
        StructureWorldAccess worldAccess = context.getWorld();
        BlockPos origin = context.getOrigin();
        Random random = context.getRandom();
        IcicleFeatureConfig config = context.getConfig();

        // read radii from config
        int xRadius = config.radius.get(random);
        int zRadius = config.radius.get(random);

        // generate
        generate(worldAccess, random, origin, xRadius, zRadius, config);

        return true;
    }

    private void generate(
            WorldAccess world,
            Random random,
            BlockPos origin,
            int xRadius,
            int zRadius,
            IcicleFeatureConfig config
    ) {
        BlockPos pos;
        float ceilingDensity = config.density.get(random);
        float floorDensity = config.density.get(random);

        for (int x = -xRadius; x < xRadius; x++) {
            for (int z = -zRadius; z < zRadius; z++) {
                pos = origin.add(x, 0, z);
                generateColumn(world, random, pos, ceilingDensity, floorDensity, config);
            }
        }
    }

    private void generateColumn(
            WorldAccess world,
            Random random,
            BlockPos position,
            float ceilingDensity,
            float floorDensity,
            IcicleFeatureConfig config
    ) {

        // get surface heights
        Optional<CaveSurface> caveSurfaceResult = CaveSurface.create(
                world,
                position,
                config.floorToCeilingSearchRange,
                IcicleHelper::canGenerate,
                IcicleHelper::canReplace
        );
        if (caveSurfaceResult.isEmpty()) {
            return;
        }
        CaveSurface surface = caveSurfaceResult.get();
        OptionalInt ceilingHeight = surface.getCeilingHeight();
        OptionalInt floorHeight = surface.getFloorHeight();
        if (ceilingHeight.isEmpty() && floorHeight.isEmpty()) {
            return;
        }

        // build ice layers
        if (ceilingHeight.isPresent() && random.nextDouble() < ceilingDensity && !this.isLava(world, position.withY(ceilingHeight.getAsInt()))) {
            int thickness = config.packedIceBlockLayerThickness.get(random);
            this.placePackedIceBlocks(world, position.withY(ceilingHeight.getAsInt()), thickness, Direction.UP);
        }

        if (floorHeight.isPresent() && random.nextDouble() < floorDensity && !this.isLava(world, position.withY(floorHeight.getAsInt()))) {
            int thickness = config.packedIceBlockLayerThickness.get(random);
            this.placePackedIceBlocks(world, position.withY(floorHeight.getAsInt()), thickness, Direction.DOWN);
        }

        // place icicles
        int floorIcicleLength = config.icicleHeight.get(random);
        int ceilingIcicleLength = config.icicleHeight.get(random);
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

        if (ceilingHeight.isPresent() && random.nextDouble() < ceilingDensity) {
            IcicleHelper.generateIcicle(
                    world,
                    position.withY(ceilingHeight.getAsInt() - 1),
                    Direction.DOWN,
                    ceilingIcicleLength,
                    merge
            );
        }
        if (floorHeight.isPresent() && random.nextDouble() < floorDensity) {
            IcicleHelper.generateIcicle(
                    world,
                    position.withY(floorHeight.getAsInt() + 1),
                    Direction.UP,
                    floorIcicleLength,
                    merge
            );
        }
    }

    private boolean isLava(WorldView world, BlockPos pos) {
        return world.getBlockState(pos).isOf(Blocks.LAVA);
    }

    private void placePackedIceBlocks(WorldAccess world, BlockPos pos, int thickness, Direction direction) {
        BlockPos.Mutable position = pos.mutableCopy();
        for (int i = 0; i < thickness; i++) {
            if (!IcicleHelper.generateIceBaseBlock(world, position)) {
                return;
            }
            position.move(direction);
        }
    }

    public record IcicleFeatureConfig(
            IntProvider icicleHeight,
            IntProvider radius,
            FloatProvider density,
            int floorToCeilingSearchRange,
            IntProvider packedIceBlockLayerThickness
    ) implements FeatureConfig {
        public static final Codec<IcicleFeatureConfig> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        IntProvider.createValidatingCodec(0, 10)
                                .fieldOf("icicle_height")
                                .orElse(UniformIntProvider.create(1, 6))
                                .forGetter(config -> config.icicleHeight),
                        IntProvider.createValidatingCodec(1, 32)
                                .fieldOf("radius")
                                .orElse(UniformIntProvider.create(2, 8))
                                .forGetter(config -> config.radius),
                        FloatProvider.createValidatedCodec(0f, 1f)
                                .fieldOf("density")
                                .orElse(UniformFloatProvider.create(0.3f, 0.7f))
                                .forGetter(config -> config.density),
                        Codec.intRange(1, 32)
                                .fieldOf("floor_to_ceiling_search_range")
                                .orElse(12)
                                .forGetter(config -> config.floorToCeilingSearchRange),
                        IntProvider.createValidatingCodec(1, 32)
                                .fieldOf("packed_ice_layer_thickness")
                                .orElse(UniformIntProvider.create(1, 4))
                                .forGetter(config -> config.packedIceBlockLayerThickness)
                ).apply(instance, IcicleFeatureConfig::new)
        );

    }

}
