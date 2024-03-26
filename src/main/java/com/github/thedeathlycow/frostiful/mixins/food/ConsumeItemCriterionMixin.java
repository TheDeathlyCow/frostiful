package com.github.thedeathlycow.frostiful.mixins.food;

import com.github.thedeathlycow.frostiful.compat.FoodIntegration;
import net.minecraft.advancement.criterion.ConsumeItemCriterion;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ConsumeItemCriterion.class)
public class ConsumeItemCriterionMixin {

    @Inject(
            method = "trigger",
            at = @At("HEAD")
    )
    private void onConsumeItem(ServerPlayerEntity player, ItemStack stack, CallbackInfo ci) {
        FoodIntegration.onConsumeFood(stack, player);
    }

}
