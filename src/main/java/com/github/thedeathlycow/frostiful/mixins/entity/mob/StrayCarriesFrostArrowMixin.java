package com.github.thedeathlycow.frostiful.mixins.entity.mob;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.registry.FItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;

@Mixin(Monster.class)
public abstract class StrayCarriesFrostArrowMixin extends PathfinderMob {

    protected StrayCarriesFrostArrowMixin(EntityType<? extends PathfinderMob> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(
            method = "getProjectile",
            at = @At("HEAD"),
            cancellable = true
    )
    private void straysHaveFrostArrows(ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {

        if (!Frostiful.getConfig().combatConfig.straysCarryFrostArrows()) {
            return;
        }

        if (this.getType() == EntityType.STRAY && stack.getItem() instanceof ProjectileWeaponItem rangedWeaponItem) {
            Predicate<ItemStack> isItemAmmoTest = rangedWeaponItem.getSupportedHeldProjectiles();
            ItemStack heldStack = ProjectileWeaponItem.getHeldProjectile(this, isItemAmmoTest);
            if (heldStack.isEmpty()) {
                cir.setReturnValue(new ItemStack(FItems.GLACIAL_ARROW));
            }
        }
    }

}
