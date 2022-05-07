package com.github.thedeathlycow.frostiful.item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;

public interface FurLinedArmorMaterial extends ArmorMaterial {

    double getFrostResistance(EquipmentSlot slot);

}
