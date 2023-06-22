package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.thermoo.api.ThermooRegistries;
import com.github.thedeathlycow.thermoo.api.temperature.effects.TemperatureEffect;
import net.minecraft.registry.Registry;

public class FTemperatureEffects {

//    @SuppressWarnings("deprecation")
//    public static final TemperatureEffect<?> LEGACY_MELT_DAMAGE = new LegacyDamageTemperatureEffect((world) -> world.getDamageSources().freeze());

    public static void registerAll() {
        //register("melt_damage_legacy", LEGACY_MELT_DAMAGE);
    }

    private static void register(String name, TemperatureEffect<?> temperatureEffect) {
        Registry.register(ThermooRegistries.TEMPERATURE_EFFECTS, Frostiful.id(name), temperatureEffect);
    }
}
