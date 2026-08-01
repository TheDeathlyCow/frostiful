package com.github.thedeathlycow.frostiful.mixins.entity;

import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Entity.class)
public interface EntityInvoker {
    @Invoker("isInRain")
    boolean frostiful$invokeIsBeingRainedOn();

    @Invoker("playEntityOnFireExtinguishedSound")
    void frostiful$invokePlayExtinguishSound();
}
