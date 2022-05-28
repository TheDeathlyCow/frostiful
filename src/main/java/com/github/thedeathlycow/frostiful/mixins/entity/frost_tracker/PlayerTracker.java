package com.github.thedeathlycow.frostiful.mixins.entity.frost_tracker;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerEntity.class)
public abstract class PlayerTracker extends LivingEntityTracker {


    @Shadow public abstract boolean isCreative();

    @Override
    @Unique
    public boolean frostiful$canApplyFrost() {
        return !this.isCreative() && super.frostiful$canApplyFrost();
    }

}
