package com.github.thedeathlycow.frostiful.item;

import com.github.thedeathlycow.frostiful.tag.items.FItemTags;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;


public enum FrostResistantArmorMaterials implements FrostResistantArmorMaterial {

    FUR_ARMOR(
            "fur",
            5,
            new int[]{1, 2, 3, 1},
            15,
            SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
            0,
            0,
            () -> Ingredient.fromTag(FItemTags.FUR),
            new double[]{0.5D, 1.0D, 2.0D, 1.5D}
    ),
    FUR_LINED_CHAIN(
            "fur_lined_chainmail",
            15,
            new int[]{2, 5, 6, 2},
            12,
            SoundEvents.ITEM_ARMOR_EQUIP_CHAIN,
            0,
            0,
            () -> Ingredient.ofItems(Items.IRON_INGOT),
            new double[]{0.5D, 1.0D, 2.0D, 1.5D}
    );

    private static final int[] BASE_DURABILITY = new int[]{13, 15, 16, 11};
    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredientSupplier;
    private final double[] frostResistanceAmounts;

    FrostResistantArmorMaterials(
            String name,
            int durabilityMultiplier,
            int[] protectionAmounts,
            int enchantability,
            SoundEvent equipSound,
            float toughness,
            float knockbackResistance,
            Supplier<Ingredient> repairIngredientSupplier,
            double[] frostResistanceAmounts
    ) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredientSupplier = Suppliers.memoize(repairIngredientSupplier);
        this.frostResistanceAmounts = frostResistanceAmounts;
    }

    @Override
    public double getFrostResistance(EquipmentSlot slot) {
        return this.frostResistanceAmounts[slot.getEntitySlotId()];
    }

    @Override
    public int getDurability(EquipmentSlot slot) {
        return BASE_DURABILITY[slot.getEntitySlotId()] * durabilityMultiplier;
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return this.protectionAmounts[slot.getEntitySlotId()];
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredientSupplier.get();
    }

    @Override
    public String getName() {
        return String.format("frostiful_" + this.name);
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
