package com.github.thedeathlycow.frostiful.item;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.tag.items.FItemTags;
import com.github.thedeathlycow.frostiful.util.TextStyles;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Wearable;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class FrostologyCloakItem extends Item implements Wearable {

    public static final Identifier TEXTURE_ID = Frostiful.id("textures/entity/frostology_cloak.png");

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
}
