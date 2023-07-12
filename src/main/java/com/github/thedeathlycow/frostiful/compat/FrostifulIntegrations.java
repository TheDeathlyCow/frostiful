package com.github.thedeathlycow.frostiful.compat;

import io.github.kosmx.emotes.api.events.client.ClientEmoteEvents;
import io.github.kosmx.emotes.api.events.server.ServerEmoteEvents;
import net.fabricmc.loader.api.FabricLoader;

public class FrostifulIntegrations {

    public static final String COLORFUL_HEARTS_ID = "colorfulhearts";

    public static final String TRINKETS_ID = "trinkets";

    public static final String EMOTES_ID = "emotecraft";

    public static boolean isModLoaded(String id) {
        return FabricLoader.getInstance().isModLoaded(id);
    }

    public static void registerEmoteCraftServerListeners() {
        if (isModLoaded(EMOTES_ID)) {
            ServerEmoteEvents.EMOTE_VERIFICATION.register(EmoteCraftListeners.INSTANCE::onStartUsingEmote);
        }
    }

    public static void registerEmoteCraftClientListeners() {
        if (isModLoaded(EMOTES_ID)) {
            ClientEmoteEvents.EMOTE_VERIFICATION.register(EmoteCraftListeners.INSTANCE::onStartUsingEmote);
        }
    }
}
