package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.sound.FSoundEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

public class FEntityTypes {

    public static final EntityType<FrostologerEntity> FROSTOLOGER = FabricEntityTypeBuilder.create(
                    SpawnGroup.MONSTER,
                    FrostologerEntity::new
            )
            .dimensions(EntityDimensions.fixed(0.6f, 1.95f))
            .trackRangeChunks(8)
            .build();

    public static final EntityType<BiterEntity> BITER = FabricEntityTypeBuilder.create(
                    SpawnGroup.MONSTER,
                    BiterEntity::new
            )
            .dimensions(EntityDimensions.fixed(1.0f, 2.0f))
            .trackRangeChunks(8)
            .build();

    public static final EntityType<ChillagerEntity> CHILLAGER = FabricEntityTypeBuilder.create(
                    SpawnGroup.MONSTER,
                    ChillagerEntity::new
            )
            .dimensions(EntityDimensions.fixed(0.6f, 1.95f))
            .trackRangeChunks(8)
            .build();

    public static final EntityType<FrostTippedArrowEntity> FROST_TIPPED_ARROW = FabricEntityTypeBuilder.create(
                    SpawnGroup.CREATURE,
                    (EntityType.EntityFactory<FrostTippedArrowEntity>) FrostTippedArrowEntity::new
            )
            .dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build();

    public static final EntityType<FrostSpellEntity> FROST_SPELL = FabricEntityTypeBuilder.create(
                    SpawnGroup.MISC,
                    (EntityType.EntityFactory<FrostSpellEntity>) FrostSpellEntity::new
            )
            .dimensions(EntityDimensions.fixed(3f / 8f, 3f / 8f))
            .trackRangeChunks(8)
            .trackedUpdateRate(10)
            .build();

    public static final EntityType<PackedSnowballEntity> PACKED_SNOWBALL = FabricEntityTypeBuilder.create(
                    SpawnGroup.MISC,
                    (EntityType.EntityFactory<PackedSnowballEntity>) PackedSnowballEntity::new)
            .dimensions(EntityDimensions.fixed(3f / 8f, 3f / 8f))
            .trackRangeChunks(8)
            .trackedUpdateRate(10)
            .build();

    public static final EntityType<ThrownIcicleEntity> THROWN_ICICLE = FabricEntityTypeBuilder.create(
                    SpawnGroup.MISC,
                    (EntityType.EntityFactory<ThrownIcicleEntity>) ThrownIcicleEntity::new
            )
            .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
            .trackRangeChunks(8)
            .trackedUpdateRate(10)
            .build();

    public static final EntityType<FreezingWindEntity> FREEZING_WIND = FabricEntityTypeBuilder.create(
                    SpawnGroup.AMBIENT,
                    FreezingWindEntity::new
            )
            .dimensions(EntityDimensions.fixed(2.0f, 2.0f))
            .trackRangeChunks(8)
            .trackedUpdateRate(10)
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
        Registry.register(Registry.ENTITY_TYPE, Frostiful.id(id), type);
    }
}
