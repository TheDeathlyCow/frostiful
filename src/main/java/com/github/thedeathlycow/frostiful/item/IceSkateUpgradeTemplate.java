package com.github.thedeathlycow.frostiful.item;

import com.github.thedeathlycow.frostiful.Frostiful;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SmithingTemplateItem;

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

    private static final Identifier EMPTY_SLOT_SWORD_TEXTURE = Identifier.withDefaultNamespace("item/empty_slot_sword");

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
        return List.of(FSmithingTemplateItem.EMPTY_ARMOR_SLOT_BOOTS_TEXTURE);
    }

    private static List<Identifier> getIceSkateUpgradeEmptyAdditionsSlotTextures() {
        return List.of(EMPTY_SLOT_SWORD_TEXTURE);
    }

    private IceSkateUpgradeTemplate() {

    }

}
