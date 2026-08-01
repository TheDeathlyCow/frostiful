package com.github.thedeathlycow.frostiful.entity.loot;

import com.github.thedeathlycow.frostiful.registry.FItems;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.Nullable;

public class StrayLootTableModifier {

    @Nullable
    private static final ResourceKey<LootTable> STRAY_LOOT_TABLE_ID = EntityType.STRAY.getDefaultLootTable().orElse(null);

    public static void addFrostTippedArrows(
            ResourceKey<LootTable> key,
            LootTable.Builder tableBuilder,
            LootTableSource source,
            HolderLookup.Provider registries
    ) {
        if (source.isBuiltin() && key == STRAY_LOOT_TABLE_ID) {
            LootPool.Builder builder = LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(
                            LootItem.lootTableItem(FItems.GLACIAL_ARROW)
                                    .apply(
                                            EnchantedCountIncreaseFunction.lootingMultiplier(
                                                    registries,
                                                    UniformGenerator.between(0.0f, 1.0f)
                                            )
                                    )
                    );
            tableBuilder.withPool(builder);
        }
    }

    private StrayLootTableModifier() {

    }
}
