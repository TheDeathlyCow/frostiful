package com.github.thedeathlycow.frostiful.datagen.generator.tag;

import com.github.thedeathlycow.frostiful.registry.FBlocks;
import com.github.thedeathlycow.frostiful.registry.tag.FBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class FBlockTagGenerator extends FabricTagProvider.BlockTagProvider {
    public FBlockTagGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        generateFrostifulTags(wrapperLookup);
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

}