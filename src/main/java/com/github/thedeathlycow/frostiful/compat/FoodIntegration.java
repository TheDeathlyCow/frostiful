package com.github.thedeathlycow.frostiful.compat;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.registry.FStatusEffects;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import com.github.thedeathlycow.frostiful.util.TextStyles;
import com.google.common.base.Suppliers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.Supplier;

public class FoodIntegration {

    private static final Supplier<Text> TOOLTIP = Suppliers.memoize(
            () -> Text.translatable("item.frostiful.warming.tooltip").setStyle(TextStyles.WARMING_TOOLTIP)
    );

    public static void onConsumeFood(ItemStack stack, LivingEntity user) {
        if (isWarmingFood(stack)) {
            applyWarmthFromFood(user);
        }
    }

    public static void appendWarmthTooltip(
            ItemStack stack,
            Item.TooltipContext context,
            TooltipType tooltipType,
            List<Text> tooltip
    ) {
        if (isWarmingFood(stack)) {
            if (tooltipType.isAdvanced()) {
                addTooltipBeforeAdvanced(stack, tooltip);
            } else {
                tooltip.add(TOOLTIP.get());
            }
        }
    }

    private static boolean isWarmingFood(ItemStack stack) {
        return stack.isIn(FItemTags.WARM_FOODS);
    }

    private static void applyWarmthFromFood(LivingEntity user) {
        int duration = Frostiful.getConfig().freezingConfig.getWarmFoodWarmthTime();
        user.addStatusEffect(new StatusEffectInstance(FStatusEffects.WARMTH, duration));
    }

    private static void addTooltipBeforeAdvanced(ItemStack stack, List<Text> tooltip) {
        Identifier identifier = Registries.ITEM.getId(stack.getItem());
        Text idAsText = Text.literal(identifier.toString());

        for (int i = tooltip.size() - 1; i >= 0; i--) {
            if (tooltip.get(i).contains(idAsText)) {
                tooltip.add(i, TOOLTIP.get());
                return;
            }
        }
    }

    private FoodIntegration() {

    }
}
