package com.github.thedeathlycow.frostiful.mixins.block;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.block.FrozenTorchBlock;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import com.github.thedeathlycow.frostiful.sound.FSoundEvents;
import com.github.thedeathlycow.frostiful.survival.wind.WindManager;
import com.github.thedeathlycow.frostiful.tag.FBlockTags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractBlock.class)
public abstract class WindBlowoutMixin {

    @Inject(
            method = "onEntityCollision",
            at = @At("HEAD")
    )
    private void onCollideWithFreezingTorch(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci) {
        if (!world.isClient && entity.getType() == FEntityTypes.FREEZING_WIND) {
            WindManager.INSTANCE.extinguishBlock(state, world, pos, entity);
        }
    }



}
