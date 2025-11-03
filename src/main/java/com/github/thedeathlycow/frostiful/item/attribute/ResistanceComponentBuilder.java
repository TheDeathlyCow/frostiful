package com.github.thedeathlycow.frostiful.item.attribute;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.registry.FDataComponentTypes;
import com.github.thedeathlycow.thermoo.api.ThermooAttributes;
import com.github.thedeathlycow.thermoo.api.item.ModifyItemAttributeModifiersCallback;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.Equippable;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class ResistanceComponentBuilder {
    private static final Map<EquipmentSlot, ResourceLocation> SLOT_IDS = new EnumMap<>(EquipmentSlot.class);
    private static final Map<EquipmentSlot, ResourceLocation> ENVIRONMENT_SLOT_IDS = new EnumMap<>(EquipmentSlot.class);

    public static void initialize() {
        initializeComponentModifiers();
        initializeItemModifiers();
    }

    private static void initializeItemModifiers() {
        ModifyItemAttributeModifiersCallback.EVENT.register((stack, builder) -> {
            if (stack.is(ConventionalItemTags.ARMORS) && stack.has(DataComponents.EQUIPPABLE)) {
                FrostResistanceComponent resistance = stack.getOrDefault(
                        FDataComponentTypes.FROST_RESISTANCE,
                        FrostResistanceComponent.DEFAULT
                );

                Equippable equippable = stack.get(DataComponents.EQUIPPABLE);
                EquipmentSlot slot = equippable.slot();
                EquipmentSlotGroup attributeModifierSlot = EquipmentSlotGroup.bySlot(slot);
                FArmorType fArmorType = FArmorType.forEquipmentSlot(slot);

                if (resistance.frostResistanceMultiplier() != 0) {
                    builder.add(
                            ThermooAttributes.FROST_RESISTANCE,
                            new AttributeModifier(
                                    SLOT_IDS.computeIfAbsent(
                                            slot,
                                            sl -> Frostiful.id("base_frost_resistance/" + sl.getSerializedName())
                                    ),
                                    fArmorType.getBaseFrostResistance() * resistance.frostResistanceMultiplier(),
                                    AttributeModifier.Operation.ADD_VALUE
                            ),
                            attributeModifierSlot
                    );
                }

                if (resistance.environmentFrostResistanceMultiplier() != 0) {
                    builder.add(
                            ThermooAttributes.ENVIRONMENT_FROST_RESISTANCE,
                            new AttributeModifier(
                                    ENVIRONMENT_SLOT_IDS.computeIfAbsent(
                                            slot,
                                            sl -> Frostiful.id("base_environment_frost_resistance/" + sl.getSerializedName())
                                    ),
                                    fArmorType.getBaseEnvironmentFrostResistance() * resistance.environmentFrostResistanceMultiplier(),
                                    AttributeModifier.Operation.ADD_VALUE
                            ),
                            attributeModifierSlot
                    );
                }
            }
        });
    }

    private static void initializeComponentModifiers() {
        DefaultItemComponentEvents.MODIFY.register(context -> {
            context.modify(
                    List.of(
                            Items.NETHERITE_HELMET,
                            Items.NETHERITE_CHESTPLATE,
                            Items.NETHERITE_LEGGINGS,
                            Items.NETHERITE_BOOTS
                    ),
                    (builder, item) -> {
                        builder.set(FDataComponentTypes.FROST_RESISTANCE, FrostResistanceComponent.PROTECTIVE);
                    }
            );

            context.modify(
                    Items.TURTLE_HELMET,
                    builder -> {
                        builder.set(FDataComponentTypes.FROST_RESISTANCE, FrostResistanceComponent.VERY_HARMFUL);
                    }
            );
        });
    }

    private ResistanceComponentBuilder() {

    }
}