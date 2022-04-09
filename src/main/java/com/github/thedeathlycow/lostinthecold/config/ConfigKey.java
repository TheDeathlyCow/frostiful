package com.github.thedeathlycow.lostinthecold.config;

import com.google.gson.JsonElement;

public interface ConfigKey {

    Object deserialize(JsonElement jsonElement);

}
