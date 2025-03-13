package com.github.thedeathlycow.frostiful.entity.component;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.registry.FComponents;
import com.github.thedeathlycow.thermoo.api.ThermooAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.ladysnake.cca.api.v3.component.Component;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class SnowAccumulationComponent implements Component, ServerTickingComponent {
    private static final EntityAttributeModifier SOAKED_MODIFIER = new EntityAttributeModifier(
            Frostiful.id("soaked_cold_vulnerability"),
            -1,
            EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
    );

    private static final String KEY = "snow_accumulation";

    private final LivingEntity provider;

    private int snowAccumulation = 0;

    private boolean appliedSoakedModifiers = false;

    public SnowAccumulationComponent(LivingEntity provider) {
        this.provider = provider;
    }

    public static SnowAccumulationComponent get(LivingEntity provider) {
        return FComponents.SNOW_ACCUMULATION.get(provider);
    }

    @Override
    public void serverTick() {
        if (this.isBeingSnowedOn()) {
            this.addSnowAccumulation();
        } else {
            this.meltSnowAccumulation();
        }

        if (Frostiful.getConfig().environmentConfig.applyEnvironmentPenaltyWhenWet()) {
            this.applySoakedEnvironmentFrostResistancePenalty();
        }
    }

    @Override
    public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        this.snowAccumulation = tag.contains(KEY, NbtElement.INT_TYPE) ? tag.getInt(KEY) : 0;
    }

    @Override
    public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        if (this.snowAccumulation > 0) {
            tag.putInt(KEY, this.snowAccumulation);
        }
    }

    public boolean isBeingSnowedOn() {
        World world = this.provider.getWorld();
        BlockPos pos = this.provider.getBlockPos();
        return hasSnow(world, pos)
                || hasSnow(world, BlockPos.ofFloored(pos.getX(), this.provider.getBoundingBox().maxY, pos.getZ()));
    }

    public static boolean hasSnow(World world, BlockPos pos) {
        if (!world.isRaining()) {
            return false;
        } else if (!world.isSkyVisible(pos)) {
            return false;
        } else if (world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, pos).getY() > pos.getY()) {
            return false;
        } else {
            Biome biome = world.getBiome(pos).value();
            return biome.getPrecipitation(pos) == Biome.Precipitation.SNOW;
        }
    }

    public void meltSnowAccumulation() {
        if (this.snowAccumulation > 0) {
            this.snowAccumulation--;
            this.provider.thermoo$addWetTicks(2);
        }
    }

    private void addSnowAccumulation() {
        if (this.snowAccumulation < Frostiful.getConfig().environmentConfig.getMaxSnowAccumulationTicks()) {
            this.snowAccumulation++;
        }
    }

    private void applySoakedEnvironmentFrostResistancePenalty() {
        // this probably doesnt belong in this component but oh well i dont feel like making another one
        boolean wet = provider.thermoo$isWet();
        if (wet && !this.appliedSoakedModifiers && !provider.thermoo$ignoresFrigidWater()) {
            var envFrostResistance = provider.getAttributeInstance(ThermooAttributes.ENVIRONMENT_FROST_RESISTANCE);

            if (envFrostResistance != null) {
                envFrostResistance.addTemporaryModifier(SOAKED_MODIFIER);
                this.appliedSoakedModifiers = true;
                Frostiful.LOGGER.debug("Applied soaked env frost resistance penalty");
            }
        } else if (!wet && this.appliedSoakedModifiers) {
            var envFrostResistance = provider.getAttributeInstance(ThermooAttributes.ENVIRONMENT_FROST_RESISTANCE);

            if (envFrostResistance != null) {
                envFrostResistance.removeModifier(SOAKED_MODIFIER);
                this.appliedSoakedModifiers = false;
                Frostiful.LOGGER.debug("Removed soaked env frost resistance penalty");
            }
        }
    }
}