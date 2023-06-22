package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;

public interface FShearable {

    Identifier POLAR_BEAR_SHEARING_LOOT_TABLE = Frostiful.id("gameplay/polar_bear_shearing");


    void frostiful$shear(PlayerEntity source, SoundCategory shearedSoundCategory);

    boolean frostiful$isShearable();

    boolean frostiful$wasSheared();
}
