package com.github.thedeathlycow.frostiful.item.attribute;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.item.FrostResistantArmorMaterials;
import com.github.thedeathlycow.frostiful.tag.FItemTags;
import com.github.thedeathlycow.thermoo.api.ThermooAttributes;
import com.google.common.collect.Multimap;
import net.fabricmc.fabric.api.item.v1.ModifyItemAttributeModifiersCallback;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;

import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

public class FrostResistantArmorTagApplicator implements ModifyItemAttributeModifiersCallback {

    public static final Map<ArmorItem.Type, UUID> UUID_MAP = Util.make(new EnumMap<>(ArmorItem.Type.class), uuidMap -> {
        uuidMap.put(ArmorItem.Type.BOOTS, UUID.fromString("43ce6852-f0e9-48b5-a451-f6d458f835a2"));
        uuidMap.put(ArmorItem.Type.LEGGINGS, UUID.fromString("015a4e56-3db0-45e7-a14c-82ebc3af86b5"));
        uuidMap.put(ArmorItem.Type.CHESTPLATE, UUID.fromString("99cdd997-74a7-4427-a272-4f2e65c7d5d1"));
        uuidMap.put(ArmorItem.Type.HELMET, UUID.fromString("87102398-36e2-4704-91c4-f2af697928ec"));
    });

    @Override
    public void modifyAttributeModifiers(
            ItemStack stack,
            EquipmentSlot slot,
            Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers
    ) {
        if (stack.getItem() instanceof ArmorItem armorItem) {
            ArmorItem.Type type = armorItem.getType();

            if (slot != type.getEquipmentSlot()) {
                return;
            }

            double amount = Double.NaN;
            if (stack.isIn(FItemTags.WARM_ARMOR)) {
                amount = Frostiful.getConfig().freezingConfig.getNetheriteFrostResistance();
            } else if (stack.isIn(FItemTags.VERY_WARM_ARMOR)) {
                amount = FrostResistantArmorMaterials.FROST_RESISTANCE_AMOUNTS.get(type);
            }

            if (!Double.isNaN(amount)) {
                attributeModifiers.put(
                        ThermooAttributes.FROST_RESISTANCE,
                        new EntityAttributeModifier(
                                UUID_MAP.get(type),
                                "Frost resistance",
                                amount,
                                EntityAttributeModifier.Operation.ADDITION
                        )
                );
            }
        }
    }
}
