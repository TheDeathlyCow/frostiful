package com.github.thedeathlycow.frostiful.config;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.simple.config.reload.ReloadEvent;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

public class ConfigReloader extends ReloadEvent implements SimpleSynchronousResourceReloadListener {

    @Override
    public Identifier getFabricId() {
        return new Identifier(Frostiful.MODID, "main_config_reload_listener");
    }

    @Override
    public void reload(ResourceManager manager) {
        super.reload();
    }
}
