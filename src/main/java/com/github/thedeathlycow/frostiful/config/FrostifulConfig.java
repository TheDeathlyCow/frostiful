package com.github.thedeathlycow.frostiful.config;

import com.github.thedeathlycow.frostiful.config.group.IcicleConfigGroup;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.google.common.collect.ImmutableList;
import com.oroarmor.config.Config;
import com.oroarmor.config.ConfigItemGroup;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.util.List;

public class FrostifulConfig extends Config {

    public static final ConfigItemGroup icicleConfigGroup = new IcicleConfigGroup();

    public static final List<ConfigItemGroup> configs = ImmutableList.of(icicleConfigGroup);

    /**
     * Creates a new config
     */
    public FrostifulConfig() {
        super(configs, new File(FabricLoader.getInstance().getConfigDir().toFile(), Frostiful.MODID + ".json"), Frostiful.MODID);
    }
}
