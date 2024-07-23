package com.github.thedeathlycow.frostiful.survival;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.registry.tag.FBlockTags;
import com.github.thedeathlycow.frostiful.registry.tag.FEnchantmentTags;
import com.github.thedeathlycow.thermoo.api.season.ThermooSeason;
import com.github.thedeathlycow.thermoo.api.temperature.EnvironmentController;
import com.github.thedeathlycow.thermoo.api.temperature.EnvironmentControllerDecorator;
import com.github.thedeathlycow.thermoo.api.temperature.TemperatureAware;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class AmbientTemperatureController extends EnvironmentControllerDecorator {

    private final boolean isScorchfulLoaded;

    public AmbientTemperatureController(boolean isScorchfulLoaded, EnvironmentController controller) {
        super(controller);
        this.isScorchfulLoaded = isScorchfulLoaded;
    }

    @Override
    public int getLocalTemperatureChange(World world, BlockPos pos) {
        if (world.getDimension().natural()) {
            return this.getNaturalWorldTemperatureChange(world, pos);
        } else if (!this.isScorchfulLoaded && world.getDimension().ultrawarm()) {
            return this.getUltrawarmTemperatureChange();
        }
        return controller.getLocalTemperatureChange(world, pos);
    }

    @Override
    public int getFloorTemperature(LivingEntity entity, World world, BlockState state, BlockPos pos) {
        if (state.isIn(FBlockTags.HOT_FLOOR)) {
            ItemStack footStack = entity.getEquippedStack(EquipmentSlot.FEET);
            if (EnchantmentHelper.hasAnyEnchantmentsIn(footStack, FEnchantmentTags.IS_FROSTY)) {
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
    public int applyAwareHeat(TemperatureAware temperatureAware, int locationHeat) {
        return temperatureAware.thermoo$isCold() ? locationHeat : 0;
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

        ThermooSeason season = ThermooSeason.getCurrentSeason(world).orElse(ThermooSeason.SPRING);
        BiomeCategory category = BiomeCategory.fromBiome(biome, season);
        int temp = category.getTemperatureChange(world, pos);
        if (temp < 0) {
            return temp;
        }

        return controller.getLocalTemperatureChange(world, pos);
    }

    private int getUltrawarmTemperatureChange() {
        return Frostiful.getConfig().environmentConfig.getUltrawarmWarmRate();
    }

}
