package com.github.thedeathlycow.frostiful.item.cloak;

import com.github.thedeathlycow.frostiful.util.TextStyles;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class InertFrostologyCloakItem extends AbstractFrostologyCloakItem {
    public InertFrostologyCloakItem(Properties settings) {
        super(settings);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        tooltip.add(
                Component.translatable("item.frostiful.inert_frostology_cloak.tooltip")
                        .setStyle(TextStyles.INERT_FROSTOLOGY_CLOAK_TOOLTIP)
        );
    }
}
