package com.github.thedeathlycow.frostiful.mixins.block;

import com.github.thedeathlycow.frostiful.tag.FItemTags;
import net.minecraft.block.PowderSnowBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.tag.EntityTypeTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PowderSnowBlock.class)
public class PowderSnowWalkableMixin {


    @Inject(
            method = "canWalkOnPowderSnow",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void canWalkOnPowderSnowBootTag(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (!entity.getType().isIn(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS)) {

            if (entity instanceof LivingEntity livingEntity) {
                boolean canWalk = livingEntity.getEquippedStack(EquipmentSlot.FEET).isIn(FItemTags.POWDER_SNOW_WALKABLE);
                cir.setReturnValue(canWalk);
            }
        }
    }

}
