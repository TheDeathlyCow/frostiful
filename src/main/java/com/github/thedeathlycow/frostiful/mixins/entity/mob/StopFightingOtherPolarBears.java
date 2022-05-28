package com.github.thedeathlycow.frostiful.mixins.entity.mob;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.entity.passive.PolarBearEntity$PolarBearEscapeDangerGoal")
public abstract class StopFightingOtherPolarBears extends EscapeDangerGoal {

    public StopFightingOtherPolarBears(PathAwareEntity mob, double speed) {
        super(mob, speed);
    }

    @Inject(
            method = "isInDanger",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void dontGetRevengeOnOtherPolarBears(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity attacker = this.mob.getAttacker();
        if (attacker != null && attacker.getType() == EntityType.POLAR_BEAR) {
            cir.setReturnValue(true);
        }
    }
}
