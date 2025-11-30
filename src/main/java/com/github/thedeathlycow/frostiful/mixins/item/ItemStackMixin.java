package com.github.thedeathlycow.frostiful.mixins.item;

import com.github.thedeathlycow.frostiful.item.attribute.ResistanceComponentBuilder;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ItemStack.class, priority = 1010)
public class ItemStackMixin {
    @Inject(
            method = "<init>(Lnet/minecraft/world/level/ItemLike;ILnet/minecraft/core/component/PatchedDataComponentMap;)V",
            at = @At("TAIL")
    )
    private void onInit(ItemLike item, int count, PatchedDataComponentMap components, CallbackInfo ci) {
        ResistanceComponentBuilder.applyLegacyArmorMaterialTags((ItemStack) (Object) this);
    }
}