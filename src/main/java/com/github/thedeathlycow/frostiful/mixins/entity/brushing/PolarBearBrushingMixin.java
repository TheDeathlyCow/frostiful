package com.github.thedeathlycow.frostiful.mixins.entity.brushing;

import com.github.thedeathlycow.frostiful.entity.FBrushable;
import com.github.thedeathlycow.frostiful.registry.FComponents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.UUID;

@Mixin(PolarBearEntity.class)
public abstract class PolarBearBrushingMixin extends AnimalEntity implements FBrushable {

    @Shadow public abstract void chooseRandomAngerTime();

    @Shadow public abstract void setAngryAt(@Nullable UUID angryAt);

    protected PolarBearBrushingMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    @Unique
    public void frostiful$brush(PlayerEntity player, SoundCategory shearedSoundCategory) {
        FBrushable.brushEntity(this, shearedSoundCategory, FBrushable.POLAR_BEAR_BRUSHING_LOOT_TABLE);

        this.chooseRandomAngerTime();
        this.setAngryAt(player.getUuid());
    }

    @Override
    @Unique
    public boolean frostiful$isBrushable() {
        return FComponents.BRUSHABLE_COMPONENT.get(this).isBrushable();
    }

    @Override
    @Unique
    public boolean frostiful$wasBrushed() {
        return FComponents.BRUSHABLE_COMPONENT.get(this).wasBrushed();
    }

}
