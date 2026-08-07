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

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SmithingTemplateItem;

import java.util.List;

public class IceSkateUpgradeTemplate {

    /// Text ///
    private static final Component ICE_SKATE_UPGRADE_APPLIES_TO_TEXT = Component.translatable(
            Util.makeDescriptionId(
                    "item",
                    Frostiful.id("smithing_template.ice_skate_upgrade.applies_to")
            )).withStyle(FSmithingTemplateItem.DESCRIPTION_FORMATTING);
    private static final Component ICE_SKATE_UPGRADE_INGREDIENTS_TEXT = Component.translatable(
            Util.makeDescriptionId(
                    "item",
                    Frostiful.id("smithing_template.ice_skate_upgrade.ingredients")
            )
    ).withStyle(FSmithingTemplateItem.DESCRIPTION_FORMATTING);
    private static final Component ICE_SKATE_UPGRADE_BASE_SLOT_DESCRIPTION_TEXT = Component.translatable(
            Util.makeDescriptionId(
                    "item", Frostiful.id("smithing_template.ice_skate_upgrade.base_slot_description")
            )
    );
    private static final Component ICE_SKATE_UPGRADE_ADDITIONS_SLOT_DESCRIPTION_TEXT = Component.translatable(
            Util.makeDescriptionId(
                    "item",
                    Frostiful.id("smithing_template.ice_skate_upgrade.additions_slot_description")
            )
    );

    private static final Identifier SWORD_SLOT_TEXTURE = Identifier.withDefaultNamespace("container/slot/sword");

    public static SmithingTemplateItem createItem(Item.Properties settings) {
        return new SmithingTemplateItem(
                ICE_SKATE_UPGRADE_APPLIES_TO_TEXT,
                ICE_SKATE_UPGRADE_INGREDIENTS_TEXT,
                ICE_SKATE_UPGRADE_BASE_SLOT_DESCRIPTION_TEXT,
                ICE_SKATE_UPGRADE_ADDITIONS_SLOT_DESCRIPTION_TEXT,
                getIceSkateUpgradeEmptyBaseSlotTextures(),
                getIceSkateUpgradeEmptyAdditionsSlotTextures(),
                settings
        );
    }

    private static List<Identifier> getIceSkateUpgradeEmptyBaseSlotTextures() {
        return List.of(FSmithingTemplateItem.BOOTS_SLOT_TEXTURE);
    }

    private static List<Identifier> getIceSkateUpgradeEmptyAdditionsSlotTextures() {
        return List.of(SWORD_SLOT_TEXTURE);
    }

    private IceSkateUpgradeTemplate() {

    }

}
