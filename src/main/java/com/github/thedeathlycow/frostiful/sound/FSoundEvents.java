package com.github.thedeathlycow.frostiful.sound;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;

public class FSoundEvents {

    public static final SoundEvent FIRE_LICHEN_DISCHARGE = FSoundEvents.of("block.frostiful.sun_lichen.discharge");
    public static final SoundEvent CAMPFIRE_HISS = FSoundEvents.of("block.frostiful.campfire.hiss");
    public static final SoundEvent ITEM_FROST_WAND_CAST_SPELL = FSoundEvents.of("item.frostiful.frost_wand.cast_spell");
    public static final SoundEvent ITEM_FROST_WAND_PREPARE_CAST = FSoundEvents.of("item.frostiful.frost_wand.prepare_cast");
    public static final SoundEvent ENTITY_FROST_SPELL_FREEZE = FSoundEvents.of("entity.frostiful.frost_spell.freeze");
    public static final SoundEvent ENTITY_THROWN_ICICLE_HIT = FSoundEvents.of("entity.frostiful.thrown_icicle.hit");
    public static final SoundEvent ENTITY_THROWN_ICICLE_THROW = FSoundEvents.of("entity.frostiful.thrown_icicle.throw");

    public static final SoundEvent BLOCK_PACKED_SNOW_BREAK = FSoundEvents.of("block.frostiful.packed_snow.break");
    public static final SoundEvent BLOCK_PACKED_SNOW_FALL = FSoundEvents.of("block.frostiful.packed_snow.fall");
    public static final SoundEvent BLOCK_PACKED_SNOW_HIT = FSoundEvents.of("block.frostiful.packed_snow.hit");
    public static final SoundEvent BLOCK_PACKED_SNOW_PLACE = FSoundEvents.of("block.frostiful.packed_snow.place");
    public static final SoundEvent BLOCK_PACKED_SNOW_STEP = FSoundEvents.of("block.frostiful.packed_snow.step");

    public static final SoundEvent ENTITY_FROSTOLOGER_CAST_SPELL = FSoundEvents.of("entity.frostiful.frostologer.cast_spell");
    public static final SoundEvent ENTITY_FROSTOLOGER_PREPARE_CAST_BLIZZARD = FSoundEvents.of("entity.frostiful.frostologer.prepare_cast.blizzard");
    public static final SoundEvent ENTITY_FROSTOLOGER_AMBIENT = FSoundEvents.of("entity.frostiful.frostologer.ambient");
    public static final SoundEvent ENTITY_FROSTOLOGER_CELEBRATE = FSoundEvents.of("entity.frostiful.frostologer.celebrate");
    public static final SoundEvent ENTITY_FROSTOLOGER_DEATH = FSoundEvents.of("entity.frostiful.frostologer.death");
    public static final SoundEvent ENTITY_FROSTOLOGER_HURT = FSoundEvents.of("entity.frostiful.frostologer.hurt");

    public static final SoundEvent ENTITY_CHILLAGER_AMBIENT = FSoundEvents.of("entity.frostiful.chillager.ambient");
    public static final SoundEvent ENTITY_CHILLAGER_CELEBRATE = FSoundEvents.of("entity.frostiful.chillager.celebrate");
    public static final SoundEvent ENTITY_CHILLAGER_DEATH = FSoundEvents.of("entity.frostiful.chillager.death");
    public static final SoundEvent ENTITY_CHILLAGER_HURT = FSoundEvents.of("entity.frostiful.chillager.hurt");

    public static final SoundEvent ENTITY_BITER_AMBIENT = FSoundEvents.of("entity.frostiful.biter.ambient");
    public static final SoundEvent ENTITY_BITER_DEATH = FSoundEvents.of("entity.frostiful.biter.death");
    public static final SoundEvent ENTITY_BITER_HURT = FSoundEvents.of("entity.frostiful.biter.hurt");
    public static final SoundEvent ENTITY_BITER_BITE = FSoundEvents.of("entity.frostiful.biter.bite");
    public static final SoundEvent ENTITY_BITER_BURP = FSoundEvents.of("entity.frostiful.biter.burp");

    public static final SoundEvent ENTITY_WIND_BLOW = FSoundEvents.of("entity.frostiful.wind.generic.blow");
    public static final SoundEvent ENTITY_WIND_HOWL = FSoundEvents.of("entity.frostiful.wind.generic.howl");
    public static final SoundEvent ENTITY_WIND_WOOSH = FSoundEvents.of("entity.frostiful.wind.generic.woosh");
    public static final SoundEvent ENTITY_FREEZING_WIND_BLOWOUT = FSoundEvents.of("entity.frostiful.freezing_wind.blowout");



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
        register(ENTITY_FROSTOLOGER_PREPARE_CAST_BLIZZARD);
        register(ENTITY_FROSTOLOGER_AMBIENT);
        register(ENTITY_FROSTOLOGER_CELEBRATE);
        register(ENTITY_FROSTOLOGER_DEATH);
        register(ENTITY_FROSTOLOGER_HURT);

        register(ENTITY_CHILLAGER_AMBIENT);
        register(ENTITY_CHILLAGER_CELEBRATE);
        register(ENTITY_CHILLAGER_DEATH);
        register(ENTITY_CHILLAGER_HURT);

        register(ENTITY_THROWN_ICICLE_HIT);
        register(ENTITY_THROWN_ICICLE_THROW);

        register(ENTITY_BITER_AMBIENT);
        register(ENTITY_BITER_DEATH);
        register(ENTITY_BITER_HURT);
        register(ENTITY_BITER_BITE);
        register(ENTITY_BITER_BURP);

        register(ENTITY_WIND_BLOW);
        register(ENTITY_WIND_HOWL);
        register(ENTITY_WIND_WOOSH);
        register(ENTITY_FREEZING_WIND_BLOWOUT);
    }

    private static SoundEvent of(String name) {
        return SoundEvent.of(Frostiful.id(name));
    }

    private static void register(SoundEvent event) {
        Registry.register(Registries.SOUND_EVENT, event.getId(), event);
    }
}
