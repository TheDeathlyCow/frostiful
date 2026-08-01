package com.github.thedeathlycow.frostiful.config.schema;

import java.io.IOException;

public interface ConfigUpdater {
    void run(int originalSchemaVersion) throws IOException;
}