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