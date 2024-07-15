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

public class FrostTippedArrowEntity extends PersistentProjectileEntity {

    private int freezeAmount = Frostiful.getConfig().icicleConfig.getFrostArrowFreezeAmount();

    private static final ItemStack DEFAULT_STACK = new ItemStack(FItems.FROST_TIPPED_ARROW);

    private static final String FREEZE_AMOUNT_NBT_KEY = "freeze_amount";

    public FrostTippedArrowEntity(EntityType<? extends FrostTippedArrowEntity> entityType, World world) {
        super(entityType, world);
    }

    public FrostTippedArrowEntity(World world, double x, double y, double z, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(FEntityTypes.FROST_TIPPED_ARROW, x, y, z, world, stack, shotFrom);
    }

    public FrostTippedArrowEntity(World world, LivingEntity owner, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(FEntityTypes.FROST_TIPPED_ARROW, owner, world, stack, shotFrom);
    }

    @Override
    public boolean isFireImmune() {
        return true;
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return new ItemStack(FItems.FROST_TIPPED_ARROW);
    }

    @Override
    public void tick() {
        super.tick();

        World world = getWorld();
        if (world.isClient && !this.inGround) {
            world.addParticle(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
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
        if (nbt.contains(FREEZE_AMOUNT_NBT_KEY, NbtElement.INT_TYPE)) {
            this.freezeAmount = nbt.getInt(FREEZE_AMOUNT_NBT_KEY);
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt(FREEZE_AMOUNT_NBT_KEY, this.freezeAmount);
    }

}
