package com.github.thedeathlycow.frostiful.datagen.generator.loot;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public final class FrostifulLootUtils {
    public static LootPoolSingletonContainer.Builder<?> uniformItem(ItemLike item, float min, float max) {
        return LootItem.lootTableItem(item).apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)));
    }

    public static LootPoolSingletonContainer.Builder<?> uniformItemTag(TagKey<Item> tag, float min, float max) {
        return TagEntry.tagContents(tag).apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)));
    }

    public static TagKey<Block> commonBlockKey(String path) {
        return blockKey("c", path);
    }

    public static TagKey<Item> commonItemKey(String path) {
        return itemKey("c", path);
    }

    public static TagKey<Block> blockKey(String id, String path) {
        return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(id, path));
    }

    public static TagKey<Item> itemKey(String id, String path) {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(id, path));
    }

    public static BiConsumer<ResourceKey<LootTable>, LootTable.Builder> withSequenceId(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        BiConsumer<ResourceKey<LootTable>, LootTable.Builder> sequenceAppender = (key, builder) -> {
            builder.setRandomSequence(key.identifier());
        };

        return sequenceAppender.andThen(output);
    }

    private FrostifulLootUtils() {

    }
}