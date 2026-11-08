/*
 * Frostiful: A Vanilla+ Freezing Temperature Mod. Also try Scorchful!
 * Copyright (C) 2026	TheDeathlyCow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.  If not, see
 * <https://www.gnu.org/licenses/>.
 */

package com.github.thedeathlycow.frostiful.compat;

import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.registry.FStatusEffects;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import com.github.thedeathlycow.frostiful.util.TextStyles;
import com.google.common.base.Suppliers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
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
        int duration = FrostifulConfigYACL.itemSettings().warmingFoodDuration();
        user.addEffect(new MobEffectInstance(FStatusEffects.WARMTH, duration));
    }

    private static void addTooltipBeforeAdvanced(ItemStack stack, List<Component> tooltip) {
        Identifier identifier = BuiltInRegistries.ITEM.getKey(stack.getItem());
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
