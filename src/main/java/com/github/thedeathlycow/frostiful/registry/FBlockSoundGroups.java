package com.github.thedeathlycow.frostiful.registry;

import net.minecraft.world.level.block.SoundType;

public final class FBlockSoundGroups {

    public static final SoundType PACKED_SNOW = new SoundType(
            1.0F, 1.0F,
            FSoundEvents.BLOCK_PACKED_SNOW_BREAK,
            FSoundEvents.BLOCK_PACKED_SNOW_STEP,
            FSoundEvents.BLOCK_PACKED_SNOW_PLACE,
            FSoundEvents.BLOCK_PACKED_SNOW_HIT,
            FSoundEvents.BLOCK_PACKED_SNOW_FALL
    );


}
