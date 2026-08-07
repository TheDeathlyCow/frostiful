package com.github.thedeathlycow.frostiful.neoforge.mixin;


import com.github.thedeathlycow.frostiful.survival.system.IceSkateSystem;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
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
                    target = "Lnet/minecraft/world/level/block/state/BlockState;getFriction(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;)F"
            )
    )
    private float setSlipperinessForIceSkates(BlockState instance, LevelReader levelReader, BlockPos blockPos, Entity entity, Operation<Float> original) {
        if (IceSkateSystem.isIceSkating((LivingEntity) (Object) this)) {
            return IceSkateSystem.getSlipperinessForEntity(this);
        }

        return original.call(instance, levelReader, blockPos, entity);
    }
}