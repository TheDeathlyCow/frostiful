package com.github.thedeathlycow.frostiful.datagen.generator;

import com.github.thedeathlycow.thermoo.api.core.v2.registry.ThermooRegistries;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;

import java.util.concurrent.CompletableFuture;

public class BootstrappedRegistryGenerator extends FabricDynamicRegistryProvider {
    public BootstrappedRegistryGenerator(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        entries.addAll(registries.lookupOrThrow(Registries.TRIM_PATTERN));
        entries.addAll(registries.lookupOrThrow(Registries.BANNER_PATTERN));
        entries.addAll(registries.lookupOrThrow(Registries.ENCHANTMENT));
        entries.addAll(registries.lookupOrThrow(Registries.DAMAGE_TYPE));
        entries.addAll(registries.lookupOrThrow(ThermooRegistries.TEMPERATURE_STATUS));
    }

    @Override
    public String getName() {
        return "FrostifulBootstrappedRegistryGenerator";
    }
}