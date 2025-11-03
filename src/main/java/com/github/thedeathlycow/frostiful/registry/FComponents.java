package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.entity.component.BrushableComponent;
import com.github.thedeathlycow.frostiful.entity.component.FrostWandRootComponent;
import com.github.thedeathlycow.frostiful.entity.component.LivingEntityComponents;
import com.github.thedeathlycow.frostiful.entity.component.SnowAccumulationComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;

public class FComponents implements EntityComponentInitializer {

    public static final ComponentKey<LivingEntityComponents> ENTITY_COMPONENTS = ComponentRegistry.getOrCreate(
            Frostiful.location("living_entity"),
            LivingEntityComponents.class
    );

    public static final ComponentKey<FrostWandRootComponent> FROST_WAND_ROOT_COMPONENT = ComponentRegistry.getOrCreate(
            Frostiful.location("frost_wand_root"),
            FrostWandRootComponent.class
    );

    public static final ComponentKey<BrushableComponent> BRUSHABLE_COMPONENT = ComponentRegistry.getOrCreate(
            Frostiful.location("brushable"),
            BrushableComponent.class
    );

    public static final ComponentKey<SnowAccumulationComponent> SNOW_ACCUMULATION = ComponentRegistry.getOrCreate(
            Frostiful.location("snow_accumulation"),
            SnowAccumulationComponent.class
    );

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(
                LivingEntity.class,
                ENTITY_COMPONENTS,
                LivingEntityComponents::new
        );
        registry.registerFor(
                LivingEntity.class,
                FROST_WAND_ROOT_COMPONENT,
                FrostWandRootComponent::new
        );
        registry.registerFor(
                Animal.class,
                BRUSHABLE_COMPONENT,
                BrushableComponent::new
        );
        registry.registerFor(
                LivingEntity.class,
                SNOW_ACCUMULATION,
                SnowAccumulationComponent::new
        );
    }
}
