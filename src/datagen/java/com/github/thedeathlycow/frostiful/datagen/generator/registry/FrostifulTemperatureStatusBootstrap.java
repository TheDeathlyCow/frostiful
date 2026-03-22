package com.github.thedeathlycow.frostiful.datagen.generator.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.registry.FDamageTypes;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import com.github.thedeathlycow.frostiful.registry.FTemperatureStatuses;
import com.github.thedeathlycow.frostiful.registry.tag.FEntityTypeTags;
import com.github.thedeathlycow.thermoo.api.temperature.status.v2.TemperatureStatus;
import com.github.thedeathlycow.thermoo.api.temperature.status.v2.effect.AttributeModifierEffect;
import com.github.thedeathlycow.thermoo.api.temperature.status.v2.effect.DamageEffect;
import com.github.thedeathlycow.thermoo.api.temperature.status.v2.effect.MobEffectEffect;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public final class FrostifulTemperatureStatusBootstrap {
    public static void bootstrap(BootstrapContext<TemperatureStatus> context) {
        HolderGetter<EntityType<?>> entityTypes = context.lookup(Registries.ENTITY_TYPE);

        context.register(
                FTemperatureStatuses.FREEZE_DAMAGE,
                TemperatureStatus.builder(TemperatureStatus.selectAllEntities().temperatureIsAtMost(-0.99))
                        .withInterval(20)
                        .addEffect(DamageEffect.create(1.0f, DamageTypes.FREEZE))
                        .build()
        );

        // players

        HolderSet<EntityType<?>> playerEffects = entityTypes.getOrThrow(FEntityTypeTags.HAS_PLAYER_TEMPERATURE_STATUSES);

        context.register(
                FTemperatureStatuses.PLAYER_MOVEMENT_SPEED,
                TemperatureStatus.builder(TemperatureStatus.selector(playerEffects).temperatureIsAtMost(0))
                        .withInterval(1)
                        .addEffect(AttributeModifierEffect.createScaled(
                                Attributes.MOVEMENT_SPEED,
                                0.05,
                                Frostiful.id("temperature_effect.freezing_slow"),
                                AttributeModifier.Operation.ADD_VALUE
                        ))
                        .build()
        );

        context.register(
                FTemperatureStatuses.PLAYER_CHILLY,
                TemperatureStatus.builder(TemperatureStatus.selector(playerEffects).temperatureIsBetween(-0.99, -0.5))
                        .withInterval(40)
                        .addEffect(
                                MobEffectEffect.builder()
                                        .addEffect(MobEffectEffect.effect(MobEffects.WEAKNESS).ambient())
                                        .build()
                        )
                        .build()
        );

        context.register(
                FTemperatureStatuses.PLAYER_COLD,
                TemperatureStatus.builder(TemperatureStatus.selector(playerEffects).temperatureIsBetween(-0.99, -0.75))
                        .withInterval(40)
                        .addEffect(
                                MobEffectEffect.builder()
                                        .addEffect(MobEffectEffect.effect(MobEffects.MINING_FATIGUE).ambient())
                                        .build()
                        )
                        .build()
        );

        context.register(
                FTemperatureStatuses.PLAYER_FREEZING,
                TemperatureStatus.builder(TemperatureStatus.selector(playerEffects).temperatureIsAtMost(-0.99))
                        .withInterval(40)
                        .addEffect(
                                MobEffectEffect.builder()
                                        .addEffect(MobEffectEffect.effect(MobEffects.WEAKNESS).withAmplifier(1).ambient())
                                        .addEffect(MobEffectEffect.effect(MobEffects.MINING_FATIGUE).withAmplifier(1).ambient())
                                        .build()
                        )
                        .build()
        );

        // players with frostologer cloak

        context.register(
                FTemperatureStatuses.FROSTOLOGY_CLOAK_MOVEMENT_SPEED,
                TemperatureStatus.builder(TemperatureStatus.selector(playerEffects).temperatureIsAtMost(0))
                        .withInterval(1)
                        .addEffect(AttributeModifierEffect.createScaled(
                                Attributes.MOVEMENT_SPEED,
                                -0.04,
                                Frostiful.id("temperature_effect.freezing_speed"),
                                AttributeModifier.Operation.ADD_VALUE
                        ))
                        .build()
        );

        context.register(
                FTemperatureStatuses.FROSTOLOGY_CLOAK_MELTING,
                TemperatureStatus.builder(TemperatureStatus.selector(playerEffects).temperatureIsAtLeast(-0.01))
                        .withInterval(20)
                        .addEffect(DamageEffect.create(4.0f, FDamageTypes.MELT))
                        .addEffect(
                                MobEffectEffect.builder()
                                        .addEffect(MobEffectEffect.effect(MobEffects.WEAKNESS).withAmplifier(1).ambient())
                                        .addEffect(MobEffectEffect.effect(MobEffects.MINING_FATIGUE).withAmplifier(1).ambient())
                                        .build()
                        )
                        .build()
        );

        context.register(
                FTemperatureStatuses.FROSTOLOGY_CLOAK_WARM,
                TemperatureStatus.builder(TemperatureStatus.selector(playerEffects).temperatureIsBetween(-0.25, -0.01))
                        .withInterval(40)
                        .addEffect(
                                MobEffectEffect.builder()
                                        .addEffect(MobEffectEffect.effect(MobEffects.WEAKNESS).ambient())
                                        .addEffect(MobEffectEffect.effect(MobEffects.MINING_FATIGUE).ambient())
                                        .build()
                        )
                        .build()
        );

        context.register(
                FTemperatureStatuses.FROSTOLOGY_CLOAK_COLD,
                TemperatureStatus.builder(TemperatureStatus.selector(playerEffects).temperatureIsAtMost(-0.75))
                        .withInterval(40)
                        .addEffect(
                                MobEffectEffect.builder()
                                        .addEffect(MobEffectEffect.effect(MobEffects.RESISTANCE).ambient())
                                        .build()
                        )
                        .build()
        );

        context.register(
                FTemperatureStatuses.FROSTOLOGY_CLOAK_FREEZING,
                TemperatureStatus.builder(TemperatureStatus.selector(playerEffects).temperatureIsAtMost(-0.99))
                        .withInterval(40)
                        .addEffect(
                                MobEffectEffect.builder()
                                        .addEffect(MobEffectEffect.effect(MobEffects.HASTE).ambient())
                                        .build()
                        )
                        .build()
        );

        // frostologers
        HolderSet<EntityType<?>> frostologer = HolderSet.direct(FEntityTypes.FROSTOLOGER.builtInRegistryHolder());

        context.register(
                FTemperatureStatuses.FROSTOLOGER_ATTACK_DAMAGE,
                TemperatureStatus.builder(TemperatureStatus.selector(frostologer).temperatureIsAtMost(0))
                        .withInterval(1)
                        .addEffect(AttributeModifierEffect.createScaled(
                                Attributes.ATTACK_DAMAGE,
                                -0.1,
                                Frostiful.id("temperature_effect.frostologer_attack_damage"),
                                AttributeModifier.Operation.ADD_VALUE
                        ))
                        .build()
        );

        context.register(
                FTemperatureStatuses.FROSTOLOGER_CHILLY,
                TemperatureStatus.builder(TemperatureStatus.selector(frostologer).temperatureIsBetween(-0.95, -0.5))
                        .withInterval(40)
                        .addEffect(
                                MobEffectEffect.builder()
                                        .addEffect(MobEffectEffect.effect(MobEffects.RESISTANCE))
                                        .addEffect(MobEffectEffect.effect(MobEffects.STRENGTH))
                                        .build()
                        )
                        .build()
        );

        context.register(
                FTemperatureStatuses.FROSTOLOGER_FREEZING,
                TemperatureStatus.builder(TemperatureStatus.selector(frostologer).temperatureIsAtMost(-0.95))
                        .withInterval(40)
                        .addEffect(
                                MobEffectEffect.builder()
                                        .addEffect(MobEffectEffect.effect(MobEffects.SPEED).withAmplifier(1).ambient())
                                        .addEffect(MobEffectEffect.effect(MobEffects.RESISTANCE).withAmplifier(2).ambient())
                                        .addEffect(MobEffectEffect.effect(MobEffects.REGENERATION).withAmplifier(2).ambient())
                                        .addEffect(MobEffectEffect.effect(MobEffects.STRENGTH).withAmplifier(1).ambient())
                                        .build()
                        )
                        .build()
        );
    }

    private FrostifulTemperatureStatusBootstrap() {

    }
}