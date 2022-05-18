package com.github.thedeathlycow.frostiful.entity.loot;

import com.github.thedeathlycow.frostiful.item.FrostifulItems;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.FabricLootSupplierBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.LootingEnchantLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.LootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

public class StrayLootTableListener {

    private static final Identifier STRAY_LOOT_TABLE_ID = EntityType.STRAY.getLootTableId();

    public static void addFrostTippedArrows(ResourceManager resourceManager, LootManager lootManager, Identifier identifier, FabricLootSupplierBuilder fabricLootSupplierBuilder, LootTableLoadingCallback.LootTableSetter setter) {
        if (identifier.equals(STRAY_LOOT_TABLE_ID)) {
            FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .with(
                            ItemEntry.builder(FrostifulItems.FROST_TIPPED_ARROW)
                    )
                    .withFunction(
                            SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)).build()
                    )
                    .withFunction(
                            LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)).build()
                    );
            fabricLootSupplierBuilder.pool(poolBuilder);
        }
    }
}
