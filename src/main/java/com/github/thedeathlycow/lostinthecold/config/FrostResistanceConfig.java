package com.github.thedeathlycow.lostinthecold.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class FrostResistanceConfig {

    public FrostResistanceConfig(int secondsPerFrostResist, double baseEntityFrostResistance) {
        this.secondsPerFrostResist = secondsPerFrostResist;
        this.baseEntityFrostResistance = baseEntityFrostResistance;
    }

    public static FrostResistanceConfig deserialize(JsonElement element) {
        JsonObject object = element.getAsJsonObject();
        int secondsPerFrostResistance = object.get("seconds_per_frost_resistance").getAsInt();
        double baseEntityFrostResistance = object.get("base_entity_frost_resistance").getAsDouble();
        return new FrostResistanceConfig(secondsPerFrostResistance, baseEntityFrostResistance);
    }

    public int getSecondsPerFrostResistance() {
        return secondsPerFrostResist;
    }

    public double getBaseEntityFrostResistance() {
        return baseEntityFrostResistance;
    }

    private final int secondsPerFrostResist;
    private final double baseEntityFrostResistance;

}
