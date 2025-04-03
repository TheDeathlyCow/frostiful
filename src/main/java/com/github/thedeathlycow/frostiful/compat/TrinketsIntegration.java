package com.github.thedeathlycow.frostiful.compat;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketInventory;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class TrinketsIntegration {
    public static <T> List<Pair<SlotReference, ItemStack>> getEquippedTrinket(LivingEntity entity, ComponentType<T> type) {
        return TrinketsApi.getTrinketComponent(entity)
                .map(trinketComponent -> trinketComponent.getEquipped(s -> s.contains(type)))
                .orElse(Collections.emptyList());
    }

    @Nullable
    public static <T> T getComponentInCapeSlot(LivingEntity entity, ComponentType<T> type) {
        return TrinketsApi.getTrinketComponent(entity)
                .map(trinket -> getFirstInCapeOrNull(trinket, type))
                .orElse(null);
    }

    @Nullable
    private static <T> T getFirstInCapeOrNull(TrinketComponent trinket, ComponentType<T> type) {
        Map<String, TrinketInventory> chest = trinket.getInventory().get("chest");
        if (chest == null) {
            return null;
        }

        TrinketInventory cape = chest.get("cape");
        if (cape == null) {
            return null;
        }

        for (int i = 0; i < cape.size(); i++) {
            T component = cape.getStack(i).get(type);
            if (component != null) {
                return component;
            }
        }

        return null;
    }

    private TrinketsIntegration() {

    }
}