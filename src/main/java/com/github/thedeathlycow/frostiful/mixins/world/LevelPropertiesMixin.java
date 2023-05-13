package com.github.thedeathlycow.frostiful.mixins.world;

import com.github.thedeathlycow.frostiful.world.spawner.WindSpawner;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.level.LevelInfo;
import net.minecraft.world.level.LevelProperties;
import net.minecraft.world.level.storage.SaveVersionInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LevelProperties.class)
public class LevelPropertiesMixin {

    @Inject(
            method = "updateProperties",
            at = @At("TAIL")
    )
    private void saveWindSpawnCount(DynamicRegistryManager registryManager, NbtCompound levelNbt, NbtCompound playerNbt, CallbackInfo ci) {

        NbtCompound frostiful = new NbtCompound();

        frostiful.putInt("WindSpawnCount", WindSpawner.INSTANCE.getWindSpawnCount());

        levelNbt.put("Frostiful", frostiful);
    }

    @Inject(
            method = "readProperties",
            at = @At("HEAD")
    )
    private static void loadWindSpawnCount(Dynamic<NbtElement> dynamic, DataFixer dataFixer, int dataVersion, NbtCompound playerData, LevelInfo levelInfo, SaveVersionInfo saveVersionInfo, GeneratorOptions generatorOptions, Lifecycle lifecycle, CallbackInfoReturnable<LevelProperties> cir) {

        NbtElement nbt = dynamic.get("Frostiful").result().map(Dynamic::getValue).orElse(null);

        if (!(nbt instanceof NbtCompound frostiful)) {
            return;
        }

        if (frostiful.contains("WindSpawnCount", NbtElement.INT_TYPE)) {
            WindSpawner.INSTANCE.setWindSpawnCount(frostiful.getInt("WindSpawnCount"));
        }

    }

}
