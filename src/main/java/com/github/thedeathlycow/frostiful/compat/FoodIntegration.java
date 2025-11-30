package com.github.thedeathlycow.frostiful.compat;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.registry.FStatusEffects;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import com.github.thedeathlycow.frostiful.util.TextStyles;
import com.google.common.base.Suppliers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;
import java.util.function.Supplier;

public class FoodIntegration {

    private static final Supplier<Component> TOOLTIP = Suppliers.memoize(
            () -> Component.translatable("item.frostiful.warming.tooltip").setStyle(TextStyles.WARMING_TOOLTIP)
    );

    public static void onConsumeFood(ItemStack stack, LivingEntity user) {
        if (isWarmingFood(stack)) {
            applyWarmthFromFood(user);
        }
    }

    public static void appendWarmthTooltip(
            ItemStack stack,
            Item.TooltipContext context,
            TooltipFlag tooltipType,
            List<Component> tooltip
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
        return stack.is(FItemTags.WARM_FOODS);
    }

    private static void applyWarmthFromFood(LivingEntity user) {
        int duration = Frostiful.getConfig().freezingConfig.getWarmFoodWarmthTime();
        user.addEffect(new MobEffectInstance(FStatusEffects.WARMTH, duration));
    }

    private static void addTooltipBeforeAdvanced(ItemStack stack, List<Component> tooltip) {
        ResourceLocation identifier = BuiltInRegistries.ITEM.getKey(stack.getItem());
        Component idAsText = Component.literal(identifier.toString());

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
