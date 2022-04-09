package com.github.thedeathlycow.lostinthecold.mixins.entity;

import com.github.thedeathlycow.lostinthecold.attributes.ModEntityAttributes;
import com.github.thedeathlycow.lostinthecold.config.HypothermiaConfig;
import com.github.thedeathlycow.lostinthecold.init.LostInTheCold;
import com.github.thedeathlycow.lostinthecold.world.ModGameRules;
import com.github.thedeathlycow.lostinthecold.world.survival.TemperatureController;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;tick()V",
                    ordinal = 0,
                    shift = At.Shift.AFTER
            )
    )
    private void tickTemperature(CallbackInfo ci) {
        PlayerEntity playerEntity = (PlayerEntity) (Object) this;

        World world = playerEntity.getWorld();
        if (!world.getGameRules().getBoolean(ModGameRules.DO_PASSIVE_FREEZING)) {
            return;
        }

        HypothermiaConfig config = LostInTheCold.getConfig();
        if (config == null) {
            LostInTheCold.LOGGER.warn("PlayerEntityMixin: Hypothermia config not found!");
            return;
        }

        int ticksFrozen = playerEntity.getFrozenTicks();
        BlockPos pos = playerEntity.getBlockPos();

        int ticksToAdd = TemperatureController.getBiomeFreezing(playerEntity, world, pos, config);
        ticksToAdd *= TemperatureController.getMultiplier(playerEntity, config);
        ticksToAdd -= TemperatureController.getWarmth(playerEntity, world, pos, config);
        ticksFrozen += ticksToAdd;

        if (ticksFrozen < 0) {
            ticksFrozen = 0;
        }

        int freezeDamageThreshold = playerEntity.getMinFreezeDamageTicks();
        if (ticksFrozen > freezeDamageThreshold) {
            ticksFrozen = freezeDamageThreshold;
        }

        playerEntity.setFrozenTicks(ticksFrozen);
    }

    @Inject(
            method = "createPlayerAttributes",
            at = @At("TAIL"),
            cancellable = true
    )
    private static void addFrostResistanceAttribute(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        HypothermiaConfig config = LostInTheCold.getConfig();
        if (config == null) {
            LostInTheCold.LOGGER.warn("LivingEntityAttributes: Hypothermia config not found!");
            return;
        }

        DefaultAttributeContainer.Builder attributeBuilder = cir.getReturnValue();
        attributeBuilder.add(ModEntityAttributes.FROST_RESISTANCE, config.getBaseEntityFrostResistance());
        cir.setReturnValue(attributeBuilder);
    }

    private boolean canFreeze(PlayerEntity player) {
        return !player.isCreative() && player.canFreeze();
    }
}
