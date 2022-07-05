package com.github.thedeathlycow.frostiful.entity.ai.goal;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class PlayFightGoal<T extends PathAwareEntity> extends Goal {

    private static final TargetPredicate VALID_PLAYFIGHT_PREDICATE = TargetPredicate.createAttackable()
            .setBaseMaxDistance(8.0D)
            .ignoreVisibility();

    private static final int MAX_FIGHT_TIME = 30;

    protected final T mob;
    private final Class<T> type;
    @Nullable
    protected T target;
    @Nullable
    protected final Identifier furLootTable;
    private final float adultChance;
    private final float babyChance;
    private int timer;
    private boolean droppedFur = false;

    public PlayFightGoal(T mob, Class<T> type, float adultChance, float babyChance, @Nullable Identifier furLootTable) {
        this.mob = mob;
        this.type = type;
        this.adultChance = adultChance;
        this.babyChance = babyChance;
        this.target = null;
        this.timer = 0;
        this.furLootTable = furLootTable;
    }

    @Override
    public boolean canStart() {

        float chance = this.mob.isBaby() ? this.babyChance : this.adultChance;

        if (this.mob.getRandom().nextFloat() < chance) {
            this.target = this.findTarget();
            return this.target != null;
        } else {
            return false;
        }
    }

    @Override
    public boolean shouldContinue() {
        return this.target != null
                && this.target.isAlive()
                && this.timer < MAX_FIGHT_TIME;
    }

    @Override
    public void stop() {
        this.target = null;
        this.timer = 0;
        this.droppedFur = false;
    }

    @Override
    public void tick() {
        if (this.target == null) {
            return;
        }

        this.mob.getLookControl().lookAt(this.target, 30.0F, 30.0F);
        this.mob.getNavigation().startMovingTo(this.target, 1.0f);

        this.timer++;
        if (this.timer >= this.getTickCount(MAX_FIGHT_TIME) && this.mob.squaredDistanceTo(this.target) < 9.0D) {
            this.playFight();
        }
    }

    protected void playFight() {
        if (target != null) {
            this.mob.swingHand(Hand.MAIN_HAND);
            this.target.lookAtEntity(this.mob, 30f, 30f);
            this.target.swingHand(Hand.MAIN_HAND);

            if (this.timer == this.getTickCount(MAX_FIGHT_TIME)) {
                this.mob.damage(DamageSource.GENERIC, 0.0f);
                this.target.damage(DamageSource.GENERIC, 0.0f);
            }

            this.dropFur();
        }

    }

    protected void dropFur() {
        if (this.target == null || this.droppedFur) {
            return;
        }

        World world = this.target.getWorld();
        LootTable lootTable = Objects.requireNonNull(world.getServer())
                .getLootManager().getTable(this.furLootTable);
        LootContext.Builder builder = new LootContext.Builder((ServerWorld) world)
                .parameter(LootContextParameters.THIS_ENTITY, this.mob)
                .parameter(LootContextParameters.ORIGIN, this.mob.getPos())
                .random(this.target.getRandom());
        List<ItemStack> generatedItems = lootTable.generateLoot(builder.build(LootContextTypes.SELECTOR));
        Vec3d pos = this.target.getPos();
        for (ItemStack stack : generatedItems) {
            world.spawnEntity(new ItemEntity(world, pos.x, pos.y, pos.z, stack));
        }
        this.droppedFur = true;
    }

    /**
     * Finds the closest possible target to playfight with
     *
     * @return Returns a nullable pointer to the nearest path aware entity that this.mob can play
     * fight with.
     */
    @Nullable
    private T findTarget() {

        World world = this.mob.world;
        List<? extends T> candidates = world.getTargets(this.type, VALID_PLAYFIGHT_PREDICATE, this.mob, this.mob.getBoundingBox().expand(8.0));
        double closestEntityDistance = Double.POSITIVE_INFINITY;

        T target = null;
        for (T candidate : candidates) {
            double distance = this.mob.squaredAttackRange(candidate);
            if (this.canPlayfightWith(candidate) && distance < closestEntityDistance) {
                target = candidate;
                closestEntityDistance = distance;
            }
        }
        return target;
    }

    private boolean canPlayfightWith(PathAwareEntity candidate) {
        return candidate.getType() == this.mob.getType() && this.mob.isBaby() == candidate.isBaby();
    }
}


