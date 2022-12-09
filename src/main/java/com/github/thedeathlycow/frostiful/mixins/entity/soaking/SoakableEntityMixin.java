package com.github.thedeathlycow.frostiful.mixins.entity.soaking;

import com.github.thedeathlycow.frostiful.entity.SoakableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class SoakableEntityMixin extends LivingEntity implements SoakableEntity {

    private static final TrackedData<Integer> WET_TICKS = DataTracker.registerData(SoakableEntityMixin.class, TrackedDataHandlerRegistry.INTEGER);

    protected SoakableEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            method = "initDataTracker",
            at = @At("TAIL")
    )
    private void trackFrostData(CallbackInfo ci) {
        this.dataTracker.startTracking(WET_TICKS, 0);
    }

    @Override
    @Unique
    public int frostiful$getWetTicks() {
        return this.dataTracker.get(WET_TICKS);
    }

    @Override
    @Unique
    public void frostiful$setWetTicks(int amount) {
        this.dataTracker.set(WET_TICKS, amount);
    }

    @Inject(
            method = "writeCustomDataToNbt",
            at = @At("TAIL")
    )
    private void writeFrostToNbt(NbtCompound nbt, CallbackInfo ci) {
        SoakableEntity.frostiful$addDataToNbt(this, nbt);
    }

    @Inject(
            method = "readCustomDataFromNbt",
            at = @At("TAIL")
    )
    private void readFrostFromNbt(NbtCompound nbt, CallbackInfo ci) {
        SoakableEntity.frostiful$setDataFromNbt(this, nbt);
    }
}
