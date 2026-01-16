package com.github.thedeathlycow.frostiful.datagen.generator;

import com.github.thedeathlycow.frostiful.block.transformer.*;
import com.github.thedeathlycow.frostiful.registry.FBlockTransformers;
import com.github.thedeathlycow.frostiful.registry.tag.FBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.advancements.criterion.BlockPredicate;
import net.minecraft.advancements.criterion.FluidPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BlockTransformerProvider extends FabricDynamicRegistryProvider {
    public BlockTransformerProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        HolderLookup<Block> blockLookup = registries.lookupOrThrow(Registries.BLOCK);

        entries.add(
                FBlockTransformers.FROSTOLOGER_BLIZZARD_FREEZE,
                frostologerBlizzardFreeze(blockLookup)
        );

        entries.add(
                FBlockTransformers.BLOW_OUT_FROM_WIND,
                blowOutFromWind(blockLookup)
        );
    }

    private static BlockTransformer blowOutFromWind(HolderLookup<Block> blockLookup) {
        return ExtinguishFlameIfFireTransformer.of(FreezeTorchTransformer.of());
    }

    private static BlockTransformer frostologerBlizzardFreeze(HolderLookup<Block> blockLookup) {
        List<BlockTransformer> sequence = List.of(
                IfBlockTransformer.ifBlock(
                        BlockPredicate.Builder.block().of(blockLookup, FBlockTags.HOT_FLOOR),
                        BlockTransformer.simple(Blocks.COBBLESTONE)
                ),
                IfBlockTransformer.ifBlock(
                        BlockPredicate.Builder.block().of(blockLookup, ConventionalBlockTags.OBSIDIANS),
                        BlockTransformer.simple(Blocks.OBSIDIAN)
                ),
                IfFluidTransformer.ifFluid(
                        FluidPredicate.Builder.fluid().of(Fluids.LAVA),
                        BlockTransformer.simple(Blocks.OBSIDIAN)
                ),
                IfFluidTransformer.ifFluid(
                        FluidPredicate.Builder.fluid().of(Fluids.FLOWING_LAVA),
                        BlockTransformer.simple(Blocks.STONE)
                ),
                FreezeTorchTransformer.of()
        );

        return IfBlockTransformer.ifBlockOrElse(
                BlockPredicate.Builder.block()
                        .of(blockLookup, FBlockTags.FROSTOLOGER_CANNOT_FREEZE),
                BlockTransformer.identity(),
                SequenceBlockTransformer.of(sequence)
        );
    }

    @Override
    public String getName() {
        return "Frostiful/BlockTransformer";
    }
}