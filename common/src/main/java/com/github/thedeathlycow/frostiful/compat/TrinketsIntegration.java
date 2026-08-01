package com.github.thedeathlycow.frostiful.compat;

import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import eu.pb4.trinkets.api.SlotGroup;
import eu.pb4.trinkets.api.TrinketInventory;
import eu.pb4.trinkets.api.TrinketsApi;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public final class TrinketsIntegration {
    private static final Predicate<ItemStack> WEARING_CHILLAGER_LORD_CLOAK = stack -> stack.is(FItemTags.CHILLAGER_LORD_CLOAK);

    public static List<ItemStack> getAllEquipped(LivingEntity entity) {
        List<ItemStack> items = new ArrayList<>();

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = entity.getItemBySlot(slot);
            if (!stack.isEmpty()) {
                items.add(stack);
            }
        }

        if (FrostifulIntegrations.isTrinketsLoaded()) {
            TrinketsApi.getAttachment(entity).forEach((_, stack) -> {
                items.add(stack);
            });
        }

        return items;
    }

    public static boolean hasAnyEquipped(LivingEntity entity, Predicate<ItemStack> predicate) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = entity.getItemBySlot(slot);
            if (predicate.test(stack)) {
                return true;
            }
        }

        if (FrostifulIntegrations.isTrinketsLoaded()) {
            return TrinketsApi.getAttachment(entity).isEquipped(predicate);
        }

        return false;
    }

    public static boolean wearingFrostologyCloak(LivingEntity entity) {
        return hasAnyEquipped(entity, WEARING_CHILLAGER_LORD_CLOAK);
    }

    @Nullable
    public static <T> T getComponentInCapeSlot(LivingEntity entity, DataComponentType<T> type) {
        return getFirstInCapeOrNull(entity, type);
    }

    @Nullable
    private static <T> T getFirstInCapeOrNull(LivingEntity entity, DataComponentType<T> type) {
        Map<String, Map<String, TrinketInventory>> inventory = TrinketsApi.getAttachment(entity).getInventory();

        Map<String, TrinketInventory> chestSlots = inventory.get("chest");

        if (chestSlots == null) {
            return null;
        }

        TrinketInventory cape = chestSlots.get("cape");

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