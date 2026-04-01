package com.github.thedeathlycow.frostiful.block;

import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.config.section.BlockSettings;
import com.github.thedeathlycow.frostiful.entity.damage.FDamageSources;
import com.github.thedeathlycow.frostiful.mixins.entity.FallingBlockEntityAccessor;
import com.github.thedeathlycow.frostiful.registry.FBlocks;
import com.github.thedeathlycow.frostiful.registry.tag.FBlockTags;
import com.github.thedeathlycow.thermoo.api.core.v2.source.TemperatureSources;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * Icicles are growths of ice that sometimes will fall and
 * damage players.
 * Largely based on code from {@link PointedDripstoneBlock}.
 */
public class IcicleBlock extends Block implements Fallable, SimpleWaterloggedBlock {

    /**
     * Icicles can point up and down
     */
    public static final EnumProperty<Direction> VERTICAL_DIRECTION = BlockStateProperties.VERTICAL_DIRECTION;
    /**
     * Icicles have varying levels of thickness
     */
    public static final EnumProperty<DripstoneThickness> THICKNESS = BlockStateProperties.DRIPSTONE_THICKNESS;
    /**
     * Icicles can be waterlogged
     */
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    /**
     * An unstable icicle is about to fall
     */
    public static final BooleanProperty UNSTABLE = BlockStateProperties.UNSTABLE;

