package com.github.thedeathlycow.frostiful.mixins.item;

import com.github.thedeathlycow.frostiful.compat.FoodIntegration;
import net.minecraft.advancements.criterion.ConsumeItemTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ConsumeItemTrigger.class)
public class ConsumeItemCriterionMixin {

    @Inject(
            method = "trigger",
            at = @At("HEAD")
    )
    private void onConsumeItem(ServerPlayer player, ItemStack stack, CallbackInfo ci) {
        FoodIntegration.onConsumeFood(stack, player);
    }

}
