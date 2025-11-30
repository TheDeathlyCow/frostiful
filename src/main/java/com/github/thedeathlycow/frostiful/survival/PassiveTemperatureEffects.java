package com.github.thedeathlycow.frostiful.survival;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.block.SunLichenBlock;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.entity.component.SnowAccumulationComponent;
import com.github.thedeathlycow.frostiful.registry.tag.FBlockTags;
import com.github.thedeathlycow.frostiful.registry.tag.FEnchantmentTags;
import com.github.thedeathlycow.thermoo.api.temperature.event.EnvironmentTickContext;
import com.github.thedeathlycow.thermoo.api.temperature.event.LivingEntityTemperatureTickEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;

public final class PassiveTemperatureEffects {
    public static void initialize() {
        LivingEntityTemperatureTickEvents.GET_PASSIVE_TEMPERATURE_CHANGE.register(PassiveTemperatureEffects::getPassiveChange);
    }

    private static int getPassiveChange(EnvironmentTickContext<? extends LivingEntity> context) {
        LivingEntity entity = context.affected();

        // don't touch scorchful's effects
        if (entity.isSpectator() || entity.thermoo$getTemperature() > 0) {
            return 0;
        }

        int total = 0;

        FrostifulConfig config = Frostiful.getConfig();
        total += getHotFloorTemperatureChange(context, config);
        total += getAndUpdateBlockLightTemperatureChange(context);

        return total;
    }

    private static int getHotFloorTemperatureChange(EnvironmentTickContext<? extends LivingEntity> context, FrostifulConfig config) {
        LivingEntity entity = context.affected();
        BlockState steppingState = entity.getBlockStateOn();

        if (steppingState.is(FBlockTags.HOT_FLOOR)) {
            ItemStack footStack = entity.getItemBySlot(EquipmentSlot.FEET);

            if (!EnchantmentHelper.hasTag(footStack, FEnchantmentTags.IS_FROSTY)) {
                // TODO: fix fire particles
                SunLichenBlock.createFireParticles(context.world(), entity.blockPosition());
                return config.freezingConfig.getHeatFromHotFloor();
            }
        }

        return 0;
    }

    private static int getAndUpdateBlockLightTemperatureChange(EnvironmentTickContext<? extends LivingEntity> context) {
        int warmthFromLight = getBlockLightTemperatureChange(context.world(), context.pos());
        if (warmthFromLight > 0) {
            SnowAccumulationComponent.get(context.affected()).meltSnowAccumulation();
        }

        return warmthFromLight;
    }

    /**
     * Gets the temperature change from block light in the surrounding area. Exposed as public for the location_warmth
     * loot condition.
     */
    public static int getBlockLightTemperatureChange(Level world, BlockPos pos) {
        FrostifulConfig config = Frostiful.getConfig();

        int lightLevel = world.getBrightness(LightLayer.BLOCK, pos);
        int minLightLevel = config.environmentConfig.getMinLightForWarmth();

        int warmth = 0;
        if (lightLevel >= minLightLevel) {
            warmth = config.environmentConfig.getWarmthPerLightLevel() * (lightLevel - minLightLevel);
        }

        return warmth;
    }

    private PassiveTemperatureEffects() {

    }
}