package com.github.thedeathlycow.frostiful.item.cloak;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;

public abstract class AbstractFrostologyCloakItem extends Item {
    public AbstractFrostologyCloakItem(Settings settings) {
        super(settings.component(
                DataComponentTypes.EQUIPPABLE,
                EquippableComponent.builder(EquipmentSlot.CHEST)
                        .damageOnHurt(false)
                        .build()
        ));
    }
}
