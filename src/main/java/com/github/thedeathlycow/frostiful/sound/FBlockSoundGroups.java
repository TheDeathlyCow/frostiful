package com.github.thedeathlycow.frostiful.sound;

import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;

public final class FBlockSoundGroups {

    public static final BlockSoundGroup PACKED_SNOW = new BlockSoundGroup(
            1.0F, 1.0F,
            FSoundEvents.BLOCK_PACKED_SNOW_BREAK,
            FSoundEvents.BLOCK_PACKED_SNOW_STEP,
            FSoundEvents.BLOCK_PACKED_SNOW_PLACE,
            FSoundEvents.BLOCK_PACKED_SNOW_HIT,
            FSoundEvents.BLOCK_PACKED_SNOW_FALL
    );


}
