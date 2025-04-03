package com.github.thedeathlycow.frostiful.compat;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;

import java.util.Collections;
import java.util.List;

public final class TrinketsIntegration {
    public static <T> List<Pair<SlotReference, ItemStack>> getEquippedTrinket(LivingEntity entity, ComponentType<T> type) {
        return TrinketsApi.getTrinketComponent(entity)
                .map(trinketComponent -> trinketComponent.getEquipped(s -> s.contains(type)))
                .orElse(Collections.emptyList());
    }

    private TrinketsIntegration() {

    }
}