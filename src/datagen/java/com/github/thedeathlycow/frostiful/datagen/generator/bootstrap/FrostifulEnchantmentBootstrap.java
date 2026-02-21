package com.github.thedeathlycow.frostiful.datagen.generator.bootstrap;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.entity.loot.RootedLootCondition;
import com.github.thedeathlycow.frostiful.item.enchantment.HeatDrainEnchantmentEffect;
import com.github.thedeathlycow.frostiful.registry.FEnchantments;
import com.github.thedeathlycow.frostiful.registry.FEntityAttributes;
import com.github.thedeathlycow.frostiful.registry.tag.FEnchantmentTags;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import net.minecraft.advancements.criterion.MinMaxBounds;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.ApplyMobEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentAttributeEffect;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

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
    }

    private static void register(
            BootstrapContext<Enchantment> context,
            ResourceKey<Enchantment> key,
            Enchantment.Builder builder
    ) {
        context.register(key, builder.build(key.identifier()));
    }

    private FrostifulEnchantmentBootstrap() {

    }
}