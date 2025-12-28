package com.github.thedeathlycow.frostiful.item;

import com.github.thedeathlycow.frostiful.Frostiful;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SmithingTemplateItem;

public class FurSmithingUpgradeTemplate {


    /// Text ///
    private static final Component FUR_UPGRADE_APPLIES_TO_TEXT = Component.translatable(
            Util.makeDescriptionId(
                    "item",
                    Frostiful.id("smithing_template.fur_upgrade.applies_to")
            )).withStyle(FSmithingTemplateItem.DESCRIPTION_FORMATTING);
    private static final Component FUR_UPGRADE_INGREDIENTS_TEXT = Component.translatable(
            Util.makeDescriptionId(
                    "item",
                    Frostiful.id("smithing_template.fur_upgrade.ingredients")
            )
    ).withStyle(FSmithingTemplateItem.DESCRIPTION_FORMATTING);
    private static final Component FUR_UPGRADE_BASE_SLOT_DESCRIPTION_TEXT = Component.translatable(
            Util.makeDescriptionId(
                    "item", Frostiful.id("smithing_template.fur_upgrade.base_slot_description")
            )
    );
    private static final Component FUR_UPGRADE_ADDITIONS_SLOT_DESCRIPTION_TEXT = Component.translatable(
            Util.makeDescriptionId(
                    "item",
                    Frostiful.id("smithing_template.fur_upgrade.additions_slot_description")
            )
    );

    private static final Identifier EMPTY_SLOT_FUR_PADDING_TEXTURE = Frostiful.id("item/empty/fur_padding");


    public static SmithingTemplateItem createItem(Item.Properties settings) {
        return new SmithingTemplateItem(
                FUR_UPGRADE_APPLIES_TO_TEXT,
                FUR_UPGRADE_INGREDIENTS_TEXT,
                FUR_UPGRADE_BASE_SLOT_DESCRIPTION_TEXT,
                FUR_UPGRADE_ADDITIONS_SLOT_DESCRIPTION_TEXT,
                getFurUpgradeEmptyBaseSlotTextures(),
                getFurUpgradeEmptyAdditionsSlotTextures(),
                settings
        );
    }

    private static List<Identifier> getFurUpgradeEmptyBaseSlotTextures() {
        return List.of(
                FSmithingTemplateItem.EMPTY_ARMOR_SLOT_HELMET_TEXTURE,
                FSmithingTemplateItem.EMPTY_ARMOR_SLOT_CHESTPLATE_TEXTURE,
                FSmithingTemplateItem.EMPTY_ARMOR_SLOT_LEGGINGS_TEXTURE,
                FSmithingTemplateItem.EMPTY_ARMOR_SLOT_BOOTS_TEXTURE
        );
    }

    private static List<Identifier> getFurUpgradeEmptyAdditionsSlotTextures() {
        return List.of(EMPTY_SLOT_FUR_PADDING_TEXTURE);
    }

    private FurSmithingUpgradeTemplate() {
    }
}
