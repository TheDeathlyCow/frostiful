package com.github.thedeathlycow.frostiful.item.component;

import com.github.thedeathlycow.frostiful.util.TextStyles;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public final class InertTooltipComponent implements TooltipAppender {
    public static final InertTooltipComponent INSTANCE = new InertTooltipComponent();

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type, ComponentsAccess components) {
        textConsumer.accept(
                Text.translatable("item.frostiful.inert_frostology_cloak.tooltip")
                        .setStyle(TextStyles.INERT_FROSTOLOGY_CLOAK_TOOLTIP)
        );
    }

    private InertTooltipComponent() {

    }
}