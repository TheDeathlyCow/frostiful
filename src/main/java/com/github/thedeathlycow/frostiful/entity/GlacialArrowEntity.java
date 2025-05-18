package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import com.github.thedeathlycow.frostiful.registry.FItems;
import com.github.thedeathlycow.thermoo.api.temperature.HeatingModes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class GlacialArrowEntity extends PersistentProjectileEntity {

    private int freezeAmount = Frostiful.getConfig().icicleConfig.getFrostArrowFreezeAmount();

    private static final String FREEZE_AMOUNT_NBT_KEY = "freeze_amount";

    public GlacialArrowEntity(EntityType<? extends GlacialArrowEntity> entityType, World world) {
        super(entityType, world);
    }

    public GlacialArrowEntity(World world, double x, double y, double z, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(FEntityTypes.GLACIAL_ARROW, x, y, z, world, stack, shotFrom);
    }

    public GlacialArrowEntity(World world, LivingEntity owner, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(FEntityTypes.GLACIAL_ARROW, owner, world, stack, shotFrom);
    }

    @Override
    public boolean isFireImmune() {
        return true;
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return new ItemStack(FItems.GLACIAL_ARROW);
    }

    @Override
    public void tick() {
        super.tick();

        World world = getWorld();
        if (world.isClient && !this.isInGround()) {
            world.addParticleClient(
                    ParticleTypes.SNOWFLAKE,
                    this.getX(), this.getY(), this.getZ(),
                    0.0D, 0.0D, 0.0D
            );
        }
    }

    @Override
    protected void onHit(LivingEntity target) {
        super.onHit(target);
        target.thermoo$addTemperature(-freezeAmount, HeatingModes.ACTIVE);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.freezeAmount = nbt.getInt(FREEZE_AMOUNT_NBT_KEY, 0);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt(FREEZE_AMOUNT_NBT_KEY, this.freezeAmount);
    }

}
