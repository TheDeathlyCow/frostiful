package com.github.thedeathlycow.lostinthecold.items;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;

public interface FurLinedArmorMaterial extends ArmorMaterial {

    double getFrostResistance(EquipmentSlot slot);

}
