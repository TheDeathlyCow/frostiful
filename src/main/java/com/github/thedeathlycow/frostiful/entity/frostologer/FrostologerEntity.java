package com.github.thedeathlycow.frostiful.entity.frostologer;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.block.FrozenTorchBlock;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.entity.BiterEntity;
import com.github.thedeathlycow.frostiful.entity.ThrownIcicleEntity;
import com.github.thedeathlycow.frostiful.item.FrostWandItem;
import com.github.thedeathlycow.frostiful.item.enchantment.HeatDrainEnchantmentEffect;
import com.github.thedeathlycow.frostiful.registry.*;
import com.github.thedeathlycow.frostiful.registry.tag.FBlockTags;
import com.github.thedeathlycow.frostiful.registry.tag.FDamageTypeTags;
import com.github.thedeathlycow.frostiful.survival.PassiveTemperatureEffects;
import com.github.thedeathlycow.thermoo.api.ThermooAttributes;
import com.github.thedeathlycow.thermoo.api.temperature.HeatingModes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.illager.AbstractIllager;
import net.minecraft.world.entity.monster.illager.SpellcasterIllager;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.BaseTorchBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ThreadLocalRandom;

/**
 * By remapping {@link SpellcasterIllager.IllagerArmPose}s, the Frostologer has the following spells:
 * <p>
 * SUMMON_VEX = SUMMON_MINIONS
 * DISAPPEAR = DESTROY_HEAT_SOURCES
 */
public class FrostologerEntity extends SpellcasterIllager implements RangedAttackMob {

    static final EntityDataAccessor<Boolean> IS_USING_FROST_WAND = SynchedEntityData.defineId(
            FrostologerEntity.class, EntityDataSerializers.BOOLEAN
    );

    public static final float MAX_POWER_SCALE_START = -0.75f;
    private static final int NUM_POWER_PARTICLES = 2;
    private static final float START_PLACING_SNOW_TEMP = -0.75f;


    public float prevStrideDistance;
    public float strideDistance;
    public double prevCapeX;
    public double prevCapeY;
    public double prevCapeZ;
    public double capeX;
    public double capeY;
    public double capeZ;
    private boolean isChanneling = false;

    private final BlockPos[] stepPositionsPool = new BlockPos[2];

    public FrostologerEntity(EntityType<? extends FrostologerEntity> entityType, Level world) {
        super(entityType, world);
        this.xpReward = 20;
    }

