package com.github.thedeathlycow.frostiful.mixins.entity;

import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.util.survival.FrostHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityPowderSnowMixin extends Entity {

    @Shadow
    public abstract double getAttributeValue(EntityAttribute attribute);

    public LivingEntityPowderSnowMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyArg(
            method = "tickMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;setFrozenTicks(I)V",
                    ordinal = 0
            )
    )
    private int tickFreezingInPowderSnow(int par1) {
        FrostifulConfig config = Frostiful.getConfig();

        FrostHelper.addLivingFrost((LivingEntity) (Object) this, config.freezingConfig.getPowderSnowFreezeRate());

        // returns the current frozen ticks as the mixin applies to a setFrozenTicks argument
        // so the result is no change
        return this.getFrozenTicks();
    }

    @ModifyArg(
            method = "tickMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;setFrozenTicks(I)V",
                    ordinal = 1
            )
    )
    private int doNotThawNormally(int par1) {
        // returns the current frozen ticks as the mixin applies to a setFrozenTicks argument
        // so the result is no change
        return this.getFrozenTicks();
    }

    @Inject(
            method = "addPowderSnowSlowIfNeeded",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void blockDefaultPowderSnowSlow(CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(
            method = "removePowderSnowSlow",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void dontNeedToRemovePowderSnowSlow(CallbackInfo ci) {
        ci.cancel();
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

}
