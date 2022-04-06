package com.github.thedeathlycow.lostinthecold.mixins.entity;

import com.github.thedeathlycow.lostinthecold.config.FreezingValues;
import com.github.thedeathlycow.lostinthecold.tag.biome.BiomeTemperatureTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(
            method = "canFreeze",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void creativePlayersCannotFreeze(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        if (livingEntity instanceof PlayerEntity player) {
            if (player.isCreative()) {
                cir.setReturnValue(false);
            }
        }
    }

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
        LivingEntity livingEntity = (LivingEntity) (Object) this;

        if (!livingEntity.canFreeze()) {
            return;
        }

        int ticksFrozen = livingEntity.getFrozenTicks();

        World world = livingEntity.getWorld();
        BlockPos pos = livingEntity.getBlockPos();
        RegistryEntry<Biome> biomeIn = world.getBiome(pos);

        int ticksToAdd;
        if (biomeIn.isIn(BiomeTemperatureTags.IS_CHILLY)) {
            ticksToAdd = FreezingValues.CHILLY_BIOME_FREEZE_RATE;
        } else if (biomeIn.isIn(BiomeTemperatureTags.IS_COLD)) {
            ticksToAdd = FreezingValues.COLD_BIOME_FREEZE_RATE;
        } else if (biomeIn.isIn(BiomeTemperatureTags.IS_FREEZING)) {
            ticksToAdd = FreezingValues.FREEZING_BIOME_FREEZE_RATE;
        } else {
            ticksToAdd = FreezingValues.WARM_BIOME_FREEZE_RATE;
            ;
        }

        if (livingEntity.inPowderSnow) {
            ticksToAdd = FreezingValues.POWDER_SNOW_FREEZE_RATE;
        }

        if (livingEntity.isWet()) {
            ticksToAdd *= FreezingValues.WET_FREEZE_RATE_MULTIPLIER;
        }

        if (livingEntity.isOnFire()) {
            ticksToAdd = FreezingValues.ON_FIRE_FREEZE_RATE;
        }

        ticksFrozen += ticksToAdd;

        if (ticksFrozen < 0) {
            ticksFrozen = 0;
        }

        int freezeDamageThreshold = livingEntity.getMinFreezeDamageTicks();
        if (ticksFrozen > freezeDamageThreshold) {
            ticksFrozen = freezeDamageThreshold;
        }

        livingEntity.setFrozenTicks(ticksFrozen);
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
