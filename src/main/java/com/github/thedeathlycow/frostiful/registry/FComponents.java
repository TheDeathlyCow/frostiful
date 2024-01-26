package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.entity.component.LivingEntityComponents;
import com.github.thedeathlycow.frostiful.entity.component.BrushableComponents;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.PolarBearEntity;

public class FComponents implements EntityComponentInitializer {

    public static final ComponentKey<LivingEntityComponents> ENTITY_COMPONENTS = ComponentRegistry.getOrCreate(
            Frostiful.id("living_entity"),
            LivingEntityComponents.class
    );

    public static final ComponentKey<BrushableComponents> BRUSHABLE_COMPONENT = ComponentRegistry.getOrCreate(
            Frostiful.id("brushable"),
            BrushableComponents.class
    );

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(
                LivingEntity.class,
                ENTITY_COMPONENTS,
                LivingEntityComponents::new
        );
        registry.registerFor(
                PolarBearEntity.class,
                BRUSHABLE_COMPONENT,
                BrushableComponents::new
        );
    }
}
