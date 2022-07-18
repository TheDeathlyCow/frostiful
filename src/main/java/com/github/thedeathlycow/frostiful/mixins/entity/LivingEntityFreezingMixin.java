package com.github.thedeathlycow.frostiful.mixins.entity;

import com.github.thedeathlycow.frostiful.attributes.FrostifulEntityAttributes;
import com.github.thedeathlycow.frostiful.config.group.AttributeConfigGroup;
import com.github.thedeathlycow.frostiful.entity.FreezableEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityFreezingMixin implements FreezableEntity {

    @Shadow public abstract AttributeContainer getAttributes();

    @Shadow public abstract boolean canFreeze();

    private int frostiful$frost = 0;

    @Override
    public int frostiful$getCurrentFrost() {
        return this.frostiful$frost;
    }

    @Override
    public int frostiful$getMaxFrost() {
        final AttributeContainer attributes = this.getAttributes();
        final double maxFrost = attributes.getValue(FrostifulEntityAttributes.MAX_FROST);
        return getTicksFromMaxFrost(maxFrost);
    }

    @Override
    public void frostiful$setFrost(int amount) {
        this.frostiful$frost = amount;
    }

    @Override
    public boolean frostiful$canFreeze() {
        return this.canFreeze();
    }

    @Inject(
            method = "writeCustomDataToNbt",
            at = @At("TAIL")
    )
    private void writeFrostToNbt(NbtCompound nbt, CallbackInfo ci) {
        FreezableEntity.super.addToNbt(nbt);
    }

    @Inject(
            method = "readCustomDataFromNbt",
            at = @At("TAIL")
    )
    private void readFrostFromNbt(NbtCompound nbt, CallbackInfo ci) {
        FreezableEntity.super.setFrostFromNbt(nbt);
    }

    private static int getTicksFromMaxFrost(double maxFrost) {
        return (int) (AttributeConfigGroup.MAX_FROST_MULTIPLIER.getValue() * maxFrost);
    }
}
