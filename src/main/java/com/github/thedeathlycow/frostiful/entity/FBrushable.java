package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.registry.FComponents;
import com.github.thedeathlycow.frostiful.util.FLootHelper;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public interface FBrushable {

    int SHEAR_COOLDOWN = 20 * 300;
    Identifier POLAR_BEAR_SHEARING_LOOT_TABLE = Frostiful.id("gameplay/polar_bear_shearing");


    void frostiful$brush(PlayerEntity source, SoundCategory shearedSoundCategory);

    boolean frostiful$isBrushable();

    boolean frostiful$wasBrushed();

    static void brushEntity(
            AnimalEntity animalEntity,
            SoundCategory shearedSoundCategory,
            Identifier furLootTable
    ) {
        World world = animalEntity.getWorld();
        world.playSoundFromEntity(
                null,
                animalEntity,
                SoundEvents.ITEM_BRUSH_BRUSHING_GENERIC,
                shearedSoundCategory,
                1.0f, 1.0f
        );

        if (!world.isClient) {
            FLootHelper.dropLootFromEntity(animalEntity, furLootTable);
        }

        FComponents.BRUSHABLE_COMPONENT.get(animalEntity).setLastBrushTime(world.getTime());
    }

}
