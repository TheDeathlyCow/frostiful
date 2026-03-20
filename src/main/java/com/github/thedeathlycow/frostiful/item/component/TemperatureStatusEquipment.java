package com.github.thedeathlycow.frostiful.item.component;

import com.github.thedeathlycow.frostiful.registry.FDataComponentTypes;
import com.github.thedeathlycow.thermoo.api.temperature.status.v2.TemperatureStatus;
import com.github.thedeathlycow.thermoo.api.temperature.status.v2.TemperatureStatusLookup;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class TemperatureStatusEquipment implements ServerEntityEvents.EquipmentChange {
    @Override
    public void onChange(LivingEntity livingEntity, EquipmentSlot equipmentSlot, ItemStack previousStack, ItemStack currentStack) {
        if (equipmentSlot.isArmor()) {
            this.updateStack(livingEntity, previousStack, false);
            this.updateStack(livingEntity, currentStack, true);
        }
    }

    private void updateStack(LivingEntity livingEntity, ItemStack currentStack, boolean added) {
        HolderSet<TemperatureStatus> toDisable = currentStack.getOrDefault(FDataComponentTypes.DISABLE_TEMPERATURE_STATUSES, HolderSet.empty());
        HolderSet<TemperatureStatus> toEnable = currentStack.getOrDefault(FDataComponentTypes.ENABLE_TEMPERATURE_STATUSES, HolderSet.empty());

        this.setEnabled(livingEntity, toDisable, !added);
        this.setEnabled(livingEntity, toEnable, added);
    }

    private void setEnabled(
            LivingEntity livingEntity,
            HolderSet<TemperatureStatus> statuses,
            boolean value
    ) {
        for (Holder<TemperatureStatus> status : statuses) {
            if (status instanceof Holder.Reference<TemperatureStatus> ref) {
                TemperatureStatusLookup.setEnabled(livingEntity, ref, value);
            }
        }
    }
}