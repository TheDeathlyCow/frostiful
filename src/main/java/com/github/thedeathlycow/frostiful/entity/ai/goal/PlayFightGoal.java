package com.github.thedeathlycow.frostiful.entity.ai.goal;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PathAwareEntity;

import java.util.UUID;

public class PlayFightGoal extends MeleeAttackGoal {

    private static final UUID NO_DAMAGE_ID = UUID.fromString("ff0c233a-900a-4846-ac48-7d6a1330d6a8");
    private static final Multimap<EntityAttribute, EntityAttributeModifier> NO_DAMAGE_MODIFIERS =
            ImmutableMultimap.of(
                    EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(NO_DAMAGE_ID, "play fight no damage", -10000.0, EntityAttributeModifier.Operation.MULTIPLY_TOTAL)
            );

    public PlayFightGoal(PathAwareEntity mob, double speed, boolean pauseWhenMobIdle) {
        super(mob, speed, pauseWhenMobIdle);
    }

    @Override
    public boolean canStart() {
        LivingEntity target = this.mob.getTarget();
        return (target != null && target.getType() == this.mob.getType()) && super.canStart();
    }

    @Override
    public void start() {

        AttributeContainer attributes = this.mob.getAttributes();
        if (attributes.hasAttribute(EntityAttributes.GENERIC_ATTACK_DAMAGE)) {
            attributes.addTemporaryModifiers(NO_DAMAGE_MODIFIERS);
        }

        super.start();
    }

    @Override
    public void stop() {
        super.stop();
        AttributeContainer attributes = this.mob.getAttributes();
        attributes.removeModifiers(NO_DAMAGE_MODIFIERS);
    }

    @Override
    protected void attack(LivingEntity target, double squaredDistance) {
        super.attack(target, squaredDistance);
        this.stop();
    }
}


