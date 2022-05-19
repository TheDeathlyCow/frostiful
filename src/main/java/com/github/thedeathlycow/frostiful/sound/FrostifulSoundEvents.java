package com.github.thedeathlycow.frostiful.sound;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FrostifulSoundEvents {

    public static final SoundEvent FIRE_LICHEN_DISCHARGE = of("block.sun_lichen.discharge");

    public static void registerSoundEvents() {
        register(FIRE_LICHEN_DISCHARGE);
    }

    private static SoundEvent of(String name) {
        return new SoundEvent(new Identifier(Frostiful.MODID, name));
    }

    private static void register(SoundEvent event) {
        Registry.register(Registry.SOUND_EVENT, event.getId(), event);
    }
}
