package com.github.thedeathlycow.frostiful.block;

import com.github.thedeathlycow.frostiful.entity.damage.FrostifulDamageSource;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.block.*;
import net.minecraft.block.enums.Thickness;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Random;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

@SuppressWarnings("deprecation")
public class Icicle extends Block implements LandingBlock, Waterloggable {

    public static final DirectionProperty VERTICAL_DIRECTION = Properties.VERTICAL_DIRECTION;
    public static final EnumProperty<Thickness> THICKNESS = Properties.THICKNESS;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty UNSTABLE = Properties.UNSTABLE;

    private static final VoxelShape TIP_MERGE_SHAPE = Block.createCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);
    private static final VoxelShape UP_TIP_SHAPE = Block.createCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 11.0D, 11.0D);
    private static final VoxelShape DOWN_TIP_SHAPE = Block.createCuboidShape(5.0D, 5.0D, 5.0D, 11.0D, 16.0D, 11.0D);
    private static final VoxelShape BASE_SHAPE = Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    private static final VoxelShape FRUSTUM_SHAPE = Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D);
    private static final VoxelShape MIDDLE_SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    private static final float BECOME_UNSTABLE_CHANCE = 0.01f;
    private static final float GROW_CHANCE = 0.02f;
    private static final IntProvider UNSTABLE_TICKS_BEFORE_FALL = UniformIntProvider.create(40, 80);

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
            entity.handleFallDamage(fallDistance + 2.0F, 2.0F, FrostifulDamageSource.ICICLE);
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
            BlockState state = worldAccess.getBlockState(blockPos.up());
            unstable = isUnstable(state) && isHeldByIcicle(state, worldAccess, blockPos);
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

                boolean makeUnstable = isUnstable(state) || (isHeldByIcicle(state, world, pos) && isUnstable(neighborState));
                return state.with(THICKNESS, thickness).with(UNSTABLE, makeUnstable);
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
        if (isPointingDown(state)) {

            if (random.nextFloat() < GROW_CHANCE) { // grow
                this.tryGrow(state, world, pos, random);
            } else if (random.nextFloat() < BECOME_UNSTABLE_CHANCE && isHeldByIcicle(state, world, pos)) { // fall
                this.tryFall(state, world, pos, random);
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
        return FrostifulDamageSource.FALLING_ICICLE;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Thickness thickness = state.get(THICKNESS);
        VoxelShape voxelShape;
        if (thickness == Thickness.TIP_MERGE) {
            voxelShape = TIP_MERGE_SHAPE;
        } else if (thickness == Thickness.TIP) {
            if (state.get(VERTICAL_DIRECTION) == Direction.DOWN) {
                voxelShape = DOWN_TIP_SHAPE;
            } else {
                voxelShape = UP_TIP_SHAPE;
            }
        } else if (thickness == Thickness.FRUSTUM) {
            voxelShape = BASE_SHAPE;
        } else if (thickness == Thickness.MIDDLE) {
            voxelShape = FRUSTUM_SHAPE;
        } else {
            voxelShape = MIDDLE_SHAPE;
        }

        Vec3d vec3d = state.getModelOffset(world, pos);
        return voxelShape.offset(vec3d.x, 0.0D, vec3d.z);
    }

    @Override
    public boolean isShapeFullCube(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }

    @Override
    public float getMaxHorizontalModelOffset() {
        return 0.125F;
    }

    private void tryFall(BlockState state, ServerWorld world, BlockPos pos, Random random) {

        if (isUnstable(state) || isPointingUp(state)) {
            return;
        }

        BlockPos tipPos = getTipPos(state, world, pos, 25, false);
        if (tipPos != null) {
            world.setBlockState(pos, state.with(UNSTABLE, true));
            world.createAndScheduleBlockTick(pos, this, UNSTABLE_TICKS_BEFORE_FALL.get(random));
        }
    }

    private void tryGrow(BlockState state, ServerWorld world, BlockPos pos, Random random) {

        if (isUnstable(state) || isPointingUp(state)) {
            return;
        }

        BlockState anchor = world.getBlockState(pos.up());
        if (isGrowableBlock(anchor)) {
            BlockPos tipPos = getTipPos(state, world, pos, 7, false);
            if (tipPos != null) {
                BlockState tipState = world.getBlockState(tipPos);
                if (canDrip(tipState) && canGrow(tipState, world, tipPos)) {
                    if (random.nextBoolean()) {
                        tryGrow(world, tipPos, Direction.DOWN);
                    } else {
                        tryGrowGroundIcicle(world, tipPos);
                    }
                }
            }
        }
    }

    private static void tryGrowGroundIcicle(ServerWorld world, BlockPos pos) {
        BlockPos.Mutable mutable = pos.mutableCopy();

        for (int i = 0; i < 10; ++i) {
            mutable.move(Direction.DOWN);
            BlockState blockState = world.getBlockState(mutable);
            if (!blockState.getFluidState().isEmpty()) {
                return;
            }

            if (isTip(blockState, Direction.UP) && canGrow(blockState, world, mutable)) {
                tryGrow(world, mutable, Direction.UP);
                return;
            }

            if (canPlaceAtWithDirection(world, mutable, Direction.UP) && !world.isWater(mutable.down())) {
                tryGrow(world, mutable.down(), Direction.UP);
                return;
            }
        }
    }

    private static void tryGrow(ServerWorld world, BlockPos pos, Direction direction) {
        BlockPos blockPos = pos.offset(direction);
        BlockState blockState = world.getBlockState(blockPos);
        if (isTip(blockState, direction.getOpposite())) {
            growMerged(blockState, world, blockPos);
        } else if (blockState.isAir() || blockState.isOf(Blocks.WATER)) {
            place(world, blockPos, direction, Thickness.TIP);
        }
    }

    private static void place(WorldAccess world, BlockPos pos, Direction direction, Thickness thickness) {
        BlockState blockState = FrostifulBlocks.ICICLE.getDefaultState().with(VERTICAL_DIRECTION, direction).with(THICKNESS, thickness).with(WATERLOGGED, world.getFluidState(pos).getFluid() == Fluids.WATER);
        world.setBlockState(pos, blockState, 3);
    }

    private static void growMerged(BlockState state, WorldAccess world, BlockPos pos) {
        BlockPos upperPos;
        BlockPos lowerPos;
        if (state.get(VERTICAL_DIRECTION) == Direction.UP) {
            lowerPos = pos;
            upperPos = pos.up();
        } else {
            upperPos = pos;
            lowerPos = pos.down();
        }

        place(world, upperPos, Direction.DOWN, Thickness.TIP_MERGE);
        place(world, lowerPos, Direction.UP, Thickness.TIP_MERGE);
    }

    @Nullable
    private static BlockPos getTipPos(BlockState state, WorldAccess world, BlockPos pos, int range, boolean allowMerged) {
        if (isTip(state, allowMerged)) {
            return pos;
        } else {
            Direction direction = state.get(VERTICAL_DIRECTION);
            BiPredicate<BlockPos, BlockState> continuePredicate = (posx, statex) -> {
                return statex.isOf(FrostifulBlocks.ICICLE) && statex.get(VERTICAL_DIRECTION) == direction;
            };
            Predicate<BlockState> stopPredicate = (statex) -> {
                return isTip(statex, allowMerged);
            };
            return searchInDirection(world, pos, direction.getDirection(), continuePredicate, stopPredicate, range).orElse(null);
        }
    }

    public static boolean canDrip(BlockState state) {
        return isPointingDown(state) && state.get(THICKNESS) == Thickness.TIP;
    }

    private static boolean canGrow(BlockState state, ServerWorld world, BlockPos pos) {
        Direction direction = state.get(VERTICAL_DIRECTION);
        BlockPos blockPos = pos.offset(direction);
        BlockState blockState = world.getBlockState(blockPos);
        if (!blockState.getFluidState().isEmpty()) {
            return false;
        } else {
            return blockState.isAir() || isTip(blockState, direction.getOpposite());
        }
    }

    private static boolean isGrowableBlock(BlockState anchorState) {
        return anchorState.isIn(BlockTags.ICE);
    }

    private static Optional<BlockPos> searchInDirection(WorldAccess world, BlockPos pos, Direction.AxisDirection direction, BiPredicate<BlockPos, BlockState> continuePredicate, Predicate<BlockState> stopPredicate, int range) {
        Direction toMove = Direction.get(direction, Direction.Axis.Y);
        BlockPos.Mutable current = pos.mutableCopy();

        for (int i = 1; i < range; i++) {
            current.move(toMove);
            BlockState blockState = world.getBlockState(current);
            if (stopPredicate.test(blockState)) {
                return Optional.of(current.toImmutable());
            }

            if (world.isOutOfHeightLimit(current.getY()) || !continuePredicate.test(current, blockState)) {
                Frostiful.LOGGER.info("do not continue");
                return Optional.empty();
            }
        }

        return Optional.empty();
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
        return state.isOf(FrostifulBlocks.ICICLE) && state.get(UNSTABLE);
    }

    private static boolean isPointingUp(BlockState state) {
        return isIcicleFacingDirection(state, Direction.UP);
    }

    private static boolean isPointingDown(BlockState state) {
        return isIcicleFacingDirection(state, Direction.DOWN);
    }

    private static boolean isIcicleFacingDirection(BlockState state, Direction direction) {
        return state.isOf(FrostifulBlocks.ICICLE) && state.get(VERTICAL_DIRECTION) == direction;
    }

    private static boolean isTip(BlockState state, Direction direction) {
        return isTip(state, false) && state.get(VERTICAL_DIRECTION) == direction;
    }

    private static boolean isTip(BlockState state, boolean allowMerged) {
        if (!state.isOf(FrostifulBlocks.ICICLE)) {
            return false;
        } else {
            Thickness thickness = state.get(THICKNESS);
            return thickness == Thickness.TIP || (allowMerged && thickness == Thickness.TIP_MERGE);
        }
    }

    private static void spawnFallingBlock(BlockState state, ServerWorld world, BlockPos pos) {
        BlockPos.Mutable current = pos.mutableCopy();

        for (BlockState blockState = state; isPointingDown(blockState); blockState = world.getBlockState(current)) {
            FallingBlockEntity fallingBlockEntity = FallingBlockEntity.spawnFromBlock(world, current, blockState);
            if (isTip(blockState, true)) {
                float fallHurtAmount = Math.max(1 + pos.getY() - current.getY(), 6);
                fallingBlockEntity.setHurtEntities(fallHurtAmount, 40);
                break;
            }

            current.move(Direction.DOWN);
        }
    }

    private static boolean isHeldByIcicle(BlockState state, WorldView world, BlockPos pos) {
        return isPointingDown(state) && world.getBlockState(pos.up()).isOf(FrostifulBlocks.ICICLE);
    }

    private static void createUnstableParticle(World world, BlockPos pos, BlockState state) {
        Vec3d vec3d = state.getModelOffset(world, pos);
        double xOffset = (double) pos.getX() + 0.5D + vec3d.x;
        double yOffset = (double) ((float) (pos.getY() + 1) - 0.6875F) - 0.0625D;
        double zOffset = (double) pos.getZ() + 0.5D + vec3d.z;
        world.addParticle(ParticleTypes.SNOWFLAKE, xOffset, yOffset, zOffset, 0.0D, 0.0D, 0.0D);
    }
}
