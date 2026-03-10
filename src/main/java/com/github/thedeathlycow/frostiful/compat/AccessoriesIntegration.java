package com.github.thedeathlycow.frostiful.compat;

import com.github.thedeathlycow.frostiful.registry.FItems;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class AccessoriesIntegration {
    public static List<ItemStack> getAllEquipped(LivingEntity entity) {
        List<ItemStack> items = new ArrayList<>();

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = entity.getItemBySlot(slot);
            if (!stack.isEmpty()) {
                items.add(stack);
            }
        }

        if (FrostifulIntegrations.isAccessoriesLoaded()) {
            var capability = AccessoriesCapability.get(entity);
            if (capability != null) {
                capability.getAllEquipped().forEach(ref -> items.add(ref.stack()));
            }
        }

        return items;
    }

    public static void removeAccessoriesRenderer() {
        if (FrostifulIntegrations.isAccessoriesLoaded()) {
            AccessoriesRendererRegistry.registerNoRenderer(FItems.FROSTOLOGY_CLOAK);
            AccessoriesRendererRegistry.registerNoRenderer(FItems.INERT_FROSTOLOGY_CLOAK);
        }
    }

    private AccessoriesIntegration() {

    }
}