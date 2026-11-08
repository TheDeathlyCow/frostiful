/*
 * Frostiful: A Vanilla+ Freezing Temperature Mod. Also try Scorchful!
 * Copyright (C) 2026	TheDeathlyCow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.  If not, see
 * <https://www.gnu.org/licenses/>.
 */

package com.github.thedeathlycow.frostiful.mixins.block;

import com.github.thedeathlycow.frostiful.block.PackedSnowBlock;
import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.registry.FBlocks;
import com.github.thedeathlycow.frostiful.registry.tag.FEntityTypeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gamerules.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockBehaviour.class)
public abstract class SnowPackingMixin {

    @Inject(
            method = "entityInside",
            at = @At("TAIL")
    )
    private void smushSnowWhenSteppedOnByHeavyEntity(BlockState state, Level world, BlockPos pos, Entity entity, InsideBlockEffectApplier handler, boolean bl, CallbackInfo ci) {
        boolean maySmushSnow = state.getBlock() == Blocks.SNOW
                && entity.is(FEntityTypeTags.HEAVY_ENTITY_TYPES)
                && !world.isClientSide()
                && FrostifulConfigYACL.entitySettings().enableHeavyMobSnowPacking()
                && ((ServerLevel) world).getGameRules().get(GameRules.MOB_GRIEFING)
                && isEntityWalkingOn(pos, entity);

        if (maySmushSnow) {

            int layers = state.getValue(SnowLayerBlock.LAYERS);

            BlockState packedSnow = FBlocks.PACKED_SNOW.defaultBlockState()
                    .setValue(PackedSnowBlock.LAYERS, layers);

            world.setBlockAndUpdate(pos, packedSnow);
            entity.playSound(SoundEvents.SNOW_PLACE, 1.0f, 1.0f);
        }
    }

    private static boolean isEntityWalkingOn(BlockPos pos, Entity entity) {
        return entity.onGround() && entity.blockPosition().equals(pos);
    }

}
