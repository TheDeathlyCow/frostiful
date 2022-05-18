package com.github.thedeathlycow.frostiful.mixins.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityFreezeOverrides extends LivingEntity {
    @Shadow public abstract boolean isCreative();

    protected PlayerEntityFreezeOverrides(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean canFreeze() {
        return !this.isCreative() && super.canFreeze();
    }
}
