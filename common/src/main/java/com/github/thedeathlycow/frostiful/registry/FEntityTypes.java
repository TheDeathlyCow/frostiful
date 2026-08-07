/*
 * Frostiful: A Vanilla+ Freezing Temperature Mod. Also try Scorchful!
 * Copyright (C) 2026	TheDeathlyCow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.  If not, see
 * <https://www.gnu.org/licenses/>.
 */

package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.entity.*;
import com.github.thedeathlycow.frostiful.entity.frostologer.Frostologer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class FEntityTypes {

    public static final EntityType<Frostologer> FROSTOLOGER = register(
            "frostologer",
            EntityType.Builder.of(Frostologer::new, MobCategory.MONSTER)
                    .sized(0.6f, 1.95f)
                    .passengerAttachments(2.0F)
                    .ridingOffset(-0.6F)
                    .clientTrackingRange(10)
    );

    public static final EntityType<Biter> BITER = register(
            "biter",
            EntityType.Builder.of(Biter::new, MobCategory.MONSTER)
                    .sized(1.0f, 1.5f)
                    .clientTrackingRange(10)
    );

    public static final EntityType<Chillager> CHILLAGER = register(
            "chillager",
            EntityType.Builder.of(Chillager::new, MobCategory.MONSTER)
                    .canSpawnFarFromPlayer()
                    .sized(0.6F, 1.95F)
                    .passengerAttachments(2.0F)
                    .ridingOffset(-0.6F)
                    .clientTrackingRange(10)
    );

    public static final EntityType<GlacialArrow> GLACIAL_ARROW = register(
            "glacial_arrow",
            EntityType.Builder.<GlacialArrow>of(GlacialArrow::new, MobCategory.CREATURE)
                    .noLootTable()
                    .sized(0.5F, 0.5F)
                    .eyeHeight(0.13F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
    );

    public static final EntityType<FrostSpell> FROST_SPELL = register(
            "frost_spell",
            EntityType.Builder.<FrostSpell>of(FrostSpell::new, MobCategory.MISC)
                    .noLootTable()
                    .sized(1.0F, 1.0F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
    );

    public static final EntityType<PackedSnowballEntity> PACKED_SNOWBALL = register(
            "packed_snowball",
            EntityType.Builder.<PackedSnowballEntity>of(PackedSnowballEntity::new, MobCategory.MISC)
                    .noLootTable()
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
    );

    public static final EntityType<ThrownIcicle> THROWN_ICICLE = register(
            "thrown_icicle",
            EntityType.Builder.<ThrownIcicle>of(ThrownIcicle::new, MobCategory.MISC)
                    .noLootTable()
                    .sized(0.5F, 0.5F)
                    .eyeHeight(0.13F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
    );

    public static final EntityType<FreezingWindEntity> FREEZING_WIND = register(
            "freezing_wind",
            EntityType.Builder.of(FreezingWindEntity::new, MobCategory.AMBIENT)
                    .noLootTable()
                    .noSave()
                    .sized(2.0f, 2.0f)
                    .clientTrackingRange(8)
                    .updateInterval(10)
    );

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful entity types");

        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register(
                ((world, entity, killedEntity, damageSource) -> {
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
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, id);
        return register(key, builder);
    }

    private static <T extends Entity> EntityType<T> register(ResourceKey<EntityType<?>> key, EntityType.Builder<T> builder) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
    }

    private FEntityTypes() {

    }
}
