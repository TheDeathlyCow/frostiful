package com.github.thedeathlycow.frostiful.mixins.server;

import com.github.thedeathlycow.frostiful.block.FBlocks;
import com.github.thedeathlycow.frostiful.block.IcicleBlock;
import com.github.thedeathlycow.frostiful.config.group.IcicleConfigGroup;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.Heightmap;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
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
                    target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V",
                    shift = At.Shift.AFTER,
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/block/Block;precipitationTick(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/biome/Biome$Precipitation;)V"
                    )
            )
    )
    private void doIcicleGrowth(WorldChunk chunk, int randomTickSpeed, CallbackInfo ci) {
        final ServerWorld instance = (ServerWorld) (Object) this;
        final Random random = instance.random;

        IcicleConfigGroup icicleConfig = Frostiful.getConfig().icicleConfig;
        if (!icicleConfig.iciclesFormInWeather()) {
            return;
        }

        if (!instance.isRaining()) {
            return;
        }

        // slow down icicles appearing in world
        if (random.nextInt(16 * 5) != 0) {
            return;
        }

        Profiler profiler = instance.getProfiler();
        profiler.push("frostiful.icicle_growth_tick");

        final ChunkPos chunkPos = chunk.getPos();
        final BlockPos startPos = instance.getTopPosition(
                Heightmap.Type.WORLD_SURFACE,
                instance.getRandomPosInChunk(chunkPos.getStartX(), 0, chunkPos.getStartZ(), 15)
        );

        if (instance.getBiome(startPos).value().doesNotSnow(startPos)) {
            profiler.pop();
            return;
        }

        final BlockState downwardIcicle = FBlocks.ICICLE.getDefaultState()
                .with(IcicleBlock.VERTICAL_DIRECTION, Direction.DOWN);

        World world = chunk.getWorld();
        Predicate<BlockPos> validCondition = (testPos) -> {
            BlockState at = instance.getBlockState(testPos);

            // testing for sky light helps increase the chance that icicles will only ever form outside
            return at.isAir()
                    && world.getLightLevel(LightType.SKY, testPos) >= icicleConfig.getMinSkylightLevelToForm()
                    && downwardIcicle.canPlaceAt(instance, testPos);
        };

        BlockPos.Mutable placePos = startPos.mutableCopy();
        for (int i = 0; i < 5; i++) {

            if (validCondition.test(placePos)) {
                // only place if can place and light is not blocking it
                if (world.getLightLevel(LightType.BLOCK, placePos) < icicleConfig.getMaxLightLevelToForm()) {
                    instance.setBlockState(placePos, downwardIcicle, Block.NOTIFY_ALL);
                }

                // if can place but there is light blocking - stop looking
                profiler.pop();
                return;
            }
            placePos.move(Direction.DOWN);
        }

        profiler.pop();
    }

}
