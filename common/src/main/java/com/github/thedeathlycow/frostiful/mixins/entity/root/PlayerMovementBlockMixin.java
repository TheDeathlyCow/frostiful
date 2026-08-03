package com.github.thedeathlycow.frostiful.mixins.entity.root;

import com.github.thedeathlycow.frostiful.survival.system.FrostRootSystem;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * This Mixin is necessary since the targeted injection method is overridden by players without a super call
 */
@Mixin(Player.class)
public class PlayerMovementBlockMixin {
    @Inject(
            method = "maybeBackOffFromEdge",
            at = @At("HEAD"),
            cancellable = true
    )
    private void blockMovementForRootedEntities(Vec3 movement, MoverType type, CallbackInfoReturnable<Vec3> cir) {
        Player instance = (Player) (Object) this;

        Vec3 adjustedMovement = FrostRootSystem.adjustMovementForRoot(type, movement, instance);
        if (adjustedMovement != null) {
            cir.setReturnValue(adjustedMovement);
        }
    }
}
