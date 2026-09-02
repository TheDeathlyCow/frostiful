/*
 * Frostiful: A Vanilla+ Freezing Temperature Mod. Also try Scorchful!
 * Copyright (C) 2026	TheDeathlyCow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.  If not, see
 * <https://www.gnu.org/licenses/>.
 */

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
