package com.github.thedeathlycow.frostiful.config;

import com.github.thedeathlycow.frostiful.config.group.AttributeConfigGroup;
import com.github.thedeathlycow.frostiful.config.group.FreezingConfigGroup;
import com.github.thedeathlycow.frostiful.config.group.IcicleConfigGroup;
import com.github.thedeathlycow.frostiful.config.group.WeatherConfigGroup;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.google.common.collect.ImmutableList;
import com.oroarmor.config.Config;
import com.oroarmor.config.ConfigItemGroup;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.File;
import java.util.List;

public class FrostifulConfig extends Config implements SimpleSynchronousResourceReloadListener {

    public static final ConfigItemGroup ICICLE = new IcicleConfigGroup();
    public static final ConfigItemGroup ATTRIBUTE = new AttributeConfigGroup();
    public static final ConfigItemGroup WEATHER = new WeatherConfigGroup();
    public static final ConfigItemGroup FREEZING = new FreezingConfigGroup();
    public static final List<ConfigItemGroup> configs = ImmutableList.of(ICICLE, ATTRIBUTE, WEATHER, FREEZING);

    /**
     * Creates a new config
     */
    public FrostifulConfig() {
        super(configs, new File(FabricLoader.getInstance().getConfigDir().toFile(), Frostiful.MODID + ".json"), Frostiful.MODID);
    }

    @Override
    public Identifier getFabricId() {
        return new Identifier(Frostiful.MODID, "config");
    }

    @Override
    public void reload(ResourceManager manager) {
        this.readConfigFromFile();
        this.saveConfigToFile();
    }
}
