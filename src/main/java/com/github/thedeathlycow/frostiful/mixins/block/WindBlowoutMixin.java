package com.github.thedeathlycow.frostiful.mixins.block;

import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import com.github.thedeathlycow.frostiful.registry.FSoundEvents;
import com.github.thedeathlycow.frostiful.survival.wind.WindManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockBehaviour.class)
public abstract class WindBlowoutMixin {

    @Inject(
            method = "entityInside",
            at = @At("HEAD")
    )
    private void onCollideWithFreezingTorch(BlockState state, Level world, BlockPos pos, Entity entity, CallbackInfo ci) {
        if (!world.isClientSide && entity.getType() == FEntityTypes.FREEZING_WIND) {
            WindManager.INSTANCE.extinguishBlock(state, world, pos, () -> entity.playSound(FSoundEvents.ENTITY_FREEZING_WIND_BLOWOUT, 1.0f, 1.0f));
        }
    }



}
