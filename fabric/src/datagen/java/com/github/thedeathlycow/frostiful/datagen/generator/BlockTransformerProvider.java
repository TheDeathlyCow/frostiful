/*
 * Frostiful: A Vanilla+ Freezing Temperature Mod. Also try Scorchful!
 * Copyright (C) 2026	TheDeathlyCow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.  If not, see
 * <https://www.gnu.org/licenses/>.
 */

package com.github.thedeathlycow.frostiful.datagen.generator;

import com.github.thedeathlycow.frostiful.block.transformer.*;
import com.github.thedeathlycow.frostiful.registry.FBlockTransformers;
import com.github.thedeathlycow.frostiful.registry.tag.FBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
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
    public BlockTransformerProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
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
                blowOutFromWind()
        );
    }

    private static BlockTransformer blowOutFromWind() {
        return FirstOfBlockTransformer.of(
                ExtinguishFlameIfFireTransformer.of(),
                FreezeTorchTransformer.of()
        );
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
                FreezeTorchTransformer.of(),
                IfBlockTransformer.ifBlock(
                        BlockPredicate.Builder.block().of(blockLookup, Blocks.JACK_O_LANTERN),
                        BlockTransformer.simple(Blocks.CARVED_PUMPKIN)
                )
        );

        return IfBlockTransformer.ifBlockOrElse(
                BlockPredicate.Builder.block()
                        .of(blockLookup, FBlockTags.FROSTOLOGER_CANNOT_FREEZE),
                BlockTransformer.identity(),
                FirstOfBlockTransformer.of(sequence)
        );
    }

    @Override
    public String getName() {
        return "Frostiful/BlockTransformer";
    }
}