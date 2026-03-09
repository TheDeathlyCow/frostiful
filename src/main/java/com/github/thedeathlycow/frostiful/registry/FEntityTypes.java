package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.entity.*;
import com.github.thedeathlycow.frostiful.entity.frostologer.FrostologerEntity;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class FEntityTypes {

    public static final EntityType<FrostologerEntity> FROSTOLOGER = register(
            "frostologer",
            EntityType.Builder.of(
                            FrostologerEntity::new,
                            MobCategory.MONSTER
                    )
                    .sized(0.6f, 1.95f)
                    .clientTrackingRange(4)
    );

    public static final EntityType<BiterEntity> BITER = register(
            "biter",
            EntityType.Builder.of(
                            BiterEntity::new,
                            MobCategory.MONSTER
                    )
                    .sized(1.0f, 1.5f)
                    .clientTrackingRange(8)
    );

    public static final EntityType<ChillagerEntity> CHILLAGER = register(
            "chillager",
            EntityType.Builder.of(
                            ChillagerEntity::new,
                            MobCategory.MONSTER
                    )
                    .sized(0.6f, 1.95f)
                    .clientTrackingRange(8)
    );

    public static final EntityType<GlacialArrowEntity> GLACIAL_ARROW = register(
            "glacial_arrow",
            EntityType.Builder.<GlacialArrowEntity>of(
                            GlacialArrowEntity::new,
                            MobCategory.CREATURE
                    )
                    .sized(0.5f, 0.5f)
    );

    public static final EntityType<FrostSpellEntity> FROST_SPELL = register(
            "frost_spell",
            EntityType.Builder.<FrostSpellEntity>of(
                            FrostSpellEntity::new,
                            MobCategory.MISC
                    )
                    .sized(3f / 8f, 3f / 8f)
                    .clientTrackingRange(8)
                    .updateInterval(10)
    );

    public static final EntityType<PackedSnowballEntity> PACKED_SNOWBALL = register(
            "packed_snowball",
            EntityType.Builder.<PackedSnowballEntity>of(
                            PackedSnowballEntity::new,
                            MobCategory.MISC
                    )
                    .sized(3f / 8f, 3f / 8f)
                    .clientTrackingRange(8)
                    .updateInterval(10)
    );

    public static final EntityType<ThrownIcicleEntity> THROWN_ICICLE = register(
            "thrown_icicle",
            EntityType.Builder.<ThrownIcicleEntity>of(
                            ThrownIcicleEntity::new,
                            MobCategory.MISC
                    )
                    .sized(0.25f, 0.25f)
                    .clientTrackingRange(8)
                    .updateInterval(10)
    );

    public static final EntityType<FreezingWindEntity> FREEZING_WIND = register(
            "freezing_wind",
            EntityType.Builder.of(
                            FreezingWindEntity::new,
                            MobCategory.AMBIENT
                    )
                    .sized(2.0f, 2.0f)
                    .clientTrackingRange(8)
                    .updateInterval(10)
    );

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful entity types");

        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register(
                ((world, entity, killedEntity) -> {
                    if (entity.getType() == BITER) {
                        entity.playSound(FSoundEvents.ENTITY_BITER_BURP, 1.0f, 1.0f);
                    }
                })
        );
    }

    private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, Frostiful.id(id), builder.build(id));
    }

    private FEntityTypes() {

    }
}
