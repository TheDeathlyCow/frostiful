package com.github.thedeathlycow.frostiful.item.component;

import com.github.thedeathlycow.frostiful.util.TextStyles;
import java.util.function.Consumer;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

public final class InertTooltipComponent implements TooltipProvider {
    public static final InertTooltipComponent INSTANCE = new InertTooltipComponent();

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> textConsumer, TooltipFlag type, DataComponentGetter components) {
        textConsumer.accept(
                Component.translatable("item.frostiful.inert_frostology_cloak.tooltip")
                        .setStyle(TextStyles.INERT_FROSTOLOGY_CLOAK_TOOLTIP)
        );
    }

    private InertTooltipComponent() {

    }
}