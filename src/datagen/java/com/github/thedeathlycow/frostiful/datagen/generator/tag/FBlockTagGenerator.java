package com.github.thedeathlycow.frostiful.datagen.generator.tag;

import com.github.thedeathlycow.frostiful.registry.FBlocks;
import com.github.thedeathlycow.frostiful.registry.tag.FBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class FBlockTagGenerator extends FabricTagProvider.BlockTagProvider {
    public FBlockTagGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        generateMinecraftTags(wrapperLookup);
        generateConventionalTags(wrapperLookup);
        generateFrostifulTags(wrapperLookup);
    }

    private void generateMinecraftTags(HolderLookup.Provider wrapperLookup) {
        valueLookupBuilder(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(FBlocks.ICICLE)
                .add(FBlocks.PACKED_SNOW)
                .add(FBlocks.PACKED_SNOW_BLOCK)
                .add(FBlocks.PACKED_SNOW_BRICKS)
                .add(FBlocks.PACKED_SNOW_BRICK_STAIRS)
                .add(FBlocks.PACKED_SNOW_BRICK_SLAB)
                .add(FBlocks.PACKED_SNOW_BRICK_WALL)
                .add(FBlocks.CUT_PACKED_ICE)
                .add(FBlocks.CUT_PACKED_ICE_SLAB)
                .add(FBlocks.CUT_PACKED_ICE_STAIRS)
                .add(FBlocks.CUT_PACKED_ICE_WALL)
                .add(FBlocks.CUT_BLUE_ICE)
                .add(FBlocks.CUT_BLUE_ICE_SLAB)
                .add(FBlocks.CUT_BLUE_ICE_STAIRS)
                .add(FBlocks.CUT_BLUE_ICE_WALL)
                .add(FBlocks.BRITTLE_ICE);

        valueLookupBuilder(BlockTags.FEATURES_CANNOT_REPLACE)
                .add(FBlocks.ICY_VAULT)
                .add(FBlocks.ICY_TRIAL_SPAWNER);

        valueLookupBuilder(BlockTags.ICE)
                .add(FBlocks.ICE_PANE)
                .add(FBlocks.CUT_PACKED_ICE)
                .add(FBlocks.CUT_PACKED_ICE_SLAB)
                .add(FBlocks.CUT_PACKED_ICE_STAIRS)
                .add(FBlocks.CUT_PACKED_ICE_WALL)
                .add(FBlocks.CUT_BLUE_ICE)
                .add(FBlocks.CUT_BLUE_ICE_SLAB)
                .add(FBlocks.CUT_BLUE_ICE_STAIRS)
                .add(FBlocks.CUT_BLUE_ICE_WALL)
                .add(FBlocks.BRITTLE_ICE);

        valueLookupBuilder(BlockTags.INSIDE_STEP_SOUND_BLOCKS)
                .addOptionalTag(FBlockTags.SUN_LICHENS);

        valueLookupBuilder(BlockTags.SLABS)
                .add(FBlocks.PACKED_SNOW_BRICK_SLAB)
                .add(FBlocks.CUT_PACKED_ICE_SLAB)
                .add(FBlocks.CUT_BLUE_ICE_SLAB);

        valueLookupBuilder(BlockTags.SNOW)
                .add(FBlocks.PACKED_SNOW)
                .add(FBlocks.PACKED_SNOW_BLOCK);

        valueLookupBuilder(BlockTags.STAIRS)
                .add(FBlocks.PACKED_SNOW_BRICK_STAIRS)
                .add(FBlocks.CUT_PACKED_ICE_STAIRS)
                .add(FBlocks.CUT_BLUE_ICE_STAIRS);

        valueLookupBuilder(BlockTags.WALL_POST_OVERRIDE)
                .add(FBlocks.FROZEN_TORCH);

        valueLookupBuilder(BlockTags.WALLS)
                .add(FBlocks.PACKED_SNOW_BRICK_WALL)
                .add(FBlocks.CUT_PACKED_ICE_WALL)
                .add(FBlocks.CUT_BLUE_ICE_WALL);
    }

    private void generateConventionalTags(HolderLookup.Provider wrapperLookup) {
        valueLookupBuilder(ConventionalBlockTags.GLASS_PANES)
                .add(FBlocks.ICE_PANE);

        valueLookupBuilder(ConventionalBlockTags.GLASS_BLOCKS_COLORLESS)
                .add(FBlocks.ICE_PANE);

        valueLookupBuilder(commonKey("icicles"))
                .add(FBlocks.ICICLE);
    }

    private void generateFrostifulTags(HolderLookup.Provider wrapperLookup) {
        valueLookupBuilder(FBlockTags.COVERED_ROCK_COVERING_REPLACEABLE)
                .addOptionalTag(FBlockTags.SUN_LICHENS)
                .add(Blocks.GLOW_LICHEN)
                .add(Blocks.SNOW);

        valueLookupBuilder(FBlockTags.COVERED_ROCKS_CANNOT_REPLACE)
                .addOptionalTag(BlockTags.FEATURES_CANNOT_REPLACE)
                .addOptionalTag(BlockTags.LOGS)
                .addOptionalTag(BlockTags.LEAVES);


        valueLookupBuilder(FBlockTags.FROSTOLOGER_CANNOT_FREEZE)
                .addOptionalTag(BlockTags.PORTALS)
                .add(Blocks.BEACON)
                .add(Blocks.VAULT)
                .add(Blocks.TRIAL_SPAWNER)
                .add(FBlocks.ICY_VAULT)
                .add(FBlocks.ICY_TRIAL_SPAWNER);

        valueLookupBuilder(FBlockTags.FROZEN_TORCHES)
                .add(FBlocks.FROZEN_TORCH)
                .add(FBlocks.FROZEN_WALL_TORCH);

        valueLookupBuilder(FBlockTags.HAS_OPEN_FLAME)
                .addOptionalTag(BlockTags.CAMPFIRES)
                .addOptionalTag(BlockTags.CANDLES)
                .addOptionalTag(BlockTags.CANDLE_CAKES);

        valueLookupBuilder(FBlockTags.HOT_FLOOR)
                .add(Blocks.MAGMA_BLOCK);

        valueLookupBuilder(FBlockTags.ICE_SPEED_BLOCKS)
                .addOptionalTag(BlockTags.ICE);

        valueLookupBuilder(FBlockTags.ICICLE_GROWABLE)
                .addOptionalTag(BlockTags.ICE);

        valueLookupBuilder(FBlockTags.ICICLE_REPLACEABLE_BLOCKS)
                .addOptionalTag(BlockTags.DRIPSTONE_REPLACEABLE);

        valueLookupBuilder(FBlockTags.IS_OPEN_FLAME)
                .addOptionalTag(BlockTags.FIRE);

        valueLookupBuilder(FBlockTags.SUN_LICHENS)
                .add(FBlocks.COLD_SUN_LICHEN)
                .add(FBlocks.COOL_SUN_LICHEN)
                .add(FBlocks.WARM_SUN_LICHEN)
                .add(FBlocks.HOT_SUN_LICHEN);
    }

    private static TagKey<Block> commonKey(String path) {
        return key("c", path);
    }

    private static TagKey<Block> key(String id, String path) {
        return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(id, path));
    }
}