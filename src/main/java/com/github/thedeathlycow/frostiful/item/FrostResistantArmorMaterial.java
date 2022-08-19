package com.github.thedeathlycow.frostiful.item;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;

public interface FrostResistantArmorMaterial extends ArmorMaterial {

    double getFrostResistance(EquipmentSlot slot);

    ArmorMaterial getParentMaterial();

    @Override
    default int getDurability(EquipmentSlot slot) {
        return this.getParentMaterial().getDurability(slot);
    }

    @Override
    default int getProtectionAmount(EquipmentSlot slot) {
        return this.getParentMaterial().getProtectionAmount(slot);
    }

    @Override
    default int getEnchantability() {
        return this.getParentMaterial().getEnchantability();
    }

    @Override
    default SoundEvent getEquipSound() {
        return this.getParentMaterial().getEquipSound();
    }

    @Override
    default Ingredient getRepairIngredient() {
        return this.getParentMaterial().getRepairIngredient();
    }

    @Override
    default String getName() {
        return String.format("%s_fur_lined_%s", Frostiful.MODID, this.getParentMaterial().getName());
    }

    @Override
    default float getToughness() {
        return this.getParentMaterial().getToughness();
    }

    @Override
    default float getKnockbackResistance() {
        return this.getParentMaterial().getKnockbackResistance();
    }
}
