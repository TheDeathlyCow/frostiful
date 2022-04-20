package com.github.thedeathlycow.lostinthecold.mixins.entity;

import com.github.thedeathlycow.datapack.config.config.Config;
import com.github.thedeathlycow.lostinthecold.attributes.LostInTheColdEntityAttributes;
import com.github.thedeathlycow.lostinthecold.config.ConfigKeys;
import com.github.thedeathlycow.lostinthecold.init.LostInTheCold;
import com.github.thedeathlycow.lostinthecold.util.survival.FrostHelper;
import com.github.thedeathlycow.lostinthecold.util.survival.PassiveFreezingHelper;
import com.github.thedeathlycow.lostinthecold.world.LostInTheColdGameRules;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;tick()V",
                    ordinal = 0,
                    shift = At.Shift.AFTER
            )
    )
    private void tickFreezing(CallbackInfo ci) {
        PlayerEntity playerEntity = (PlayerEntity) (Object) this;

        boolean doPassiveFreezing = playerEntity.getWorld().getGameRules().getBoolean(LostInTheColdGameRules.DO_PASSIVE_FREEZING);
        if (doPassiveFreezing) {
            int passiveFreezing = PassiveFreezingHelper.getPassiveFreezing(playerEntity);
            FrostHelper.addFrozenTicks(playerEntity, passiveFreezing);
        }

        int warmth = PassiveFreezingHelper.getWarmth(playerEntity);
        FrostHelper.removeFrozenTicks(playerEntity, warmth);

        LostInTheCold.LOGGER.info("Overlay frozen ticks: " + playerEntity.getFrozenTicks());

    }

    @Inject(
            method = "createPlayerAttributes",
            at = @At("TAIL"),
            cancellable = true
    )
    private static void addFrostResistanceAttribute(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        Config config = LostInTheCold.getConfig();
        DefaultAttributeContainer.Builder attributeBuilder = cir.getReturnValue();
        attributeBuilder.add(LostInTheColdEntityAttributes.FROST_RESISTANCE, config.get(ConfigKeys.BASE_PLAYER_FROST_RESISTANCE));
        attributeBuilder.add(LostInTheColdEntityAttributes.MAX_FROST, config.get(ConfigKeys.PLAYER_MAX_FROST));
        cir.setReturnValue(attributeBuilder);
    }
}
