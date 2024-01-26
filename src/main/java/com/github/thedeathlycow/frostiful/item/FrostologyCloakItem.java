package com.github.thedeathlycow.frostiful.item;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.compat.FrostifulIntegrations;
import com.github.thedeathlycow.frostiful.registry.FItems;
import com.github.thedeathlycow.frostiful.tag.FItemTags;
import com.github.thedeathlycow.frostiful.util.TextStyles;
import com.github.thedeathlycow.thermoo.api.ThermooAttributes;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Equipment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public class FrostologyCloakItem extends Item implements Equipment {

    public static final Identifier TEXTURE_ID = Frostiful.id("textures/entity/frostology_cloak.png");

    private static final Multimap<EntityAttribute, EntityAttributeModifier> ATTRIBUTE_MODIFIERS = ImmutableMultimap.
            <EntityAttribute, EntityAttributeModifier>builder()
            .put(
                    ThermooAttributes.FROST_RESISTANCE,
                    new EntityAttributeModifier(
                            UUID.fromString("6b330e71-f106-43f4-a568-9042ae560c65"),
                            () -> "Frostology cloak",
                            -3,
                            EntityAttributeModifier.Operation.ADDITION
                    )
            )
            .build();

    public FrostologyCloakItem(Settings settings) {
        super(settings);
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.isIn(FItemTags.FUR);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemInHand = user.getStackInHand(hand);
        EquipmentSlot slotToEquip = LivingEntity.getPreferredEquipmentSlot(itemInHand);
        ItemStack itemInSlot = user.getEquippedStack(slotToEquip);
        if (itemInSlot.isEmpty()) {
            user.equipStack(slotToEquip, itemInHand.copy());
            if (!world.isClient()) {
                user.incrementStat(Stats.USED.getOrCreateStat(this));
            }

            itemInHand.setCount(0);
            return TypedActionResult.success(itemInHand, world.isClient());
        } else {
            return TypedActionResult.fail(itemInHand);
        }
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
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

    public static EquipmentSlot getPreferredEquipmentSlot(ItemStack stack) {
        return EquipmentSlot.CHEST;
    }

    @Override
    public EquipmentSlot getSlotType() {
        return EquipmentSlot.CHEST;
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        if (slot == EquipmentSlot.CHEST) {
            return ATTRIBUTE_MODIFIERS;
        }
        return super.getAttributeModifiers(stack, slot);
    }
}
