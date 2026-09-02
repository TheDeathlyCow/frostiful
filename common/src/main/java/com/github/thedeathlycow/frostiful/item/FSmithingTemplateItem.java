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

package com.github.thedeathlycow.frostiful.item;

import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
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
    public static final Identifier HELMET_SLOT_TEXTURE = Identifier.withDefaultNamespace("container/slot/helmet");
    public static final Identifier CHESTPLATE_SLOT_TEXTURE = Identifier.withDefaultNamespace("container/slot/chestplate");
    public static final Identifier LEGGINGS_SLOT_TEXTURE = Identifier.withDefaultNamespace("container/slot/leggings");
    public static final Identifier BOOTS_SLOT_TEXTURE = Identifier.withDefaultNamespace("container/slot/boots");

    public static void addTemplatesToLoot() {
        addTemplateToLoot(
                FItems.ICE_SKATE_UPGRADE_TEMPLATE,
                Identifier.withDefaultNamespace("chests/igloo_chest"),
                FrostifulConfigYACL.itemSettings().skateUpgradeTemplateIglooGenerateChance()
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
