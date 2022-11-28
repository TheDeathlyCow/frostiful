package com.github.thedeathlycow.frostiful.mixins.entity;

import com.github.thedeathlycow.frostiful.attributes.FEntityAttributes;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.entity.FreezableEntity;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.tag.entitytype.FEntityTypeTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class FreezableEntityImplMixin extends Entity implements FreezableEntity {

    public FreezableEntityImplMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow public abstract AttributeContainer getAttributes();

    private static final TrackedData<Integer> FROST = DataTracker.registerData(FreezableEntityImplMixin.class, TrackedDataHandlerRegistry.INTEGER);;


    @Inject(
            method = "initDataTracker",
            at = @At("TAIL")
    )
    private void trackFrostData(CallbackInfo ci) {
        this.dataTracker.startTracking(FROST, 0);
    }

    @Override
    @Unique
    public int frostiful$getCurrentFrost() {
        return this.getFrozenTicks();
    }

    @Override
    @Unique
    public int frostiful$getMaxFrost() {
        final AttributeContainer attributes = this.getAttributes();
        final double maxFrost = attributes.getValue(FEntityAttributes.MAX_FROST);
        return getTicksFromMaxFrost(maxFrost);
    }

    @Override
    @Unique
    public void frostiful$setFrost(int amount) {
        this.setFrozenTicks(amount);
    }

    @Override
    @Unique
    public boolean frostiful$canFreeze() {
        final LivingEntity instance = (LivingEntity) (Object) this;
        if (instance.getType().isIn(FEntityTypeTags.FREEZE_IMMUNE)) {
            return false;
        }
        if (instance.isSpectator()) {
            return false;
        }
        if (instance instanceof PlayerEntity player) {
            return !player.isCreative();
        }
        return true;
    }

//    @Inject(
//            method = "writeCustomDataToNbt",
//            at = @At("TAIL")
//    )
//    private void writeFrostToNbt(NbtCompound nbt, CallbackInfo ci) {
//        FreezableEntity.frostiful$addFrostToNbt(this, nbt);
//    }
//
//    @Inject(
//            method = "readCustomDataFromNbt",
//            at = @At("TAIL")
//    )
//    private void readFrostFromNbt(NbtCompound nbt, CallbackInfo ci) {
//        FreezableEntity.frostiful$setFrostFromNbt(this, nbt);
//    }

    private static int getTicksFromMaxFrost(double maxFrost) {
        return (int) (140 * maxFrost);
    }
}
