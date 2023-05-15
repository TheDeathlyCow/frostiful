package com.github.thedeathlycow.frostiful.mixins.world;

import com.github.thedeathlycow.frostiful.world.spawner.WindSpawner;
import net.minecraft.server.world.ServerChunkManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerChunkManager.class)
public class ServerChunkManagerMixin {

    private int frostiful$tickCount = 0;

    @Inject(
            method = "tickChunks",
            at = @At("HEAD")
    )
    private void resetWindSpawnCount(CallbackInfo ci) {
        if ((++frostiful$tickCount) % 20 == 0) {
            WindSpawner.INSTANCE.resetWindSpawnCount();
            frostiful$tickCount = 0;
        }

    }

}
