package com.github.thedeathlycow.frostiful.sound;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FSoundEvents {

    public static final SoundEvent FIRE_LICHEN_DISCHARGE = FSoundEvents.of("block.frostiful.sun_lichen.discharge");
    public static final SoundEvent CAMPFIRE_HISS = FSoundEvents.of("block.frostiful.campfire.hiss");
    public static final SoundEvent ITEM_FROST_WAND_CAST_SPELL = FSoundEvents.of("item.frostiful.frost_wand.cast_spell");
    public static final SoundEvent ITEM_FROST_WAND_PREPARE_CAST = FSoundEvents.of("item.frostiful.frost_wand.prepare_cast");
    public static final SoundEvent ENTITY_FROST_SPELL_FREEZE = FSoundEvents.of("entity.frostiful.frost_spell.freeze");

    public static final SoundEvent BLOCK_PACKED_SNOW_BREAK = FSoundEvents.of("block.frostiful.packed_snow.break");
    public static final SoundEvent BLOCK_PACKED_SNOW_FALL = FSoundEvents.of("block.frostiful.packed_snow.fall");
    public static final SoundEvent BLOCK_PACKED_SNOW_HIT = FSoundEvents.of("block.frostiful.packed_snow.hit");
    public static final SoundEvent BLOCK_PACKED_SNOW_PLACE = FSoundEvents.of("block.frostiful.packed_snow.place");
    public static final SoundEvent BLOCK_PACKED_SNOW_STEP = FSoundEvents.of("block.frostiful.packed_snow.step");


    public static final SoundEvent ENTITY_FROSTOLOGER_CAST_SPELL = FSoundEvents.of("entity.frostiful.frostologer.cast_spell");
    public static final SoundEvent ENTITY_FROSTOLOGER_AMBIENT = FSoundEvents.of("entity.frostiful.frostologer.ambient");
    public static final SoundEvent ENTITY_FROSTOLOGER_CELEBRATE = FSoundEvents.of("entity.frostiful.frostologer.celebrate");
    public static final SoundEvent ENTITY_FROSTOLOGER_DEATH = FSoundEvents.of("entity.frostiful.frostologer.death");
    public static final SoundEvent ENTITY_FROSTOLOGER_HURT = FSoundEvents.of("entity.frostiful.frostologer.hurt");

    public static final SoundEvent ENTITY_CHILLAGER_AMBIENT = FSoundEvents.of("entity.frostiful.chillager.ambient");
    public static final SoundEvent ENTITY_CHILLAGER_CELEBRATE = FSoundEvents.of("entity.frostiful.chillager.celebrate");
    public static final SoundEvent ENTITY_CHILLAGER_DEATH = FSoundEvents.of("entity.frostiful.chillager.death");
    public static final SoundEvent ENTITY_CHILLAGER_HURT = FSoundEvents.of("entity.frostiful.chillager.hurt");

    public static void registerSoundEvents() {
        register(FIRE_LICHEN_DISCHARGE);
        register(CAMPFIRE_HISS);
        register(ITEM_FROST_WAND_CAST_SPELL);
        register(ITEM_FROST_WAND_PREPARE_CAST);
        register(ENTITY_FROST_SPELL_FREEZE);

        register(BLOCK_PACKED_SNOW_BREAK);
        register(BLOCK_PACKED_SNOW_FALL);
        register(BLOCK_PACKED_SNOW_HIT);
        register(BLOCK_PACKED_SNOW_PLACE);
        register(BLOCK_PACKED_SNOW_STEP);

        register(ENTITY_FROSTOLOGER_CAST_SPELL);
        register(ENTITY_FROSTOLOGER_AMBIENT);
        register(ENTITY_FROSTOLOGER_CELEBRATE);
        register(ENTITY_FROSTOLOGER_DEATH);
        register(ENTITY_FROSTOLOGER_HURT);

        register(ENTITY_CHILLAGER_AMBIENT);
        register(ENTITY_CHILLAGER_CELEBRATE);
        register(ENTITY_CHILLAGER_DEATH);
        register(ENTITY_CHILLAGER_HURT);
    }

    private static SoundEvent of(String name) {
        return new SoundEvent(new Identifier(Frostiful.MODID, name));
    }

    private static void register(SoundEvent event) {
        Registry.register(Registry.SOUND_EVENT, event.getId(), event);
    }
}
