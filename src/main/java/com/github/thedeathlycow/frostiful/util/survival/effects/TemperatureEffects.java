package com.github.thedeathlycow.frostiful.util.survival.effects;

import com.github.thedeathlycow.frostiful.entity.damage.FDamageSource;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.registry.FRegistries;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TemperatureEffects {

    public static final TemperatureEffect<?> STATUS_EFFECT = new StatusEffectTemperatureEffect();
    public static final TemperatureEffect<?> SCALING_ATTRIBUTE_MODIFIER = new ScalingAttributeModifierTemperatureEffect();
    public static final TemperatureEffect<?> FREEZE_DAMAGE = new DamageTemperatureEffect(DamageSource.FREEZE);
    public static final TemperatureEffect<?> MELT_DAMAGE = new DamageTemperatureEffect(FDamageSource.MELT);
    public static final TemperatureEffect<?> EMPTY = new EmptyTemperatureEffect();

    public static void registerAll() {
        register("status_effect", STATUS_EFFECT);
        register("scaling_attribute_modifier", SCALING_ATTRIBUTE_MODIFIER);
        register("freeze_damage", FREEZE_DAMAGE);
        register("melt_damage", MELT_DAMAGE);
        register("empty", EMPTY);
    }

    private static void register(String name, TemperatureEffect<?> temperatureEffect) {
        Registry.register(
                FRegistries.TEMPERATURE_EFFECTS,
                Frostiful.id(name),
                temperatureEffect);
    }
}
