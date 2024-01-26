package com.github.thedeathlycow.frostiful.mixins.entity.brushing;

import com.github.thedeathlycow.frostiful.entity.FBrushable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public abstract class MobBrushingMixin extends LivingEntity {

    protected MobBrushingMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    /**
     * Inject into MobEntity as some mobs (such as Polar Bears) lack a definition of {@link MobEntity#interactMob(PlayerEntity, Hand)}
     *
     * @param player The player who interacted with the mob
     * @param hand   The hand they used to interact
     * @param cir    Callback info
     */
    @Inject(
            method = "interactMob",
            at = @At("HEAD")
    )
    private void brushPolarBear(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        MobEntity animal = (MobEntity) (Object) this;
        ItemStack heldItem = player.getStackInHand(hand);
        if (heldItem.isOf(Items.BRUSH) && animal instanceof FBrushable brushable && brushable.frostiful$isBrushable()) {
            brushable.frostiful$brush(player, SoundCategory.PLAYERS);
            this.emitGameEvent(GameEvent.SHEAR, player);

            if (!this.getWorld().isClient) {
                heldItem.damage(
                        1, player,
                        callback -> callback.sendToolBreakStatus(hand)
                );
            }
        }
    }
}
