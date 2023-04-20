package com.github.thedeathlycow.frostiful.tag.entitytype;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.entity.EntityType;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FEntityTypeTags {

    public static final TagKey<EntityType<?>> ROOT_IMMUNE = register("root_immune");

    public static final TagKey<EntityType<?>> HEAVY_ENTITY_TYPES = register("heavy_entity_types");

    private static TagKey<EntityType<?>> register(String id) {
        return TagKey.of(Registry.ENTITY_TYPE_KEY, Frostiful.id(id));
    }

    private static TagKey<EntityType<?>> registerCommon(String id) {
        return TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier("c", id));
    }

}
