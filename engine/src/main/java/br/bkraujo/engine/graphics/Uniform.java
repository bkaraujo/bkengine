package br.bkraujo.engine.graphics;

import org.joml.Matrix4fc;

public interface Uniform {

    boolean setUniform(String name, int ... value);
    boolean setUniform(String name, float ... value);
    boolean setUniformMatrix(String name, boolean transpose, float ... value);
    boolean setUniformMatrix(String name, boolean transpose, Matrix4fc matrix);

}
