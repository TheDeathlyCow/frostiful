package com.github.thedeathlycow.frostiful.survival;

import com.github.thedeathlycow.frostiful.entity.damage.FDamageSource;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.thermoo.api.ThermooRegistries;
import com.github.thedeathlycow.thermoo.api.temperature.effects.LegacyDamageTemperatureEffect;
import com.github.thedeathlycow.thermoo.api.temperature.effects.TemperatureEffect;
import net.minecraft.util.registry.Registry;

public class FTemperatureEffects {

    public static final TemperatureEffect<?> LEGACY_MELT_DAMAGE = new LegacyDamageTemperatureEffect(FDamageSource.MELT);

    public static void registerAll() {
        register("melt_damage_legacy", LEGACY_MELT_DAMAGE);
    }

    private static void register(String name, TemperatureEffect<?> temperatureEffect) {
        Registry.register(ThermooRegistries.TEMPERATURE_EFFECTS, Frostiful.id(name), temperatureEffect);
    }
}
