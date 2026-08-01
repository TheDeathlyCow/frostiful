package com.github.thedeathlycow.frostiful.mixins.entity;

import net.minecraft.world.entity.item.FallingBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FallingBlockEntity.class)
public interface FallingBlockEntityAccessor {


    @Accessor("cancelDrop")
    void frostiful$setDestroyOnLanding(boolean value);

}
