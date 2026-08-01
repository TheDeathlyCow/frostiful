package com.github.thedeathlycow.frostiful.config;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.schema.ConfigUpdater;
import com.github.thedeathlycow.frostiful.config.section.SchemaSettings;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

import java.io.IOException;
import java.util.function.Supplier;

class Updater {
    private static final Supplier<Int2ObjectMap<ConfigUpdater>> SCHEMAS = () -> {
        Int2ObjectMap<ConfigUpdater> map = new Int2ObjectArrayMap<>();

        return map;
    };

    static void run() {
        SchemaSettings.HANDLER.load();
        SchemaSettings schemaConfig = SchemaSettings.HANDLER.instance();

        final int latestSchemaVersion = SchemaSettings.CONFIG_VERSION;
        final int currentSchemaVersion = schemaConfig.getSchemaVersion();

        boolean continueUpgrade;

        if (currentSchemaVersion < latestSchemaVersion) {
            Frostiful.LOGGER.info("Frostiful config is out of date! Config files will be automatically upgraded.");
            continueUpgrade = true;
        } else if (currentSchemaVersion > latestSchemaVersion) {
            Frostiful.LOGGER.error(
                    "Current Frostiful config schema version {} is greater than the latest supported by " +
                            "this version ({}). This may result in unexpected changes to the config files, are you " +
                            "sure you're using the right mod version?",
                    currentSchemaVersion,
                    latestSchemaVersion
            );
            continueUpgrade = false;
        } else {
            Frostiful.LOGGER.info("Frostiful config is up to date!");
            continueUpgrade = false;
        }

        if (!continueUpgrade) {
            return;
        }

        Int2ObjectMap<ConfigUpdater> schemas = SCHEMAS.get();

        for (int step = currentSchemaVersion + 1; step <= latestSchemaVersion; step++) {
            ConfigUpdater updater = schemas.get(step);

            if (updater != null) {
                try {
                    updater.run(currentSchemaVersion);
                } catch (IOException e) {
                    Frostiful.LOGGER.warn(
                            "Unable to upgrade config file from schema version {} to {}, due to IO error. Aborting upgrade.",
                            step - 1,
                            step,
                            e
                    );
                    break;
                }
            }

            schemaConfig.setSchemaVersion(step);
        }

        SchemaSettings.HANDLER.save();
        Frostiful.LOGGER.info(
                "Frostiful config successfully updated from schema version {} to {}.",
                currentSchemaVersion,
                latestSchemaVersion
        );
    }

    private Updater() {

    }
}