package com.github.thedeathlycow.frostiful.mixins.compat.farmersdelight.present;

import com.github.thedeathlycow.frostiful.compat.FoodIntegration;
import com.nhoryzon.mc.farmersdelight.item.HotCocoaItem;
import com.nhoryzon.mc.farmersdelight.item.MilkBottleItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(HotCocoaItem.class)
public abstract class HotCocoaItemMixin extends MilkBottleItem {

    @Inject(
            method = "affectConsumer",
            at = @At("HEAD")
    )
    private void onConsume(ItemStack stack, World world, LivingEntity user, CallbackInfo ci) {
        FoodIntegration.onConsumeFood(this, stack, user);
    }

    @Inject(
            method = "appendTooltip",
            at = @At("HEAD")
    )
    private void appendWarmthToolTip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci) {
        FoodIntegration.appendWarmthTooltip(this, stack, tooltip);
    }

}
