package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.entity.advancement.FrozenByFrostWandTrigger;
import com.github.thedeathlycow.frostiful.entity.advancement.SunLichenDischargeCriterion;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public final class FCriteria {
    public static final SunLichenDischargeCriterion SUN_LICHEN_DISCHARGE = register(
            "sun_lichen_discharge",
            new SunLichenDischargeCriterion()
    );

    public static final FrozenByFrostWandTrigger FROZEN_BY_FROST_WAND = register(
            "frozen_by_frost_wand",
            new FrozenByFrostWandTrigger()
    );

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful advancement criteria");
    }

    public static <T extends CriterionTrigger<?>> T register(String name, T criterion) {
        return Registry.register(BuiltInRegistries.TRIGGER_TYPES, Frostiful.id(name), criterion);
    }

    private FCriteria() {

    }
}