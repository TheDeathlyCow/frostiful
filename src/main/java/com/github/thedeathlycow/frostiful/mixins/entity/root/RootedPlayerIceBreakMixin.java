package com.github.thedeathlycow.frostiful.mixins.entity.root;

import com.github.thedeathlycow.frostiful.entity.RootedEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(PlayerEntity.class)
public abstract class RootedPlayerIceBreakMixin extends LivingEntity implements RootedEntity {

    protected RootedPlayerIceBreakMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    /**
     * Modifies the damage taken by the player when their ice is broken. Bypasses armour, but does not bypass
     * protection. This mixin is needed the target method is completely overridden by {@link PlayerEntity}
     *
     * @param args A tuple containing a {@link DamageSource} and a {@link Float}
     */
    @ModifyArgs(
            method = "applyDamage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;modifyAppliedDamage(Lnet/minecraft/entity/damage/DamageSource;F)F"
            )
    )
    private void applyIceBreakDamage(Args args) {
        if (this.frostiful$isRooted() && !this.getWorld().isClient) {
            final DamageSource source = args.get(0);
            final float amount = args.get(1);

            float damage = RootedEntity.getIceBreakerDamage(source.getAttacker());

            args.set(1, amount + damage);
        }
    }

}
