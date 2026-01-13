package com.github.thedeathlycow.frostiful.datagen;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.datagen.generator.FRecipeProvider;
import com.github.thedeathlycow.frostiful.datagen.generator.tag.FBlockTagGenerator;
import com.github.thedeathlycow.frostiful.datagen.generator.tag.FItemTagGenerator;
import com.github.thedeathlycow.frostiful.datagen.generator.client.FrostifulModelGenerator;
import com.github.thedeathlycow.frostiful.registry.FArmorTrimPatterns;
import com.github.thedeathlycow.frostiful.registry.FBannerPatterns;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
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

        FBlockTagGenerator blockTagProvider = pack.addProvider(FBlockTagGenerator::new);
        pack.addProvider((output, registriesFuture) -> new FItemTagGenerator(output, registriesFuture, blockTagProvider));
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        DataGeneratorEntrypoint.super.buildRegistry(registryBuilder);
        registryBuilder.add(
                Registries.TRIM_PATTERN,
                FArmorTrimPatterns::bootstrap
        );
        registryBuilder.add(
                Registries.BANNER_PATTERN,
                FBannerPatterns::bootstrap
        );
    }

    @Override
    @Nullable
    public String getEffectiveModId() {
        return Frostiful.MODID;
    }
}