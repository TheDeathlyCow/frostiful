package com.github.thedeathlycow.frostiful.tag.biome;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class FrostifulBiomeTemperatureTags {

    public static final TagKey<Biome> IS_CHILLY = register("is_chilly");
    public static final TagKey<Biome> IS_COLD = register("is_cold");
    public static final TagKey<Biome> IS_FREEZING = register("is_freezing");

    private static TagKey<Biome> register(String id) {
        return TagKey.of(Registry.BIOME_KEY, new Identifier(Frostiful.MODID, id));
    }

}
