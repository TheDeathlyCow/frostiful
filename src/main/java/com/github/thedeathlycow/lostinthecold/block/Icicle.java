package com.github.thedeathlycow.lostinthecold.block;

import com.github.thedeathlycow.lostinthecold.entity.damage.LostInTheColdDamageSource;
import net.minecraft.block.*;
import net.minecraft.block.enums.Thickness;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

@SuppressWarnings("deprecation")
public class Icicle extends Block implements LandingBlock, Waterloggable {

    public static final DirectionProperty VERTICAL_DIRECTION = Properties.VERTICAL_DIRECTION;
    public static final EnumProperty<Thickness> THICKNESS = Properties.THICKNESS;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty UNSTABLE = Properties.UNSTABLE;

    private static final float BECOME_UNSTABLE_CHANCE = 0.2f;
    private static final int UNSTABLE_TICKS_BEFORE_FALL = 60;

    public Icicle(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(VERTICAL_DIRECTION, Direction.UP)
                .with(THICKNESS, Thickness.TIP)
                .with(WATERLOGGED, false)
                .with(UNSTABLE, false)
        );
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(VERTICAL_DIRECTION, THICKNESS, WATERLOGGED, UNSTABLE);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return canPlaceAtWithDirection(world, pos, state.get(VERTICAL_DIRECTION));
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (state.get(VERTICAL_DIRECTION) == Direction.UP) {
            entity.handleFallDamage(fallDistance + 2.0F, 2.0F, LostInTheColdDamageSource.ICICLE);
        } else {
            super.onLandedUpon(world, state, pos, entity, fallDistance);
        }
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        WorldAccess worldAccess = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        Direction lookingDirection = ctx.getVerticalPlayerLookDirection().getOpposite();
        Direction direction = getDirectionToPlaceAt(worldAccess, blockPos, lookingDirection);
        if (direction == null) {
            return null;
        }

        boolean bl = !ctx.shouldCancelInteraction();
        Thickness thickness = getThickness(worldAccess, blockPos, direction, bl);

