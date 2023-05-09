package com.github.thedeathlycow.frostiful.mixins.item;

import com.github.thedeathlycow.frostiful.item.FrostResistantArmorMaterial;
import com.github.thedeathlycow.thermoo.api.ThermooAttributes;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.EnumMap;
import java.util.UUID;

@Mixin(ArmorItem.class)
public class ArmorItemMixin {


    private static final EnumMap<ArmorItem.Type, UUID> frostiful$MODIFIERS = Util.make(new EnumMap<>(ArmorItem.Type.class), uuidMap -> {
        uuidMap.put(ArmorItem.Type.BOOTS, UUID.fromString("43ce6852-f0e9-48b5-a451-f6d458f835a2"));
        uuidMap.put(ArmorItem.Type.LEGGINGS, UUID.fromString("015a4e56-3db0-45e7-a14c-82ebc3af86b5"));
        uuidMap.put(ArmorItem.Type.CHESTPLATE, UUID.fromString("99cdd997-74a7-4427-a272-4f2e65c7d5d1"));
        uuidMap.put(ArmorItem.Type.HELMET, UUID.fromString("87102398-36e2-4704-91c4-f2af697928ec"));
    });

    @Shadow
    @Final
    @Mutable
    private Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    private void addFrostResistanceToFurLinedArmour(ArmorMaterial material, ArmorItem.Type type, Item.Settings settings, CallbackInfo ci) {
        UUID uUID = frostiful$MODIFIERS.get(type);

        if (material instanceof FrostResistantArmorMaterial frostResistantArmorMaterial) {
            ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();

            this.attributeModifiers.forEach(builder::put);

            builder.put(
                    ThermooAttributes.FROST_RESISTANCE,
                    new EntityAttributeModifier(
                            uUID,
                            "Armor frost resistance",
                            frostResistantArmorMaterial.getFrostResistance(type),
                            EntityAttributeModifier.Operation.ADDITION
                    )
            );

            this.attributeModifiers = builder.build();
        }
    }
}
