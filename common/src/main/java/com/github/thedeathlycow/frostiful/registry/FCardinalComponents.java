package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.entity.attachment.LivingEntityComponents;
import net.minecraft.world.entity.LivingEntity;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;

public class FCardinalComponents implements EntityComponentInitializer {

    public static final ComponentKey<LivingEntityComponents> ENTITY_COMPONENTS = ComponentRegistry.getOrCreate(
            Frostiful.id("living_entity"),
            LivingEntityComponents.class
    );

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(
                LivingEntity.class,
                ENTITY_COMPONENTS,
                LivingEntityComponents::new
        );
    }
}
