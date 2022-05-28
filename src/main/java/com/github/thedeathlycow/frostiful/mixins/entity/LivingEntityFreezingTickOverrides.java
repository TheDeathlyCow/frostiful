package com.github.thedeathlycow.frostiful.mixins.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LivingEntity.class)
public abstract class LivingEntityFreezingTickOverrides extends Entity {

    protected LivingEntityFreezingTickOverrides(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
}
