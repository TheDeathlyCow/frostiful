package com.github.thedeathlycow.lostinthecold.mixins.entity;

import com.github.thedeathlycow.lostinthecold.attributes.ModEntityAttributes;
import com.github.thedeathlycow.lostinthecold.config.LostInTheColdConfig;
import com.github.thedeathlycow.lostinthecold.config.ConfigKeys;
import com.github.thedeathlycow.lostinthecold.init.LostInTheCold;
import com.github.thedeathlycow.lostinthecold.world.ModGameRules;
import com.github.thedeathlycow.lostinthecold.world.survival.TemperatureController;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Shadow public abstract void playSound(SoundEvent sound, float volume, float pitch);

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
        int ticksFrozen = playerEntity.getFrozenTicks();
        BlockPos pos = playerEntity.getBlockPos();

        if (world.getGameRules().getBoolean(ModGameRules.DO_PASSIVE_FREEZING)) {
            ticksFrozen += TemperatureController.getPassiveFreezing(playerEntity, world, pos);
        }

        ticksFrozen -= TemperatureController.getWarmth(playerEntity, world, pos);

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
        LostInTheColdConfig config = LostInTheCold.getConfig();
        DefaultAttributeContainer.Builder attributeBuilder = cir.getReturnValue();
        attributeBuilder.add(ModEntityAttributes.FROST_RESISTANCE, config.getDouble(ConfigKeys.BASE_PLAYER_FROST_RESITANCE));
        cir.setReturnValue(attributeBuilder);
    }
}
