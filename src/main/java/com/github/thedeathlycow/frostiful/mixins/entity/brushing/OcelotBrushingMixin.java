package com.github.thedeathlycow.frostiful.mixins.entity.brushing;

import com.github.thedeathlycow.frostiful.entity.FBrushable;
import com.github.thedeathlycow.frostiful.registry.FComponents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(OcelotEntity.class)
public abstract class OcelotBrushingMixin extends AnimalEntity implements FBrushable {
    protected OcelotBrushingMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    @Unique
    public void frostiful$brush(PlayerEntity player, SoundCategory shearedSoundCategory) {
        FBrushable.brushEntity(this, shearedSoundCategory, FBrushable.OCELOT_BRUSHING_LOOT_TABLE);
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