    public static AttributeSupplier.Builder createFrostologerAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.FOLLOW_RANGE, 32.0)
                .add(Attributes.MAX_HEALTH, 150.0)
                .add(ThermooAttributes.MIN_TEMPERATURE, 45.0)
                .add(ThermooAttributes.MAX_TEMPERATURE, 0.0)
                .add(FEntityAttributes.ICE_BREAK_DAMAGE, 5.0);
    }

    public boolean isAtMaxPower() {
        return this.thermoo$getTemperatureScale() <= MAX_POWER_SCALE_START;
    }

    /**
     * 'Destroys' the heat source at the given `blockPos` by transforming `state` into another block according to the following rules:
     * <ul>
     * <li> protected block -> do nothing </li>
     * <li> full cubes -> ice </li>
     * <li> lava (level 8) -> obsidian </li>
     * <li> torches -> frozen torch </li>
     * <li> waterlogged blocks -> ice </li>
     * <li> everything else -> air </li>
     * </ul>
     *
     * @param world    The server world
     * @param state    The state to transform
     * @param blockPos The position of `state` in `world`.
     */
    public void destroyHeatSource(ServerLevel world, BlockState state, BlockPos blockPos) {
        BlockState frozenState;
        Block heatedBlock = state.getBlock();
        FluidState fluidState = state.getFluidState();

        if (state.is(FBlockTags.FROSTOLOGER_CANNOT_FREEZE)) {
            frozenState = state;
        } else if (blockPos.equals(this.blockPosition())) {
            frozenState = Blocks.AIR.defaultBlockState();
        } else if (state.is(FBlockTags.HOT_FLOOR)) {
            frozenState = Blocks.COBBLESTONE.defaultBlockState();
        } else if (state.isCollisionShapeFullBlock(world, blockPos)) {
            frozenState = Blocks.ICE.defaultBlockState();
        } else if (fluidState.is(Fluids.LAVA) && fluidState.getAmount() == 8) {
            frozenState = Blocks.OBSIDIAN.defaultBlockState();
        } else if (heatedBlock instanceof BaseTorchBlock) {
            BlockState torch = FrozenTorchBlock.freezeTorch(state);
            frozenState = torch != null ? torch : Blocks.AIR.defaultBlockState();
        } else {
            frozenState = Blocks.AIR.defaultBlockState();
        }

        if (!frozenState.isAir()) {
            world.setBlockAndUpdate(blockPos, frozenState);
        } else {
            world.destroyBlock(blockPos, true);
        }

        world.playSound(
                null,
                blockPos,
                SoundEvents.FIRE_EXTINGUISH,
                SoundSource.HOSTILE,
                0.5f, 1.0f + ((this.random.nextFloat() % 0.2f) - 0.1f)
        );

        Vec3 centeredPos = Vec3.atCenterOf(blockPos);

        world.sendParticles(
                ParticleTypes.SMOKE,
                centeredPos.x, centeredPos.y, centeredPos.z,
                12,
                0.1, 1, 0.1,
                0.1
        );
    }

    @Override
    public boolean isInvulnerableTo(ServerLevel world, DamageSource damageSource) {
        if (damageSource.is(DamageTypeTags.IS_PROJECTILE) && this.isChanneling()) {
            return true;
        }

        return damageSource.is(DamageTypeTags.IS_FREEZING)
                || damageSource.is(FDamageTypeTags.IS_ICICLE)
                || super.isInvulnerableTo(world, damageSource);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SpellcasterIllager.SpellcasterCastingSpellGoal());

        this.goalSelector.addGoal(2, new FrostWandCastGoal(this, 1.0, 40, 10f));

        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, IronGolem.class, 8.0F, 1.2, 1.5));

        this.goalSelector.addGoal(3, new IcicleAttackGoal(UniformInt.of(20, 30), UniformInt.of(10, 15)));
        this.goalSelector.addGoal(4, new FrostWandAttackGoal(this));

        this.goalSelector.addGoal(6, new DestroyHeatSourcesGoal(15));

        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));

        this.targetSelector.addGoal(
                1,
                new HurtByTargetGoal(this, Raider.class)
                        .setAlertOthers()
        );
        this.targetSelector.addGoal(
                2,
                new NearestAttackableTargetGoal<>(this, Player.class, true)
                        .setUnseenMemoryTicks(300)
        );
        this.targetSelector.addGoal(
                3,
                new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false)
                        .setUnseenMemoryTicks(300)
        );
        this.targetSelector.addGoal(
                3,
                new NearestAttackableTargetGoal<>(this, IronGolem.class, false)
        );
    }

    @Override
    public boolean fireImmune() {
        return this.isChanneling() || super.fireImmune();
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(
            ServerLevelAccessor world,
            DifficultyInstance difficulty,
            EntitySpawnReason spawnReason,
            @Nullable SpawnGroupData entityData
    ) {
        this.populateDefaultEquipmentSlots(world.getRandom(), difficulty);
        this.populateDefaultEquipmentEnchantments(world, random, difficulty);
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
    }

    @Override
    protected void enchantSpawnedWeapon(ServerLevelAccessor world, RandomSource random, DifficultyInstance localDifficulty) {
        ItemStack stack = this.getItemBySlot(EquipmentSlot.MAINHAND);
        if (!stack.isEmpty()) {
            EnchantmentHelper.enchantItemFromProvider(
                    stack,
                    world.registryAccess(),
                    FEnchantmentProviders.FROSTOLOGER_SPAWN_FROST_WAND,
                    localDifficulty,
                    random
            );
            this.setItemSlot(EquipmentSlot.MAINHAND, stack);
        }
    }

    @Override
    public void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        this.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(FItems.FROST_WAND));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(FItems.FROSTOLOGY_CLOAK));

        // equipment drops handled with loot table
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0f);
        this.setDropChance(EquipmentSlot.CHEST, 0.0f);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(IS_USING_FROST_WAND, false);
    }

    @Override
    public void tick() {
        super.tick();

        this.updateCapeAngles();

        Level world = this.level();
        if (world.isClientSide() && this.isAtMaxPower()) {
            this.spawnPowerParticles();
        }

        if (!world.isClientSide() && this.isOnFire()) {
            int fireTicks = this.getRemainingFireTicks();
            this.setRemainingFireTicks(Math.min(10, fireTicks));
        }
    }

    @Override
    public boolean hurtServer(ServerLevel world, DamageSource source, float amount) {
        if (source.is(DamageTypeTags.IS_FIRE)) {
            FrostifulConfig config = Frostiful.getConfig();
            amount *= config.combatConfig.getFrostologerFireDamageMultiplier();
        }

        return super.hurtServer(world, source, amount);
    }

    @Override
    public void rideTick() {
        super.rideTick();
        this.prevStrideDistance = this.strideDistance;
        this.strideDistance = 0.0F;
    }

    @Override
    public void aiStep() {
        super.aiStep();

        this.prevStrideDistance = this.strideDistance;

        float walkSpeed;
        if (this.onGround() && !this.isDeadOrDying() && !this.isSwimming()) {
            walkSpeed = Math.min(0.1F, (float) this.getDeltaMovement().horizontalDistance());
        } else {
            walkSpeed = 0.0F;
        }
        this.strideDistance += (walkSpeed - this.strideDistance) * 0.4f;

        Level world = this.level();
        if (world.isClientSide()) {
            // dont place snow if client
            return;
        }
        ServerLevel serverWorld = (ServerLevel) world; // covered by isClient check above

        // do not place snow/destroy heat sources unless mobGriefing is on
        if (!serverWorld.getGameRules().get(GameRules.MOB_GRIEFING)) {
            return;
        }

        BlockPos frostologerPos = this.blockPosition();
        BlockState snow = Blocks.SNOW.defaultBlockState();

        boolean canPlaceSnow;
        stepPositionsPool[0] = frostologerPos;
        stepPositionsPool[1] = frostologerPos.below();
        for (BlockPos blockPos : stepPositionsPool) {
            BlockState blockState = world.getBlockState(blockPos);
            if (PassiveTemperatureEffects.getBlockLightTemperatureChange(world, blockPos) > 0) {
                this.destroyHeatSource(serverWorld, blockState, blockPos);
            }

            canPlaceSnow = blockState.isAir()
                    && this.thermoo$getTemperatureScale() <= START_PLACING_SNOW_TEMP
                    && snow.canSurvive(world, blockPos);
            if (canPlaceSnow) {
                world.setBlockAndUpdate(blockPos, snow);
                world.gameEvent(
                        GameEvent.BLOCK_PLACE,
                        blockPos,
                        GameEvent.Context.of(this, blockState)
                );
            }
        }
    }

    @Environment(EnvType.CLIENT)
    private void spawnPowerParticles() {

        ThreadLocalRandom random = ThreadLocalRandom.current();

        AABB box = this.getBoundingBox();

        for (int i = 0; i < NUM_POWER_PARTICLES; i++) {
            // pick random pos in bounding box
            double x = box.min(Direction.Axis.X) + random.nextDouble(box.getXsize());
            double y = box.min(Direction.Axis.Y) + random.nextDouble(box.getYsize());
            double z = box.min(Direction.Axis.Z) + random.nextDouble(box.getZsize());

            this.level().addParticle(
                    ParticleTypes.SNOWFLAKE,
                    x, y, z,
                    0, 0, 0
            );
        }
    }

    @Override
    public AbstractIllager.IllagerArmPose getArmPose() {
        if (this.isCastingSpell()) {
            return IllagerArmPose.SPELLCASTING;
        } else {
            return this.isCelebrating() ? IllagerArmPose.CELEBRATING : IllagerArmPose.NEUTRAL;
        }
    }

    @Override
    public void performRangedAttack(LivingEntity target, float pullProgress) {
        if (this.useItem.is(FItems.FROST_WAND)) {
            this.getLookControl().setLookAt(target);
            FrostWandItem.fireFrostSpell(this.useItem.copy(), this.level(), this);
        }
    }

    public boolean hasTarget() {
        LivingEntity target = this.getTarget();
        return target != null && target.isAlive();
    }

    public boolean isTargetPlayer() {
        LivingEntity target = this.getTarget();
        return target != null && target.isAlive() && target.isAlwaysTicking();
    }

    public boolean isTargetRooted() {
        LivingEntity target = this.getTarget();
        return target != null
                && FComponents.FROST_WAND_ROOT_COMPONENT.get(target).isRooted();
    }

    public boolean isUsingFrostWand() {
        return this.entityData.get(IS_USING_FROST_WAND);
    }

    @Override
    public void applyRaidBuffs(ServerLevel world, int wave, boolean unused) {

    }

    private void updateCapeAngles() {
        this.prevCapeX = this.capeX;
        this.prevCapeY = this.capeY;
        this.prevCapeZ = this.capeZ;
        double dx = this.getX() - this.capeX;
        double dy = this.getY() - this.capeY;
        double dz = this.getZ() - this.capeZ;
        double threshold = 10.0;
        if (dx > threshold) {
            this.capeX = this.getX();
            this.prevCapeX = this.capeX;
        }

        if (dz > threshold) {
            this.capeZ = this.getZ();
            this.prevCapeZ = this.capeZ;
        }

        if (dy > threshold) {
            this.capeY = this.getY();
            this.prevCapeY = this.capeY;
        }

        if (dx < -threshold) {
            this.capeX = this.getX();
            this.prevCapeX = this.capeX;
        }

        if (dz < -threshold) {
            this.capeZ = this.getZ();
            this.prevCapeZ = this.capeZ;
        }

        if (dy < -threshold) {
            this.capeY = this.getY();
            this.prevCapeY = this.capeY;
        }

        this.capeX += dx / 4;
        this.capeZ += dz / 4;
        this.capeY += dy / 4;
    }

    @Override
    public boolean considersEntityAsAlly(@Nullable Entity other) {
        if (other == this) {
            return true;
        } else if (super.considersEntityAsAlly(other)) {
            return true;
        } else {
            if (other instanceof BiterEntity biter && biter.getOwner() != null) {
                return this.considersEntityAsAlly(biter.getOwner());
            }
            return false;
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return FSoundEvents.ENTITY_FROSTOLOGER_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FSoundEvents.ENTITY_FROSTOLOGER_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FSoundEvents.ENTITY_FROSTOLOGER_HURT;
    }

    @Override
    public SoundEvent getCelebrateSound() {
        return FSoundEvents.ENTITY_FROSTOLOGER_CELEBRATE;
    }

    @Override
    protected SoundEvent getCastingSoundEvent() {
        return FSoundEvents.ENTITY_FROSTOLOGER_CAST_SPELL;
    }

    public boolean isChanneling() {
        return isChanneling;
    }

    @Override
    public void readAdditionalSaveData(ValueInput readView) {
        super.readAdditionalSaveData(readView);
        this.entityData.set(IS_USING_FROST_WAND, readView.getBooleanOr("IsUsingFrostWand", false));
    }

    @Override
    public void addAdditionalSaveData(ValueOutput writeView) {
        super.addAdditionalSaveData(writeView);
        writeView.putBoolean("IsUsingFrostWand", this.entityData.get(IS_USING_FROST_WAND));
    }

    protected class DestroyHeatSourcesGoal extends SpellcasterIllager.SpellcasterUseSpellGoal {

        private final int range;

        protected DestroyHeatSourcesGoal(int range) {
            super();
            this.range = range;
        }

        @Override
        public void start() {
            super.start();
            FrostologerEntity.this.isChanneling = true;
        }

        @Override
        public boolean canUse() {
            FrostologerEntity frostologer = FrostologerEntity.this;
            return super.canUse() && frostologer.thermoo$getTemperatureScale() <= -0.9f;
        }

        @Override
        public void tick() {
            FrostologerEntity frostologer = FrostologerEntity.this;

            AABB box = frostologer.getBoundingBox().inflate(this.range);

            Level world = frostologer.level();

            int heatDrain = Frostiful.getConfig().combatConfig.getFrostologerHeatDrainPerTick();
            frostologer.thermoo$addTemperature(heatDrain);

            for (LivingEntity victim : world.getEntitiesOfClass(LivingEntity.class, box, entity -> entity != frostologer)) {
                victim.thermoo$addTemperature(-heatDrain, HeatingModes.ACTIVE);

                if (world instanceof ServerLevel serverWorld) {
                    HeatDrainEnchantmentEffect.addHeatDrainParticles(serverWorld, victim, frostologer, 5, 0.08);
                }
            }

            super.tick();
        }

        @Override
        protected void performSpellCasting() {
            ServerLevel world = getServerLevel(level());
            if (!world.getGameRules().get(GameRules.MOB_GRIEFING)) {
                return;
            }

            BlockPos origin = FrostologerEntity.this.blockPosition();
            Vec3i distance = new Vec3i(this.range, this.range, this.range);

            for (BlockPos pos : BlockPos.betweenClosed(origin.subtract(distance), origin.offset(distance))) {
                if (world instanceof ServerLevel serverWorld && PassiveTemperatureEffects.getBlockLightTemperatureChange(world, pos) > 0) {
                    FrostologerEntity.this.destroyHeatSource(serverWorld, world.getBlockState(pos), pos);
                }
            }

            FrostologerEntity.this.isChanneling = false;
        }

        @Override
        protected int getCastWarmupTime() {
            return 60;
        }

        @Override
        protected int getCastingTime() {
            return 60;
        }

        @Override
        protected int getCastingInterval() {
            return 140;
        }

        @Nullable
        @Override
        protected SoundEvent getSpellPrepareSound() {
            return FSoundEvents.ENTITY_FROSTOLOGER_PREPARE_CAST_BLIZZARD;
        }

        @Override
        protected IllagerSpell getSpell() {
            return IllagerSpell.DISAPPEAR;
        }

    }

    protected class IcicleAttackGoal extends SpellcasterIllager.SpellcasterUseSpellGoal {

        private final IntProvider numIciclesProvider;

        private final IntProvider cooldownProvider;

        private int nextStartTime = -1;

        public IcicleAttackGoal(IntProvider numIciclesProvider, IntProvider cooldownProvider) {
            this.numIciclesProvider = numIciclesProvider;
            this.cooldownProvider = cooldownProvider;
        }

        @Override
        public void start() {
            super.start();
            if (FrostologerEntity.this.isOnFire()) {
                FrostologerEntity.this.clearFire();
                FrostologerEntity.this.playEntityOnFireExtinguishedSound();
            }
        }

        @Override
        public boolean canUse() {
            if (FrostologerEntity.this.tickCount <= nextStartTime) {
                return false;
            } else if (!super.canUse()) {
                return false;
            } else {
                return FrostologerEntity.this.isTargetRooted();
            }
        }

        @Override
        protected void performSpellCasting() {
            ServerLevel serverWorld = (ServerLevel) level();

            int numIcicles = this.numIciclesProvider.sample(random);
            nextStartTime = FrostologerEntity.this.tickCount + cooldownProvider.sample(random) * 20;
            for (int i = 0; i < numIcicles; ++i) {
                BlockPos blockPos = FrostologerEntity.this.blockPosition()
                        .offset(
                                -2 + FrostologerEntity.this.random.nextInt(5),
                                2,
                                -2 + FrostologerEntity.this.random.nextInt(5)
                        );

                ThrownIcicleEntity icicle = FEntityTypes.THROWN_ICICLE.create(serverWorld, EntitySpawnReason.SPAWN_ITEM_USE);

                if (icicle == null) {
                    return;
                }

                icicle.snapTo(blockPos, 0.0F, 0.0F);
                icicle.setOwner(FrostologerEntity.this);

                icicle.shootFromRotation(
                        FrostologerEntity.this,
                        FrostologerEntity.this.getXRot() + FrostologerEntity.this.random.nextFloat(),
                        FrostologerEntity.this.getYHeadRot() + FrostologerEntity.this.random.nextFloat(),
                        0.0f, 3.0f, 1.0f
                );

                serverWorld.addFreshEntityWithPassengers(icicle);
            }
        }

        @Override
        protected int getCastingTime() {
            return 20;
        }

        @Override
        protected int getCastingInterval() {
            return 20;
        }

        @Nullable
        @Override
        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.EVOKER_PREPARE_SUMMON;
        }

        @Override
        protected IllagerSpell getSpell() {
            return IllagerSpell.SUMMON_VEX;
        }
    }
}
