package com.github.thedeathlycow.frostiful.tag;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class FEntityTypeTags {

    public static final TagKey<EntityType<?>> ROOT_IMMUNE = register("root_immune");

    public static final TagKey<EntityType<?>> HEAVY_ENTITY_TYPES = register("heavy_entity_types");

    private static TagKey<EntityType<?>> register(String id) {
        return TagKey.of(RegistryKeys.ENTITY_TYPE, Frostiful.id(id));
    }

    private static TagKey<EntityType<?>> registerCommon(String id) {
        return TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier("c", id));
    }

}
