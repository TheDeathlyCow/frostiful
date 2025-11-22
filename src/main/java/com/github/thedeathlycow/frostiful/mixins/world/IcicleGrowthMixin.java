package com.github.thedeathlycow.frostiful.mixins.world;

import com.github.thedeathlycow.frostiful.server.world.IcicleWeatherGenerator;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public abstract class IcicleGrowthMixin {

    @Inject(
            method = "tickChunk",
            at = @At("TAIL")
    )
    private void doIcicleGrowth(LevelChunk chunk, int randomTickSpeed, CallbackInfo ci) {
        final ServerLevel instance = (ServerLevel) (Object) this;
        ProfilerFiller profiler = Profiler.get();
        profiler.push("frostiful.icicle_growth_tick");
        IcicleWeatherGenerator.tickIciclesForChunk(instance, chunk, randomTickSpeed);
        profiler.pop();
    }

}
