package com.github.thedeathlycow.frostiful.mixins.entity;

import net.minecraft.entity.FallingBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FallingBlockEntity.class)
public interface FallingBlockEntityAccessor {


    @Accessor("destroyedOnLanding")
    void frostiful$setDestroyOnLanding(boolean value);

}
