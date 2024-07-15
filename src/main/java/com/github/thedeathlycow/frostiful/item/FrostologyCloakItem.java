package com.github.thedeathlycow.frostiful.item;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.compat.FrostifulIntegrations;
import com.github.thedeathlycow.frostiful.registry.FItems;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import com.github.thedeathlycow.frostiful.util.TextStyles;
import com.github.thedeathlycow.thermoo.api.ThermooAttributes;
import com.google.common.base.Suppliers;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.block.DispenserBlock;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Supplier;

public class FrostologyCloakItem extends Item implements Equipment {

    public static final Identifier TEXTURE_ID = Frostiful.id("textures/entity/frostology_cloak.png");

    private final Supplier<AttributeModifiersComponent> attributeModifiers;

    public FrostologyCloakItem(Settings settings) {
        super(settings);
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);

        this.attributeModifiers = Suppliers.memoize(
                () -> {
                    AttributeModifiersComponent.Builder builder = AttributeModifiersComponent.builder();
                    builder.add(
                            ThermooAttributes.FROST_RESISTANCE,
                            new EntityAttributeModifier(
                                    Frostiful.id("cloak.frost_resistance_penalty"),
                                    -3.0,
                                    EntityAttributeModifier.Operation.ADD_VALUE
                            ),
                            AttributeModifierSlot.BODY
                    );
                    return builder.build();
                }
        );
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.isIn(FItemTags.FUR);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return this.equipAndSwap(this, world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        tooltip.add(
                Text.translatable("item.frostiful.frostology_cloak.tooltip")
                        .setStyle(TextStyles.FROSTOLOGY_CLOAK_TOOLTIP)
        );
    }

    public static boolean isWornBy(PlayerEntity player) {
        boolean isWearingInTrinketSlot = false;
        if (FrostifulIntegrations.isModLoaded(FrostifulIntegrations.TRINKETS_ID)) {
            isWearingInTrinketSlot = TrinketsApi.getTrinketComponent(player)
                    .map(trinketComponent -> trinketComponent.isEquipped(FItems.FROSTOLOGY_CLOAK))
                    .orElse(false);
        }

        return isWearingInTrinketSlot || player.getInventory()
                .getArmorStack(EquipmentSlot.CHEST.getEntitySlotId())
                .isOf(FItems.FROSTOLOGY_CLOAK);
    }

    public static EquipmentSlot getPreferredEquipmentSlot(LivingEntity entity, ItemStack stack) {
        return EquipmentSlot.CHEST;
    }

    @Override
    public EquipmentSlot getSlotType() {
        return EquipmentSlot.CHEST;
    }

    @Override
    @SuppressWarnings("deprecation")
    public AttributeModifiersComponent getAttributeModifiers() {
        return this.attributeModifiers.get();
    }
}
