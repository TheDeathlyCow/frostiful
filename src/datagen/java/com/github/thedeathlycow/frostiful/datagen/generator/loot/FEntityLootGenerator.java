package com.github.thedeathlycow.frostiful.datagen.generator.loot;

import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import com.github.thedeathlycow.frostiful.registry.FItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricEntityLootTableProvider;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.RaiderPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetOminousBottleAmplifierFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class FEntityLootGenerator extends FabricEntityLootTableProvider {
    public FEntityLootGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generate() {
        add(
                FEntityTypes.BITER,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1f))
                                        .add(
                                                TagEntry.expandTag(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "icicles")))
                                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0f, 2f)))
                                        )
                        )
        );

        add(
                FEntityTypes.CHILLAGER,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1f))
                                        .add(
                                                LootItem.lootTableItem(Items.OMINOUS_BOTTLE)
                                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1f)))
                                                        .apply(SetOminousBottleAmplifierFunction.setAmplifier(UniformGenerator.between(0f, 4f)))
                                        )
                                        .when(
                                                LootItemEntityPropertyCondition.hasProperties(
                                                        LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate(RaiderPredicate.CAPTAIN_WITHOUT_RAID)
                                                )
                                        )
                        )
        );

        add(
                FEntityTypes.FROSTOLOGER,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0f))
                                        .add(
                                                LootItem.lootTableItem(FItems.FROZEN_ROD)
                                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 2f)))
                                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(1f, 2f)))
                                        )
                                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        )
        );
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> biConsumer) {
        BiConsumer<ResourceKey<LootTable>, LootTable.Builder> sequenceAppender = (key, builder) -> {
            builder.setRandomSequence(key.identifier());
        };

        super.generate(sequenceAppender.andThen(biConsumer));
    }
}