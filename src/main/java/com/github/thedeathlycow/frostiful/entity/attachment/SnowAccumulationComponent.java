package com.github.thedeathlycow.frostiful.entity.attachment;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.registry.FrostifulEntityAttachments;
import com.github.thedeathlycow.thermoo.api.ThermooAttributes;
import com.google.common.base.Preconditions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.INBTSerializable;

public class SnowAccumulationComponent implements INBTSerializable<CompoundTag> {
    private static final AttributeModifier SOAKED_MODIFIER = new AttributeModifier(
            Frostiful.id("soaked_cold_vulnerability"),
            -1,
            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
    );

    private static final String KEY = "snow_accumulation";

    private final IAttachmentHolder provider;

    private int snowAccumulation = 0;

    private boolean appliedSoakedModifiers = false;

    public SnowAccumulationComponent(IAttachmentHolder provider) {
        this.provider = provider;
    }

    public static SnowAccumulationComponent get(LivingEntity provider) {
        return provider.getData(FrostifulEntityAttachments.SNOW_ACCUMULATION);
    }

    public void serverTick(LivingEntity providerEntity) {
        if (!FMLEnvironment.production) {
            Preconditions.checkArgument(this.provider == providerEntity, "Provided entity is not the attachment holder!");
        }

        if (this.isBeingSnowedOn(providerEntity)) {
            this.addSnowAccumulation();
        } else {
            this.meltSnowAccumulation(providerEntity);
        }

        if (Frostiful.getConfig().environmentConfig.applyEnvironmentPenaltyWhenWet()) {
            this.applySoakedEnvironmentFrostResistancePenalty(providerEntity);
        }
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        var tag = new CompoundTag();

        if (this.snowAccumulation > 0) {
            tag.putInt(KEY, this.snowAccumulation);
        }

        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        this.snowAccumulation = tag.contains(KEY, Tag.TAG_INT) ? tag.getInt(KEY) : 0;
    }

    public boolean isBeingSnowedOn(LivingEntity providerEntity) {
        Level world = providerEntity.level();
        BlockPos pos = providerEntity.blockPosition();
        return hasSnow(world, pos)
                || hasSnow(world, BlockPos.containing(pos.getX(), providerEntity.getBoundingBox().maxY, pos.getZ()));
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
            return biome.getPrecipitationAt(pos) == Biome.Precipitation.SNOW;
        }
    }

    public void meltSnowAccumulation(LivingEntity providerEntity) {
        if (this.snowAccumulation > 0) {
            this.snowAccumulation--;
            providerEntity.thermoo$addWetTicks(2);
        }
    }

    private void addSnowAccumulation() {
        if (this.snowAccumulation < Frostiful.getConfig().environmentConfig.getMaxSnowAccumulationTicks()) {
            this.snowAccumulation++;
        }
    }

    private void applySoakedEnvironmentFrostResistancePenalty(LivingEntity providerEntity) {
        // this probably doesnt belong in this component but oh well i dont feel like making another one
        boolean wet = providerEntity.thermoo$isWet();
        if (wet && !this.appliedSoakedModifiers && !providerEntity.thermoo$ignoresFrigidWater()) {
            var envFrostResistance = providerEntity.getAttribute(ThermooAttributes.ENVIRONMENT_FROST_RESISTANCE);

            if (envFrostResistance != null) {
                envFrostResistance.addTransientModifier(SOAKED_MODIFIER);
                this.appliedSoakedModifiers = true;
                Frostiful.LOGGER.debug("Applied soaked env frost resistance penalty");
            }
        } else if (!wet && this.appliedSoakedModifiers) {
            var envFrostResistance = providerEntity.getAttribute(ThermooAttributes.ENVIRONMENT_FROST_RESISTANCE);

            if (envFrostResistance != null) {
                envFrostResistance.removeModifier(SOAKED_MODIFIER);
                this.appliedSoakedModifiers = false;
                Frostiful.LOGGER.debug("Removed soaked env frost resistance penalty");
            }
        }
    }
}