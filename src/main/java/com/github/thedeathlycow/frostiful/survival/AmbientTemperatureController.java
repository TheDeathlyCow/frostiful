package com.github.thedeathlycow.frostiful.survival;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.tag.FBlockTags;
import com.github.thedeathlycow.thermoo.api.temperature.EnvironmentController;
import com.github.thedeathlycow.thermoo.api.temperature.EnvironmentControllerDecorator;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class AmbientTemperatureController extends EnvironmentControllerDecorator {

    public AmbientTemperatureController(EnvironmentController controller) {
        super(controller);
    }

    @Override
    public int getLocalTemperatureChange(World world, BlockPos pos) {
        if (world.getDimension().natural()) {
            Biome biome = world.getBiome(pos).value();
            float temperature = biome.getTemperature();
            return this.getTempChangeFromBiomeTemperature(
                    world,
                    temperature,
                    !biome.hasPrecipitation()
            );
        } else if (world.getDimension().ultrawarm()) {
            return Frostiful.getConfig().environmentConfig.getUltrawarmWarmRate();
        }
        return controller.getLocalTemperatureChange(world, pos);
    }

    @Override
    public int getFloorTemperature(LivingEntity entity, World world, BlockState state, BlockPos pos) {
        if (state.isIn(FBlockTags.HOT_FLOOR)) {
            if (EnchantmentHelper.hasFrostWalker(entity)) {
                return controller.getFloorTemperature(entity, world, state, pos);
            }

            FrostifulConfig config = Frostiful.getConfig();
            return config.freezingConfig.getHeatFromHotFloor();
        } else {
            return controller.getFloorTemperature(entity, world, state, pos);
        }
    }

    @Override
    public int getHeatAtLocation(World world, BlockPos pos) {
        FrostifulConfig config = Frostiful.getConfig();

        int lightLevel = world.getLightLevel(LightType.BLOCK, pos);
        int minLightLevel = config.environmentConfig.getMinLightForWarmth();

        int warmth = controller.getHeatAtLocation(world, pos);
        if (lightLevel >= minLightLevel) {
            warmth = config.environmentConfig.getWarmthPerLightLevel() * (lightLevel - minLightLevel);
        }

        return warmth;
    }

    @Override
    public int getHeatFromBlockState(BlockState state) {
        return state.getLuminance();
    }

    @Override
    public boolean isHeatSource(BlockState state) {
        int minLightForWarmth = Frostiful.getConfig().environmentConfig.getMinLightForWarmth();
        return getHeatFromBlockState(state) >= minLightForWarmth;
    }

    @Override
    public boolean isAreaHeated(World world, BlockPos pos) {
        int minLightForWarmth = Frostiful.getConfig().environmentConfig.getMinLightForWarmth();
        return getHeatAtLocation(world, pos) > minLightForWarmth;
    }

    private int getTempChangeFromBiomeTemperature(World world, float temperature, boolean isDryBiome) {
        FrostifulConfig config = Frostiful.getConfig();
        double mul = config.environmentConfig.getBiomeTemperatureMultiplier();
        double cutoff = config.environmentConfig.getPassiveFreezingCutoffTemp();

        double tempShift = 0.0;
        if (world.isNight() && config.environmentConfig.doDryBiomeNightFreezing()) {
            if (isDryBiome) {
                temperature = Math.min(temperature, config.environmentConfig.getDryBiomeNightTemperature());
            } else {
                tempShift = config.environmentConfig.getNightTimeTemperatureDecrease();
            }
        }

        return MathHelper.floor(mul * (temperature - cutoff - tempShift) - 1);
    }

}
