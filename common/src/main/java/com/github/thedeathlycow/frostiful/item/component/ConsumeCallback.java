package com.github.thedeathlycow.frostiful.item.component;

import com.github.thedeathlycow.frostiful.Frostiful;
import dev.yumi.commons.event.Event;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.level.Level;

@FunctionalInterface
public interface ConsumeCallback {
    Event<Identifier, ConsumeCallback> EVENT = Frostiful.EVENT_MANAGER.create(
            ConsumeCallback.class,
            listeners -> (level, user, stack, consumable) -> {
                for (ConsumeCallback listener : listeners) {
                    listener.onConsume(level, user, stack, consumable);
                }
            }
    );

    void onConsume(final Level level, final LivingEntity user, final ItemStack stack, final Consumable consumable);
}