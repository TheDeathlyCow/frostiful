package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;

public class FPotions {

    public static final Holder<Potion> FREEZING = registerReference(
            "freezing",
            create("freezing", new MobEffectInstance(FStatusEffects.FROST_BITE, 180 * 20, 0))
    );
    public static final Holder<Potion> FREEZING_LONG = registerReference(
            "long_freezing",
            create("freezing", new MobEffectInstance(FStatusEffects.FROST_BITE, 2 * 180 * 20, 0))
    );
    public static final Holder<Potion> FREEZING_STRONG = registerReference(
            "strong_freezing",
            create("freezing", new MobEffectInstance(FStatusEffects.FROST_BITE, 90 * 20, 1))
    );

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful potions");
        FabricBrewingRecipeRegistryBuilder.BUILD.register(
                builder -> {
                    builder.addMix(Potions.AWKWARD, FItems.FROZEN_ROD, FPotions.FREEZING);
                    builder.addMix(FPotions.FREEZING, Items.REDSTONE, FPotions.FREEZING_LONG);
                    builder.addMix(FPotions.FREEZING, Items.GLOWSTONE_DUST, FPotions.FREEZING_STRONG);
                }
        );
    }

    private static Potion create(String name, MobEffectInstance... effects) {
        return new Potion(String.format("%s.%s", Frostiful.MODID, name), effects);
    }

    private static Holder<Potion> registerReference(String name, Potion potion) {
        return Registry.registerForHolder(BuiltInRegistries.POTION, Frostiful.location(name), potion);
    }

    private FPotions() {

    }

}
