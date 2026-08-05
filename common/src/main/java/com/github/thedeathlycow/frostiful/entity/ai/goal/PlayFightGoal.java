package com.github.thedeathlycow.frostiful.entity.ai.goal;

import com.github.thedeathlycow.frostiful.registry.FDataAttachments;
import com.github.thedeathlycow.frostiful.survival.system.BrushSystem;
import com.github.thedeathlycow.frostiful.util.FLootHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PlayFightGoal<T extends PathfinderMob> extends Goal {

    private static final TargetingConditions VALID_PLAYFIGHT_PREDICATE = TargetingConditions.forCombat()
            .range(8.0D)
            .ignoreLineOfSight();

    private static final int MAX_FIGHT_TIME = 30;

    protected final T mob;
    private final Class<T> type;
    @Nullable
    protected T target;
    @Nullable
    protected final ResourceKey<LootTable> furLootTable;
    private final float adultChance;
    private final float babyChance;
    private int timer;
    private boolean droppedFur = false;

    public PlayFightGoal(T mob, Class<T> type, float adultChance, float babyChance, @Nullable ResourceKey<LootTable> furLootTable) {
        this.mob = mob;
        this.type = type;
        this.adultChance = adultChance;
        this.babyChance = babyChance;
        this.target = null;
        this.timer = 0;
        this.furLootTable = furLootTable;
    }

    @Override
    public boolean canUse() {

        float chance = this.mob.isBaby() ? this.babyChance : this.adultChance;

        if (this.mob.getRandom().nextFloat() < chance) {
            this.target = this.findTarget();
            return this.target != null;
        } else {
            return false;
        }
    }

    @Override
    public boolean canContinueToUse() {
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

        this.mob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
        this.mob.getNavigation().moveTo(this.target, 1.0f);

        this.timer++;
        if (this.timer >= this.adjustedTickDelay(MAX_FIGHT_TIME) && this.mob.distanceToSqr(this.target) < 9.0D) {
            this.playFight();
        }
    }

    protected void playFight() {
        if (target != null) {
            this.mob.swing(InteractionHand.MAIN_HAND);
            this.target.lookAt(this.mob, 30f, 30f);
            this.target.swing(InteractionHand.MAIN_HAND);

            if (this.timer == this.adjustedTickDelay(MAX_FIGHT_TIME)) {
                ServerLevel world = getServerLevel(this.mob);
                this.mob.hurtServer(world, this.mob.damageSources().generic(), 0.0f);
                this.target.hurtServer(world, this.target.damageSources().generic(), 0.0f);
            }

            this.dropFur();
        }

    }

    protected void dropFur() {
        if (this.target == null || this.droppedFur) {
            return;
        }

        long lastBrushTime = this.mob.getAttachedOrCreate(FDataAttachments.LAST_BRUSH_TIME);
        if (!BrushSystem.wasBrushed(this.mob, lastBrushTime)) {
            FLootHelper.dropPlayfightLoot(this.mob, this.furLootTable);
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
        ServerLevel world = getServerLevel(this.mob);
        List<? extends T> candidates = world.getNearbyEntities(this.type, VALID_PLAYFIGHT_PREDICATE, this.mob, this.mob.getBoundingBox().inflate(8.0));
        double closestEntityDistance = Double.POSITIVE_INFINITY;

        T closestTargetSoFar = null;
        for (T candidate : candidates) {
            double distance = this.mob.distanceToSqr(candidate);
            if (this.canPlayFightWith(candidate) && distance < closestEntityDistance) {
                closestTargetSoFar = candidate;
                closestEntityDistance = distance;
            }
        }
        return closestTargetSoFar;
    }

    private boolean canPlayFightWith(PathfinderMob candidate) {
        return candidate.getType() == this.mob.getType() && this.mob.isBaby() == candidate.isBaby();
    }
}


