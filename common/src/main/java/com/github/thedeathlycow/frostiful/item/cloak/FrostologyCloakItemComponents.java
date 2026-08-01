package com.github.thedeathlycow.frostiful.item.cloak;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.thermoo.api.entity.v1.ThermooAttributes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.equipment.Equippable;

public final class FrostologyCloakItemComponents {
    public static Equippable createEquippableComponent() {
        return Equippable.builder(EquipmentSlot.CHEST)
                .setDamageOnHurt(false)
                .build();
    }

    public static ItemAttributeModifiers createAttributeModifiers() {
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        builder.add(
                ThermooAttributes.FROST_RESISTANCE,
                new AttributeModifier(
                        Frostiful.id("cloak.frost_resistance_penalty"),
                        -3.0,
                        AttributeModifier.Operation.ADD_VALUE
                ),
                EquipmentSlotGroup.BODY
        );
        return builder.build();
    }

    private FrostologyCloakItemComponents() {

    }
}
