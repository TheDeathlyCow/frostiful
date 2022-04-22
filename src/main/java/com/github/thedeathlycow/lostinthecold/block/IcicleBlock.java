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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class IcicleBlock extends Block implements LandingBlock, Waterloggable {

    public static final DirectionProperty VERTICAL_DIRECTION = Properties.VERTICAL_DIRECTION;
    public static final EnumProperty<Thickness> THICKNESS = Properties.THICKNESS;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public IcicleBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(VERTICAL_DIRECTION, Direction.UP).with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(VERTICAL_DIRECTION, THICKNESS, WATERLOGGED);
    }

    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (state.get(VERTICAL_DIRECTION) == Direction.UP) {
            entity.handleFallDamage(fallDistance + 2.0F, 2.0F, LostInTheColdDamageSource.ICICLE);
        } else {
            super.onLandedUpon(world, state, pos, entity, fallDistance);
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

    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.DESTROY;
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        WorldAccess worldAccess = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        Direction lookingDirection = ctx.getVerticalPlayerLookDirection().getOpposite();
        Direction direction = getDirectionToPlaceAt(worldAccess, blockPos, lookingDirection);
        if (direction == null) {
            return null;
        } else {
            boolean bl = !ctx.shouldCancelInteraction();
            Thickness thickness = getThickness(worldAccess, blockPos, direction, bl);
            return thickness == null ? null : this.getDefaultState().with(VERTICAL_DIRECTION, direction).with(THICKNESS, thickness).with(WATERLOGGED, worldAccess.getFluidState(blockPos).getFluid() == Fluids.WATER);
        }
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
            Thickness thickness = (Thickness)blockState.get(THICKNESS);
            if (thickness != Thickness.TIP && thickness != Thickness.TIP_MERGE) {
                BlockState blockState2 = world.getBlockState(pos.offset(direction2));
                return !isIcicleFacingDirection(blockState2, direction) ? Thickness.BASE : Thickness.MIDDLE;
            } else {
                return Thickness.FRUSTUM;
            }
        }
    }

    private static boolean isPointingUp(BlockState state) {
        return isIcicleFacingDirection(state, Direction.UP);
    }

    private static boolean isPointingDown(BlockState state) {
        return isIcicleFacingDirection(state, Direction.DOWN);
    }

    private static boolean isIcicleFacingDirection(BlockState state, Direction direction) {
        return state.isOf(LostInTheColdBlocks.ICICLE_BLOCK) && state.get(VERTICAL_DIRECTION) == direction;
    }

    private static boolean isTip(BlockState state, boolean allowMerged) {
        if (!state.isOf(Blocks.POINTED_DRIPSTONE)) {
            return false;
        } else {
            Thickness thickness = state.get(THICKNESS);
            return thickness == Thickness.TIP || allowMerged && thickness == Thickness.TIP_MERGE;
        }
    }

    private static void spawnFallingBlock(BlockState state, ServerWorld world, BlockPos pos) {
        BlockPos.Mutable mutablePosition = pos.mutableCopy();

        for(BlockState blockState = state; isPointingDown(blockState); blockState = world.getBlockState(mutablePosition)) {
            FallingBlockEntity fallingBlockEntity = FallingBlockEntity.spawnFromBlock(world, mutablePosition, blockState);
            if (isTip(blockState, true)) {
                float fallHurtAmount = Math.max(1 + pos.getY() - mutablePosition.getY(), 6);
                fallingBlockEntity.setHurtEntities(fallHurtAmount, 40);
                break;
            }

            mutablePosition.move(Direction.DOWN);
        }

    }
}
