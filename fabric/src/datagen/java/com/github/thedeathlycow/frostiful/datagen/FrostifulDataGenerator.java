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

package com.github.thedeathlycow.frostiful.datagen;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.datagen.generator.BlockTransformerProvider;
import com.github.thedeathlycow.frostiful.datagen.generator.BootstrappedRegistryGenerator;
import com.github.thedeathlycow.frostiful.datagen.generator.FRecipeProvider;
import com.github.thedeathlycow.frostiful.datagen.generator.client.EnglishUSGenerator;
import com.github.thedeathlycow.frostiful.datagen.generator.client.FrostifulModelGenerator;
import com.github.thedeathlycow.frostiful.datagen.generator.loot.*;
import com.github.thedeathlycow.frostiful.datagen.generator.registry.FrostifulEnchantmentBootstrap;
import com.github.thedeathlycow.frostiful.datagen.generator.registry.FrostifulTemperatureStatusBootstrap;
import com.github.thedeathlycow.frostiful.datagen.generator.tag.*;
import com.github.thedeathlycow.frostiful.registry.FArmorTrimPatterns;
import com.github.thedeathlycow.frostiful.registry.FBannerPatterns;
import com.github.thedeathlycow.frostiful.registry.FDamageTypes;
import com.github.thedeathlycow.thermoo.api.core.v2.registry.ThermooRegistries;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
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

        pack.addProvider(EnglishUSGenerator::new);

        pack.addProvider(BootstrappedRegistryGenerator::new);

        pack.addProvider(FBlockLootGenerator::new);
        pack.addProvider(FEntityLootGenerator::new);
        pack.addProvider(FBrushingLootGenerator::new);
        pack.addProvider(FPlayfightLootGenerator::new);
        pack.addProvider(FChestLootGenerator::new);

        pack.addProvider(FRecipeProvider::new);
        pack.addProvider(BlockTransformerProvider::new);

        pack.addProvider(FrostifulModelGenerator::new);

        FBlockTagGenerator blockTagProvider = pack.addProvider(FBlockTagGenerator::new);
        pack.addProvider((output, registriesFuture) -> new FItemTagGenerator(output, registriesFuture, blockTagProvider));
        pack.addProvider(FEntityTypeTagGenerator::new);
        pack.addProvider(FEnchantmentTagGenerator::new);
        pack.addProvider(FTemperatureStatusTagGenerator::new);
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
        registryBuilder.add(
                Registries.ENCHANTMENT,
                FrostifulEnchantmentBootstrap::bootstrap
        );
        registryBuilder.add(
                Registries.DAMAGE_TYPE,
                FDamageTypes::bootstrap
        );
        registryBuilder.add(
                ThermooRegistries.TEMPERATURE_STATUS,
                FrostifulTemperatureStatusBootstrap::bootstrap
        );
    }

    @Override
    @Nullable
    public String getEffectiveModId() {
        return Frostiful.MODID;
    }
}