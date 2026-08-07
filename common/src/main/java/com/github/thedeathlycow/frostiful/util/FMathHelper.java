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

package com.github.thedeathlycow.frostiful.util;

import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class FMathHelper {

    /**
     * Returns the mid point of two vectors.
     *
     * @param vec1 First vector
     * @param vec2 Second vector
     * @return Returns a new vector that represents the mid point between
     * the two vectors. If the vectors are equal, returns the first vector.
     */
    public static Vector3f getMidPoint(Vector3f vec1, Vector3f vec2) {
        return vec1.equals(vec2) ? vec1 : new Vector3f(
                (vec1.x() + vec2.x()) / 2,
                (vec1.y() + vec2.y()) / 2,
                (vec1.z() + vec2.z()) / 2
        );
    }

    /**
     * Returns the mid point of two vectors.
     *
     * @param vec1 First vector
     * @param vec2 Second vector
     * @return Returns a new vector that represents the mid point between
     * the two vectors. If the vectors are equal, returns the first vector.
     */
    public static Vec3i getMidPoint(Vec3i vec1, Vec3i vec2) {
        return vec1.equals(vec2) ? vec1 : new Vec3i(
                (vec1.getX() + vec2.getX()) / 2,
                (vec1.getY() + vec2.getY()) / 2,
                (vec1.getZ() + vec2.getZ()) / 2
        );
    }

    /**
     * Returns the mid point of two vectors.
     *
     * @param vec1 First vector
     * @param vec2 Second vector
     * @return Returns a new vector that represents the mid point between
     * the two vectors. If the vectors are equal, returns the first vector.
     */
    public static Vec3 getMidPoint(Vec3 vec1, Vec3 vec2) {
        return vec1.equals(vec2) ? vec1 : new Vec3(
                (vec1.x() + vec2.x()) / 2,
                (vec1.y() + vec2.y()) / 2,
                (vec1.z() + vec2.z()) / 2
        );
    }
}
