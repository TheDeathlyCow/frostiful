package com.github.thedeathlycow.frostiful.mixins.item;

import com.github.thedeathlycow.frostiful.item.FrostResistantArmorMaterial;
import com.github.thedeathlycow.frostiful.item.attribute.FrostResistantArmorTagApplicator;
import com.github.thedeathlycow.thermoo.api.ThermooAttributes;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
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




    @Shadow
    @Final
    @Mutable
    private Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    private void addFrostResistanceToFurLinedArmour(ArmorMaterial material, ArmorItem.Type type, Item.Settings settings, CallbackInfo ci) {
        UUID uUID = FrostResistantArmorTagApplicator.UUID_MAP.get(type);

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
