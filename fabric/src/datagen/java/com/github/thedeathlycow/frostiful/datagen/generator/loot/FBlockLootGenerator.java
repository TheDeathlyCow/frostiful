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

package com.github.thedeathlycow.frostiful.datagen.generator.loot;

import com.github.thedeathlycow.frostiful.block.PackedSnowBlock;
import com.github.thedeathlycow.frostiful.registry.FBlocks;
import com.github.thedeathlycow.frostiful.registry.FItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class FBlockLootGenerator extends FabricBlockLootSubProvider {
    public FBlockLootGenerator(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        dropWhenSilkTouch(FBlocks.ICE_PANE);
        dropWhenSilkTouch(FBlocks.BRITTLE_ICE);
        dropWhenSilkTouch(FBlocks.ICICLE);

        add(FBlocks.COLD_SUN_LICHEN, block -> this.createMultifaceBlockDrops(block, this.hasShears()));
        add(FBlocks.COOL_SUN_LICHEN, block -> this.createMultifaceBlockDrops(block, this.hasShears()));
        add(FBlocks.WARM_SUN_LICHEN, block -> this.createMultifaceBlockDrops(block, this.hasShears()));
        add(FBlocks.HOT_SUN_LICHEN, block -> this.createMultifaceBlockDrops(block, this.hasShears()));

        dropSelf(FBlocks.CUT_BLUE_ICE);
        dropSelf(FBlocks.CUT_BLUE_ICE_SLAB);
        dropSelf(FBlocks.CUT_BLUE_ICE_STAIRS);
        dropSelf(FBlocks.CUT_BLUE_ICE_WALL);

        dropSelf(FBlocks.CUT_PACKED_ICE);
        dropSelf(FBlocks.CUT_PACKED_ICE_SLAB);
        dropSelf(FBlocks.CUT_PACKED_ICE_STAIRS);
        dropSelf(FBlocks.CUT_PACKED_ICE_WALL);

        add(FBlocks.PACKED_SNOW, this::packedSnowLayers);
        dropSelf(FBlocks.PACKED_SNOW_BLOCK);

        dropSelf(FBlocks.PACKED_SNOW_BRICKS);
        dropSelf(FBlocks.PACKED_SNOW_BRICK_SLAB);
        dropSelf(FBlocks.PACKED_SNOW_BRICK_STAIRS);
        dropSelf(FBlocks.PACKED_SNOW_BRICK_WALL);

        add(FBlocks.FROZEN_TORCH, block -> createSingleItemTableWithSilkTouch(block, Items.STICK));
    }

    private LootTable.Builder packedSnowLayers(Block block) {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .when(LootItemEntityPropertyCondition.entityPresent(LootContext.EntityTarget.THIS))
                                .add(
                                        AlternativesEntry.alternatives(
                                                AlternativesEntry.alternatives(
                                                                PackedSnowBlock.LAYERS.getPossibleValues(),
                                                                numLayers -> LootItem.lootTableItem(FItems.PACKED_SNOWBALL)
                                                                        .when(
                                                                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                                                        .setProperties(
                                                                                                StatePropertiesPredicate.Builder.properties()
                                                                                                        .hasProperty(PackedSnowBlock.LAYERS, numLayers)
                                                                                        )
                                                                        )
                                                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(numLayers)))
                                                        )
                                                        .when(this.doesNotHaveSilkTouch()),
                                                AlternativesEntry.alternatives(
                                                        PackedSnowBlock.LAYERS.getPossibleValues(),
                                                        numLayers -> numLayers == PackedSnowBlock.MAX_LAYERS
                                                                ? LootItem.lootTableItem(FBlocks.PACKED_SNOW_BLOCK)
                                                                : LootItem.lootTableItem(FBlocks.PACKED_SNOW)
                                                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(numLayers)))
                                                                .when(
                                                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                                                                        .hasProperty(PackedSnowBlock.LAYERS, numLayers))
                                                                )
                                                )
                                        )
                                )
                );
    }


    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        super.generate(FrostifulLootUtils.withSequenceId(output));
    }
}