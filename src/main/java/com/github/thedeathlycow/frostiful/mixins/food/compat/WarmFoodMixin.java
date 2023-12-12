package com.github.thedeathlycow.frostiful.mixins.food.compat;

import com.github.thedeathlycow.frostiful.compat.FoodIntegration;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Item.class)
public abstract class WarmFoodMixin {

    @Inject(
            method = "finishUsing",
            at = @At("HEAD")
    )
    private void addWarmthToWarmFoods(
            ItemStack stack,
            World world,
            LivingEntity user,
            CallbackInfoReturnable<ItemStack> cir
    ) {
        FoodIntegration.onConsumeFood((Item) (Object) this, stack, user);
    }

    @Inject(
            method = "appendTooltip",
            at = @At("TAIL")
    )
    private void addToolTip(
            ItemStack stack,
            @Nullable World world,
            List<Text> tooltip,
            TooltipContext context,
            CallbackInfo ci
    ) {
        FoodIntegration.appendWarmthTooltip((Item) (Object) this, stack, tooltip);
    }
}
