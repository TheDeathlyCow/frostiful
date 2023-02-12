package com.github.thedeathlycow.frostiful.mixins.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(LivingEntity.class)
public abstract class LivingEntityFreezeDamageConfigMixin extends Entity {

    public LivingEntityFreezeDamageConfigMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyArg(
            method = "tickMovement",
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/entity/LivingEntity;addPowderSnowSlowIfNeeded()V"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z",
                    ordinal = 0
            ),
            index = 1
    )
    private float blockFreezeDamage(float amount) {
        return 0f;
    }
}
