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

package com.github.thedeathlycow.frostiful.datagen.generator.tag;

import com.github.thedeathlycow.frostiful.registry.FTemperatureStatuses;
import com.github.thedeathlycow.frostiful.registry.tag.FTemperatureStatusTags;
import com.github.thedeathlycow.thermoo.api.core.v2.registry.ThermooRegistries;
import com.github.thedeathlycow.thermoo.api.temperature.status.v2.TemperatureStatus;
import com.github.thedeathlycow.thermoo.api.temperature.status.v2.tag.TemperatureStatusTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class FTemperatureStatusTagGenerator extends FabricTagsProvider<TemperatureStatus> {
    public FTemperatureStatusTagGenerator(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, ThermooRegistries.TEMPERATURE_STATUS, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        // thermoo tags
        builder(TemperatureStatusTags.COLD)
                .add(FTemperatureStatuses.FREEZE_DAMAGE)
                .add(FTemperatureStatuses.FROSTOLOGY_CLOAK_MELTING)
                .add(FTemperatureStatuses.FROSTOLOGY_CLOAK_WARM)
                .add(FTemperatureStatuses.PLAYER_MOVEMENT_SPEED)
                .add(FTemperatureStatuses.PLAYER_CHILLY)
                .add(FTemperatureStatuses.PLAYER_COLD)
                .add(FTemperatureStatuses.PLAYER_FREEZING)
                .add(FTemperatureStatuses.FROSTOLOGER_ATTACK_DAMAGE)
                .add(FTemperatureStatuses.FROSTOLOGER_CHILLY)
                .add(FTemperatureStatuses.FROSTOLOGER_FREEZING)
                .add(FTemperatureStatuses.FROSTOLOGY_CLOAK_MOVEMENT_SPEED)
                .add(FTemperatureStatuses.FROSTOLOGY_CLOAK_COLD)
                .add(FTemperatureStatuses.FROSTOLOGY_CLOAK_FREEZING);

        builder(TemperatureStatusTags.HARMFUL)
                .add(FTemperatureStatuses.FREEZE_DAMAGE)
                .add(FTemperatureStatuses.FROSTOLOGY_CLOAK_MELTING)
                .add(FTemperatureStatuses.FROSTOLOGY_CLOAK_WARM)
                .add(FTemperatureStatuses.PLAYER_MOVEMENT_SPEED)
                .add(FTemperatureStatuses.PLAYER_CHILLY)
                .add(FTemperatureStatuses.PLAYER_COLD)
                .add(FTemperatureStatuses.PLAYER_FREEZING);

        builder(TemperatureStatusTags.BENEFICIAL)
                .add(FTemperatureStatuses.FROSTOLOGER_ATTACK_DAMAGE)
                .add(FTemperatureStatuses.FROSTOLOGER_CHILLY)
                .add(FTemperatureStatuses.FROSTOLOGER_FREEZING)
                .add(FTemperatureStatuses.FROSTOLOGY_CLOAK_MOVEMENT_SPEED)
                .add(FTemperatureStatuses.FROSTOLOGY_CLOAK_COLD)
                .add(FTemperatureStatuses.FROSTOLOGY_CLOAK_FREEZING);

        builder(TemperatureStatusTags.APPLICATION_ORDER)
                .addTag(FTemperatureStatusTags.PLAYER_STATUSES)
                .addTag(FTemperatureStatusTags.FROSTOLOGER_STATUSES)
                .add(FTemperatureStatuses.FREEZE_DAMAGE);

        // frostiful tags

        builder(FTemperatureStatusTags.PLAYER_STATUSES)
                .addTag(FTemperatureStatusTags.NORMAL_PLAYER_STATUSES)
                .addTag(FTemperatureStatusTags.FROSTOLOGY_CLOAK_PLAYER_STATUSES);

        builder(FTemperatureStatusTags.NORMAL_PLAYER_STATUSES)
                .add(FTemperatureStatuses.PLAYER_MOVEMENT_SPEED)
                .add(FTemperatureStatuses.PLAYER_CHILLY)
                .add(FTemperatureStatuses.PLAYER_COLD)
                .add(FTemperatureStatuses.PLAYER_FREEZING);

        builder(FTemperatureStatusTags.FROSTOLOGY_CLOAK_PLAYER_STATUSES)
                .add(FTemperatureStatuses.FROSTOLOGY_CLOAK_MOVEMENT_SPEED)
                .add(FTemperatureStatuses.FROSTOLOGY_CLOAK_MELTING)
                .add(FTemperatureStatuses.FROSTOLOGY_CLOAK_WARM)
                .add(FTemperatureStatuses.FROSTOLOGY_CLOAK_COLD)
                .add(FTemperatureStatuses.FROSTOLOGY_CLOAK_FREEZING);

        builder(FTemperatureStatusTags.FROSTOLOGER_STATUSES)
                .add(FTemperatureStatuses.FROSTOLOGER_ATTACK_DAMAGE)
                .add(FTemperatureStatuses.FROSTOLOGER_CHILLY)
                .add(FTemperatureStatuses.FROSTOLOGY_CLOAK_FREEZING);
    }
}