package com.github.thedeathlycow.frostiful.entity.effect;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.registry.FItems;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.registry.Registry;

public class FPotions {

    public static final Potion FREEZING = create("freezing", new StatusEffectInstance(FStatusEffects.FROST_BITE, 180 * 20, 0));
    public static final Potion FREEZING_LONG = create("freezing_long", new StatusEffectInstance(FStatusEffects.FROST_BITE, 2 * 180 * 20, 0));
    public static final Potion FREEZING_STRONG = create("freezing_strong", new StatusEffectInstance(FStatusEffects.FROST_BITE, 90 * 20, 1));

    public static void register() {
        register("freezing", FREEZING);
        register("freezing_long", FREEZING_LONG);
        register("freezing_strong", FREEZING_STRONG);

        BrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, FItems.ICICLE, FPotions.FREEZING);
        BrewingRecipeRegistry.registerPotionRecipe(FPotions.FREEZING, Items.REDSTONE, FPotions.FREEZING_LONG);
        BrewingRecipeRegistry.registerPotionRecipe(FPotions.FREEZING, Items.GLOWSTONE_DUST, FPotions.FREEZING_STRONG);
    }

    private static Potion create(String name, StatusEffectInstance... effects) {
        return new Potion(String.format("%s.%s", Frostiful.MODID, name), effects);
    }

    private static void register(String name, Potion potion) {
        Registry.register(Registry.POTION, Frostiful.id(name), potion);
    }

}
