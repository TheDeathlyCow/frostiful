package com.github.thedeathlycow.frostiful.compat;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.entity.effect.FStatusEffects;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import com.github.thedeathlycow.frostiful.util.TextStyles;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.List;

public class FoodIntegration {

    public static void onConsumeFood(ItemStack stack, LivingEntity user) {
        if (isWarmingFood(stack)) {
            applyWarmthFromFood(user);
        }
    }

    public static void appendWarmthTooltip(ItemStack stack, List<Text> tooltip) {
        if (isWarmingFood(stack)) {
            tooltip.add(Text.translatable("item.frostiful.warming.tooltip")
                    .setStyle(TextStyles.WARMING_TOOLTIP));
        }
    }

    private static boolean isWarmingFood(ItemStack stack) {
        return stack.isIn(FItemTags.WARM_FOODS);
    }

    private static void applyWarmthFromFood(LivingEntity user) {
        int duration = Frostiful.getConfig().freezingConfig.getWarmFoodWarmthTime();
        user.addStatusEffect(new StatusEffectInstance(FStatusEffects.WARMTH, duration));
    }

    private FoodIntegration() {

    }
}
