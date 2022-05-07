package com.github.thedeathlycow.frostiful.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

public class FrostifulEntityTypes {

    public static final EntityType<FrostTippedArrowEntity> FROST_TIPPED_ARROW = register(
            "frost_tipped_arrow",
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, (EntityType.EntityFactory<FrostTippedArrowEntity>) FrostTippedArrowEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
    );

    public static void registerEntities() {
        // entities already registered - just ensure this class is loaded
    }

    private static <T extends Entity> EntityType<T> register(String id, FabricEntityTypeBuilder<T> type) {
        return Registry.register(Registry.ENTITY_TYPE, id, type.build());
    }
}
