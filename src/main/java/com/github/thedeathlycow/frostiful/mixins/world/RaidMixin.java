package com.github.thedeathlycow.frostiful.mixins.world;

import com.github.thedeathlycow.frostiful.server.world.ChillagerRaidSpawnerUtil;
import com.llamalad7.mixinextras.injector.ModifyReceiver;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.raid.Raid;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Raid.class)
public class RaidMixin {
    @Shadow @Final private ServerWorld world;

    @Inject(
            method = "spawnNextWave",
            at = @At("HEAD")
    )
    private void trackIsBiomeCold(
            BlockPos pos,
            CallbackInfo ci,
            @Share("isBiomeCold") LocalBooleanRef isBiomeCold
    ) {
        Biome biome = this.world.getBiome(pos).value();

        isBiomeCold.set(biome.isCold(pos));
    }

    @ModifyReceiver(
            method = "spawnNextWave",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/EntityType;create(Lnet/minecraft/world/World;)Lnet/minecraft/entity/Entity;"
            )
    )
    private EntityType<?> replacePillagersWithChillagers(
            EntityType<?> instance,
            World world,
            @Share("isBiomeCold") LocalBooleanRef isBiomeCold
    ) {
        return ChillagerRaidSpawnerUtil.replaceRaidersInColdBiomes(instance, isBiomeCold.get());
    }
}