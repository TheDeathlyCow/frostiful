package com.github.thedeathlycow.frostiful.mixins.block;

import com.github.thedeathlycow.frostiful.config.group.FreezingConfigGroup;
import com.github.thedeathlycow.frostiful.entity.effect.FrostifulStatusEffects;
import com.github.thedeathlycow.frostiful.sound.FrostifulSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.ChestBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(CampfireBlock.class)
public abstract class CampfireWarmthMixin {

    @Inject(
            method = "onUse",
            at = @At("TAIL"),
            cancellable = true
    )
    public void warmEntityOnUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {

        if (!state.get(CampfireBlock.LIT)) {
            return;
        }

        ItemStack inHand = player.getStackInHand(hand);
        inHand = player.isCreative() ? inHand.copy() : inHand;
        if (!inHand.isEmpty() && inHand.isIn(ItemTags.LOGS_THAT_BURN)) {
            if (!world.isClient) {
                player.incrementStat(Stats.INTERACT_WITH_CAMPFIRE);
                inHand.decrement(1);
                warmNearbyEntities(world, pos);
                cir.setReturnValue(ActionResult.SUCCESS);
            } else {
                cir.setReturnValue(ActionResult.CONSUME);
            }
        }
    }

    private static void warmNearbyEntities(World world, BlockPos pos) {
        final double boxLength = FreezingConfigGroup.CAMPFIRE_WARMTH_SEARCH_DELTA.getValue();
        final int duration = FreezingConfigGroup.CAMPFIRE_WARMTH_TIME.getValue();

        // get all nearby living entities that do not have warmth or
        // who have a weak warmth effect
        List<LivingEntity> nearbyEntities = world.getEntitiesByClass(
                LivingEntity.class,
                Box.of(Vec3d.ofCenter(pos), boxLength, boxLength, boxLength),
                (entity) -> {
                    StatusEffectInstance instance = entity.getStatusEffect(FrostifulStatusEffects.WARMTH);
                    return !entity.isSpectator() && (instance == null || (instance.getAmplifier() == 0 && instance.getDuration() < duration));
                }
        );

        // apply warmth effect to all nearby entities
        for (LivingEntity entity : nearbyEntities) {
            StatusEffectInstance instance = new StatusEffectInstance(FrostifulStatusEffects.WARMTH, duration, 0, true, true);
            entity.addStatusEffect(instance);
        }

        world.playSound(null, pos, FrostifulSoundEvents.CAMPFIRE_HISS, SoundCategory.BLOCKS, 0.5F, 1.5f);
    }
}
