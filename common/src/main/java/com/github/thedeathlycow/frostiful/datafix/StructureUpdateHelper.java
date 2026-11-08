/*
 * Frostiful: A Vanilla+ Freezing Temperature Mod. Also try Scorchful!
 * Copyright (C) 2026	TheDeathlyCow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.  If not, see
 * <https://www.gnu.org/licenses/>.
 */

package com.github.thedeathlycow.frostiful.datafix;

import com.github.thedeathlycow.frostiful.Frostiful;
import dev.yumi.mc.core.api.YumiMods;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.data.structures.StructureUpdater;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public final class StructureUpdateHelper {
    // relative to the /run/structure_update dir
    private static final Path STRUCTURE_PATH = Paths.get("./structure");
    private static final Path OUT_PATH = Paths.get("./generated");

    public static void initialize() {
        if (YumiMods.get().isDevelopmentEnvironment() && System.getProperty("frostiful.update-structures") != null) {
            ServerLifecycleEvents.SERVER_STARTED.register(server -> {
                updateAllStructures();
                Frostiful.LOGGER.info("All structures updated! :)");
                server.halt(false);
            });
        }
    }

    private static void updateAllStructures() {
        if (!YumiMods.get().isDevelopmentEnvironment()) {
            throw new IllegalStateException("Structures may only be updated in a dev environment!");
        }

        try (Stream<Path> paths = Files.walk(STRUCTURE_PATH)) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".nbt"))
                    .forEach(StructureUpdateHelper::updateStructureFile);
        } catch (IOException e) {
            Frostiful.LOGGER.error("Unable to read structures", e);
        }
    }

    private static void updateStructureFile(Path path) {
        try (InputStream in = Files.newInputStream(path)) {
            CompoundTag oldNbt = NbtIo.readCompressed(in, NbtAccounter.unlimitedHeap());

            CompoundTag updatedNbt = StructureUpdater.update(path.toString(), oldNbt);

            Path outPath = OUT_PATH.resolve(path).normalize();
            Files.createDirectories(outPath.getParent());
            try (OutputStream out = Files.newOutputStream(outPath)) {
                NbtIo.writeCompressed(updatedNbt, out);
                Frostiful.LOGGER.info("Updated structure {}", path);
            }
        } catch (IOException e) {
            Frostiful.LOGGER.error("Failed to update structure file {}", path);
        }
    }

    private StructureUpdateHelper() {

    }
}