package com.github.thedeathlycow.frostiful.item;

import net.minecraft.item.FoodComponent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class FoodHelper {

    @Contract("_->new")
    @NotNull
    public static FoodComponent copyFoodComponent(@NotNull FoodComponent other) {
        var builder = new FoodComponent.Builder();
        builder.hunger(other.getHunger());
        builder.saturationModifier(other.getSaturationModifier());
        other.getStatusEffects().forEach(effectAndChance -> {
            builder.statusEffect(effectAndChance.getFirst(), effectAndChance.getSecond());
        });
        if (other.isAlwaysEdible()) {
            builder.alwaysEdible();
        }
        if (other.isMeat()) {
            builder.meat();
        }
        if (other.isSnack()) {
            builder.snack();
        }

        return builder.build();
    }

    private FoodHelper() {

    }
}
