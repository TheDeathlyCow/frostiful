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

import com.github.thedeathlycow.frostiful.registry.FDataAttachments;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

public record RootedLootCondition(
        MinMaxBounds.Ints rootTicksRemaining
) implements LootItemCondition {

    public static final MapCodec<RootedLootCondition> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    MinMaxBounds.Ints.CODEC
                            .fieldOf("root_ticks_remaining")
                            .forGetter(RootedLootCondition::rootTicksRemaining)
            ).apply(instance, RootedLootCondition::new)
    );

    @Override
    public MapCodec<RootedLootCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(LootContext lootContext) {
        Entity entity = lootContext.getOptionalParameter(LootContextParams.THIS_ENTITY);
        if (entity != null) {
            int rootTicksRemaining = entity.getAttached(FDataAttachments.FROST_WAND_ROOT_TICKS);
            return this.rootTicksRemaining.matches(rootTicksRemaining);
        }

        return false;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder implements LootItemCondition.Builder {
        private MinMaxBounds.Ints bounds = MinMaxBounds.Ints.ANY;

        public Builder atLeast(int min) {
            this.bounds = MinMaxBounds.Ints.atLeast(min);
            return this;
        }

        public Builder atMost(int max) {
            this.bounds = MinMaxBounds.Ints.atMost(max);
            return this;
        }

        public Builder between(int min, int max) {
            this.bounds = MinMaxBounds.Ints.between(min, max);
            return this;
        }

        public Builder exactly(int value) {
            this.bounds = MinMaxBounds.Ints.exactly(value);
            return this;
        }

        @Override
        @NotNull
        public RootedLootCondition build() {
            return new RootedLootCondition(bounds);
        }
    }
}