        boolean unstable = false;
        if (direction == Direction.DOWN) {
            unstable = isUnstable(worldAccess.getBlockState(blockPos.up()));
        }
        return thickness == null ? null : this.getDefaultState()
                .with(VERTICAL_DIRECTION, direction)
                .with(THICKNESS, thickness)
                .with(WATERLOGGED, worldAccess.getFluidState(blockPos).getFluid() == Fluids.WATER)
                .with(UNSTABLE, unstable);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        if (direction != Direction.UP && direction != Direction.DOWN) {
            return state;
        } else {
            Direction pointingIn = state.get(VERTICAL_DIRECTION);
            if (pointingIn == Direction.DOWN && world.getBlockTickScheduler().isQueued(pos, this)) {
                return state;
            } else if (direction == pointingIn.getOpposite() && !this.canPlaceAt(state, world, pos)) {
                if (pointingIn == Direction.DOWN) {
                    world.createAndScheduleBlockTick(pos, this, 2);
                } else {
                    world.createAndScheduleBlockTick(pos, this, 1);
                }

                return state;
            } else {
                boolean tryMerge = state.get(THICKNESS) == Thickness.TIP_MERGE;
                Thickness thickness = getThickness(world, pos, pointingIn, tryMerge);

                //boolean makeUnstable = !isUnstable(state) && isUnstable(neighborState);
                return state.with(THICKNESS, thickness).with(UNSTABLE, isUnstable(neighborState));
            }
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (isPointingUp(state) && !this.canPlaceAt(state, world, pos)) {
            world.breakBlock(pos, true);
        } else {
            spawnFallingBlock(state, world, pos);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (isHeldByIcicle(state, world, pos)) {
            if (!isUnstable(state) && random.nextFloat() < BECOME_UNSTABLE_CHANCE) { // fall
                world.setBlockState(pos, state.with(UNSTABLE, true));
                world.createAndScheduleBlockTick(pos, this, UNSTABLE_TICKS_BEFORE_FALL);
            }
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (isUnstable(state)) {
            createUnstableParticle(world, pos, state);
        }
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.DESTROY;
    }

    @Override
    public DamageSource getDamageSource() {
        return LostInTheColdDamageSource.FALLING_ICICLE;
    }


    private static boolean canPlaceAtWithDirection(WorldView world, BlockPos pos, Direction direction) {
        BlockPos blockPos = pos.offset(direction.getOpposite());
        BlockState blockState = world.getBlockState(blockPos);
        return blockState.isSideSolidFullSquare(world, blockPos, direction) || isIcicleFacingDirection(blockState, direction);
    }

    @Nullable
    private static Direction getDirectionToPlaceAt(WorldView world, BlockPos pos, Direction direction) {
        Direction toPlace;
        if (canPlaceAtWithDirection(world, pos, direction)) {
            toPlace = direction;
        } else {
            if (!canPlaceAtWithDirection(world, pos, direction.getOpposite())) {
                return null;
            }
            toPlace = direction.getOpposite();
        }

        return toPlace;
    }

    private static Thickness getThickness(WorldView world, BlockPos pos, Direction direction, boolean tryMerge) {
        Direction direction2 = direction.getOpposite();
        BlockState blockState = world.getBlockState(pos.offset(direction));
        if (isIcicleFacingDirection(blockState, direction2)) {
            return !tryMerge && blockState.get(THICKNESS) != Thickness.TIP_MERGE ? Thickness.TIP : Thickness.TIP_MERGE;
        } else if (!isIcicleFacingDirection(blockState, direction)) {
            return Thickness.TIP;
        } else {
            Thickness thickness = blockState.get(THICKNESS);
            if (thickness != Thickness.TIP && thickness != Thickness.TIP_MERGE) {
                BlockState blockState2 = world.getBlockState(pos.offset(direction2));
                return !isIcicleFacingDirection(blockState2, direction) ? Thickness.BASE : Thickness.MIDDLE;
            } else {
                return Thickness.FRUSTUM;
            }
        }
    }

    private static boolean isUnstable(BlockState state) {
        return state.isOf(LostInTheColdBlocks.ICICLE) && state.get(UNSTABLE);
    }

    private static boolean isPointingUp(BlockState state) {
        return isIcicleFacingDirection(state, Direction.UP);
    }

    private static boolean isPointingDown(BlockState state) {
        return isIcicleFacingDirection(state, Direction.DOWN);
    }

    private static boolean isIcicleFacingDirection(BlockState state, Direction direction) {
        return state.isOf(LostInTheColdBlocks.ICICLE) && state.get(VERTICAL_DIRECTION) == direction;
    }

    private static boolean isTip(BlockState state, boolean allowMerged) {
        if (!state.isOf(LostInTheColdBlocks.ICICLE)) {
            return false;
        } else {
            Thickness thickness = state.get(THICKNESS);
            return thickness == Thickness.TIP || allowMerged && thickness == Thickness.TIP_MERGE;
        }
    }

    private static void spawnFallingBlock(BlockState state, ServerWorld world, BlockPos pos) {
        BlockPos.Mutable mutablePosition = pos.mutableCopy();

        for (BlockState blockState = state; isPointingDown(blockState); blockState = world.getBlockState(mutablePosition)) {
            FallingBlockEntity fallingBlockEntity = FallingBlockEntity.spawnFromBlock(world, mutablePosition, blockState);
            if (isTip(blockState, true)) {
                float fallHurtAmount = Math.max(1 + pos.getY() - mutablePosition.getY(), 6);
                fallingBlockEntity.setHurtEntities(fallHurtAmount, 40);
                break;
            }

            mutablePosition.move(Direction.DOWN);
        }
    }

    private static boolean isHeldByIcicle(BlockState state, WorldView world, BlockPos pos) {
        return isPointingDown(state) && !world.getBlockState(pos.up()).isOf(LostInTheColdBlocks.ICICLE);
    }

    private static void createUnstableParticle(World world, BlockPos pos, BlockState state) {
        Vec3d vec3d = state.getModelOffset(world, pos);
        double xOffset = (double)pos.getX() + 0.5D + vec3d.x;
        double yOffset = (double)((float)(pos.getY() + 1) - 0.6875F) - 0.0625D;
        double zOffset = (double)pos.getZ() + 0.5D + vec3d.z;
        world.addParticle(ParticleTypes.SNOWFLAKE, xOffset, yOffset, zOffset, 0.0D, 0.0D, 0.0D);
    }
}
