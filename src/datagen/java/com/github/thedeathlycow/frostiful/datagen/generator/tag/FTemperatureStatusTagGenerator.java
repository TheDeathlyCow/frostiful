package com.github.thedeathlycow.frostiful.datagen.generator.tag;

import com.github.thedeathlycow.frostiful.registry.FTemperatureStatuses;
import com.github.thedeathlycow.frostiful.registry.tag.FTemperatureStatusTags;
import com.github.thedeathlycow.thermoo.api.core.v2.registry.ThermooRegistryKeys;
import com.github.thedeathlycow.thermoo.api.temperature.status.v2.TemperatureStatus;
import com.github.thedeathlycow.thermoo.api.temperature.status.v2.tag.TemperatureStatusTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class FTemperatureStatusTagGenerator extends FabricTagsProvider<TemperatureStatus> {
    public FTemperatureStatusTagGenerator(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, ThermooRegistryKeys.TEMPERATURE_STATUS, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        // thermoo tags
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