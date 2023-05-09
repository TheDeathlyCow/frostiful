package com.github.thedeathlycow.frostiful.mixins.block;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.block.FrozenTorchBlock;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import com.github.thedeathlycow.frostiful.sound.FSoundEvents;
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

            if (!Frostiful.getConfig().freezingConfig.isWindDestroysTorches()) {
                return;
            }

            if (!world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                return;
            }

            if (state.isIn(FBlockTags.FROZEN_TORCHES)) {
                return; // dont need to freeze frozen torches
            }

            @Nullable
            BlockState blownOutState;

            if (state.isIn(FBlockTags.IS_OPEN_FLAME)) {
                blownOutState = state.getFluidState().getBlockState();
            } else if (
                    state.isIn(FBlockTags.HAS_OPEN_FLAME)
                            && state.contains(Properties.LIT)
                            && state.get(Properties.LIT)
            ) {
                blownOutState = state.with(Properties.LIT, false);
            } else {
                blownOutState = FrozenTorchBlock.freezeTorch(state);
            }

            if (blownOutState != null) {
                world.setBlockState(pos, blownOutState);

                entity.playSound(FSoundEvents.ENTITY_FREEZING_WIND_BLOWOUT, 1.0f, 1.0f);

            }
        }
    }

}
