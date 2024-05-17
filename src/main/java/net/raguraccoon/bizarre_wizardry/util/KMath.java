package net.raguraccoon.bizarre_wizardry.util;

import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class KMath {


    //Accepts two 2-D vectors and returns the angle between them
    public static double vectorAngle(Vec2 vector1, Vec2 vector2) {

        float numerator = vector1.dot(vector2);
        float denominator = vector1.length() * vector2.length();

        return Math.acos(numerator / denominator);

    }

    public static double vectorAngle3(Vec3 vec1, Vec3 vec2) {

        double numerator = vec1.dot(vec2);
        double denominator = vec1.length() * vec2.length();

        return Math.acos(numerator / denominator);

    }

}
