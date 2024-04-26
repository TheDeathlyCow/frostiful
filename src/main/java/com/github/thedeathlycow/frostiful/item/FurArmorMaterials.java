package com.github.thedeathlycow.frostiful.item;

import com.github.thedeathlycow.frostiful.sound.FSoundEvents;
import com.github.thedeathlycow.frostiful.tag.FItemTags;
import com.google.common.base.Suppliers;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;

import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * Handles normal attributes for fur armor. Frost Resistance is applied by {@link com.github.thedeathlycow.thermoo.api.attribute.ItemAttributeModifier}
 */
public enum FurArmorMaterials implements ArmorMaterial {

    FUR_ARMOR(
            "fur",
            5,
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 1);
                map.put(ArmorItem.Type.LEGGINGS, 2);
                map.put(ArmorItem.Type.CHESTPLATE, 3);
                map.put(ArmorItem.Type.HELMET, 1);
            }),
            15,
            FSoundEvents.ITEM_ARMOR_EQUIP_FUR,
            0,
            0,
            () -> Ingredient.fromTag(FItemTags.FUR)
    ),
    FUR_LINED_CHAIN(
            "fur_lined_chainmail",
            15,
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 2);
                map.put(ArmorItem.Type.LEGGINGS, 5);
                map.put(ArmorItem.Type.CHESTPLATE, 6);
                map.put(ArmorItem.Type.HELMET, 2);
            }),
            12,
            SoundEvents.ITEM_ARMOR_EQUIP_CHAIN,
            0,
            0,
            () -> Ingredient.ofItems(Items.IRON_INGOT)
    );

    public static final Map<ArmorItem.Type, Double> FROST_RESISTANCE_AMOUNTS = Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 0.5);
        map.put(ArmorItem.Type.LEGGINGS, 1.0);
        map.put(ArmorItem.Type.CHESTPLATE, 2.0);
        map.put(ArmorItem.Type.HELMET, 1.5);
    });

    public static final Map<ArmorItem.Type, UUID> UUID_MAP = Util.make(new EnumMap<>(ArmorItem.Type.class), uuidMap -> {
        uuidMap.put(ArmorItem.Type.BOOTS, UUID.fromString("43ce6852-f0e9-48b5-a451-f6d458f835a2"));
        uuidMap.put(ArmorItem.Type.LEGGINGS, UUID.fromString("015a4e56-3db0-45e7-a14c-82ebc3af86b5"));
        uuidMap.put(ArmorItem.Type.CHESTPLATE, UUID.fromString("99cdd997-74a7-4427-a272-4f2e65c7d5d1"));
        uuidMap.put(ArmorItem.Type.HELMET, UUID.fromString("87102398-36e2-4704-91c4-f2af697928ec"));
    });

    private static final EnumMap<ArmorItem.Type, Integer> BASE_DURABILITY;

    private final String name;
    private final int durabilityMultiplier;
    private final Map<ArmorItem.Type, Integer> protectionAmounts;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredientSupplier;


    FurArmorMaterials(
            String name,
            int durabilityMultiplier,
            Map<ArmorItem.Type, Integer> protectionAmounts,
            int enchantability,
            SoundEvent equipSound,
            float toughness,
            float knockbackResistance,
            Supplier<Ingredient> repairIngredientSupplier
    ) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredientSupplier = Suppliers.memoize(repairIngredientSupplier::get);
    }

    @Override
    public int getDurability(ArmorItem.Type slot) {
        return BASE_DURABILITY.get(slot) * durabilityMultiplier;
    }

    @Override
    public int getProtection(ArmorItem.Type slot) {
        return this.protectionAmounts.get(slot);
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
        return "frostiful_" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }

    static {
        BASE_DURABILITY = Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
            map.put(ArmorItem.Type.BOOTS, 13);
            map.put(ArmorItem.Type.LEGGINGS, 15);
            map.put(ArmorItem.Type.CHESTPLATE, 16);
            map.put(ArmorItem.Type.HELMET, 11);
        });
    }
}
