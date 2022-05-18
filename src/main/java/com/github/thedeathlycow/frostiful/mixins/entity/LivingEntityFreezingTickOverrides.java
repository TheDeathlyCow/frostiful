package com.github.thedeathlycow.frostiful.mixins.entity;

import com.github.thedeathlycow.frostiful.attributes.FrostifulEntityAttributes;
import com.github.thedeathlycow.frostiful.config.group.AttributeConfigGroup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LivingEntity.class)
public abstract class LivingEntityFreezingTickOverrides extends Entity {

    @Shadow
    public abstract AttributeContainer getAttributes();

    protected LivingEntityFreezingTickOverrides(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public int getMinFreezeDamageTicks() {
        // add more time to freeze
        final AttributeContainer attributes = this.getAttributes();
        if (attributes.hasAttribute(FrostifulEntityAttributes.MAX_FROST)) {
            final double maxFrost = attributes.getValue(FrostifulEntityAttributes.MAX_FROST);
            return getTicksFromMaxFrost(maxFrost);
        } else {
            return super.getMinFreezeDamageTicks();
        }
    }


    private static int getTicksFromMaxFrost(final double maxFrost) {
        return (int) (AttributeConfigGroup.MAX_FROST_MULTIPLIER.getValue() * maxFrost);
    }
}
