package com.github.thedeathlycow.frostiful.mixins.entity.root;

import com.github.thedeathlycow.frostiful.entity.RootedEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTracker;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DamageTracker.class)
public class RootShatterMixin {

    @Shadow @Final private LivingEntity entity;

    @Inject(
            method = "onDamage",
            at = @At("HEAD")
    )
    private void breakIceIfRooted(DamageSource damageSource, float damage, CallbackInfo ci) {
        RootedEntity.breakRootOnEntity(this.entity);
    }

}
