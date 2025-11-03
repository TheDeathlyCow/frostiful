package com.github.thedeathlycow.frostiful.registry.tag;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class FEntityTypeTags {

    public static final TagKey<EntityType<?>> ROOT_IMMUNE = register("root_immune");

    public static final TagKey<EntityType<?>> HEAVY_ENTITY_TYPES = register("heavy_entity_types");

    public static final TagKey<EntityType<?>> DOES_NOT_BREAK_BRITTLE_ICE = register("does_not_break_brittle_ice");

    public static final TagKey<EntityType<?>> IS_BRUSHABLE = register("is_brushable");

    public static final TagKey<EntityType<?>> BRUSHING_DROPS_POLAR_BEAR_FUR = register("brushing/drops_polar_bear_fur");

    public static final TagKey<EntityType<?>> BRUSHING_DROPS_WOLF_FUR = register("brushing/drops_wolf_fur");

    public static final TagKey<EntityType<?>> BRUSHING_DROPS_OCELOT_FUR = register("brushing/drops_ocelot_fur");

    private static TagKey<EntityType<?>> register(String id) {
        return TagKey.create(Registries.ENTITY_TYPE, Frostiful.location(id));
    }

    private static TagKey<EntityType<?>> registerCommon(String id) {
        return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("c", id));
    }

    private FEntityTypeTags() {

    }
}
