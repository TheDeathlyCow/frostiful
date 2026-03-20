package com.github.thedeathlycow.frostiful.datagen.generator.tag;

import com.github.thedeathlycow.frostiful.registry.tag.FEntityTypeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.entity.EntityType;

import java.util.concurrent.CompletableFuture;

public class FEntityTypeTagGenerator extends FabricTagsProvider.EntityTypeTagsProvider {
    public FEntityTypeTagGenerator(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        valueLookupBuilder(FEntityTypeTags.HAS_PLAYER_TEMPERATURE_STATUSES)
                .add(EntityType.PLAYER)
                .add(EntityType.MANNEQUIN);
    }
}