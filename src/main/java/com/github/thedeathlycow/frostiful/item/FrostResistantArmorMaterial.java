package com.github.thedeathlycow.frostiful.item;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;

public interface FrostResistantArmorMaterial extends ArmorMaterial {

    double getFrostResistance(ArmorItem.Type slot);

}
