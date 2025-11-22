package com.github.thedeathlycow.frostiful.compat;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketInventory;
import dev.emi.trinkets.api.TrinketsApi;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public final class TrinketsIntegration {
    public static <T> List<Tuple<SlotReference, ItemStack>> getEquippedTrinket(LivingEntity entity, DataComponentType<T> type) {
        return TrinketsApi.getTrinketComponent(entity)
                .map(trinketComponent -> trinketComponent.getEquipped(s -> s.has(type)))
                .orElse(Collections.emptyList());
    }

    @Nullable
    public static <T> T getComponentInCapeSlot(LivingEntity entity, DataComponentType<T> type) {
        return TrinketsApi.getTrinketComponent(entity)
                .map(trinket -> getFirstInCapeOrNull(trinket, type))
                .orElse(null);
    }

    @Nullable
    private static <T> T getFirstInCapeOrNull(TrinketComponent trinket, DataComponentType<T> type) {
        Map<String, TrinketInventory> chest = trinket.getInventory().get("chest");
        if (chest == null) {
            return null;
        }

        TrinketInventory cape = chest.get("cape");
        if (cape == null) {
            return null;
        }

        for (int i = 0; i < cape.getContainerSize(); i++) {
            T component = cape.getItem(i).get(type);
            if (component != null) {
                return component;
            }
        }

        return null;
    }

    private TrinketsIntegration() {

    }
}