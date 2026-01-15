package com.github.thedeathlycow.frostiful.registry.tag;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.structure.Structure;

public final class FStructureTags {
    public static final TagKey<Structure> CHILLAGER_MAP_LOCATABLE = register("chillager_map_locatable");

    private static TagKey<Structure> register(String id) {
        return TagKey.create(Registries.STRUCTURE, Frostiful.id(id));
    }

    private FStructureTags() {

    }
}