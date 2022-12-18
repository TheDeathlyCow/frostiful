package com.github.thedeathlycow.frostiful.mixins.item;

import com.github.thedeathlycow.frostiful.attributes.FEntityAttributes;
import com.github.thedeathlycow.frostiful.item.FrostResistantArmorMaterial;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(ArmorItem.class)
public class ArmorItemMixin {


    private static final UUID[] frostiful$MODIFIERS = new UUID[]{UUID.fromString("43ce6852-f0e9-48b5-a451-f6d458f835a2"), UUID.fromString("015a4e56-3db0-45e7-a14c-82ebc3af86b5"), UUID.fromString("99cdd997-74a7-4427-a272-4f2e65c7d5d1"), UUID.fromString("87102398-36e2-4704-91c4-f2af697928ec")};

    @Shadow
    @Final
    @Mutable
    private Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    private void addFrostResistanceToFurLinedArmour(ArmorMaterial material, EquipmentSlot slot, Item.Settings settings, CallbackInfo ci) {
        UUID uUID = frostiful$MODIFIERS[slot.getEntitySlotId()];

        if (material instanceof FrostResistantArmorMaterial frostResistantArmorMaterial) {
            ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();

            this.attributeModifiers.forEach(builder::put);

            builder.put(
                    FEntityAttributes.FROST_RESISTANCE,
                    new EntityAttributeModifier(
                            uUID,
                            "Armor frost resistance",
                            frostResistantArmorMaterial.getFrostResistance(slot),
                            EntityAttributeModifier.Operation.ADDITION
                    )
            );

            this.attributeModifiers = builder.build();
        }
    }
}
