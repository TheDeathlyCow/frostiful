package com.github.thedeathlycow.frostiful.mixins.entity.root;

import com.github.thedeathlycow.frostiful.entity.RootedEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerMovementBlockMixin {

    @Inject(
            method = "adjustMovementForSneaking",
            at = @At("HEAD"),
            cancellable = true
    )
    private void blockMovementForRootedEntities(Vec3d movement, MovementType type, CallbackInfoReturnable<Vec3d> cir) {
        Entity instance = (Entity) (Object) this;

        if (instance instanceof RootedEntity rootedEntity && rootedEntity.frostiful$isRooted()) {
            switch (type) {
                case SELF, PLAYER -> cir.setReturnValue(Vec3d.ZERO);
                default -> {}
            }
        }
    }

}
