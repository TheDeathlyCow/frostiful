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

package com.github.thedeathlycow.frostiful.datagen.generator.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.entity.loot.RootedLootCondition;
import com.github.thedeathlycow.frostiful.item.enchantment.HeatDrainEnchantmentEffect;
import com.github.thedeathlycow.frostiful.registry.FEnchantments;
import com.github.thedeathlycow.frostiful.registry.FEntityAttributes;
import com.github.thedeathlycow.frostiful.registry.tag.FBlockTags;
import com.github.thedeathlycow.frostiful.registry.tag.FEnchantmentTags;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import net.minecraft.advancements.criterion.BlockPredicate;
import net.minecraft.advancements.criterion.EntityFlagsPredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.LocationPredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AllOf;
import net.minecraft.world.item.enchantment.effects.ApplyMobEffect;
import net.minecraft.world.item.enchantment.effects.ChangeItemDamage;
import net.minecraft.world.item.enchantment.effects.EnchantmentAttributeEffect;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.EnchantmentLevelProvider;

public final class FrostifulEnchantmentBootstrap {
    public static void bootstrap(BootstrapContext<Enchantment> context) {
        HolderGetter<DamageType> damageTypes = context.lookup(Registries.DAMAGE_TYPE);
        HolderGetter<Enchantment> enchantments = context.lookup(Registries.ENCHANTMENT);
        HolderGetter<Item> items = context.lookup(Registries.ITEM);
        HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);
        HolderGetter<EntityType<?>> entityTypes = context.lookup(Registries.ENTITY_TYPE);

        register(
                context,
                FEnchantments.ENERVATION,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        items.getOrThrow(FItemTags.ENCHANTABLE_FROST_WAND),
                                        10,
                                        3,
                                        Enchantment.dynamicCost(1, 11),
                                        Enchantment.dynamicCost(21, 11),
                                        4,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .exclusiveWith(enchantments.getOrThrow(FEnchantmentTags.HEAT_DRAIN_EXCLUSIVE_SET))
                        .withEffect(
                                EnchantmentEffectComponents.POST_ATTACK,
                                EnchantmentTarget.ATTACKER,
                                EnchantmentTarget.VICTIM,
                                new HeatDrainEnchantmentEffect(
                                        LevelBasedValue.perLevel(210),
                                        0.75f,
                                        false
                                )
                        )
        );

        register(
                context,
                FEnchantments.FROZEN_TOUCH_CURSE,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        items.getOrThrow(FItemTags.ENCHANTABLE_FROST_WAND),
                                        10,
                                        3,
                                        Enchantment.dynamicCost(1, 11),
                                        Enchantment.dynamicCost(21, 11),
                                        4,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .exclusiveWith(enchantments.getOrThrow(FEnchantmentTags.HEAT_DRAIN_EXCLUSIVE_SET))
                        .withEffect(
                                EnchantmentEffectComponents.POST_ATTACK,
                                EnchantmentTarget.ATTACKER,
                                EnchantmentTarget.VICTIM,
                                new HeatDrainEnchantmentEffect(
                                        LevelBasedValue.perLevel(210),
                                        0.75f,
                                        true
                                )
                        )
        );

        register(
                context,
                FEnchantments.ICE_BREAKER,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(FItemTags.ENCHANTABLE_FROST_WAND),
                                        items.getOrThrow(FItemTags.ENCHANTABLE_FROST_WAND),
                                        10,
                                        3,
                                        Enchantment.dynamicCost(1, 11),
                                        Enchantment.dynamicCost(21, 11),
                                        4,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withEffect(
                                EnchantmentEffectComponents.ATTRIBUTES,
                                new EnchantmentAttributeEffect(
                                        Frostiful.id("enchantment.ice_breaker"),
                                        FEntityAttributes.ICE_BREAKER_DAMAGE,
                                        LevelBasedValue.perLevel(1),
                                        AttributeModifier.Operation.ADD_VALUE
                                )
                        )
                        .withEffect(
                                EnchantmentEffectComponents.POST_ATTACK,
                                EnchantmentTarget.ATTACKER,
                                EnchantmentTarget.VICTIM,
                                new ApplyMobEffect(
                                        HolderSet.direct(MobEffects.SLOWNESS),
                                        LevelBasedValue.perLevel(1),
                                        LevelBasedValue.perLevel(2, 1),
                                        LevelBasedValue.perLevel(0, 1),
                                        LevelBasedValue.perLevel(0, 1)
                                ),
                                RootedLootCondition.builder()
                                        .atLeast(1)
                        )
        );

