package br.bkraujo.utils;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3fc;

public abstract class Matrices {
    private Matrices(){}

    public static Matrix4f translate(Vector3fc offset) { return new Matrix4f().translate(offset); }

    public static Matrix4fc copy(Matrix4fc original) {
        return new Matrix4f(original);
    }
}
