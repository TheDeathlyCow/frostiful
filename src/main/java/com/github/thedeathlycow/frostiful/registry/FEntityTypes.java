package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.entity.*;
import com.github.thedeathlycow.frostiful.sound.FSoundEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class FEntityTypes {

    public static final EntityType<FrostologerEntity> FROSTOLOGER = EntityType.Builder.create(
                    FrostologerEntity::new,
                    SpawnGroup.MONSTER
            )
            .setDimensions(0.6f, 1.95f)
            .maxTrackingRange(4)
            .build();

    public static final EntityType<BiterEntity> BITER = EntityType.Builder.create(
                    BiterEntity::new,
                    SpawnGroup.MONSTER
            )
            .setDimensions(1.0f, 2.0f)
            .maxTrackingRange(8)
            .build();

    public static final EntityType<ChillagerEntity> CHILLAGER = EntityType.Builder.create(
                    ChillagerEntity::new,
                    SpawnGroup.MONSTER
            )
            .setDimensions(0.6f, 1.95f)
            .maxTrackingRange(8)
            .build();

    public static final EntityType<FrostTippedArrowEntity> FROST_TIPPED_ARROW = EntityType.Builder.create(
                    (EntityType.EntityFactory<FrostTippedArrowEntity>) FrostTippedArrowEntity::new,
                    SpawnGroup.CREATURE
            )
            .setDimensions(0.5f, 0.5f)
            .build();

    public static final EntityType<FrostSpellEntity> FROST_SPELL = EntityType.Builder.create(
                    (EntityType.EntityFactory<FrostSpellEntity>) FrostSpellEntity::new,
                    SpawnGroup.MISC
            )
            .setDimensions(3f / 8f, 3f / 8f)
            .maxTrackingRange(8)
            .trackingTickInterval(10)
            .build();

    public static final EntityType<PackedSnowballEntity> PACKED_SNOWBALL = EntityType.Builder.create(
                    (EntityType.EntityFactory<PackedSnowballEntity>) PackedSnowballEntity::new,
                    SpawnGroup.MISC
            )
            .setDimensions(3f / 8f, 3f / 8f)
            .maxTrackingRange(8)
            .trackingTickInterval(10)
            .build();

    public static final EntityType<ThrownIcicleEntity> THROWN_ICICLE = EntityType.Builder.create(
                    (EntityType.EntityFactory<ThrownIcicleEntity>) ThrownIcicleEntity::new,
                    SpawnGroup.MISC
            )
            .setDimensions(0.25f, 0.25f)
            .maxTrackingRange(8)
            .trackingTickInterval(10)
            .build();

    public static final EntityType<FreezingWindEntity> FREEZING_WIND = EntityType.Builder.create(
                    FreezingWindEntity::new,
                    SpawnGroup.AMBIENT
            )
            .setDimensions(2.0f, 2.0f)
            .maxTrackingRange(8)
            .trackingTickInterval(10)
            .build();

    public static void registerEntities() {
        register("frostologer", FROSTOLOGER);
        register("chillager", CHILLAGER);
        register("biter", BITER);
        register("frost_tipped_arrow", FROST_TIPPED_ARROW);
        register("frost_spell", FROST_SPELL);
        register("packed_snowball", PACKED_SNOWBALL);
        register("thrown_icicle", THROWN_ICICLE);
        register("freezing_wind", FREEZING_WIND);

        FabricDefaultAttributeRegistry.register(FROSTOLOGER, FrostologerEntity.createFrostologerAttributes());
        FabricDefaultAttributeRegistry.register(CHILLAGER, ChillagerEntity.createChillagerAttributes());
        FabricDefaultAttributeRegistry.register(BITER, BiterEntity.createBiterAttributes());

        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register(
                ((world, entity, killedEntity) -> {
                    if (entity.getType() == BITER) {
                        entity.playSound(FSoundEvents.ENTITY_BITER_BURP, 1.0f, 1.0f);
                    }
                })
        );
    }

    private static <T extends Entity> void register(String id, EntityType<T> type) {
        Registry.register(Registries.ENTITY_TYPE, Frostiful.id(id), type);
    }
}
