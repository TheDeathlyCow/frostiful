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

package com.github.thedeathlycow.frostiful.entity.loot;

import com.github.thedeathlycow.frostiful.survival.PassiveTemperatureEffects;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.Objects;

public record LocationWarmthLootCondition(
        MinMaxBounds.Ints value
) implements LootItemCondition {

    public static final MapCodec<LocationWarmthLootCondition> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    MinMaxBounds.Ints.CODEC
                            .fieldOf("value")
                            .orElse(MinMaxBounds.Ints.ANY)
                            .forGetter(LocationWarmthLootCondition::value)
            ).apply(instance, LocationWarmthLootCondition::new)
    );

    @Override
    public MapCodec<LocationWarmthLootCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(LootContext lootContext) {
        Level world = lootContext.getLevel();
        BlockPos pos = BlockPos.containing(Objects.requireNonNull(lootContext.getOptionalParameter(LootContextParams.ORIGIN)));

        int areaWarmth = PassiveTemperatureEffects.getBlockLightTemperatureChange(world, pos);
        return this.value.matches(areaWarmth);
    }

    public static LootItemCondition.Builder builder(MinMaxBounds.Ints value) {
        return () -> new LocationWarmthLootCondition(value);
    }
}