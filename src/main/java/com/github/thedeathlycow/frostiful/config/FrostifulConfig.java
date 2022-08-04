package com.github.thedeathlycow.frostiful.config;

import com.github.thedeathlycow.frostiful.config.group.*;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Config(name = Frostiful.MODID)
public class FrostifulConfig extends PartitioningSerializer.GlobalData {

    @Environment(EnvType.CLIENT)
    @ConfigEntry.Gui.CollapsibleObject
    ClientConfigGroup clientConfig = new ClientConfigGroup();

    @ConfigEntry.Gui.CollapsibleObject
    CombatConfigGroup combatConfig = new CombatConfigGroup();

    @ConfigEntry.Gui.CollapsibleObject
    FreezingConfigGroup freezingConfig = new FreezingConfigGroup();

    @ConfigEntry.Gui.CollapsibleObject
    IcicleConfigGroup icicleConfig = new IcicleConfigGroup();

    @ConfigEntry.Gui.CollapsibleObject
    WeatherConfigGroup weatherConfig = new WeatherConfigGroup();

}
