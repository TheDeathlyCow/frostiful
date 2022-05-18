package com.github.thedeathlycow.frostiful.mixins.entity.mob;

import com.github.thedeathlycow.frostiful.item.FrostifulItems;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(HostileEntity.class)
public class StrayDefaultArrowItemMixin {

    @Inject(
            method = "getArrowType",
            at = @At(
                    value = "RETURN",
                    ordinal = 0
            ),
            cancellable = true
    )
    private void straysHoldFrostTippedArrowsByDefault(ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        HostileEntity instance = (HostileEntity) (Object) this;

        if (instance instanceof StrayEntity) {
            Predicate<ItemStack> isArrowItem = ((RangedWeaponItem) stack.getItem()).getHeldProjectiles();
            ItemStack offhandStack = RangedWeaponItem.getHeldProjectile(instance, isArrowItem);
            cir.setReturnValue(offhandStack.isEmpty() ? new ItemStack(FrostifulItems.FROST_TIPPED_ARROW) : offhandStack);
        }
    }

}
