package com.github.thedeathlycow.frostiful.mixins.world.spawner;

import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.server.world.ChillagerPatrolSpawner;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.PatrolSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PatrolSpawner.class)
public class PatrolSpawnerMixin {


    @Inject(
            method = "spawnPatrolMember",
            at = @At("HEAD"),
            cancellable = true
    )
    private void spawnChillagerInColdBiomes(ServerLevel world, BlockPos pos, RandomSource random, boolean captain, CallbackInfoReturnable<Boolean> cir) {
        if (!FrostifulConfigYACL.combatConfig().doChillagerPatrols()) {
            return;
        }

        var biome = world.getBiome(pos).value();

        if (biome.coldEnoughToSnow(pos, world.getSeaLevel())) {
            cir.setReturnValue(ChillagerPatrolSpawner.spawnChillagerPatrol(world, pos, random, captain));
        }
    }

}
