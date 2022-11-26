package com.github.thedeathlycow.frostiful.sound;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FSoundEvents {

    public static final SoundEvent FIRE_LICHEN_DISCHARGE = FSoundEvents.of("block.frostiful.sun_lichen.discharge");
    public static final SoundEvent CAMPFIRE_HISS = FSoundEvents.of("block.frostiful.campfire.hiss");
    public static final SoundEvent ITEM_FROST_WAND_CAST_SPELL = FSoundEvents.of("item.frostiful.frost_wand.cast_spell");

    public static final SoundEvent ITEM_FROST_WAND_PREPARE_CAST = FSoundEvents.of("item.frostiful.frost_wand.prepare_cast");

    public static final SoundEvent ENTITY_FROST_SPELL_FREEZE = FSoundEvents.of("entity.frostiful.frost_spell.freeze");


    public static void registerSoundEvents() {
        register(FIRE_LICHEN_DISCHARGE);
        register(CAMPFIRE_HISS);
        register(ITEM_FROST_WAND_CAST_SPELL);
        register(ENTITY_FROST_SPELL_FREEZE);
    }

    private static SoundEvent of(String name) {
        return new SoundEvent(new Identifier(Frostiful.MODID, name));
    }

    private static void register(SoundEvent event) {
        Registry.register(Registry.SOUND_EVENT, event.getId(), event);
    }
}
