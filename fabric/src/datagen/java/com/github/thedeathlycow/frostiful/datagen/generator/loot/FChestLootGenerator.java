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

import com.github.thedeathlycow.frostiful.registry.FEnchantments;
import com.github.thedeathlycow.frostiful.registry.FItems;
import com.github.thedeathlycow.frostiful.registry.FLootTables;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import com.github.thedeathlycow.frostiful.registry.tag.FStructureTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.InstrumentTags;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Instruments;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.saveddata.maps.MapDecorationTypes;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.ExplorationMapFunction;
import net.minecraft.world.level.storage.loot.functions.SetInstrumentFunction;
import net.minecraft.world.level.storage.loot.functions.SetNameFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import static com.github.thedeathlycow.frostiful.datagen.generator.loot.FrostifulLootUtils.*;

public class FChestLootGenerator extends SimpleFabricLootTableSubProvider {
    public static final String FILLED_MAP_TRANSLATION_KEY = "filled_map.frostiful.frostologer_castle";

    private final CompletableFuture<HolderLookup.Provider> registriesFuture;

    public FChestLootGenerator(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture, LootContextParamSets.CHEST);
        this.registriesFuture = registriesFuture;
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        BiConsumer<ResourceKey<LootTable>, LootTable.Builder> sequencedOutput = FrostifulLootUtils.withSequenceId(output);
        this.generateChillagerOutpostChests(sequencedOutput, this.registriesFuture.join());
    }

    private void generateChillagerOutpostChests(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output, HolderLookup.Provider lookup) {
        HolderLookup<Enchantment> enchantmentRegistry = lookup.lookupOrThrow(Registries.ENCHANTMENT);
        HolderLookup<Instrument> instruments = lookup.lookupOrThrow(Registries.INSTRUMENT);

        HolderSet<Instrument> instrumentOptions = instruments.getOrThrow(InstrumentTags.REGULAR_GOAT_HORNS);

        output.accept(
                FLootTables.CHILLAGER_OUTPOST_TOWER,
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
                                        .add(uniformItem(FItems.PACKED_SNOWBALL, 1f, 2f))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1f))
                                        .add(LootItem.lootTableItem(Items.GOAT_HORN).apply(
                                                SetInstrumentFunction.setInstrumentOptions(
                                                        instruments.getOrThrow(InstrumentTags.REGULAR_GOAT_HORNS)
                                                )))
                        )
        );

        output.accept(
                FLootTables.CHILLAGER_OUTPOST_SMITH,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1f, 4f))
                                        .add(uniformItem(Items.IRON_INGOT, 1f, 2f))
                                        .add(uniformItem(Items.EMERALD, 1f, 3f))
                                        .add(uniformItem(Items.WHEAT, 1f, 3f))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1f, 2f))
                                        .add(LootItem.lootTableItem(FItems.FUR_UPGRADE_TEMPLATE).setWeight(2))
                                        .add(uniformItem(FItems.FUR_PADDING, 1f, 2f))
                                        .add(expandUniformItemTag(FItemTags.FUR_TUFTS, 1f, 4f).setWeight(2))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1f, 2f))
                                        .add(LootItem.lootTableItem(Items.CHAINMAIL_HELMET))
                                        .add(LootItem.lootTableItem(Items.CHAINMAIL_CHESTPLATE))
                                        .add(LootItem.lootTableItem(Items.CHAINMAIL_LEGGINGS))
                                        .add(LootItem.lootTableItem(Items.CHAINMAIL_BOOTS))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1f))
                                        .when(LootItemRandomChanceCondition.randomChance(0.25f))
                                        .add(LootItem.lootTableItem(FItems.SNOW_MAN_ARMOR_TRIM_SMITHING_TEMPLATE))
                        )
        );

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
                                        .add(LootItem.lootTableItem(Items.GOAT_HORN).setWeight(2).apply(SetInstrumentFunction.setInstrumentOptions(instrumentOptions)))
                                        .add(LootItem.lootTableItem(FItems.FUR_UPGRADE_TEMPLATE))
                        )
        );

        output.accept(
                FLootTables.CHILLAGER_OUTPOST_MAP,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(7))
                                        .add(uniformItem(Items.PAPER, 1f, 7f).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.COMPASS))
                                        .add(LootItem.lootTableItem(Items.EXPERIENCE_BOTTLE))
                                        .add(uniformItem(Items.STRING, 1f, 3f))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1))
                                        .add(
                                                LootItem.lootTableItem(Items.MAP)
                                                        .apply(
                                                                ExplorationMapFunction.makeExplorationMap()
                                                                        .setDestination(FStructureTags.CHILLAGER_MAP_LOCATABLE)
                                                                        .setMapDecoration(MapDecorationTypes.TARGET_X)
                                                                        .setSkipKnownStructures(false)
                                                        )
                                                        .apply(
                                                                SetNameFunction.setName(
                                                                        Component.translatable(FILLED_MAP_TRANSLATION_KEY)
                                                                                .withStyle(Style.EMPTY.withFont(new FontDescription.Resource(Identifier.withDefaultNamespace("illageralt")))),
                                                                        SetNameFunction.Target.ITEM_NAME
                                                                )
                                                        )
                                        )
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1))
                                        .add(
                                                LootItem.lootTableItem(Items.BOOK)
                                                        .apply(EnchantRandomlyFunction.randomEnchantment().withEnchantment(enchantmentRegistry.getOrThrow(FEnchantments.FROZEN_TOUCH_CURSE)))
                                        )
                        )
        );
    }
}