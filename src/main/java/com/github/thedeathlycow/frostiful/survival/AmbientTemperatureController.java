package com.github.thedeathlycow.frostiful.survival;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.compat.AdaptedSeason;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.tag.FBlockTags;
import com.github.thedeathlycow.thermoo.api.temperature.EnvironmentController;
import com.github.thedeathlycow.thermoo.api.temperature.EnvironmentControllerDecorator;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

public class AmbientTemperatureController extends EnvironmentControllerDecorator {

    public AmbientTemperatureController(EnvironmentController controller) {
        super(controller);
    }

    @Override
    public int getLocalTemperatureChange(World world, BlockPos pos) {
        if (world.getDimension().natural()) {
            return getNaturalWorldTemperatureChange(world, pos);
        } else if (world.getDimension().ultrawarm()) {
            return getUltrawarmTemperatureChange();
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
            warmth += config.environmentConfig.getWarmthPerLightLevel() * (lightLevel - minLightLevel);
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
        return getHeatAtLocation(world, pos) > 0;
    }

    private int getNaturalWorldTemperatureChange(World world, BlockPos pos) {
        RegistryEntry<Biome> biome = world.getBiome(pos);

        @Nullable AdaptedSeason season = AdaptedSeason.getCurrentSeason(world);
        BiomeCategory category = BiomeCategory.fromBiome(biome, season);
        int temp = category.getTemperatureChange(world, pos, season);
        if (temp < 0) {
            return temp;
        }

        return controller.getLocalTemperatureChange(world, pos);
    }

    private int getUltrawarmTemperatureChange() {
        return Frostiful.getConfig().environmentConfig.getUltrawarmWarmRate();
    }

}
