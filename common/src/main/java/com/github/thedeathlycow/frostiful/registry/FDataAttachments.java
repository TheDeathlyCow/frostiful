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