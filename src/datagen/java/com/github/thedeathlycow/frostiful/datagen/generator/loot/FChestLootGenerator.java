package com.github.thedeathlycow.frostiful.datagen.generator.loot;

import com.github.thedeathlycow.frostiful.registry.FEnchantments;
import com.github.thedeathlycow.frostiful.registry.FItems;
import com.github.thedeathlycow.frostiful.registry.FLootTables;
import com.github.thedeathlycow.frostiful.registry.tag.FStructureTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.InstrumentTags;
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
        HolderLookup.RegistryLookup<Enchantment> enchantmentRegistry = lookup.lookupOrThrow(Registries.ENCHANTMENT);

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