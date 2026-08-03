package com.github.thedeathlycow.frostiful.survival.system;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.registry.FDataAttachments;
import com.github.thedeathlycow.thermoo.api.entity.v1.ThermooAttributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public final class SoakedFreezingSystem {
    private static final AttributeModifier SOAKED_MODIFIER = new AttributeModifier(
            Frostiful.id("soaked_cold_vulnerability"),
            -1,
            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
    );

    public static void serverTick(LivingEntity provider) {
        if (FrostifulConfigYACL.soakingSettings().removeEnvironmentFrostResistanceWhenWet()) {
            applySoakedEnvironmentFrostResistancePenalty(provider);
        }
    }

    private static void applySoakedEnvironmentFrostResistancePenalty(LivingEntity provider) {
        final boolean wet = provider.thermoo$isWet();
        final boolean appliedSoakedModifiers = provider.getAttachedOrCreate(FDataAttachments.APPLIED_SOAKED_MODIFIERS);

        if (wet && !appliedSoakedModifiers && !provider.thermoo$ignoresFrigidWater()) {
            var envFrostResistance = provider.getAttribute(ThermooAttributes.ENVIRONMENT_FROST_RESISTANCE);

            if (envFrostResistance != null) {
                envFrostResistance.addOrUpdateTransientModifier(SOAKED_MODIFIER);
                provider.setAttached(FDataAttachments.APPLIED_SOAKED_MODIFIERS, true);

                if (Frostiful.isDevelopmentEnvironment()) {
                    Frostiful.LOGGER.info("Applied soaked env frost resistance penalty");
                }
            }
        } else if (!wet && appliedSoakedModifiers) {
            var envFrostResistance = provider.getAttribute(ThermooAttributes.ENVIRONMENT_FROST_RESISTANCE);

            if (envFrostResistance != null) {
                envFrostResistance.removeModifier(SOAKED_MODIFIER);
                provider.setAttached(FDataAttachments.APPLIED_SOAKED_MODIFIERS, false);

                if (Frostiful.isDevelopmentEnvironment()) {
                    Frostiful.LOGGER.debug("Removed soaked env frost resistance penalty");
                }
            }
        }
    }

    private SoakedFreezingSystem() {

    }
}