    private static final VoxelShape TIP_MERGE_SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);
    private static final VoxelShape UP_TIP_SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 11.0D, 11.0D);
    private static final VoxelShape DOWN_TIP_SHAPE = Block.box(5.0D, 5.0D, 5.0D, 11.0D, 16.0D, 11.0D);
    private static final VoxelShape BASE_SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    private static final VoxelShape FRUSTUM_SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D);
    private static final VoxelShape MIDDLE_SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    private static final float BECOME_UNSTABLE_CHANCE = 0.05f;
    private static final IntProvider UNSTABLE_TICKS_BEFORE_FALL = UniformInt.of(40, 80);

    public IcicleBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(VERTICAL_DIRECTION, Direction.UP)
                .setValue(THICKNESS, DripstoneThickness.TIP)
                .setValue(WATERLOGGED, false)
                .setValue(UNSTABLE, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(VERTICAL_DIRECTION, THICKNESS, WATERLOGGED, UNSTABLE);
    }

    /**
     * Determine if the state can legally be placed as an icicle
     *
     * @param state State of the icicle to test
     * @param world World the icicle is in
     * @param pos   The position of the icicle
     * @return Returns true if the icicle can be placed, false otherwise
     */
    @Override
    protected boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return canPlaceAtWithDirection(world, pos, state.getValue(VERTICAL_DIRECTION));
    }

    /**
     * Icicles collapse when hit by any projectile
     *
     * @param world      The world the icicle is in
     * @param state      The state of the icicle
     * @param hit        How the projectile hit the icicle
     * @param projectile The projectile that hit the icicle
     */
    @Override
    protected void onProjectileHit(Level world, BlockState state, BlockHitResult hit, Projectile projectile) {
        BlockPos blockPos = hit.getBlockPos();
        if (world instanceof ServerLevel serverWorld && projectile.mayInteract(serverWorld, blockPos) && projectile.getDeltaMovement().length() > 0.6D) {
            world.destroyBlock(blockPos, true);
        }
    }

    /**
     * Hurt entities when they fall on icicles
     *
     * @param level        The world the icicle is in
     * @param state        The state of the icicle
     * @param pos          The position of the icicle
     * @param entity       The entity that fell
     * @param fallDistance How far the entity fell
     */
    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, double fallDistance) {
        if (state.getValue(VERTICAL_DIRECTION) == Direction.UP) {
            DamageSource damageSource = FDamageSources.getDamageSources(level).frostiful$icicle();
            boolean tookDamage = entity.causeFallDamage(fallDistance + 2.0, 2.0f, damageSource);
            if (tookDamage && entity instanceof LivingEntity livingEntity) {
                livingEntity.thermoo$addTemperature(
                        FrostifulConfigYACL.temperatureSourceSettings().icicleCollisionFreezeAmount(),
                        level.thermoo$temperatureSources().create(TemperatureSources.ACTIVE, pos.getCenter())
                );
            }
        } else {
            super.fallOn(level, state, pos, entity, fallDistance);
        }
    }

    /**
     * Get the state of an icicle to be placed
     *
     * @param ctx Context for placement
     * @return Returns the {@link BlockState} to be placed
     */
    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        LevelAccessor worldAccess = ctx.getLevel();
        BlockPos blockPos = ctx.getClickedPos();
        Direction lookingDirection = ctx.getNearestLookingVerticalDirection().getOpposite();
        Direction direction = getDirectionToPlaceAt(worldAccess, blockPos, lookingDirection);
        if (direction == null) {
            return null;
        }

        DripstoneThickness thickness = getThickness(worldAccess, blockPos, direction, !ctx.isSecondaryUseActive());

        boolean unstable = false;
        if (direction == Direction.DOWN) {
            BlockState state = worldAccess.getBlockState(blockPos.above());
            unstable = isUnstable(state) && isHeldByIcicle(state, worldAccess, blockPos);
        }
        return thickness == null ? null : this.defaultBlockState()
                .setValue(VERTICAL_DIRECTION, direction)
                .setValue(THICKNESS, thickness)
                .setValue(WATERLOGGED, worldAccess.getFluidState(blockPos).getType() == Fluids.WATER)
                .setValue(UNSTABLE, unstable);
    }

    /**
     * Gets an updated block state to change the icicle to when a neighbour is updated.
     * Handles fluid ticking when water logged.
     * Schedules the icicle to be broken if its support has been broken or if its support
     * is unstable.
     *
     * @param state         the state of this block
     * @param direction     the direction from this block to the neighbor
     * @param neighborState the state of the updated neighbor block
     * @param world         the world
     * @param pos           the position of this block
     * @param neighborPos   the position of the neighbor block
     * @return Returns the updated {@link BlockState} of the icicle
     */
    @Override
    protected BlockState updateShape(
            BlockState state,
            LevelReader world,
            ScheduledTickAccess tickView,
            BlockPos pos,
            Direction direction,
            BlockPos neighborPos,
            BlockState neighborState,
            RandomSource random
    ) {
        if (state.getValue(WATERLOGGED)) {
            tickView.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        if (direction != Direction.UP && direction != Direction.DOWN) {
            return state;
        } else {
            Direction pointingIn = state.getValue(VERTICAL_DIRECTION);
            if (pointingIn == Direction.DOWN && tickView.getBlockTicks().hasScheduledTick(pos, this)) {
                return state;
            } else if (direction == pointingIn.getOpposite() && !this.canSurvive(state, world, pos)) {
                if (pointingIn == Direction.DOWN) {
                    tickView.scheduleTick(pos, this, 2);
                } else {
                    tickView.scheduleTick(pos, this, 1);
                }

                return state;
            } else {
                boolean tryMerge = state.getValue(THICKNESS) == DripstoneThickness.TIP_MERGE;
                DripstoneThickness thickness = getThickness(world, pos, pointingIn, tryMerge);

                boolean makeUnstable = isUnstable(state)
                        || (isHeldByIcicle(state, world, pos)
                        && isUnstable(neighborState)
                        && direction == Direction.UP);

                return state.setValue(THICKNESS, thickness).setValue(UNSTABLE, makeUnstable);
            }
        }
    }

    @Override
    protected void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (isPointingUp(state) && !this.canSurvive(state, world, pos)) {
            world.destroyBlock(pos, true);
        } else {
            spawnFallingBlock(state, world, pos);
        }
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (isPointingDown(state)) {
            BlockSettings settings = FrostifulConfigYACL.blockSettings();

            boolean tryFall = settings.enableIcicleInstability()
                    && random.nextFloat() < BECOME_UNSTABLE_CHANCE * settings.icicleInstabilityChanceMultiplier()
                    && isHeldByIcicleFallable(state, world, pos);

            if (tryFall) {
                this.tryFall(state, world, pos, random);
            }

            final double growChance = settings.icicleGrowthChanceMultiplier() * getGrowChance(world);
            if (random.nextFloat() < growChance) { // grow
                this.tryGrowIcicle(state, world, pos, random);
            }
        }
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (isUnstable(state)) {
            createUnstableParticle(world, pos, state);
        }
    }

    @Override
    public DamageSource getFallDamageSource(Entity attacker) {
        return FDamageSources.getDamageSources(attacker.level()).frostiful$fallingIcicle(attacker);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return Boolean.TRUE.equals(state.getValue(WATERLOGGED))
                ? Fluids.WATER.getSource(false)
                : super.getFluidState(state);
    }

    @Override
    protected VoxelShape getOcclusionShape(BlockState state) {
        return Shapes.empty();
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        DripstoneThickness thickness = state.getValue(THICKNESS);
        VoxelShape voxelShape;
        if (thickness == DripstoneThickness.TIP_MERGE) {
            voxelShape = TIP_MERGE_SHAPE;
        } else if (thickness == DripstoneThickness.TIP) {
            if (state.getValue(VERTICAL_DIRECTION) == Direction.DOWN) {
                voxelShape = DOWN_TIP_SHAPE;
            } else {
                voxelShape = UP_TIP_SHAPE;
            }
        } else if (thickness == DripstoneThickness.FRUSTUM) {
            voxelShape = BASE_SHAPE;
        } else if (thickness == DripstoneThickness.MIDDLE) {
            voxelShape = FRUSTUM_SHAPE;
        } else {
            voxelShape = MIDDLE_SHAPE;
        }

        Vec3 vec3d = state.getOffset(pos);
        return voxelShape.move(vec3d.x, 0.0D, vec3d.z);
    }

    @Override
    protected boolean isCollisionShapeFullBlock(BlockState state, BlockGetter world, BlockPos pos) {
        return false;
    }

    @Override
    protected float getMaxHorizontalOffset() {
        return 0.125F;
    }

    private static double getGrowChance(ServerLevel world) {
        if (world.isThundering()) {
            return 0.15;
        } else if (world.isRaining()) {
            return 0.09;
        } else {
            return 0.02;
        }
    }

    private void tryFall(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {

        if (isUnstable(state) || isPointingUp(state)) {
            return;
        }

        BlockPos tipPos = getTipPos(state, world, pos, 25, false);
        if (tipPos != null) {
            world.setBlockAndUpdate(pos, state.setValue(UNSTABLE, true));
            world.scheduleTick(pos, this, UNSTABLE_TICKS_BEFORE_FALL.sample(random));
        }
    }

    private void tryGrowIcicle(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {

        if (isUnstable(state) || isPointingUp(state)) {
            return;
        }

        BlockPos anchorPos = pos.above();
        BlockState anchor = world.getBlockState(anchorPos);
        if (canGrowIcicleOnAnchor(world, anchorPos, anchor)) {
            BlockPos tipPos = getTipPos(state, world, pos, 7, false);
            if (tipPos != null) {
                BlockState tipState = world.getBlockState(tipPos);
                if (!isUnstable(tipState) && isTipDown(tipState) && canGrow(tipState, world, tipPos)) {
                    tryGrow(world, tipPos, Direction.DOWN);
                }
            }
        }
    }

    private static void tryGrowGroundIcicle(ServerLevel world, BlockPos pos) {
        BlockPos.MutableBlockPos mutable = pos.mutable();

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

            if (canPlaceAtWithDirection(world, mutable, Direction.UP)) {
                tryGrow(world, mutable.below(), Direction.UP);
                return;
            }
        }
    }

    private static void tryGrow(ServerLevel world, BlockPos pos, Direction direction) {
        BlockPos posGrowingInto = pos.relative(direction);
        BlockState stateGrowingInto = world.getBlockState(posGrowingInto);
        if (isTip(stateGrowingInto, direction.getOpposite())) {
            growMerged(stateGrowingInto, world, posGrowingInto);
        } else if (stateGrowingInto.isAir() || stateGrowingInto.is(Blocks.WATER)) {
            place(world, posGrowingInto, direction, DripstoneThickness.TIP);
        }
    }

    private static void place(LevelAccessor world, BlockPos pos, Direction direction, DripstoneThickness thickness) {
        BlockState blockState = FBlocks.ICICLE.defaultBlockState().setValue(VERTICAL_DIRECTION, direction).setValue(THICKNESS, thickness).setValue(WATERLOGGED, world.getFluidState(pos).getType() == Fluids.WATER);
        world.setBlock(pos, blockState, 3);
    }

    private static void growMerged(BlockState state, LevelAccessor world, BlockPos pos) {
        BlockPos upperPos;
        BlockPos lowerPos;
        if (state.getValue(VERTICAL_DIRECTION) == Direction.UP) {
            lowerPos = pos;
            upperPos = pos.above();
        } else {
            upperPos = pos;
            lowerPos = pos.below();
        }

        place(world, upperPos, Direction.DOWN, DripstoneThickness.TIP_MERGE);
        place(world, lowerPos, Direction.UP, DripstoneThickness.TIP_MERGE);
    }

    @Nullable
    private static BlockPos getTipPos(BlockState state, LevelAccessor world, BlockPos pos, int range, boolean allowMerged) {
        if (isTip(state, allowMerged)) {
            return pos;
        } else {
            Direction direction = state.getValue(VERTICAL_DIRECTION);
            BiPredicate<BlockPos, BlockState> continuePredicate = (posx, statex) -> {
                return statex.is(FBlocks.ICICLE) && statex.getValue(VERTICAL_DIRECTION) == direction;
            };
            Predicate<BlockState> stopPredicate = (statex) -> {
                return isTip(statex, allowMerged);
            };
            return searchInDirection(world, pos, direction.getAxisDirection(), continuePredicate, stopPredicate, range).orElse(null);
        }
    }

    private static boolean isTipDown(BlockState state) {
        return isPointingDown(state) && state.getValue(THICKNESS) == DripstoneThickness.TIP;
    }

    private static boolean canGrow(BlockState state, ServerLevel world, BlockPos pos) {
        Direction direction = state.getValue(VERTICAL_DIRECTION);
        BlockPos blockPos = pos.relative(direction);
        BlockState blockState = world.getBlockState(blockPos);
        if (!blockState.getFluidState().isEmpty()) {
            return false;
        } else {
            return blockState.isAir() || isTip(blockState, direction.getOpposite());
        }
    }

    private static boolean canGrowIcicleOnAnchor(ServerLevel world, BlockPos anchorPos, BlockState anchorState) {

        if (world.isRaining()) {
            Biome biome = world.getBiome(anchorPos).value();
            return biome.coldEnoughToSnow(anchorPos, world.getSeaLevel());
        }

        return anchorState.is(FBlockTags.ICICLE_GROWABLE);
    }

    private static Optional<BlockPos> searchInDirection(LevelAccessor world, BlockPos pos, Direction.AxisDirection direction, BiPredicate<BlockPos, BlockState> continuePredicate, Predicate<BlockState> stopPredicate, int range) {
        Direction toMove = Direction.get(direction, Direction.Axis.Y);
        BlockPos.MutableBlockPos current = pos.mutable();

        for (int i = 1; i < range; i++) {
            current.move(toMove);
            BlockState blockState = world.getBlockState(current);
            if (stopPredicate.test(blockState)) {
                return Optional.of(current.immutable());
            }

            if (world.isOutsideBuildHeight(current.getY()) || !continuePredicate.test(current, blockState)) {
                return Optional.empty();
            }
        }

        return Optional.empty();
    }

    private static boolean canPlaceAtWithDirection(LevelReader world, BlockPos pos, Direction direction) {
        BlockPos candidateAnchorPos = pos.relative(direction.getOpposite());
        BlockState candidateAnchorState = world.getBlockState(candidateAnchorPos);
        return candidateAnchorState.isFaceSturdy(world, candidateAnchorPos, direction)
                || isIcicleFacingDirection(candidateAnchorState, direction);
    }

    @Nullable
    private static Direction getDirectionToPlaceAt(LevelReader world, BlockPos pos, Direction direction) {
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

    private static DripstoneThickness getThickness(LevelReader world, BlockPos pos, Direction direction, boolean tryMerge) {
        Direction direction2 = direction.getOpposite();
        BlockState blockState = world.getBlockState(pos.relative(direction));
        if (isIcicleFacingDirection(blockState, direction2)) {
            return !tryMerge && blockState.getValue(THICKNESS) != DripstoneThickness.TIP_MERGE ? DripstoneThickness.TIP : DripstoneThickness.TIP_MERGE;
        } else if (!isIcicleFacingDirection(blockState, direction)) {
            return DripstoneThickness.TIP;
        } else {
            DripstoneThickness thickness = blockState.getValue(THICKNESS);
            if (thickness != DripstoneThickness.TIP && thickness != DripstoneThickness.TIP_MERGE) {
                BlockState blockState2 = world.getBlockState(pos.relative(direction2));
                return !isIcicleFacingDirection(blockState2, direction) ? DripstoneThickness.BASE : DripstoneThickness.MIDDLE;
            } else {
                return DripstoneThickness.FRUSTUM;
            }
        }
    }

    private static boolean isUnstable(BlockState state) {
        return state.is(FBlocks.ICICLE) && state.getValue(UNSTABLE);
    }

    private static boolean isPointingUp(BlockState state) {
        return isIcicleFacingDirection(state, Direction.UP);
    }

    private static boolean isPointingDown(BlockState state) {
        return isIcicleFacingDirection(state, Direction.DOWN);
    }

    private static boolean isIcicleFacingDirection(BlockState state, Direction direction) {
        return state.is(FBlocks.ICICLE) && state.getValue(VERTICAL_DIRECTION) == direction;
    }

    private static boolean isTip(BlockState state, Direction direction) {
        return isTip(state, false) && state.getValue(VERTICAL_DIRECTION) == direction;
    }

    private static boolean isTip(BlockState state, boolean allowMerged) {
        if (!state.is(FBlocks.ICICLE)) {
            return false;
        } else {
            DripstoneThickness thickness = state.getValue(THICKNESS);
            return thickness == DripstoneThickness.TIP || (allowMerged && thickness == DripstoneThickness.TIP_MERGE);
        }
    }

    private static void spawnFallingBlock(BlockState state, ServerLevel world, BlockPos pos) {
        BlockPos.MutableBlockPos current = pos.mutable();

        for (BlockState blockState = state; isPointingDown(blockState); blockState = world.getBlockState(current)) {
            FallingBlockEntity fallingBlockEntity = FallingBlockEntity.fall(world, current, blockState);
            fallingBlockEntity.dropItem = false;
            ((FallingBlockEntityAccessor) fallingBlockEntity).frostiful$setDestroyOnLanding(true);
            if (isTip(blockState, true)) {
                float fallHurtAmount = Math.max(1 + pos.getY() - current.getY(), 6);
                fallingBlockEntity.setHurtsEntities(fallHurtAmount, 40);
            }

            current.move(Direction.DOWN);
        }
    }

    private static boolean isHeldByIcicleFallable(BlockState state, LevelReader world, BlockPos pos) {
        return !(world.getBlockState(pos.above()).is(FBlockTags.ICICLE_GROWABLE)) || isHeldByIcicle(state, world, pos);
    }

    private static boolean isHeldByIcicle(BlockState state, LevelReader world, BlockPos pos) {
        return isPointingDown(state) && world.getBlockState(pos.above()).is(FBlocks.ICICLE);
    }

    private static void createUnstableParticle(Level world, BlockPos pos, BlockState state) {
        Vec3 vec3d = state.getOffset(pos);
        double xOffset = (double) pos.getX() + 0.5D + vec3d.x;
        double yOffset = (double) ((float) (pos.getY() + 1) - 0.6875F) - 0.0625D;
        double zOffset = (double) pos.getZ() + 0.5D + vec3d.z;
        world.addParticle(ParticleTypes.SNOWFLAKE, xOffset, yOffset, zOffset, 0.0D, 0.0D, 0.0D);
    }
}
