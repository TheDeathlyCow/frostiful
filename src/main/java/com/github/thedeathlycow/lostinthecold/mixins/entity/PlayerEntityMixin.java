package com.github.thedeathlycow.lostinthecold.mixins.entity;

import com.github.thedeathlycow.lostinthecold.attributes.LostInTheColdEntityAttributes;
import com.github.thedeathlycow.lostinthecold.config.LostInTheColdConfig;
import com.github.thedeathlycow.lostinthecold.config.ConfigKeys;
import com.github.thedeathlycow.lostinthecold.init.LostInTheCold;
import com.github.thedeathlycow.lostinthecold.world.survival.TemperatureController;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
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

        int ticksFrozen = playerEntity.getFrozenTicks();

        if (TemperatureController.canPassivelyFreeze(playerEntity)) {
            ticksFrozen += TemperatureController.getPassiveFreezing(playerEntity);
        }

        ticksFrozen -= TemperatureController.getWarmth(playerEntity);

        if (ticksFrozen < 0) {
            ticksFrozen = 0;
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
        attributeBuilder.add(LostInTheColdEntityAttributes.FROST_RESISTANCE, config.get(ConfigKeys.BASE_PLAYER_FROST_RESISTANCE));
        cir.setReturnValue(attributeBuilder);
    }
}
