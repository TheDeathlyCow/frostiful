package com.github.thedeathlycow.frostiful.util;

import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
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
    public static Vec3d getMidPoint(Vec3d vec1, Vec3d vec2) {
        return vec1.equals(vec2) ? vec1 : new Vec3d(
                (vec1.getX() + vec2.getX()) / 2,
                (vec1.getY() + vec2.getY()) / 2,
                (vec1.getZ() + vec2.getZ()) / 2
        );
    }
}
