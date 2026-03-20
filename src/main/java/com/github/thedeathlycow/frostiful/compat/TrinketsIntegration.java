package com.github.thedeathlycow.frostiful.compat;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class TrinketsIntegration {
    public static List<ItemStack> getAllEquipped(LivingEntity entity) {
        List<ItemStack> items = new ArrayList<>();

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = entity.getItemBySlot(slot);
            if (!stack.isEmpty()) {
                items.add(stack);
            }
        }

//        if (FrostifulIntegrations.isTrinketsLoaded()) {
//            TrinketsApi.getTrinketComponent(entity).ifPresent(component -> {
//                component.forEach((ref, stack) -> items.add(stack));
//            });
//        }

        return items;
    }

    public static boolean hasAnyEquipped(LivingEntity entity, Predicate<ItemStack> predicate) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = entity.getItemBySlot(slot);
            if (predicate.test(stack)) {
                return true;
            }
        }

//        if (FrostifulIntegrations.isTrinketsLoaded()) {
//            TrinketsApi.getTrinketComponent(entity).ifPresent(component -> {
//                component.forEach((ref, stack) -> items.add(stack));
//            });
//        }

        return false;
    }

    @Nullable
    public static <T> T getComponentInCapeSlot(LivingEntity entity, DataComponentType<T> type) {
//        return TrinketsApi.getTrinketComponent(entity)
//                .map(trinket -> getFirstInCapeOrNull(trinket, type))
//                .orElse(null);
        return null;
    }

//    @Nullable
//    private static <T> T getFirstInCapeOrNull(TrinketComponent trinket, DataComponentType<T> type) {
//        Map<String, TrinketInventory> chest = trinket.getInventory().get("chest");
//        if (chest == null) {
//            return null;
//        }
//
//        TrinketInventory cape = chest.get("cape");
//        if (cape == null) {
//            return null;
//        }
//
//        for (int i = 0; i < cape.getContainerSize(); i++) {
//            T component = cape.getItem(i).get(type);
//            if (component != null) {
//                return component;
//            }
//        }
//
//        return null;
//    }

    private TrinketsIntegration() {

    }
}