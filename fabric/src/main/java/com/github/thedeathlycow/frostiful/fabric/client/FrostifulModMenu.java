package com.github.thedeathlycow.frostiful.fabric.client;

import com.github.thedeathlycow.frostiful.client.FrostifulConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class FrostifulModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return FrostifulConfigScreen.getConfigScreenFactory()::apply;
    }
}