        register(
                context,
                FEnchantments.ICE_SPEED,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(FItemTags.ENCHANTABLE_ICE_SKATES),
                                        items.getOrThrow(FItemTags.ENCHANTABLE_ICE_SKATES),
                                        2,
                                        3,
                                        Enchantment.dynamicCost(10, 10),
                                        Enchantment.dynamicCost(25, 10),
                                        8,
                                        EquipmentSlotGroup.FEET
                                )
                        )
                        .withEffect(
                                EnchantmentEffectComponents.LOCATION_CHANGED,
                                AllOf.locationBasedEffects(
                                        new EnchantmentAttributeEffect(
                                                Frostiful.id("enchantment.ice_speed"),
                                                Attributes.MOVEMENT_SPEED,
                                                LevelBasedValue.perLevel(0.0405f, 0.0105f),
                                                AttributeModifier.Operation.ADD_VALUE
                                        )
                                ),
                                applyMovementSpeedRequirements(blocks)
                        )
                        .withEffect(
                                EnchantmentEffectComponents.LOCATION_CHANGED,
                                new ChangeItemDamage(LevelBasedValue.constant(1.0f)),
                                AllOfCondition.allOf(
                                        LootItemRandomChanceCondition.randomChance(
                                                EnchantmentLevelProvider.forEnchantmentLevel(
                                                        LevelBasedValue.constant(0.04f)
                                                )
                                        ),
                                        LootItemEntityPropertyCondition.hasProperties(
                                                LootContext.EntityTarget.THIS,
                                                EntityPredicate.Builder.entity()
                                                        .flags(EntityFlagsPredicate.Builder.flags().setOnGround(true))
                                                        .movementAffectedBy(
                                                                LocationPredicate.Builder.location()
                                                                        .setBlock(BlockPredicate.Builder.block().of(blocks, FBlockTags.ICE_SPEED_BLOCKS))
                                                        )
                                        )
                                )
                        )
        );
    }

    private static void register(
            BootstrapContext<Enchantment> context,
            ResourceKey<Enchantment> key,
            Enchantment.Builder builder
    ) {
        context.register(key, builder.build(key.identifier()));
    }

    private static AllOfCondition.Builder applyMovementSpeedRequirements(HolderGetter<Block> blocks) {
        var notFlying = EntityFlagsPredicate.Builder.flags().setIsFlying(false);

        var isIceSpeedBlock = LocationPredicate.Builder.location()
                .setBlock(
                        BlockPredicate.Builder.block().of(blocks, FBlockTags.ICE_SPEED_BLOCKS)
                );

        var notInVehicle = InvertedLootItemCondition.invert(
                LootItemEntityPropertyCondition.hasProperties(
                        LootContext.EntityTarget.THIS,
                        EntityPredicate.Builder.entity().vehicle(EntityPredicate.Builder.entity())
                )
        );

        var activeOnIce = AllOfCondition.allOf(
                EnchantmentActiveCheck.enchantmentActiveCheck(),
                LootItemEntityPropertyCondition.hasProperties(
                        LootContext.EntityTarget.THIS,
                        EntityPredicate.Builder.entity().flags(notFlying)
                ),
                AnyOfCondition.anyOf(
                        LootItemEntityPropertyCondition.hasProperties(
                                LootContext.EntityTarget.THIS,
                                EntityPredicate.Builder.entity()
                                        .movementAffectedBy(isIceSpeedBlock)
                        ),
                        LootItemEntityPropertyCondition.hasProperties(
                                LootContext.EntityTarget.THIS,
                                EntityPredicate.Builder.entity().flags(notFlying).build()
                        )
                )
        );

        var inactiveOnIce = AllOfCondition.allOf(
                EnchantmentActiveCheck.enchantmentInactiveCheck(),
                LootItemEntityPropertyCondition.hasProperties(
                        LootContext.EntityTarget.THIS,
                        EntityPredicate.Builder.entity()
                                .movementAffectedBy(isIceSpeedBlock)
                                .flags(notFlying)
                )
        );

        var onIce = AnyOfCondition.anyOf(activeOnIce, inactiveOnIce);

        return AllOfCondition.allOf(notInVehicle, onIce);
    }

    private FrostifulEnchantmentBootstrap() {

    }
}