package com.github.thedeathlycow.frostiful.item.cloak;

import com.github.thedeathlycow.frostiful.util.TextStyles;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.List;

public class InertFrostologyCloakItem extends AbstractFrostologyCloakItem {
    public InertFrostologyCloakItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        tooltip.add(
                Text.translatable("item.frostiful.inert_frostology_cloak.tooltip")
                        .setStyle(TextStyles.INERT_FROSTOLOGY_CLOAK_TOOLTIP)
        );
    }
}
