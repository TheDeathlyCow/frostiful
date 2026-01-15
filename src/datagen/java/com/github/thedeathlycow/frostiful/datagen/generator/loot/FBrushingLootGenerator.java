package com.github.thedeathlycow.frostiful.datagen.generator.loot;

import com.github.thedeathlycow.frostiful.registry.FItems;
import com.github.thedeathlycow.frostiful.registry.FLootTables;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class FBrushingLootGenerator extends SimpleFabricLootTableProvider {
    public FBrushingLootGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup, LootContextParamSets.SHEARING);
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        output = FrostifulLootUtils.withSequenceId(output);
        
        output.accept(FLootTables.POLAR_BEAR_BRUSHING_GAMEPLAY, brushingLoot(FItems.POLAR_BEAR_FUR_TUFT));
        output.accept(FLootTables.OCELOT_BRUSHING_GAMEPLAY, brushingLoot(FItems.OCELOT_FUR_TUFT));
        output.accept(FLootTables.WOLF_BRUSHING_GAMEPLAY, brushingLoot(FItems.WOLF_FUR_TUFT));
    }

    private static LootTable.Builder brushingLoot(ItemLike item) {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1f))
                                .add(LootItem.lootTableItem(item)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 2f)))
                                )
                );
    }
}