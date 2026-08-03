package com.github.thedeathlycow.frostiful.mixins.entity;

import com.github.thedeathlycow.frostiful.survival.system.BrushSystem;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Animal.class)
public abstract class AnimalEntityMixin extends Mob {
    protected AnimalEntityMixin(EntityType<? extends Mob> entityType, Level world) {
        super(entityType, world);
    }

    /**
     * Inject into MobEntity as some mobs (such as Polar Bears) lack a definition of {@link Mob#mobInteract(Player, InteractionHand)}
     */
    @ModifyReturnValue(
            method = "mobInteract",
            at = @At("TAIL")
    )
    private InteractionResult postInteract(InteractionResult original, Player player, InteractionHand hand) {
        Animal animal = (Animal) (Object) this;
        return BrushSystem.interactWithMob(animal, player, hand, original);
    }
}