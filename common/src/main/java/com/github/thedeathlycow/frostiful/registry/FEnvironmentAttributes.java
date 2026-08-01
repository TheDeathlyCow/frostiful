package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.registry.tag.FBiomeTags;
import com.github.thedeathlycow.frostiful.survival.wind.WindBehavior;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.attribute.EnvironmentAttribute;

public final class FEnvironmentAttributes {
    public static final EnvironmentAttribute<WindBehavior> WIND_BEHAVIOR = register(
            "gameplay/wind_behavior",
            EnvironmentAttribute.builder(FAttributeTypes.WIND_BEHAVIOR)
                    .defaultValue(WindBehavior.NEVER)
    );

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful environment attributes");
        BiomeModifications.create(Frostiful.id("set_windy_attribute"))
                .add(
                        ModificationPhase.ADDITIONS,
                        BiomeSelectors.tag(FBiomeTags.FREEZING_WIND_SPAWNS_IN_STORMS),
                        (selection, modification) -> {
                            modification.getAttributes().set(FEnvironmentAttributes.WIND_BEHAVIOR, WindBehavior.DURING_RAIN);
                        }
                )
                .add(
                        ModificationPhase.ADDITIONS,
                        BiomeSelectors.tag(FBiomeTags.FREEZING_WIND_ALWAYS_SPAWNS),
                        (selection, modification) -> {
                            modification.getAttributes().set(FEnvironmentAttributes.WIND_BEHAVIOR, WindBehavior.ALWAYS);
                        }
                )
                .add(
                        ModificationPhase.POST_PROCESSING,
                        BiomeSelectors.tag(FBiomeTags.FREEZING_WIND_NEVER_SPAWNS),
                        (selection, modification) -> {
                            modification.getAttributes().set(FEnvironmentAttributes.WIND_BEHAVIOR, WindBehavior.NEVER);
                        }
                );
    }

    private static <V> EnvironmentAttribute<V> register(String name, EnvironmentAttribute.Builder<V> builder) {
        return Registry.register(BuiltInRegistries.ENVIRONMENT_ATTRIBUTE, Frostiful.id(name), builder.build());
    }

    private FEnvironmentAttributes() {

    }
}