package com.github.thedeathlycow.frostiful.neoforge;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.client.compat.ColorfulHeartsIntegration;
import com.github.thedeathlycow.frostiful.client.render.FrostWandItemRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.LoadingModList;
import net.neoforged.neoforge.client.event.ModelEvent;

@Mod(value = Frostiful.MODID, dist = Dist.CLIENT)
public class FrostifulClientMod {
    public FrostifulClientMod(IEventBus modBus) {
        if (LoadingModList.get().getModFileById("colorfulhearts") != null) {
            ColorfulHeartsIntegration.initialize(modBus);
        }

        modBus.addListener(FrostifulClientMod::onRegisterAdditionalModels);
    }

    private static void onRegisterAdditionalModels(ModelEvent.RegisterAdditional event) {
        event.register(FrostWandItemRenderer.INVENTORY_MODEL_ID);
    }
}