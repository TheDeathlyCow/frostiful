package com.github.thedeathlycow.lostinthecold.attributes;

import com.github.thedeathlycow.lostinthecold.init.InitializeServerListener;
import com.github.thedeathlycow.lostinthecold.init.LostInTheCold;
import com.github.thedeathlycow.lostinthecold.config.FreezingValues;
import com.github.thedeathlycow.lostinthecold.init.LostInTheColdClient;
import net.fabricmc.loader.impl.lib.sat4j.pb.tools.INegator;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEntityAttributes {

    /**
     * Frost resistance is an attribute that determines the amount of time (in increments of 30 seconds) that a player
     * can last in the cold.
     */
    public static final EntityAttribute FROST_RESISTANCE = (new ClampedEntityAttribute("attribute.name.generic.frost_resistance", FreezingValues.BASE_FROST_RESISTANCE, 1.0D, 2048.0D)).setTracked(true);

    private ModEntityAttributes() {
        LostInTheCold.addOnInitializeListener(this::registerAttributes);
        LostInTheColdClient.addOnInitializeListener(this::registerAttributes);
    }

    private void registerAttributes() {
        register("generic.frost_resistance", FROST_RESISTANCE);
    }

    private static void register(String id, EntityAttribute attribute) {
        Registry.register(Registry.ATTRIBUTE, new Identifier(LostInTheCold.MODID, id), attribute);
    }

    private static final ModEntityAttributes INSTANCE = new ModEntityAttributes();

}
