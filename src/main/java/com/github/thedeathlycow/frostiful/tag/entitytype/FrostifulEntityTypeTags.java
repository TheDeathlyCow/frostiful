package com.github.thedeathlycow.frostiful.tag.entitytype;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.entity.EntityType;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FrostifulEntityTypeTags {

    public static final TagKey<EntityType<?>> FREEZE_IMMUNE = register("freeze_immune");

    private static TagKey<EntityType<?>> register(String id) {
        return TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(Frostiful.MODID, id));
    }

}
