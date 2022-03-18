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
import org.spongepowered.asm.mixin.injection.Redirect;
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
        } else {
            ticksToAdd = -5;
        }

        if (instance.isWet()) {
            ticksToAdd *= 1.5f;
        }

        if (instance.inPowderSnow) {
            ticksToAdd = 100;
        }

        if (instance.isOnFire()) {
            ticksToAdd = -100;
        }

        ticksFrozen += ticksToAdd;

        if (ticksFrozen < 0) {
            ticksFrozen = 0;
        }

        int freezeDamageThreshold = instance.getMinFreezeDamageTicks();
        if (ticksFrozen > freezeDamageThreshold) {
            ticksFrozen = freezeDamageThreshold;
        }

        instance.setFrozenTicks(ticksFrozen);
    }

    @Redirect(
            method = "tickMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;setFrozenTicks(I)V"
            )
    )
    private void blockDefaultPowderSnowFreezing(LivingEntity instance, int i) {

    }

}
