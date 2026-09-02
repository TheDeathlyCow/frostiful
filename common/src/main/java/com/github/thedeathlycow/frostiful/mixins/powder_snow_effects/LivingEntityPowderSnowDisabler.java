/*
 * Frostiful: A Vanilla+ Freezing Temperature Mod. Also try Scorchful!
 * Copyright (C) 2026	TheDeathlyCow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.  If not, see
 * <https://www.gnu.org/licenses/>.
 */

package com.github.thedeathlycow.frostiful.mixins.powder_snow_effects;

import com.github.thedeathlycow.thermoo.api.core.v2.TemperatureAware;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityPowderSnowDisabler extends Entity implements TemperatureAware {

    public LivingEntityPowderSnowDisabler(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Inject(
            method = "canFreeze",
            at = @At(
                    value = "TAIL"
            ),
            cancellable = true
    )
    private void overrideLeatherArmourFreezeImmunity(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(super.canFreeze());
    }

    @ModifyArg(
            method = "aiStep",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;setTicksFrozen(I)V"
            )
    )
    private int disableTicksFreezingIncreaseInPowderSnow(int par1) {
        return this.getTicksFrozen();
    }

    @Inject(
            method = "tryAddFrost",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void blockPowderSnowSlow(CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(
            method = "removeFrost",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void dontNeedToRemovePowderSnowSlow(CallbackInfo ci) {
        ci.cancel();
    }


    @ModifyArg(
            method = "aiStep",
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/world/entity/LivingEntity;tryAddFrost()V"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;hurtServer(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;F)Z",
                    ordinal = 0
            ),
            index = 2
    )
    private float blockPowderSnowFreezeDamage(float amount) {
        return 0f;
    }
}
