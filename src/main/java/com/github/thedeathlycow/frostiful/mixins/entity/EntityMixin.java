package com.github.thedeathlycow.frostiful.mixins.entity;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
abstract class EntityMixin {

//    @Redirect(
//            method = "baseTick",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/entity/Entity;getFrozenTicks()I"
//            )
//    )
//    private int doNotExtinguishColdPlayers(Entity instance) {
//        return 0;
//    }
}
