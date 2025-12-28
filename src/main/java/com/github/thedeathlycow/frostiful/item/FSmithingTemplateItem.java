package com.github.thedeathlycow.frostiful.item;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.registry.FItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class FSmithingTemplateItem {

    public static final ChatFormatting TITLE_FORMATTING = ChatFormatting.GRAY;
    public static final ChatFormatting DESCRIPTION_FORMATTING = ChatFormatting.BLUE;

    /// Texture IDs ///
    public static final Identifier EMPTY_ARMOR_SLOT_HELMET_TEXTURE = Identifier.withDefaultNamespace("item/empty_armor_slot_helmet");
    public static final Identifier EMPTY_ARMOR_SLOT_CHESTPLATE_TEXTURE = Identifier.withDefaultNamespace("item/empty_armor_slot_chestplate");
    public static final Identifier EMPTY_ARMOR_SLOT_LEGGINGS_TEXTURE = Identifier.withDefaultNamespace("item/empty_armor_slot_leggings");
    public static final Identifier EMPTY_ARMOR_SLOT_BOOTS_TEXTURE = Identifier.withDefaultNamespace("item/empty_armor_slot_boots");

    public static void addTemplatesToLoot() {
        FrostifulConfig config = Frostiful.getConfig();
        addTemplateToLoot(
                FItems.ICE_SKATE_UPGRADE_TEMPLATE,
                Identifier.withDefaultNamespace("chests/igloo_chest"),
                config.combatConfig.getSkateUpgradeTemplateIglooGenerateChance()
        );
    }

    private static void addTemplateToLoot(Item template, Identifier lootTableId, float chance) {
        LootTableEvents.MODIFY.register(
                (key, tableBuilder, source, registries) -> {
                    if (source.isBuiltin() && lootTableId.equals(key.identifier())) {
                        LootPool.Builder builder = LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1f))
                                .when(LootItemRandomChanceCondition.randomChance(chance))
                                .add(LootItem.lootTableItem(template));
                        tableBuilder.withPool(builder);
                    }
                }
        );
    }

    private FSmithingTemplateItem() {
    }
}
