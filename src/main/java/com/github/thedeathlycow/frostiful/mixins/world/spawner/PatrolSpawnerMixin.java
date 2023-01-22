package com.github.thedeathlycow.frostiful.mixins.world.spawner;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.world.spawner.ChillagerPatrolSpawner;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.spawner.PatrolSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PatrolSpawner.class)
public class PatrolSpawnerMixin {


    @Inject(
            method = "spawnPillager",
            at = @At("HEAD"),
            cancellable = true
    )
    private void spawnChillagerInColdBiomes(ServerWorld world, BlockPos pos, Random random, boolean captain, CallbackInfoReturnable<Boolean> cir) {
        if (!Frostiful.getConfig().combatConfig.doChillagerPatrols()) {
            return;
        }

        var biome = world.getBiome(pos).value();

        if (biome.isCold(pos)) {
            cir.setReturnValue(ChillagerPatrolSpawner.spawnChillagerPatrol(world, pos, random, captain));
        }
    }

}
