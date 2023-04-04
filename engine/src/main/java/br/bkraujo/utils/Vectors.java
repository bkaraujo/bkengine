package br.bkraujo.utils;

import org.joml.*;

public final class Vectors {
    private Vectors(){}

    public static Vector2i of (int x, int y) { return new Vector2i(x, y); }
    public static Vector3i of (int x, int y, int z) { return new Vector3i(x, y, z); }
    public static Vector4i of (int x, int y, int z, int w) { return new Vector4i(x, y, z, w); }

    public static Vector2f of (float x, float y) { return new Vector2f(x, y); }
    public static Vector3f of (float x, float y, float z) { return new Vector3f(x, y, z); }
    public static Vector4f of (float x, float y, float z, float w) { return new Vector4f(x, y, z, w); }

    public static Vector2d of (double x, double y) { return new Vector2d(x, y); }
    public static Vector3d of (double x, double y, double z) { return new Vector3d(x, y, z); }
    public static Vector4d of (double x, double y, double z, double w) { return new Vector4d(x, y, z, w); }

    public static float[] decompose (Vector2fc vector) { return new float[] {vector.x(), vector.y()}; }
    public static float[] decompose (Vector3fc vector) { return new float[] {vector.x(), vector.y(), vector.z()}; }
    public static float[] decompose (Vector4fc vector) { return new float[] {vector.x(), vector.y(), vector.z(), vector.w()}; }
}
