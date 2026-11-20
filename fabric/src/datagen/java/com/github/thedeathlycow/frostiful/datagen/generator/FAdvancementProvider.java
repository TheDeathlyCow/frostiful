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

package com.github.thedeathlycow.frostiful.datagen.generator;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.registry.FAdvancements;
import com.github.thedeathlycow.frostiful.registry.FItems;
import com.github.thedeathlycow.frostiful.registry.tag.FEntityTypeTags;
import com.github.thedeathlycow.thermoo.api.entity.v1.predicate.TemperatureLootCondition;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.*;
import net.minecraft.advancements.criterion.*;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Util;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class FAdvancementProvider extends FabricAdvancementProvider {
    public FAdvancementProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(HolderLookup.Provider registryLookup, Consumer<AdvancementHolder> consumer) {
        AdvancementHolder root = this.root().save(consumer, FAdvancements.ROOT.identifier());

        AdvancementHolder brushPolarBear = this.brushPolarBear(registryLookup)
                .parent(root)
                .save(consumer, FAdvancements.BRUSH_POLAR_BEAR.identifier());

        this.brushEveryAnimal(registryLookup)
                .parent(brushPolarBear)
                .save(consumer, FAdvancements.BRUSH_EVERY_ANIMAL.identifier());
    }

    private Advancement.Builder root() {
        var builder = new Advancement.Builder();

        builder.display(
                FItems.FROST_WAND,
                advancementTitle(FAdvancements.ROOT),
                advancementDesc(FAdvancements.ROOT),
                Frostiful.id("block/brittle_ice/cracked_0"),
                AdvancementType.TASK,
                true,
                false,
                false
        );

        builder.addCriterion(
                "player_is_cold",
                CriteriaTriggers.TICK.createCriterion(
                        new PlayerTrigger.TriggerInstance(Optional.of(ContextAwarePredicate.create(
                                TemperatureLootCondition.builder(MinMaxBounds.Doubles.atMost(-0.1)).build()
                        )))
                )
        );

        return builder;
    }

    private Advancement.Builder brushPolarBear(HolderLookup.Provider registryLookup) {
        var builder = new Advancement.Builder();

        builder.display(
                FItems.POLAR_BEAR_FUR_TUFT,
                advancementTitle(FAdvancements.BRUSH_POLAR_BEAR),
                advancementDesc(FAdvancements.BRUSH_POLAR_BEAR),
                null,
                AdvancementType.TASK,
                true,
                true,
                false
        );

        HolderLookup.RegistryLookup<EntityType<?>> entityTypes = registryLookup.lookupOrThrow(Registries.ENTITY_TYPE);
        HolderLookup.RegistryLookup<Item> items = registryLookup.lookupOrThrow(Registries.ITEM);

        builder.addCriterion(
                "brush_polar_bear",
                brushedEntityType(entityTypes, items, EntityType.POLAR_BEAR)
        );
        builder.rewards(new AdvancementRewards.Builder().addExperience(15));

        return builder;
    }

    private Advancement.Builder brushEveryAnimal(HolderLookup.Provider registryLookup) {
        var builder = new Advancement.Builder();

        builder.display(
                FItems.FOX_FUR_TUFT,
                advancementTitle(FAdvancements.BRUSH_EVERY_ANIMAL),
                advancementDesc(FAdvancements.BRUSH_EVERY_ANIMAL),
                null,
                AdvancementType.GOAL,
                true,
                true,
                false
        );

        HolderLookup.RegistryLookup<EntityType<?>> entityTypes = registryLookup.lookupOrThrow(Registries.ENTITY_TYPE);
        HolderLookup.RegistryLookup<Item> items = registryLookup.lookupOrThrow(Registries.ITEM);

        List<Holder.Reference<EntityType<?>>> brushables = List.of(
                EntityType.POLAR_BEAR.builtInRegistryHolder(),
                EntityType.PANDA.builtInRegistryHolder(),
                EntityType.FOX.builtInRegistryHolder(),
                EntityType.WOLF.builtInRegistryHolder(),
                EntityType.OCELOT.builtInRegistryHolder()
        );

        for (Holder<EntityType<?>> type : brushables) {
            builder.addCriterion(
                    "brush_" + type.unwrapKey().orElseThrow().identifier().getPath(),
                    brushedEntityType(entityTypes, items, type.value())
            );
        }

        builder.requirements(AdvancementRequirements.Strategy.AND);
        builder.rewards(new AdvancementRewards.Builder().addExperience(200));

        return builder;
    }

    private static Criterion<PlayerInteractTrigger.TriggerInstance> brushedEntityType(
            HolderLookup.RegistryLookup<EntityType<?>> entityTypes,
            HolderLookup.RegistryLookup<Item> items,
            EntityType<?> entityType
    ) {
        return PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(
                ItemPredicate.Builder.item().of(items, Items.BRUSH),
                Optional.of(ContextAwarePredicate.create(
                        LootItemEntityPropertyCondition.hasProperties(
                                LootContext.EntityTarget.THIS,
                                EntityPredicate.Builder.entity()
                                        .entityType(EntityTypePredicate.of(entityTypes, entityType))
                        ).build()
                ))
        );
    }

    private static Component advancementTitle(ResourceKey<Advancement> key) {
        return Component.translatable(advancementTranslationId(key, "title"));
    }

    private static Component advancementDesc(ResourceKey<Advancement> key) {
        return Component.translatable(advancementTranslationId(key, "description"));
    }

    private static String advancementTranslationId(ResourceKey<Advancement> key, String suffix) {
        return Util.makeDescriptionId("advancements", key.identifier()) + "." + suffix;
    }
}