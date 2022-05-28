package com.github.thedeathlycow.frostiful.mixins.entity.frost_tracker;

import com.github.thedeathlycow.frostiful.entity.FrostDataTracker;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityTracker implements FrostDataTracker {

    private static final TrackedData<Integer> FROSTIFUL$FROST = DataTracker.registerData(Entity.class, TrackedDataHandlerRegistry.INTEGER);

    @Shadow @Final protected DataTracker dataTracker;

    @Shadow public abstract EntityType<?> getType();

    @Shadow public abstract boolean isSpectator();

    @Inject(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/data/DataTracker;startTracking(Lnet/minecraft/entity/data/TrackedData;Ljava/lang/Object;)V",
                    ordinal = 0
            )
    )
    private void trackFrost(EntityType<?> type, World world, CallbackInfo ci) {
        this.dataTracker.startTracking(FROSTIFUL$FROST, 0);
    }

    @Inject(
            method = "writeNbt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;getFrozenTicks()I",
                    ordinal = 0
            )
    )
    private void writeFrostToNbt(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
        int frost = this.frostiful$getFrost();
        if (frost > 0) {
            nbt.putInt("Frostiful.Frost", frost);
        }
    }

    @Inject(
            method = "readNbt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;setFrozenTicks(I)V",
                    ordinal = 0
            )
    )
    private void readFrostFromNbt(NbtCompound nbt, CallbackInfo ci) {
        this.frostiful$setFrost(nbt.getInt("Frostiful.Frost"));
    }

    @Override
    @Unique
    public int frostiful$getFrost() {
        return this.dataTracker.get(FROSTIFUL$FROST);
    }

    @Override
    @Unique
    public void frostiful$setFrost(int frost) {
        frost = MathHelper.clamp(frost, 0, this.frostiful$getMaxFrost());
        this.dataTracker.set(FROSTIFUL$FROST, frost);
    }

    @Override
    @Unique
    public int frostiful$getMaxFrost() {
        return 0;
    }

    @Override
    @Unique
    public void frostiful$applyEffects() {

    }

    @Override
    @Unique
    public boolean frostiful$canApplyFrost() {
        return !this.getType().isIn(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES);
    }

    @Override
    @Unique
    public float frostiful$getFrostProgress() {
        final int maxFrost = this.frostiful$getMaxFrost();
        return (float)Math.min(this.frostiful$getFrost(), maxFrost) / (float)maxFrost;
    }
}
