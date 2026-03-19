package com.github.thedeathlycow.frostiful.block;

import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.config.section.FreezingConfig;
import com.github.thedeathlycow.frostiful.registry.FSoundEvents;
import com.github.thedeathlycow.frostiful.registry.FStatusEffects;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class CampfireUseEventListener implements UseBlockCallback {

    @Override
    public InteractionResult interact(Player player, Level world, InteractionHand hand, BlockHitResult hitResult) {

        if (player.isSpectator()) {
            return InteractionResult.PASS;
        }

        BlockPos pos = hitResult.getBlockPos();
        BlockState state = world.getBlockState(pos);

        if (CampfireBlock.isLitCampfire(state)) {
            ItemStack stack = player.getItemInHand(hand);
            stack = player.isCreative() ? stack.copy() : stack;

            if (!stack.isEmpty() && stack.is(ItemTags.LOGS_THAT_BURN)) {
                if (!world.isClientSide()) {
                    warmNearbyEntities(world, pos);
                    addSmokeParticles(world, pos);
                    player.awardStat(Stats.INTERACT_WITH_CAMPFIRE);
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                    stack.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    private static void addSmokeParticles(Level world, BlockPos pos) {
        if (world instanceof ServerLevel serverWorld) {

            Vec3 position = pos.getCenter().add(0, -0.3, 0);

            serverWorld.sendParticles(
                    ParticleTypes.SMOKE,
                    position.x, position.y, position.z,
                    15,
                    0.5, 0.7, 0.5,
                    1e-3
            );
            serverWorld.sendParticles(
                    ParticleTypes.LAVA,
                    position.x, position.y, position.z,
                    8,
                    0.5, 0.7, 0.5,
                    1e-2
            );

        }
    }

    private static void warmNearbyEntities(Level world, BlockPos pos) {
        FreezingConfig config = FrostifulConfigYACL.freezingConfig();
        final double boxLength = config.getCampfireWarmthSearchRadius();
        final int duration = config.getCampfireWarmthTime();

        // get all nearby living entities that do not have warmth or
        // who have a weak warmth effect
        List<LivingEntity> nearbyEntities = world.getEntitiesOfClass(
                LivingEntity.class,
                AABB.ofSize(Vec3.atCenterOf(pos), boxLength, boxLength, boxLength),
                (entity) -> {
                    MobEffectInstance instance = entity.getEffect(FStatusEffects.WARMTH);
                    return !entity.isSpectator() && (instance == null || (instance.getAmplifier() == 0 && instance.getDuration() < duration));
                }
        );

        // apply warmth effect to all nearby entities
        for (LivingEntity entity : nearbyEntities) {
            MobEffectInstance instance = new MobEffectInstance(FStatusEffects.WARMTH, duration, 0, true, true);
            entity.addEffect(instance);
        }

        world.playSound(null, pos, FSoundEvents.CAMPFIRE_HISS, SoundSource.BLOCKS, 0.5F, 1.5f);
    }
}
