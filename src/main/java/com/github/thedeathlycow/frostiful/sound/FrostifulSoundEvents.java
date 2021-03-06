package com.github.thedeathlycow.frostiful.sound;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FrostifulSoundEvents {

    public static final SoundEvent FIRE_LICHEN_DISCHARGE = FrostifulSoundEvents.of("block.frostiful.sun_lichen.discharge");
    public static final SoundEvent CAMPFIRE_HISS = FrostifulSoundEvents.of("block.frostiful.campfire.hiss");

    public static void registerSoundEvents() {
        register(FIRE_LICHEN_DISCHARGE);
        register(CAMPFIRE_HISS);
    }

    private static SoundEvent of(String name) {
        return new SoundEvent(new Identifier(Frostiful.MODID, name));
    }

    private static void register(SoundEvent event) {
        Registry.register(Registry.SOUND_EVENT, event.getId(), event);
    }
}
