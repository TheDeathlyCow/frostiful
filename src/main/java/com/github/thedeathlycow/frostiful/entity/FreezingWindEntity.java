package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.particle.WindParticleEffect;
import com.github.thedeathlycow.frostiful.tag.biome.FBiomeTags;
import com.github.thedeathlycow.frostiful.util.survival.FrostHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;

public class FreezingWindEntity extends Entity {

    private static final IntProvider LIFE_TICKS = UniformIntProvider.create(80, 140);

    private float windSpeed = 0.2f;

    private int lifeTicks;

    public FreezingWindEntity(EntityType<? extends FreezingWindEntity> type, World world) {
        super(type, world);
        this.setNoGravity(true);
        this.setLifeTicks(LIFE_TICKS.get(this.random));
    }

    public static void trySpawnFreezingWind(World world, WorldChunk chunk) {
        if (!Frostiful.getConfig().freezingConfig.doWindSpawning()) {
            return;
        }

        if (world.random.nextInt(world.isThundering() ? 50 : 100) != 0) {
            return;
        }

        ChunkPos chunkPos = chunk.getPos();
        BlockPos spawnPos = world.getTopPosition(
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                world.getRandomPosInChunk(chunkPos.getStartX(), 0, chunkPos.getStartZ(), 15)
        );

        boolean spawnInAir = world.random.nextBoolean();
        if (spawnInAir) {
            int diff = world.getTopY() - spawnPos.getY();
            int yoffset = (int)world.random.nextTriangular(diff, diff);
            spawnPos.up(yoffset);
        }

        var biome = world.getBiome(spawnPos);
        boolean canSpawnOnGround = (world.isRaining() && biome.isIn(FBiomeTags.FREEZING_WIND_SPAWNS_IN_STORMS))
                || biome.isIn(FBiomeTags.FREEZING_WIND_ALWAYS_SPAWNS);

        if (canSpawnOnGround || spawnInAir) {
            FreezingWindEntity wind = FEntityTypes.FREEZING_WIND.create(world);
            if (wind != null) {
                wind.setPosition(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
                world.spawnEntity(wind);
            }
        }

    }

    public boolean isFireImmune() {
        return true;
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

    @Override
    public void tick() {
        super.tick();

        if (this.isRemoved()) {
            return;
        }

        if (this.isOnFire()) {
            this.extinguish();
        }

        Vec3d velocity = Vec3d.ZERO.add(-this.windSpeed, 0, 0);

        if (this.horizontalCollision) {
            velocity = Vec3d.ZERO.add(0, this.windSpeed, 0);
        }
        if (this.verticalCollision) {
            velocity = Vec3d.ZERO.add(0, 0, this.windSpeed);
        }

        this.setVelocity(velocity);
        this.move(MovementType.SELF, this.getVelocity());

        if (!this.world.isClient && this.age % 5 == 0) {
            this.checkCollidingEntities();
        }

        if (this.world.isClient) {

            WindParticleEffect particle = this.random.nextBoolean()
                    ? new WindParticleEffect(true)
                    : new WindParticleEffect(false);

            for (int i = 0; i < 2; ++i) {
                this.world.addParticle(
                        particle,
                        this.getParticleX(0.5),
                        this.getRandomBodyY(),
                        this.getParticleZ(0.5),
                        -0.5, 0.0, 0.0
                );

                this.world.addParticle(
                        ParticleTypes.SNOWFLAKE,
                        this.getParticleX(1.0),
                        this.getRandomBodyY(),
                        this.getParticleZ(1.0),
                        -0.1, 0.1, 0.0
                );
            }
        }

        this.lifeTicks--;
        if (this.lifeTicks <= 0) {
            this.discard();
        }
    }

    @Override
    protected void initDataTracker() {

    }

    protected MoveEffect getMoveEffect() {
        return MoveEffect.NONE;
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.contains("WindSpeed")) {
            this.setWindSpeed(nbt.getFloat("WindSpeed"));
        }

        if (nbt.contains("LifeTicks")) {
            this.setLifeTicks(nbt.getInt("LifeTicks"));
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putFloat("WindSpeed", this.getWindSpeed());

        if (this.isAlive()) {
            nbt.putInt("LifeTicks", this.getLifeTicks());
        }
    }

    private void checkCollidingEntities() {
        int frost = Frostiful.getConfig().freezingConfig.getFreezingWindFrost();
        for (var victim : this.world.getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox())) {
            FrostHelper.addLivingFrost(victim, frost);
        }
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}
