package com.github.thedeathlycow.frostiful.datagen;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.datagen.generator.FRecipeProvider;
import com.github.thedeathlycow.frostiful.datagen.generator.client.FrostifulModelGenerator;
import com.github.thedeathlycow.frostiful.registry.FArmorTrimPatterns;
import com.github.thedeathlycow.frostiful.registry.FBannerPatterns;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.item.equipment.trim.ArmorTrimPatterns;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrostifulDataGenerator implements DataGeneratorEntrypoint {
    public static final String MODID = "frostiful-datagen";

    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        LOGGER.info("Running Frostiful datagen");
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();


        pack.addProvider(FRecipeProvider::new);
        pack.addProvider(FrostifulModelGenerator::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        DataGeneratorEntrypoint.super.buildRegistry(registryBuilder);
        registryBuilder.addRegistry(
                RegistryKeys.TRIM_PATTERN,
                FArmorTrimPatterns::bootstrap
        );
        registryBuilder.addRegistry(
                RegistryKeys.BANNER_PATTERN,
                FBannerPatterns::bootstrap
        );
    }

    @Override
    @Nullable
    public String getEffectiveModId() {
        return Frostiful.MODID;
    }
}