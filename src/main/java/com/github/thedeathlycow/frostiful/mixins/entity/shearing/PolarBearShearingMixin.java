package com.github.thedeathlycow.frostiful.mixins.entity.shearing;

import com.github.thedeathlycow.frostiful.entity.FShearable;
import com.github.thedeathlycow.frostiful.entity.component.FComponents;
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
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PolarBearEntity.class)
public abstract class PolarBearShearingMixin extends AnimalEntity implements FShearable {

    @Shadow private int angerTime;

    protected PolarBearShearingMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    private static final int frostiful$SHEAR_COOLDOWN = 20 * 300;

    @Override
    @Unique
    public void frostiful$shear(PlayerEntity player, SoundCategory shearedSoundCategory) {
        this.world.playSoundFromEntity(null, this, SoundEvents.ENTITY_SHEEP_SHEAR, shearedSoundCategory, 1.0F, 1.0F);
        this.damage(DamageSource.player(player), Frostiful.getConfig().combatConfig.getPolarBearShearingDamage());

        if (!this.world.isClient) {
            FLootHelper.dropLootFromEntity(this, FShearable.POLAR_BEAR_SHEARING_LOOT_TABLE);
        }

        FComponents.POLAR_BEAR_COMPONENTS.get(this).setLastShearedAge(this.age);
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
        int lastShearedAge = FComponents.POLAR_BEAR_COMPONENTS.get(this).getLastShearedAge();

        return lastShearedAge >= 0
                && this.age - lastShearedAge <= frostiful$SHEAR_COOLDOWN;
    }

}
