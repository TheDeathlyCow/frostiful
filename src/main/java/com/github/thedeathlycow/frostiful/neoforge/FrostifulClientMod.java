package com.github.thedeathlycow.frostiful.neoforge;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.client.compat.ColorfulHeartsIntegration;
import com.github.thedeathlycow.frostiful.client.render.FrostWandItemRenderer;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.LoadingModList;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = Frostiful.MODID, dist = Dist.CLIENT)
public class FrostifulClientMod {
    public FrostifulClientMod(ModContainer mod, IEventBus modBus) {
        if (LoadingModList.get().getModFileById("colorfulhearts") != null) {
            ColorfulHeartsIntegration.initialize(modBus);
        }

        modBus.addListener(FrostifulClientMod::onRegisterAdditionalModels);

        mod.registerExtensionPoint(IConfigScreenFactory.class, (container, parent) -> {
            return AutoConfig.getConfigScreen(FrostifulConfig.class, parent).get();
        });
    }

    private static void onRegisterAdditionalModels(ModelEvent.RegisterAdditional event) {
        event.register(FrostWandItemRenderer.INVENTORY_MODEL_ID);
    }
}