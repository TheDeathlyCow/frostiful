package com.github.thedeathlycow.frostiful.compat;

import com.github.thedeathlycow.frostiful.entity.IceSkater;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.core.impl.event.EventResult;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class EmoteCraftListeners {

    public static final EmoteCraftListeners INSTANCE = new EmoteCraftListeners();

    @Nullable
    private MinecraftServer server;

    public EventResult onStartUsingEmote(KeyframeAnimation emote, UUID userID) {
        if (server == null) {
            return EventResult.PASS;
        }

        @Nullable
        PlayerEntity player = server.getPlayerManager().getPlayer(userID);

        if (player instanceof IceSkater skater && skater.frostiful$isIceSkating()) {
            return EventResult.SUCCESS;
        }

        return EventResult.PASS;
    }


    private EmoteCraftListeners() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            this.server = server;
        });
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            this.server = null;
        });
    }
}
