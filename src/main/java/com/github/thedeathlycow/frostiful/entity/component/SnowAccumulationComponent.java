package com.github.thedeathlycow.frostiful.entity.component;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.config.section.SoakingSettings;
import com.github.thedeathlycow.frostiful.registry.FCardinalComponents;
import com.github.thedeathlycow.thermoo.api.entity.v1.ThermooAttributes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;
import org.ladysnake.cca.api.v8.component.CardinalComponent;

public class SnowAccumulationComponent implements CardinalComponent, ServerTickingComponent {
    private static final AttributeModifier SOAKED_MODIFIER = new AttributeModifier(
            Frostiful.id("soaked_cold_vulnerability"),
            -1,
            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
    );

    private static final String KEY = "snow_accumulation";

    private final LivingEntity provider;

    private int snowAccumulation = 0;

    private boolean appliedSoakedModifiers = false;

    public SnowAccumulationComponent(LivingEntity provider) {
        this.provider = provider;
    }

    public static SnowAccumulationComponent get(LivingEntity provider) {
        return FCardinalComponents.SNOW_ACCUMULATION.get(provider);
    }

    @Override
    public void serverTick() {
        SoakingSettings settings = FrostifulConfigYACL.soakingSettings();

        if (this.isBeingSnowedOn()) {
            this.addSnowAccumulation(settings);
        } else {
            this.meltSnowAccumulation();
        }

        if (settings.removeEnvironmentFrostResistanceWhenWet()) {
            this.applySoakedEnvironmentFrostResistancePenalty();
        }
    }

    @Override
    public void readData(ValueInput readView) {
        this.snowAccumulation = readView.getIntOr(KEY, 0);
    }

    @Override
    public void writeData(ValueOutput writeView) {
        if (this.snowAccumulation > 0) {
            writeView.putInt(KEY, this.snowAccumulation);
        }
    }

    public boolean isBeingSnowedOn() {
        Level world = this.provider.level();
        BlockPos pos = this.provider.blockPosition();
        return hasSnow(world, pos)
                || hasSnow(world, BlockPos.containing(pos.getX(), this.provider.getBoundingBox().maxY, pos.getZ()));
    }

    public static boolean hasSnow(Level world, BlockPos pos) {
        if (!world.isRaining()) {
            return false;
        } else if (!world.canSeeSky(pos)) {
            return false;
        } else if (world.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, pos).getY() > pos.getY()) {
            return false;
        } else {
            Biome biome = world.getBiome(pos).value();
            return biome.getPrecipitationAt(pos, world.getSeaLevel()) == Biome.Precipitation.SNOW;
        }
    }

    public void meltSnowAccumulation() {
        if (this.snowAccumulation > 0) {
            this.snowAccumulation--;
            this.provider.thermoo$addWetTicks(2);
        }
    }

    private void addSnowAccumulation(SoakingSettings settings) {
        if (this.snowAccumulation < settings.maxSnowAccumulationTicks()) {
            this.snowAccumulation++;
        }
    }

    private void applySoakedEnvironmentFrostResistancePenalty() {
        // FIXME this probably doesnt belong in this component but oh well i dont feel like making another one
        boolean wet = provider.thermoo$isWet();
        if (wet && !this.appliedSoakedModifiers && !provider.thermoo$ignoresFrigidWater()) {
            var envFrostResistance = provider.getAttribute(ThermooAttributes.ENVIRONMENT_FROST_RESISTANCE);

            if (envFrostResistance != null) {
                envFrostResistance.addTransientModifier(SOAKED_MODIFIER);
                this.appliedSoakedModifiers = true;

                if (Frostiful.isDevelopmentEnvironment()) {
                    Frostiful.LOGGER.info("Applied soaked env frost resistance penalty");
                }
            }
        } else if (!wet && this.appliedSoakedModifiers) {
            var envFrostResistance = provider.getAttribute(ThermooAttributes.ENVIRONMENT_FROST_RESISTANCE);

            if (envFrostResistance != null) {
                envFrostResistance.removeModifier(SOAKED_MODIFIER);
                this.appliedSoakedModifiers = false;

                if (Frostiful.isDevelopmentEnvironment()) {
                    Frostiful.LOGGER.debug("Removed soaked env frost resistance penalty");
                }
            }
        }
    }
}