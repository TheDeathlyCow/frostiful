package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.particle.WindParticleEffect;
import com.github.thedeathlycow.frostiful.registry.FSoundEvents;
import com.github.thedeathlycow.frostiful.registry.tag.FEntityTypeTags;
import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

public class WindEntity extends Entity {

    private static final IntProvider LIFE_TICKS_PROVIDER = UniformInt.of(80, 140);
    public static final Vec3 REGULAR_PUSH = new Vec3(-0.16, 0, 0);
    public static final Vec3 ELYTRA_PUSH = new Vec3(-1.75, 0, 0);

    public static final Predicate<Entity> CAN_BE_BLOWN = EntitySelector.NO_SPECTATORS
            .and(EntitySelector.ENTITY_STILL_ALIVE)
            .and(entity -> !entity.getType().is(FEntityTypeTags.HEAVY_ENTITY_TYPES));

    private float windSpeed = 1.0f;

    private int lifeTicks;

    private final int moveTickOffset;

    public WindEntity(EntityType<? extends WindEntity> type, Level world) {
        super(type, world);
        this.setNoGravity(true);
        this.setLifeTicks(LIFE_TICKS_PROVIDER.sample(this.random));
        this.moveTickOffset = world.random.nextIntBetweenInclusive(1, 10) - 1;
    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }

//    @Override
//    public void baseTick() {
//        World world = getWorld();
//        Profiler profiler = world.getProfiler();
//        profiler.push("entityBaseTick");
//
//        this.attemptTickInVoid();
//
//        this.prevHorizontalSpeed = this.horizontalSpeed;
//        this.prevPitch = this.getPitch();
//        this.prevYaw = this.getYaw();
//
//        this.firstUpdate = false;
//        profiler.pop();
//    }

    @Override
    public void tick() {
        this.baseTick();

        if (this.isRemoved()) {
            return;
        }

        Level world = level();
        var profiler = Profiler.get();
        profiler.push("windTick");

        if (this.isOnFire()) {
            this.clearFire();
        }

        if (this.tickCount % 10 == moveTickOffset) {

            Vec3 velocity;
            if (this.verticalCollision) {
                velocity = Vec3.ZERO.add(0, 0, this.windSpeed * 0.5f);
            } else if (this.horizontalCollision) {
                velocity = Vec3.ZERO.add(0, this.windSpeed * 0.5f, 0);
            } else {
                velocity = Vec3.ZERO.add(-this.windSpeed, 0, 0);
            }

            this.setDeltaMovement(velocity);
            this.move(MoverType.SELF, this.getDeltaMovement());
        }


        if (!world.isClientSide()) {
            if (this.tickCount % 30 == 0) {
                this.playSound(FSoundEvents.ENTITY_WIND_BLOW, 0.75f, 0.9f + this.random.nextFloat() / 3);
            }

            if (this.tickCount % 5 == 0) {
                profiler.push("windCollision");
                this.checkCollidingEntities();
                profiler.pop();
            }
        } else {

            WindParticleEffect particle = this.random.nextBoolean()
                    ? new WindParticleEffect(true)
                    : new WindParticleEffect(false);

            ParticleOptions dust = this.getDustParticle();

            for (int i = 0; i < 2; ++i) {
                world.addParticle(
                        particle,
                        this.getRandomX(0.5),
                        this.getRandomY(),
                        this.getRandomZ(0.5),
                        -0.5, 0.0, 0.0
                );

                world.addParticle(
                        dust,
                        this.getRandomX(1.0),
                        this.getRandomY(),
                        this.getRandomZ(1.0),
                        -0.1, 0.1, 0.0
                );
            }
        }

        this.lifeTicks--;
        if (this.lifeTicks <= 0) {
            this.dissipate();
        }

        profiler.pop();
    }

    public boolean startRiding(Entity entity, boolean force) {
        return false;
    }

    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean hurtServer(ServerLevel world, DamageSource source, float amount) {
        return false;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float speed) {
        this.windSpeed = speed;
    }

    public int getLifeTicks() {
        return this.lifeTicks;
    }

    public void setLifeTicks(int lifeTicks) {
        this.lifeTicks = lifeTicks;
    }

    protected void dissipate() {
        this.playSound(FSoundEvents.ENTITY_WIND_WOOSH, 1.0f, 1.0f);
        Level world = level();
        if (world.isClientSide()) {
            ParticleOptions particle = this.getDustParticle();
            for (int i = 0; i < 20; ++i) {
                double vx = this.random.nextGaussian() * 0.02;
                double vy = this.random.nextGaussian() * 0.02;
                double vz = this.random.nextGaussian() * 0.02;
                world.addParticle(
                        particle,
                        this.getRandomX(1.0), this.getRandomY(), this.getRandomZ(1.0),
                        vx, vy, vz
                );
            }
        }
        this.discard();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    public void onEntityCollision(LivingEntity entity) {
        pushEntity(entity, level(), this.position(), 1);
    }

    public static void pushEntity(LivingEntity entity, Level world, Vec3 pos, double scale) {
        Vec3 push = entity.isFallFlying() ? ELYTRA_PUSH : REGULAR_PUSH;
        scale *= 1.0 - entity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);

        entity.push(push.x * scale, push.y * scale, push.z * scale);
        entity.hurtMarked = true;
        if (!world.isClientSide() && entity instanceof ServerPlayer serverPlayer) {
            serverPlayer.connection
                    .send(new ClientboundSoundPacket(
                            Holder.direct(FSoundEvents.ENTITY_WIND_HOWL),
                            SoundSource.WEATHER,
                            pos.x, pos.y, pos.z,
                            1.0f, 1.0f,
                            world.getRandom().nextLong()
                    ));
        }
    }

    protected ParticleOptions getDustParticle() {
        return ParticleTypes.POOF;
    }

    protected MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }

    private void checkCollidingEntities() {
        this.level().getEntitiesOfClass(
                        LivingEntity.class,
                        this.getBoundingBox(),
                        CAN_BE_BLOWN
                )
                .forEach(this::onEntityCollision);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput readView) {
        this.setWindSpeed(readView.getFloatOr("WindSpeed", 1.0f));
        this.setLifeTicks(readView.getIntOr("LifeTicks", 80));
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput writeView) {
        writeView.putFloat("WindSpeed", this.getWindSpeed());

        if (this.isAlive()) {
            writeView.putInt("LifeTicks", this.getLifeTicks());
        }
    }
}
