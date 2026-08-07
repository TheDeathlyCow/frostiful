package com.github.thedeathlycow.frostiful.fabric.mixin;

import com.github.thedeathlycow.frostiful.survival.system.IceSkateSystem;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @WrapOperation(
            method = "travelInAir",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Block;getFriction()F"
            )
    )
    private float setSlipperinessForIceSkates(Block instance, Operation<Float> original) {
        if (IceSkateSystem.isIceSkating((LivingEntity) (Object) this)) {
            return IceSkateSystem.getSlipperinessForEntity(this);
        }

        return original.call(instance);
    }
}