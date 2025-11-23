package com.github.thedeathlycow.frostiful.block;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.registry.FBlocks;
import com.github.thedeathlycow.frostiful.registry.FCriteria;
import com.github.thedeathlycow.frostiful.registry.FSoundEvents;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import com.github.thedeathlycow.thermoo.api.temperature.HeatingModes;
import net.fabricmc.fabric.api.registry.LandPathNodeTypesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.GlowLichenBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("deprecation")
public class SunLichenBlock extends GlowLichenBlock implements Heatable {

    public static final int COLD_LEVEL = 0;
    public static final int COOL_LEVEL = 1;
    public static final int WARM_LEVEL = 2;
    public static final int HOT_LEVEL = 3;


    private static final float BASE_GROW_CHANCE = 0.017f;
    private static final float RANDOM_DISCHARGE_CHANCE = 0.13f;

    private final int heatLevel;

    public SunLichenBlock(int heatLevel, Properties settings) {
        super(settings);
        this.heatLevel = heatLevel;
        if (heatLevel > COLD_LEVEL) {
            LandPathNodeTypesRegistry.register(this, PathType.DAMAGE_OTHER, PathType.DAMAGE_OTHER);
        }
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            if (this.heatLevel > COLD_LEVEL && this.canBurnEntity(livingEntity)) {
                FrostifulConfig config = Frostiful.getConfig();

                int heatToDischarge = config.freezingConfig.getSunLichenHeatPerLevel() * this.heatLevel;
                // burn if hot sun lichen and target is warm
                if (livingEntity.thermoo$getTemperature() > 0 && this.heatLevel == HOT_LEVEL) {
                    livingEntity.setRemainingFireTicks(config.freezingConfig.getSunLichenBurnTime());
                } else if (livingEntity.thermoo$isCold()) { // only add heatToDischarge if cold, but always damage
                    livingEntity.thermoo$addTemperature(heatToDischarge, HeatingModes.ACTIVE);

                    // reset temperature if temp change overheated
                    if (livingEntity.thermoo$isWarm()) {
                        livingEntity.thermoo$setTemperature(0);
                    }
                }

                entity.hurt(world.damageSources().hotFloor(), 1);
                if (livingEntity instanceof ServerPlayer player) {
                    FCriteria.SUN_LICHEN_DISCHARGE.trigger(player, heatToDischarge);
                }
                createFireParticles(world, pos);

                BlockState coldSunLichenState = FBlocks.COLD_SUN_LICHEN.withPropertiesOf(state);
                world.setBlockAndUpdate(pos, coldSunLichenState);

                this.playSound(world, pos);
            }
        }

        super.entityInside(state, world, pos, entity);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        int skyLight = world.getBrightness(LightLayer.SKY, pos);
        if ((skyLight > 0 && world.isDay()) && world.getRandom().nextFloat() < this.getChargeChance(skyLight)) {
            Optional<BlockState> nextState = Heatable.getNextState(state);
            nextState.ifPresent(blockState -> world.setBlockAndUpdate(pos, blockState));
        } else if ((skyLight == 0) && world.getRandom().nextFloat() < RANDOM_DISCHARGE_CHANCE) {
            Optional<BlockState> previousState = Heatable.getPreviousState(state);
            previousState.ifPresent(blockState -> world.setBlockAndUpdate(pos, blockState));
        }
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {

        ItemStack toPlace = context.getItemInHand();

        boolean isDifferentSunLichen = toPlace.is(FItemTags.SUN_LICHENS) && !toPlace.is(this.asItem());

        if (isDifferentSunLichen) {
            return false;
        }

        return super.canBeReplaced(state, context);
    }

    @Override
    public int getHeatLevel() {
        return this.heatLevel;
    }

    private float getChargeChance(int skyLight) {
        return BASE_GROW_CHANCE * skyLight;
    }

    private boolean canBurnEntity(LivingEntity entity) {
        if (entity.isSpectator() || (entity instanceof Player player && player.isCreative())) {
            return false;
        } else if (entity.fireImmune()) {
            return false;
        } else {
            return true;
        }
    }

    private void playSound(Level world, BlockPos pos) {
        if (world.isClientSide()) {
            return;
        }
        RandomSource random = world.getRandom();
        float pitch = 0.8F + (random.nextFloat() - random.nextFloat()) * 0.4F;
        world.playSound(null, pos, FSoundEvents.FIRE_LICHEN_DISCHARGE, SoundSource.BLOCKS, 0.7F, pitch);
    }

    public static void createFireParticles(Level world, BlockPos pos) {
        final double maxHorizontalOffset = 0.5;

        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < 10; i++) {
            double x = pos.getX() + 0.5;
            double y = pos.getY() + random.nextDouble(0.33);
            double z = pos.getZ() + 0.5;
            x += random.nextDouble(-maxHorizontalOffset, maxHorizontalOffset);
            z += random.nextDouble(-maxHorizontalOffset, maxHorizontalOffset);
            world.addParticle(ParticleTypes.FLAME, x, y, z, 0.0D, 0.1D, 0.0D);
        }
    }
}
