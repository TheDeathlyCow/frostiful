package com.github.thedeathlycow.lostinthecold.config;

import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;

public interface ConfigKey {

    @NotNull
    Object getDefaultValue();

    Object deserialize(JsonElement jsonElement);

}
