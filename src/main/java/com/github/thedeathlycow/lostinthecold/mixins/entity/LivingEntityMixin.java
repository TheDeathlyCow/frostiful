package com.github.thedeathlycow.lostinthecold.mixins.entity;

import com.github.thedeathlycow.lostinthecold.config.HypothermiaConfig;
import com.github.thedeathlycow.lostinthecold.init.LostInTheCold;
import com.github.thedeathlycow.lostinthecold.tag.biome.BiomeTemperatureTags;
import com.github.thedeathlycow.lostinthecold.world.ModGameRules;
import net.minecraft.block.Block;
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

        HypothermiaConfig config = LostInTheCold.getConfig();
        if (config == null) {
            LostInTheCold.LOGGER.warn("LivingEntityMixin: Hypothermia config not found!");
            return;
        }

        World world = livingEntity.getWorld();
        int ticksFrozen = livingEntity.getFrozenTicks();
        BlockPos pos = livingEntity.getBlockPos();

        int ticksToAdd = this.getBiomeFreezing(livingEntity, world, pos, config);
        ticksToAdd *= this.getMultiplier(livingEntity, config);
        ticksToAdd -= this.getWarmth(livingEntity, world, pos, config);
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

    private int getWarmth(LivingEntity livingEntity, World world, BlockPos pos, HypothermiaConfig config) {
        int warmth = 0;

        int lightLevel = world.getLightLevel(LightType.BLOCK, pos);
        if (lightLevel >= config.getMinWarmthLightLevel()) {
            warmth += config.getWarmthPerLightLevel() * (lightLevel - config.getMinWarmthLightLevel());
        }

        if (livingEntity.isOnFire()) {
            warmth += config.getOnFireFreezeRate();
        }
        return warmth;
    }

    private double getMultiplier(LivingEntity livingEntity, HypothermiaConfig config) {

        double multiplier = 1.0D;

        if (livingEntity.inPowderSnow) {
            multiplier += config.getPowderSnowFreezeRateMultiplier();
        }

        if (livingEntity.isWet()) {
            multiplier += config.getWetFreezeRateMultiplier();
        }

        return multiplier;
    }

    private int getBiomeFreezing(LivingEntity livingEntity, World world, BlockPos pos, HypothermiaConfig config) {
        RegistryEntry<Biome> biomeIn = world.getBiome(pos);

        if (!livingEntity.canFreeze() || !world.getGameRules().getBoolean(ModGameRules.DO_PASSIVE_FREEZING)) {
            return config.getWarmBiomeFreezeRate();
        }

        if (biomeIn.isIn(BiomeTemperatureTags.IS_CHILLY)) {
            return config.getChillyBiomeFreezeRate();
        } else if (biomeIn.isIn(BiomeTemperatureTags.IS_COLD)) {
            return config.getColdBiomeFreezeRate();
        } else if (biomeIn.isIn(BiomeTemperatureTags.IS_FREEZING)) {
            return config.getFreezingBiomeFreezeRate();
        } else {
            return config.getWarmBiomeFreezeRate();
        }
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
