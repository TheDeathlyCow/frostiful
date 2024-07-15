package com.github.thedeathlycow.frostiful.entity.loot;

import com.github.thedeathlycow.frostiful.registry.FItems;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.LootingEnchantLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

public class StrayLootTableModifier {

    private static final RegistryKey<LootTable> STRAY_LOOT_TABLE_ID = EntityType.STRAY.getLootTableId();


    public static void addFrostTippedArrows(
            RegistryKey<LootTable> key,
            LootTable.Builder tableBuilder,
            LootTableSource source
    ) {
        if (source.isBuiltin() && key.equals(STRAY_LOOT_TABLE_ID)) {
            LootPool.Builder builder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .with(
                            ItemEntry.builder(FItems.FROST_TIPPED_ARROW)
                                    .apply(
                                            LootingEnchantLootFunction.builder(
                                                    UniformLootNumberProvider.create(0.0f, 1.0f)
                                            )
                                    )
                    );
            tableBuilder.pool(builder);
        }
    }
}
