package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import com.github.thedeathlycow.frostiful.registry.FItems;
import com.github.thedeathlycow.thermoo.api.temperature.HeatingModes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class GlacialArrowEntity extends AbstractArrow {

    private int freezeAmount = Frostiful.getConfig().icicleConfig.getFrostArrowFreezeAmount();

    private static final String FREEZE_AMOUNT_NBT_KEY = "freeze_amount";

    public GlacialArrowEntity(EntityType<? extends GlacialArrowEntity> entityType, Level world) {
        super(entityType, world);
    }

    public GlacialArrowEntity(Level world, double x, double y, double z, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(FEntityTypes.GLACIAL_ARROW, x, y, z, world, stack, shotFrom);
    }

    public GlacialArrowEntity(Level world, LivingEntity owner, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(FEntityTypes.GLACIAL_ARROW, owner, world, stack, shotFrom);
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(FItems.GLACIAL_ARROW);
    }

    @Override
    public void tick() {
        super.tick();

        Level world = level();
        if (world.isClientSide && !this.inGround) {
            world.addParticle(
                    ParticleTypes.SNOWFLAKE,
                    this.getX(), this.getY(), this.getZ(),
                    0.0D, 0.0D, 0.0D
            );
        }
    }

    @Override
    protected void doPostHurtEffects(LivingEntity target) {
        super.doPostHurtEffects(target);
        target.thermoo$addTemperature(-freezeAmount, HeatingModes.ACTIVE);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        if (nbt.contains(FREEZE_AMOUNT_NBT_KEY, Tag.TAG_INT)) {
            this.freezeAmount = nbt.getInt(FREEZE_AMOUNT_NBT_KEY);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt(FREEZE_AMOUNT_NBT_KEY, this.freezeAmount);
    }

}
