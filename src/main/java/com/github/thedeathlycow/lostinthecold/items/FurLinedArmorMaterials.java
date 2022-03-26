package com.github.thedeathlycow.lostinthecold.items;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;

public enum FurLinedArmorMaterials implements ArmorMaterial {

    FUR_LINED_IRON(ArmorMaterials.IRON, 2.0D);

    private final ArmorMaterial parentMaterial;
    private final double frostResistance;

    FurLinedArmorMaterials(ArmorMaterial parentMaterial, double frostResistance) {
        this.parentMaterial = parentMaterial;
        this.frostResistance = frostResistance;
    }

    @Override
    public int getDurability(EquipmentSlot slot) {
        return parentMaterial.getDurability(slot);
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return parentMaterial.getProtectionAmount(slot);
    }

    @Override
    public int getEnchantability() {
        return parentMaterial.getEnchantability();
    }

    @Override
    public SoundEvent getEquipSound() {
        return parentMaterial.getEquipSound();
    }

    @Override
    public Ingredient getRepairIngredient() {
        return parentMaterial.getRepairIngredient();
    }

    @Override
    public String getName() {
        return "fur_lined_" + parentMaterial.getName();
    }

    @Override
    public float getToughness() {
        return parentMaterial.getToughness();
    }

    @Override
    public float getKnockbackResistance() {
        return parentMaterial.getKnockbackResistance();
    }

    public double getFrostResistance() {
        return this.frostResistance;
    }
}
