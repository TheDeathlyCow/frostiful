package com.github.thedeathlycow.frostiful.util;

import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.math.Vec3i;

public class FrostifulMathHelper {

    /**
     * Returns the mid point of two vectors.
     *
     * @param vec1 First vector
     * @param vec2 Second vector
     * @return Returns a new vector that represents the mid point between
     * the two vectors. If the vectors are equal, returns the first vector.
     */
    public static Vec3f getMidPoint(Vec3f vec1, Vec3f vec2) {
        return vec1.equals(vec2) ? vec1 : new Vec3f(
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
    public static Vec3d getMidPoint(Vec3d vec1, Vec3d vec2) {
        return vec1.equals(vec2) ? vec1 : new Vec3d(
                (vec1.getX() + vec2.getX()) / 2,
                (vec1.getY() + vec2.getY()) / 2,
                (vec1.getZ() + vec2.getZ()) / 2
        );
    }
}
