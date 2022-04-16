package com.github.thedeathlycow.lostinthecold.mixins.server;

import com.github.thedeathlycow.lostinthecold.world.ModGameRules;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {

    @Redirect(
            method = "tickChunk",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/world/ServerWorld;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z"
            ),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/Biome;canSetSnow(Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;)Z")
            )
    )
    private boolean doSnowBuildup(ServerWorld instance, BlockPos blockPos, BlockState blockState) {
        BlockState current = instance.getBlockState(blockPos);
        if (!instance.getGameRules().getBoolean(ModGameRules.DO_SNOW_BUILD_UP)) {
            if (current.isAir()) {
                return instance.setBlockState(blockPos, blockState);
            } else {
                return false;
            }
        }

        if (current.isOf(Blocks.SNOW)) {
            int layers = Math.min(SnowBlock.MAX_LAYERS, current.get(SnowBlock.LAYERS) + 1);
            blockState = current.with(SnowBlock.LAYERS, layers);
        }
        return instance.setBlockState(blockPos, blockState);
    }

}
