package com.github.thedeathlycow.frostiful.mixins.entity;

import com.github.thedeathlycow.frostiful.attributes.FrostifulEntityAttributes;
import com.github.thedeathlycow.frostiful.config.GlobalConfig;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.util.survival.FrostHelper;
import com.github.thedeathlycow.frostiful.util.survival.PassiveFreezingHelper;
import com.github.thedeathlycow.frostiful.world.FrostifulGameRules;
import com.github.thedeathlycow.simple.config.Config;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
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
        World world = playerEntity.getWorld();

        if (world.isClient()) {
            return;
        }

        final boolean doPassiveFreezing = world.getGameRules().getBoolean(FrostifulGameRules.DO_PASSIVE_FREEZING);
        if (doPassiveFreezing) {
            int passiveFreezing = PassiveFreezingHelper.getPassiveFreezing(playerEntity);

            if (passiveFreezing > 0) {
                FrostHelper.addFrost(playerEntity, passiveFreezing);
            } else {
                FrostHelper.removeFrost(playerEntity, -passiveFreezing);
            }
        }

        int warmth = PassiveFreezingHelper.getWarmth(playerEntity);
        FrostHelper.removeFrost(playerEntity, warmth);

    }

    @Inject(
            method = "createPlayerAttributes",
            at = @At("TAIL"),
            cancellable = true
    )
    private static void addFrostResistanceAttribute(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        Config config = Frostiful.getConfig();
        DefaultAttributeContainer.Builder attributeBuilder = cir.getReturnValue();
        attributeBuilder.add(FrostifulEntityAttributes.FROST_RESISTANCE, config.get(GlobalConfig.BASE_PLAYER_FROST_RESISTANCE));
        attributeBuilder.add(FrostifulEntityAttributes.MAX_FROST, config.get(GlobalConfig.PLAYER_MAX_FROST));
        cir.setReturnValue(attributeBuilder);
    }
}
