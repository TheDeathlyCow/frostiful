package com.github.thedeathlycow.lostinthecold.mixins.entity;

import com.github.thedeathlycow.lostinthecold.config.HypothermiaConfig;
import com.github.thedeathlycow.lostinthecold.init.LostInTheCold;
import com.github.thedeathlycow.lostinthecold.tag.biome.BiomeTemperatureTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

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

        HypothermiaConfig config = LostInTheCold.getConfig();
        if (config == null) {
            LostInTheCold.LOGGER.warn("LivingEntityMixin: Hypothermia config not found!");
            return;
        }

        int ticksFrozen = livingEntity.getFrozenTicks();

        World world = livingEntity.getWorld();
        BlockPos pos = livingEntity.getBlockPos();
        RegistryEntry<Biome> biomeIn = world.getBiome(pos);

        int ticksToAdd;
        if (biomeIn.isIn(BiomeTemperatureTags.IS_CHILLY)) {
            ticksToAdd = config.getChillyBiomeFreezeRate();
        } else if (biomeIn.isIn(BiomeTemperatureTags.IS_COLD)) {
            ticksToAdd = config.getColdBiomeFreezeRate();
        } else if (biomeIn.isIn(BiomeTemperatureTags.IS_FREEZING)) {
            ticksToAdd = config.getFreezingBiomeFreezeRate();
        } else {
            ticksToAdd = config.getWarmBiomeFreezeRate();
        }

        if (livingEntity.inPowderSnow) {
            ticksToAdd *= config.getPowderSnowFreezeRateMultiplier();
        }

        if (livingEntity.isWet()) {
            ticksToAdd *= config.getWetFreezeRateMultiplier();
        }

        int lightLevel = world.getLightLevel(LightType.BLOCK, pos);
        if (lightLevel >= config.getMinWarmthLightLevel()) {
            ticksToAdd -= config.getWarmthPerLightLevel() * lightLevel;
        }

        if (livingEntity.isOnFire()) {
            ticksToAdd = config.getOnFireFreezeRate();
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
