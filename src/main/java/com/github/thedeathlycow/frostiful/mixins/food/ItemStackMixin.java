package com.github.thedeathlycow.frostiful.mixins.food;

import com.github.thedeathlycow.frostiful.compat.FoodIntegration;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow public abstract Item getItem();

    @ModifyArg(
            method = "getTooltip",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/Item;appendTooltip(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/item/TooltipContext;)V"
            ),
            index = 2
    )
    private List<Text> appendTooltip(List<Text> tooltip) {
        FoodIntegration.appendWarmthTooltip(
                this.getItem(),
                (ItemStack) (Object) this,
                tooltip
        );

        return tooltip;
    }

}
