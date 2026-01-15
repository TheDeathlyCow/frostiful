package com.github.thedeathlycow.frostiful.datagen.generator.loot;

import com.github.thedeathlycow.frostiful.registry.FItems;
import com.github.thedeathlycow.frostiful.registry.FLootTables;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.InstrumentTags;
import net.minecraft.world.item.Instruments;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetInstrumentFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import static com.github.thedeathlycow.frostiful.datagen.generator.loot.FrostifulLootUtils.*;

public class FChestLootGenerator extends SimpleFabricLootTableProvider {
    public FChestLootGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup, LootContextParamSets.CHEST);
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        output = FrostifulLootUtils.withSequenceId(output);

        output.accept(
                FLootTables.CHILLAGER_OUTPOST_FLETCHER,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1f, 3f))
                                        .add(uniformItem(Items.ARROW, 1f, 5f))
                                        .add(uniformItem(FItems.GLACIAL_ARROW, 1f, 5f))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1f, 3f))
                                        .add(uniformItem(Items.FEATHER, 1f, 3f))
                                        .add(LootItem.lootTableItem(Items.FLINT))
                                        .add(uniformItem(Items.STICK, 1f, 3f))
                                        .add(uniformItemTag(commonItemKey("icicles"), 1f, 5f))
                                        .add(uniformItem(FItems.PACKED_SNOWBALL, 1f, 2f))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1f))
                                        .add(LootItem.lootTableItem(Items.CROSSBOW).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.GOAT_HORN).setWeight(2).apply(SetInstrumentFunction.setInstrumentOptions(InstrumentTags.REGULAR_GOAT_HORNS)))
                                        .add(LootItem.lootTableItem(FItems.FUR_UPGRADE_TEMPLATE))
                        )
        );
    }


}