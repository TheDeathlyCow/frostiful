package com.github.thedeathlycow.frostiful.mixins.entity.mob;

import com.github.thedeathlycow.frostiful.item.FrostResistantArmorMaterials;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Deprecated
@Mixin(PiglinBrain.class)
public class PiglinBrainMixin {

    @Inject(
            method = "wearsGoldArmor",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void wearsFurLinedGoldArmor(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        for (ItemStack stack : entity.getArmorItems()) {
            Item item = stack.getItem();
            if (item instanceof ArmorItem armorItem) {
                if (armorItem.getMaterial() == FrostResistantArmorMaterials.FUR_LINED_GOLD) {
                    cir.setReturnValue(true);
                }
            }
        }
        // not wearing fur lined gold armor - allow method to continue as normal
    }

}