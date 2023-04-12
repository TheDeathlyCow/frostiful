package com.github.thedeathlycow.frostiful.mixins.entity;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.thermoo.api.temperature.HeatingModes;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class BurnDamageMixin {

    @Inject(
            method = "damage",
            at = @At("TAIL")
    )
    private void applyHeatFromBurnDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {


        if (!cir.getReturnValue() || source != DamageSource.HOT_FLOOR) {
            return;
        }

        LivingEntity instance = (LivingEntity) (Object) this;

        if (instance.thermoo$isWarm() || EnchantmentHelper.hasFrostWalker(instance)) {
            return;
        }

        int heat = Frostiful.getConfig().freezingConfig.getHeatFromHotFloor();
        instance.thermoo$addTemperature(heat, HeatingModes.ACTIVE);
    }

}
