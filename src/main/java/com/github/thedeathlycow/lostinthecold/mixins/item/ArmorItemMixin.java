package com.github.thedeathlycow.lostinthecold.mixins.item;

import com.github.thedeathlycow.lostinthecold.attributes.ModEntityAttributes;
import com.github.thedeathlycow.lostinthecold.items.FurLinedArmorMaterials;
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

    @Shadow @Final private static UUID[] MODIFIERS;
    @Shadow @Final @Mutable
    private Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    @Shadow @Final protected EquipmentSlot slot;

    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    private void addFrostResistanceToFurLinedArmour(ArmorMaterial material, EquipmentSlot slot, Item.Settings settings, CallbackInfo ci) {
        UUID uUID = MODIFIERS[slot.getEntitySlotId()];

        if (material instanceof FurLinedArmorMaterials furLinedMaterial) {
            ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();

            this.attributeModifiers.forEach(builder::put);

            builder.put(
                    ModEntityAttributes.FROST_RESISTANCE,
                    new EntityAttributeModifier(
                            uUID,
                            "Armor frost resistance",
                            furLinedMaterial.getFrostResistance(slot),
                            EntityAttributeModifier.Operation.ADDITION
                    )
            );

            this.attributeModifiers = builder.build();
        }
    }
}
