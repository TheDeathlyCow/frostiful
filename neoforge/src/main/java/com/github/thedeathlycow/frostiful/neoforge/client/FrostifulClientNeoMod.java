package com.github.thedeathlycow.frostiful.neoforge.client;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.client.FrostifulConfigScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = Frostiful.MODID, dist = Dist.CLIENT)
public class FrostifulClientNeoMod {
    public FrostifulClientNeoMod(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, (container, parent) -> {
            return FrostifulConfigScreen.getConfigScreenFactory().apply(parent);
        });
    }
}