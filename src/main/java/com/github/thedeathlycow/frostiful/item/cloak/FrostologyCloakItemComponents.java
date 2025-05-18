package com.github.thedeathlycow.frostiful.item.cloak;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.util.TextStyles;
import com.github.thedeathlycow.thermoo.api.ThermooAttributes;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.List;

public final class FrostologyCloakItemComponents {
    public static EquippableComponent createEquippableComponent() {
        return EquippableComponent.builder(EquipmentSlot.CHEST)
                .damageOnHurt(false)
                .build();
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

    private FrostologyCloakItemComponents() {

    }
}
