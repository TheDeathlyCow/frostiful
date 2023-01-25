package com.github.thedeathlycow.frostiful.mixins.entity.shearing;

import com.github.thedeathlycow.frostiful.entity.FShearable;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.util.FLootHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PolarBearEntity.class)
public abstract class PolarBearShearingMixin extends AnimalEntity implements FShearable {

    protected PolarBearShearingMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    private static final String FROSTIFUL$KEY = "Frostiful";
    private static final String FROSTIFUL$LAST_SHEARED_AGE_KEY = "LastShearedAge";

    private static final int frostiful$SHEAR_COOLDOWN = 20 * 300;


    private static final TrackedData<Integer> LAST_SHEARED_AGE = DataTracker.registerData(PolarBearShearingMixin.class, TrackedDataHandlerRegistry.INTEGER);


    @Inject(
            method = "initDataTracker",
            at = @At("TAIL")
    )
    private void trackFrostData(CallbackInfo ci) {
        this.dataTracker.startTracking(LAST_SHEARED_AGE, -1);
    }


    @Inject(
            method = "writeCustomDataToNbt",
            at = @At("TAIL")
    )
    private void saveShearingData(NbtCompound nbt, CallbackInfo ci) {
        NbtCompound frostiful = new NbtCompound();

        if (this.frostiful$wasSheared()) {
            frostiful.putInt(FROSTIFUL$LAST_SHEARED_AGE_KEY, this.dataTracker.get(LAST_SHEARED_AGE));
        }

        if (!frostiful.isEmpty()) {
            nbt.put(FROSTIFUL$KEY, frostiful);
        }
    }

    @Inject(
            method = "readCustomDataFromNbt",
            at = @At("TAIL")
    )
    private void readShearingData(NbtCompound nbt, CallbackInfo ci) {

        int lastShearedAge = -1;

        if (nbt.contains(FROSTIFUL$KEY, NbtElement.COMPOUND_TYPE)) {
            NbtCompound frostiful = nbt.getCompound(FROSTIFUL$KEY);

            if (frostiful.contains(FROSTIFUL$LAST_SHEARED_AGE_KEY, NbtElement.INT_TYPE)) {
                lastShearedAge = frostiful.getInt(FROSTIFUL$LAST_SHEARED_AGE_KEY);
            }
        }

        this.dataTracker.set(LAST_SHEARED_AGE, lastShearedAge);

    }

    @Override
    @Unique
    public void frostiful$shear(PlayerEntity player, SoundCategory shearedSoundCategory) {
        this.world.playSoundFromEntity(null, this, SoundEvents.ENTITY_SHEEP_SHEAR, shearedSoundCategory, 1.0F, 1.0F);
        this.damage(DamageSource.player(player), Frostiful.getConfig().combatConfig.getPolarBearShearingDamage());

        if (!this.world.isClient) {
            FLootHelper.dropLootFromEntity(this, FShearable.POLAR_BEAR_SHEARING_LOOT_TABLE);
        }

        this.dataTracker.set(LAST_SHEARED_AGE, this.age);
    }

    @Override
    @Unique
    public boolean frostiful$isShearable() {
        return this.isAlive()
                && !this.isBaby()
                && !this.frostiful$wasSheared();
    }

    @Override
    @Unique
    public boolean frostiful$wasSheared() {
        int lastShearedAge = this.dataTracker.get(LAST_SHEARED_AGE);

        return lastShearedAge >= 0
                && this.age - lastShearedAge <= frostiful$SHEAR_COOLDOWN;
    }

}
