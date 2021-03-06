package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FrostifulEntityTypes {

    public static final EntityType<FrostTippedArrowEntity> FROST_TIPPED_ARROW = register(
            "frost_tipped_arrow",
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, (EntityType.EntityFactory<FrostTippedArrowEntity>) FrostTippedArrowEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
    );

    public static void registerEntities() {
        Frostiful.LOGGER.info("Registering entities...");
        // entities already registered - just ensure this class is loaded
        Frostiful.LOGGER.info("Registered entities!");
    }

    private static <T extends Entity> EntityType<T> register(String id, FabricEntityTypeBuilder<T> type) {
        return Registry.register(Registry.ENTITY_TYPE, new Identifier(Frostiful.MODID, id), type.build());
    }
}
