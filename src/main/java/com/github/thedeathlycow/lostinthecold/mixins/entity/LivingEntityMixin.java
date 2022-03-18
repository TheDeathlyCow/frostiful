package com.github.thedeathlycow.lostinthecold.mixins.entity;

import com.github.thedeathlycow.lostinthecold.tag.biome.BiomeTemperatureTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.tag.BiomeTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(
            method = "tickMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;getFrozenTicks()I",
                    ordinal = 0,
                    shift = At.Shift.BEFORE
            )
    )
    private void tickTemperature(CallbackInfo ci) {
        LivingEntity instance = (LivingEntity)(Object)this;
        int ticksFrozen = instance.getFrozenTicks();

        World world = instance.getWorld();
        BlockPos pos = instance.getBlockPos();
        RegistryEntry<Biome> biomeIn = world.getBiome(pos);

        int ticksToAdd = 0;
        if (biomeIn.isIn(BiomeTemperatureTags.IS_CHILLY)) {
            ticksToAdd = 1;
        } else if (biomeIn.isIn(BiomeTemperatureTags.IS_COLD)) {
            ticksToAdd = 3;
        } else if (biomeIn.isIn(BiomeTemperatureTags.IS_FREEZING)) {
            ticksToAdd = 10;
        }
        instance.setFrozenTicks(ticksFrozen + ticksToAdd);
    }

}
