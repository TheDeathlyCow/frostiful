package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.entity.*;
import com.github.thedeathlycow.frostiful.entity.frostologer.FrostologerEntity;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class FEntityTypes {

    public static final EntityType<FrostologerEntity> FROSTOLOGER = register(
            "frostologer",
            EntityType.Builder.create(FrostologerEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.6f, 1.95f)
                    .passengerAttachments(2.0F)
                    .vehicleAttachment(-0.6F)
                    .maxTrackingRange(10)
    );

    public static final EntityType<BiterEntity> BITER = register(
            "biter",
            EntityType.Builder.create(BiterEntity::new, SpawnGroup.MONSTER)
                    .dimensions(1.0f, 1.5f)
                    .maxTrackingRange(10)
    );

    public static final EntityType<ChillagerEntity> CHILLAGER = register(
            "chillager",
            EntityType.Builder.create(ChillagerEntity::new, SpawnGroup.MONSTER)
                    .spawnableFarFromPlayer()
                    .dimensions(0.6F, 1.95F)
                    .passengerAttachments(2.0F)
                    .vehicleAttachment(-0.6F)
                    .maxTrackingRange(10)
    );

    public static final EntityType<GlacialArrowEntity> GLACIAL_ARROW = register(
            "glacial_arrow",
            EntityType.Builder.<GlacialArrowEntity>create(GlacialArrowEntity::new, SpawnGroup.CREATURE)
                    .dropsNothing()
                    .dimensions(0.5F, 0.5F)
                    .eyeHeight(0.13F)
                    .maxTrackingRange(4)
                    .trackingTickInterval(20)
    );

    public static final EntityType<FrostSpellEntity> FROST_SPELL = register(
            "frost_spell",
            EntityType.Builder.<FrostSpellEntity>create(FrostSpellEntity::new, SpawnGroup.MISC)
                    .dropsNothing()
                    .dimensions(1.0F, 1.0F)
                    .maxTrackingRange(4)
                    .trackingTickInterval(10)
    );

    public static final EntityType<PackedSnowballEntity> PACKED_SNOWBALL = register(
            "packed_snowball",
            EntityType.Builder.<PackedSnowballEntity>create(PackedSnowballEntity::new, SpawnGroup.MISC)
                    .dropsNothing()
                    .dimensions(0.25F, 0.25F)
                    .maxTrackingRange(4)
                    .trackingTickInterval(10)
    );

    public static final EntityType<ThrownIcicleEntity> THROWN_ICICLE = register(
            "thrown_icicle",
            EntityType.Builder.<ThrownIcicleEntity>create(ThrownIcicleEntity::new, SpawnGroup.MISC)
                    .dropsNothing()
                    .dimensions(0.5F, 0.5F)
                    .eyeHeight(0.13F)
                    .maxTrackingRange(4)
                    .trackingTickInterval(20)
    );

    public static final EntityType<FreezingWindEntity> FREEZING_WIND = register(
            "freezing_wind",
            EntityType.Builder.create(FreezingWindEntity::new, SpawnGroup.AMBIENT)
                    .dropsNothing()
                    .disableSaving()
                    .dimensions(2.0f, 2.0f)
                    .maxTrackingRange(8)
                    .trackingTickInterval(10)
    );

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful entity types");

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

    private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder)  {
        return register(Frostiful.id(id), builder);
    }

    private static <T extends Entity> EntityType<T> register(Identifier id, EntityType.Builder<T> builder)  {
        RegistryKey<EntityType<?>> key = RegistryKey.of(RegistryKeys.ENTITY_TYPE, id);
        return register(key, builder);
    }

    private static <T extends Entity> EntityType<T> register(RegistryKey<EntityType<?>> key, EntityType.Builder<T> builder) {
        return Registry.register(Registries.ENTITY_TYPE, key, builder.build(key));
    }

    private FEntityTypes() {

    }
}
