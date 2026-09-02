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

package com.github.thedeathlycow.frostiful.registry.tag;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.entity.BannerPattern;

public class FBannerPatternTags {

    public static final TagKey<BannerPattern> SNOWFLAKE_PATTERN_ITEM = key("pattern_item/snowflake");
    public static final TagKey<BannerPattern> ICICLE_PATTERN_ITEM = key("pattern_item/icicle");
    public static final TagKey<BannerPattern> FROSTOLOGY_PATTERN_ITEM = key("pattern_item/frostology");

    private static TagKey<BannerPattern> key(String id) {
        return TagKey.create(Registries.BANNER_PATTERN, Frostiful.id(id));
    }

    private FBannerPatternTags() {

    }
}
