package com.github.thedeathlycow.frostiful.mixins.entity.brushing;

import com.github.thedeathlycow.frostiful.entity.FBrushable;
import com.github.thedeathlycow.frostiful.registry.FComponents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PolarBearEntity.class)
public abstract class PolarBearBrushingMixin extends AnimalEntity implements FBrushable {

    protected PolarBearBrushingMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    @Unique
    public void frostiful$brush(PlayerEntity player, SoundCategory shearedSoundCategory) {
        FBrushable.brushEntity(this, shearedSoundCategory, FBrushable.POLAR_BEAR_SHEARING_LOOT_TABLE);
    }

    @Override
    @Unique
    public boolean frostiful$isBrushable() {
        return this.isAlive()
                && !this.isBaby()
                && !this.frostiful$wasBrushed();
    }

    @Override
    @Unique
    public boolean frostiful$wasBrushed() {
        return FComponents.BRUSHABLE_COMPONENT.get(this).wasBrushed();
    }

}
