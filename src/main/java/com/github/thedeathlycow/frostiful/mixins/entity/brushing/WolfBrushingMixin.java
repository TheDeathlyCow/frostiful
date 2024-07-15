package com.github.thedeathlycow.frostiful.mixins.entity.brushing;

import com.github.thedeathlycow.frostiful.entity.FBrushable;
import com.github.thedeathlycow.frostiful.registry.FComponents;
import com.github.thedeathlycow.frostiful.registry.FLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.UUID;

@Mixin(WolfEntity.class)
public abstract class WolfBrushingMixin extends TameableEntity implements FBrushable {
    @Shadow public abstract void setAngryAt(@Nullable UUID angryAt);

    @Shadow public abstract void chooseRandomAngerTime();

    protected WolfBrushingMixin(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    @Unique
    public void frostiful$brush(PlayerEntity player, SoundCategory shearedSoundCategory) {
        FBrushable.brushEntity(this, shearedSoundCategory, FLootTables.WOLF_BRUSHING_GAMEPLAY);
        if (!this.isTamed()) {
            this.setAngryAt(player.getUuid());
            this.chooseRandomAngerTime();
        }
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
