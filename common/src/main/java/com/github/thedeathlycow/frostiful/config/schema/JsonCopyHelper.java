package com.github.thedeathlycow.frostiful.config.schema;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class JsonCopyHelper {
    public static void moveInto(JsonObject src, JsonObject dest) {
        for (String key : src.deepCopy().keySet()) {
            dest.add(key, src.remove(key));
        }
    }

    public static void copyStringProperty(JsonObject src, JsonObject dest, String propertyName) {
        dest.addProperty(propertyName, src.remove(propertyName).getAsString());
    }

    public static void copyBooleanProperty(JsonObject src, JsonObject dest, String propertyName) {
        dest.addProperty(propertyName, src.remove(propertyName).getAsBoolean());
    }

    public static void copyFloatProperty(JsonObject src, JsonObject dest, String propertyName) {
        dest.addProperty(propertyName, src.remove(propertyName).getAsFloat());
    }

    public static void copyDoubleProperty(JsonObject src, JsonObject dest, String propertyName) {
        dest.addProperty(propertyName, src.remove(propertyName).getAsDouble());
    }

    public static void copyIntProperty(JsonObject src, JsonObject dest, String propertyName) {
        dest.addProperty(propertyName, src.remove(propertyName).getAsInt());
    }

    public static void convertIntToFloatMultiplier(JsonObject src, String name, int oldDefaultValue) {
        convertIntToFloatMultiplier(src, name, oldDefaultValue, 1.0f);
    }

    public static void normalizeFloat(JsonObject src, String name, float base) {
        float value = src.remove(name).getAsFloat();
        src.addProperty(name, value / base);
    }

    public static void convertIntToFloatMultiplier(JsonObject src, String name, int oldDefaultValue, float base) {
        int savedValue = Math.abs(src.remove(name).getAsInt());

        float multiplier = (float) savedValue / oldDefaultValue;

        src.addProperty(name, multiplier * base);
    }

    public static void rename(JsonObject json, String oldName, String newName) {
        JsonElement prop = json.remove(oldName);
        json.add(newName, prop);
    }

    public static JsonObject read(Path path) throws IOException {
        return JsonParser.parseString(Files.readString(path)).getAsJsonObject();
    }

    private JsonCopyHelper() {

    }
}