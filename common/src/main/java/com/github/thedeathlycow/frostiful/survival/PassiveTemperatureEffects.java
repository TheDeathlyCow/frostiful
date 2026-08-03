package com.github.thedeathlycow.frostiful.survival;

import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.config.section.TemperatureSourceSettings;
import com.github.thedeathlycow.frostiful.registry.tag.FBlockTags;
import com.github.thedeathlycow.frostiful.registry.tag.FEnchantmentTags;
import com.github.thedeathlycow.frostiful.survival.system.SnowAccumulationSystem;
import com.github.thedeathlycow.thermoo.api.core.v2.event.EnvironmentTickContext;
import com.github.thedeathlycow.thermoo.api.core.v2.event.LivingEntityTemperatureTickEvents;
import com.github.thedeathlycow.thermoo.api.core.v2.source.TemperatureSources;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;

public final class PassiveTemperatureEffects {
    public static void initialize() {
        LivingEntityTemperatureTickEvents.getTemperatureChange(TemperatureSources.PASSIVE).register(PassiveTemperatureEffects::getPassiveChange);
    }

    private static int getPassiveChange(EnvironmentTickContext<? extends LivingEntity> context) {
        LivingEntity entity = context.affected();

        // don't touch scorchful's effects
        if (entity.isSpectator() || entity.thermoo$getTemperature() > 0) {
            return 0;
        }

        int total = 0;

        total += getHotFloorTemperatureChange(context, FrostifulConfigYACL.temperatureSourceSettings());
        total += getAndUpdateBlockLightTemperatureChange(context);

        return total;
    }

    private static int getHotFloorTemperatureChange(EnvironmentTickContext<? extends LivingEntity> context, TemperatureSourceSettings config) {
        LivingEntity entity = context.affected();
        BlockState steppingState = entity.getBlockStateOn();
        ItemStack footStack = entity.getItemBySlot(EquipmentSlot.FEET);

        boolean applyHeat = steppingState.is(FBlockTags.HOT_FLOOR)
                && !EnchantmentHelper.hasTag(footStack, FEnchantmentTags.IS_FROSTY);

        if (applyHeat) {
            if (entity.getRandom().nextInt(10) == 0) {
                context.level().sendParticles(
                        ParticleTypes.FLAME,
                        entity.getX(),
                        entity.getY() + 0.3,
                        entity.getZ(),
                        2,
                        0.2, 0.7, 0.2,
                        1e-2
                );
                return config.hotFloorTemperatureChange();
            } else {
                return config.hotFloorTemperatureChange() / 10;
            }
        }

        return 0;
    }

    private static int getAndUpdateBlockLightTemperatureChange(EnvironmentTickContext<? extends LivingEntity> context) {
        int warmthFromLight = getBlockLightTemperatureChange(context.level(), context.pos());
        if (warmthFromLight > 0) {
            SnowAccumulationSystem.tryMeltSnowAccumulation(context.affected());
        }

        return warmthFromLight;
    }

    /**
     * Gets the temperature change from block light in the surrounding area. Exposed as public for the location_warmth
     * loot condition.
     */
    public static int getBlockLightTemperatureChange(Level world, BlockPos pos) {
        int lightLevel = world.getBrightness(LightLayer.BLOCK, pos);
        int minLightLevel = FrostifulConfigYACL.environmentSettings().minLightForWarmth();

        int warmth = 0;
        if (lightLevel >= minLightLevel) {
            warmth = FrostifulConfigYACL.temperatureSourceSettings().lightTemperatureChangeForLevel(lightLevel - minLightLevel);
        }

        return warmth;
    }

    private PassiveTemperatureEffects() {

    }
}