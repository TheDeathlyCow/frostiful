package com.github.thedeathlycow.frostiful.item.cloak;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.util.TextStyles;
import com.github.thedeathlycow.thermoo.api.ThermooAttributes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.List;

public class FrostologyCloakItem extends AbstractFrostologyCloakItem {

    public FrostologyCloakItem(Settings settings) {
        super(settings);
    }

    public static AttributeModifiersComponent createAttributeModifiers() {
        AttributeModifiersComponent.Builder builder = AttributeModifiersComponent.builder();
        builder.add(
                ThermooAttributes.FROST_RESISTANCE,
                new EntityAttributeModifier(
                        Frostiful.id("cloak.frost_resistance_penalty"),
                        -3.0,
                        EntityAttributeModifier.Operation.ADD_VALUE
                ),
                AttributeModifierSlot.BODY
        );
        return builder.build();
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        tooltip.add(
                Text.translatable("item.frostiful.frostology_cloak.tooltip")
                        .setStyle(TextStyles.FROSTOLOGY_CLOAK_TOOLTIP)
        );
    }
}
