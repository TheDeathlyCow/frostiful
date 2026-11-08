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

import com.github.thedeathlycow.frostiful.Frostiful;
import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.codec.ByteBufCodecs;

public final class FDataAttachments {
    public static final AttachmentType<Long> LAST_BRUSH_TIME = AttachmentRegistry.create(
            Frostiful.id("last_brush_time"),
            builder -> builder.initializer(() -> -1L)
                    .persistent(Codec.LONG)
                    .syncWith(ByteBufCodecs.VAR_LONG, AttachmentSyncPredicate.all())
    );

    public static final AttachmentType<Integer> FROST_WAND_ROOT_TICKS = AttachmentRegistry.create(
            Frostiful.id("frost_wand_root_ticks"),
            builder -> builder.initializer(() -> 0)
                    .persistent(Codec.INT)
                    .syncWith(ByteBufCodecs.VAR_INT, AttachmentSyncPredicate.all())
    );

    public static final AttachmentType<Integer> SNOW_ACCUMULATION_TICKS = AttachmentRegistry.create(
            Frostiful.id("snow_accumulation_ticks"),
            builder -> builder.initializer(() -> 0)
                    .persistent(Codec.INT)
                    .syncWith(ByteBufCodecs.VAR_INT, AttachmentSyncPredicate.all())
    );

    public static final AttachmentType<Boolean> APPLIED_SOAKED_MODIFIERS = AttachmentRegistry.create(
            Frostiful.id("applied_soaked_modifiers"),
            builder -> builder.initializer(() -> false)
    );

    public static final AttachmentType<Boolean> WAS_ICE_SKATING = AttachmentRegistry.create(
            Frostiful.id("was_ice_skating"),
            builder -> builder.initializer(() -> false)
                    .syncWith(ByteBufCodecs.BOOL, AttachmentSyncPredicate.all())
    );

    public static final AttachmentType<Boolean> WAS_SLOWED_BY_SKATES = AttachmentRegistry.create(
            Frostiful.id("was_slowed_by_skates"),
            builder -> builder.initializer(() -> false)
    );

    public static void initialize() {
        Frostiful.LOGGER.info("Initialized data attachments");
    }

    private FDataAttachments() {

    }
}