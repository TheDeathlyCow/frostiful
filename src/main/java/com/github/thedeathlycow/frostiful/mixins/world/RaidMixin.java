package com.github.thedeathlycow.frostiful.mixins.world;

import com.github.thedeathlycow.frostiful.server.world.ChillagerRaidSpawnerUtil;
import com.llamalad7.mixinextras.injector.ModifyReceiver;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Raid.class)
public class RaidMixin {
    @Inject(
            method = "spawnGroup",
            at = @At("HEAD")
    )
    private void trackIsBiomeCold(
            ServerLevel world,
            BlockPos pos,
            CallbackInfo ci,
            @Share("isBiomeCold") LocalBooleanRef isBiomeCold
    ) {
        Biome biome = world.getBiome(pos).value();

        isBiomeCold.set(biome.coldEnoughToSnow(pos, world.getSeaLevel()));
    }

    @ModifyReceiver(
            method = "spawnGroup",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/EntityType;create(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/EntitySpawnReason;)Lnet/minecraft/world/entity/Entity;"
            )
    )
    private EntityType<?> replacePillagersWithChillagers(
            EntityType<?> instance,
            Level world,
            EntitySpawnReason reason,
            @Share("isBiomeCold") LocalBooleanRef isBiomeCold
    ) {
        return ChillagerRaidSpawnerUtil.replaceRaidersInColdBiomes(instance, isBiomeCold.get());
    }
}