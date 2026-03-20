package com.github.thedeathlycow.frostiful.datagen.generator;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.registry.FTemperatureStatuses;
import com.github.thedeathlycow.frostiful.registry.tag.FEntityTypeTags;
import com.github.thedeathlycow.thermoo.api.temperature.status.v2.TemperatureStatus;
import com.github.thedeathlycow.thermoo.api.temperature.status.v2.effect.AttributeModifierEffect;
import com.github.thedeathlycow.thermoo.api.temperature.status.v2.effect.DamageEffect;
import com.github.thedeathlycow.thermoo.api.temperature.status.v2.effect.MobEffectEffect;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.concurrent.CompletableFuture;

public class TemperatureStatusProvider extends FabricDynamicRegistryProvider {
    public TemperatureStatusProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        HolderGetter<EntityType<?>> entityTypes = registries.lookupOrThrow(Registries.ENTITY_TYPE);

        entries.add(
                FTemperatureStatuses.FREEZE_DAMAGE,
                TemperatureStatus.builder(TemperatureStatus.selectAllEntities().temperatureIsAtMost(-1.0))
                        .withInterval(20)
                        .addEffect(DamageEffect.create(1.0f, DamageTypes.FREEZE))
                        .build()
        );

        HolderSet<EntityType<?>> playerEffects = entityTypes.getOrThrow(FEntityTypeTags.HAS_PLAYER_TEMPERATURE_STATUSES);

        entries.add(
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

        entries.add(
                FTemperatureStatuses.PLAYER_CHILLY,
                TemperatureStatus.builder(TemperatureStatus.selector(playerEffects).temperatureIsBetween(-0.99, -0.5))
                        .withInterval(1)
                        .addEffect(
                                MobEffectEffect.builder()
                                        .addEffect(MobEffectEffect.effect(MobEffects.WEAKNESS).ambient())
                                        .build()
                        )
                        .build()
        );

        entries.add(
                FTemperatureStatuses.PLAYER_COLD,
                TemperatureStatus.builder(TemperatureStatus.selector(playerEffects).temperatureIsBetween(-0.99, -0.75))
                        .withInterval(1)
                        .addEffect(
                                MobEffectEffect.builder()
                                        .addEffect(MobEffectEffect.effect(MobEffects.MINING_FATIGUE).ambient())
                                        .build()
                        )
                        .build()
        );

        entries.add(
                FTemperatureStatuses.PLAYER_FREEZING,
                TemperatureStatus.builder(TemperatureStatus.selector(playerEffects).temperatureIsAtMost(-0.99))
                        .withInterval(1)
                        .addEffect(
                                MobEffectEffect.builder()
                                        .addEffect(MobEffectEffect.effect(MobEffects.WEAKNESS).withAmplifier(1).ambient())
                                        .addEffect(MobEffectEffect.effect(MobEffects.MINING_FATIGUE).withAmplifier(1).ambient())
                                        .build()
                        )
                        .build()
        );
    }

    @Override
    public String getName() {
        return "FrostifulTemperatureStatusProvider";
    }
}