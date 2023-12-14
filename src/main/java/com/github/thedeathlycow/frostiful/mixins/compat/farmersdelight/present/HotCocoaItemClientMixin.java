package com.github.thedeathlycow.frostiful.mixins.compat.farmersdelight.present;

import com.github.thedeathlycow.frostiful.compat.FoodIntegration;
import com.nhoryzon.mc.farmersdelight.item.HotCocoaItem;
import com.nhoryzon.mc.farmersdelight.item.MilkBottleItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(HotCocoaItem.class)
public abstract class HotCocoaItemClientMixin extends MilkBottleItem {

    @Inject(
            method = "appendTooltip",
            at = @At("HEAD")
    )
    private void appendWarmthToolTip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci) {
        FoodIntegration.appendWarmthTooltip(this, stack, tooltip);
    }

}
