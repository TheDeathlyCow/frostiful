package com.github.thedeathlycow.frostiful.mixins.entity.mob;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.registry.FItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(HostileEntity.class)
public abstract class StrayCarriesFrostArrowMixin extends PathAwareEntity {

    protected StrayCarriesFrostArrowMixin(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            method = "getArrowType",
            at = @At("HEAD"),
            cancellable = true
    )
    private void straysHaveFrostArrows(ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {

        if (!Frostiful.getConfig().combatConfig.straysCarryFrostArrows()) {
            return;
        }

        if (this.getType() == EntityType.STRAY && stack.getItem() instanceof RangedWeaponItem rangedWeaponItem) {
            Predicate<ItemStack> isItemAmmoTest = rangedWeaponItem.getHeldProjectiles();
            ItemStack heldStack = RangedWeaponItem.getHeldProjectile(this, isItemAmmoTest);
            if (heldStack.isEmpty()) {
                cir.setReturnValue(new ItemStack(FItems.FROST_TIPPED_ARROW));
            }
        }
    }

}
