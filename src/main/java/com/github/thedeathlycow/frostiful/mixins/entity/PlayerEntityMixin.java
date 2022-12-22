package com.github.thedeathlycow.frostiful.mixins.entity;

import com.github.thedeathlycow.frostiful.attributes.FEntityAttributes;
import com.github.thedeathlycow.frostiful.entity.SoakableEntity;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.util.survival.FrostHelper;
import com.github.thedeathlycow.frostiful.util.survival.PassiveFreezingHelper;
import com.github.thedeathlycow.frostiful.util.survival.SoakingHelper;
import com.github.thedeathlycow.frostiful.world.FGameRules;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.profiler.Profiler;
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

        Profiler profiler = world.getProfiler();
        profiler.push("frostiful.passiveFreezingTick");

        final boolean doPassiveFreezing = Frostiful.getConfig().freezingConfig.doPassiveFreezing()
                && world.getGameRules().getBoolean(FGameRules.DO_PASSIVE_FREEZING);

        //* tick passive freezing
        if (doPassiveFreezing) {
            int passiveFreezing = PassiveFreezingHelper.getPassiveFreezing(playerEntity);
            if (passiveFreezing > 0) {
                FrostHelper.addLivingFrost(playerEntity, passiveFreezing);
            } else {
                FrostHelper.removeLivingFrost(playerEntity, -passiveFreezing);
            }
        }

        //* tick wetness
        if (!playerEntity.isSpectator()) {
            SoakableEntity soakableEntity = (SoakableEntity) this;

            int current = soakableEntity.frostiful$getWetTicks();
            int wetnessIncrease = SoakingHelper.getWetnessChange(playerEntity);

            soakableEntity.frostiful$setWetTicks(
                    MathHelper.clamp(current + wetnessIncrease, 0, soakableEntity.frostiful$getMaxWetTicks())
            );
        }
        profiler.pop();
    }

    @Inject(
            method = "createPlayerAttributes",
            at = @At("TAIL")
    )
    private static void addFrostResistanceAttribute(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        DefaultAttributeContainer.Builder attributeBuilder = cir.getReturnValue();
        attributeBuilder.add(FEntityAttributes.FROST_RESISTANCE);
        attributeBuilder.add(FEntityAttributes.MAX_FROST, 45.0d);
    }
}
