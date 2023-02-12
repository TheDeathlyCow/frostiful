package com.github.thedeathlycow.frostiful.block;

import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.sound.FSoundEvents;
import com.github.thedeathlycow.frostiful.tag.items.FItemTags;
import com.github.thedeathlycow.frostiful.util.survival.FrostHelper;
import net.fabricmc.fabric.api.registry.LandPathNodeTypesRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.GlowLichenBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("deprecation")
public class SunLichenBlock extends GlowLichenBlock implements Heatable {

    private static final float BASE_GROW_CHANCE = 0.017f;
    private static final float RANDOM_DISCHARGE_CHANCE = 0.13f;

    private final int heatLevel;

    public SunLichenBlock(int heatLevel, Settings settings) {
        super(settings);
        this.heatLevel = heatLevel;
        if (heatLevel > 0){
            LandPathNodeTypesRegistry.register(this, PathNodeType.DAMAGE_OTHER, PathNodeType.DANGER_OTHER);
        }
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            if (this.heatLevel > 0 && this.canBurn(livingEntity)) {
                FrostifulConfig config = Frostiful.getConfig();
                int heat = config.freezingConfig.getSunLichenHeatPerLevel() * this.heatLevel;
                FrostHelper.removeLivingFrost(livingEntity, heat);
                entity.damage(DamageSource.HOT_FLOOR, 1);
                this.createFireParticles(world, pos);

                BlockState coldSunLichenState = FBlocks.COLD_SUN_LICHEN.getStateWithProperties(state);
                world.setBlockState(pos, coldSunLichenState);

                this.playSound(world, pos);
            }
        }

        super.onEntityCollision(state, world, pos, entity);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int skyLight = world.getLightLevel(LightType.SKY, pos);
        if ((skyLight > 0 && world.isDay()) && world.getRandom().nextFloat() < this.getChargeChance(skyLight)) {
            Optional<BlockState> nextState = Heatable.getNextState(state);
            nextState.ifPresent(blockState -> world.setBlockState(pos, blockState));
        } else if ((skyLight == 0) && world.getRandom().nextFloat() < RANDOM_DISCHARGE_CHANCE) {
            Optional<BlockState> previousState = Heatable.getPreviousState(state);
            previousState.ifPresent(blockState -> world.setBlockState(pos, blockState));
        }
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {

        ItemStack toPlace = context.getStack();

        boolean isDifferentSunLichen = toPlace.isIn(FItemTags.SUN_LICHENS) && !toPlace.isOf(this.asItem());

        if (isDifferentSunLichen) {
            return false;
        }

        return super.canReplace(state, context);
    }

    @Override
    public int getHeatLevel() {
        return this.heatLevel;
    }

    private float getChargeChance(int skyLight) {
        return BASE_GROW_CHANCE * skyLight;
    }

    private boolean canBurn(LivingEntity entity) {
        if (entity.isSpectator() || (entity instanceof PlayerEntity player && player.isCreative())) {
            return false;
        }
        return !entity.isFireImmune();
    }

    private void playSound(World world, BlockPos pos) {
        if (world.isClient()) {
            return;
        }
        Random random = world.getRandom();
        float pitch = 0.8F + (random.nextFloat() - random.nextFloat()) * 0.4F;
        world.playSound(null, pos, FSoundEvents.FIRE_LICHEN_DISCHARGE, SoundCategory.BLOCKS, 0.7F, pitch);
    }

    private void createFireParticles(World world, BlockPos pos) {
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
