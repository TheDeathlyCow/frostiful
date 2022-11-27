package com.github.thedeathlycow.frostiful.mixins.server;

import com.github.thedeathlycow.frostiful.block.FBlocks;
import com.github.thedeathlycow.frostiful.block.IcicleBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.Heightmap;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;

@Mixin(ServerWorld.class)
public abstract class IcicleGrowthMixin {


    @Inject(
            method = "tickChunk",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/world/ServerWorld;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z"
            ),
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/world/biome/Biome;canSetSnow(Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;)Z"
                    )
            )
    )
    private void doIcicleGrowth(WorldChunk chunk, int randomTickSpeed, CallbackInfo ci) {
        final ServerWorld instance = (ServerWorld) (Object) this;
        final Random random = instance.random;

        // slow down icicles appearing in world
        if (random.nextInt(5) != 0) {
            return;
        }

        Profiler profiler = instance.getProfiler();
        profiler.push("frostiful.icicle_growth_tick");

        final ChunkPos chunkPos = chunk.getPos();
        final BlockPos startPos = instance.getTopPosition(
                Heightmap.Type.WORLD_SURFACE,
                instance.getRandomPosInChunk(chunkPos.getStartX(), 0, chunkPos.getStartZ(), 15)
        );

        final BlockState downwardIcicle = FBlocks.ICICLE.getDefaultState()
                .with(IcicleBlock.VERTICAL_DIRECTION, Direction.DOWN);

        Predicate<BlockPos> validCondition = (testPos) -> {
            BlockState at = instance.getBlockState(testPos);

            return at.isAir() && downwardIcicle.canPlaceAt(instance, testPos);
        };

        BlockPos.Mutable placePos = startPos.mutableCopy();
        for (int i = 0; i < 5; i++) {
            if (validCondition.test(placePos)) {
                instance.setBlockState(placePos, downwardIcicle, Block.NOTIFY_ALL);
                return;
            }
            placePos.move(Direction.DOWN);
        }

        profiler.pop();
    }

}
