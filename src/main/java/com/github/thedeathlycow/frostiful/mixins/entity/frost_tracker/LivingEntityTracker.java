package com.github.thedeathlycow.frostiful.mixins.entity.frost_tracker;

import com.github.thedeathlycow.frostiful.attributes.FrostifulEntityAttributes;
import com.github.thedeathlycow.frostiful.config.group.AttributeConfigGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LivingEntity.class)
public abstract class LivingEntityTracker extends EntityTracker {

    @Shadow public abstract AttributeContainer getAttributes();

    @Override
    @Unique
    public int frostiful$getMaxFrost() {
        // add more time to freeze
        final AttributeContainer attributes = this.getAttributes();
        if (attributes.hasAttribute(FrostifulEntityAttributes.MAX_FROST)) {
            final double maxFrost = attributes.getValue(FrostifulEntityAttributes.MAX_FROST);
            return getTicksFromMaxFrost(maxFrost);
        } else {
            return super.frostiful$getMaxFrost();
        }
    }

    private static int getTicksFromMaxFrost(final double maxFrost) {
        return (int) (AttributeConfigGroup.MAX_FROST_MULTIPLIER.getValue() * maxFrost);
    }
}
