package com.github.thedeathlycow.frostiful.entity.loot;

import com.github.thedeathlycow.frostiful.registry.FItems;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.EnchantedCountIncreaseLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;

public class StrayLootTableModifier {

    private static final RegistryKey<LootTable> STRAY_LOOT_TABLE_ID = EntityType.STRAY.getLootTableId();

    public static void addFrostTippedArrows(
            RegistryKey<LootTable> key,
            LootTable.Builder tableBuilder,
            LootTableSource source,
            RegistryWrapper.WrapperLookup registries
    ) {
        if (source.isBuiltin() && key.equals(STRAY_LOOT_TABLE_ID)) {
            LootPool.Builder builder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .with(
                            ItemEntry.builder(FItems.GLACIAL_ARROW)
                                    .apply(
                                            EnchantedCountIncreaseLootFunction.builder(
                                                    registries,
                                                    UniformLootNumberProvider.create(0.0f, 1.0f)
                                            )
                                    )
                    );
            tableBuilder.pool(builder);
        }
    }

    private StrayLootTableModifier() {

    }
}
