package com.github.thedeathlycow.frostiful.mixins.world;

import com.github.thedeathlycow.frostiful.server.world.IcicleWeatherGenerator;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public abstract class IcicleGrowthMixin {

    @Inject(
            method = "tickChunk",
            at = @At("TAIL")
    )
    private void doIcicleGrowth(WorldChunk chunk, int randomTickSpeed, CallbackInfo ci) {
        final ServerWorld instance = (ServerWorld) (Object) this;
        Profiler profiler = instance.getProfiler();
        profiler.push("frostiful.icicle_growth_tick");
        IcicleWeatherGenerator.tickIciclesForChunk(instance, chunk, randomTickSpeed);
        profiler.pop();
    }

}
