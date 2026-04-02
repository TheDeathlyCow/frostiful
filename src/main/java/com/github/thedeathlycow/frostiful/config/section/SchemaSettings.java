package com.github.thedeathlycow.frostiful.config.section;

import com.github.thedeathlycow.frostiful.Frostiful;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;

import java.nio.file.Path;

public class SchemaSettings {
    public static final Path PATH = Frostiful.getConfigDir().resolve("schema.json5");

    public static final ConfigClassHandler<SchemaSettings> HANDLER = ConfigClassHandler.createBuilder(SchemaSettings.class)
            .id(Frostiful.id("schema"))
            .serializer(
                    config -> GsonConfigSerializerBuilder.create(config)
                            .setPath(PATH)
                            .setJson5(true)
                            .build()
            )
            .build();

    public static final int CONFIG_VERSION = 1;

    @SerialEntry(comment = "Config schema version, do not touch! Changing this value may result in unexpected behaviour.")
    int schemaVersion = CONFIG_VERSION;

    public int getSchemaVersion() {
        return schemaVersion;
    }

    public void setSchemaVersion(int schemaVersion) {
        this.schemaVersion = schemaVersion;
    }
}