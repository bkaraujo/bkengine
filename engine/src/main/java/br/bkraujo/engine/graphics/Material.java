package br.bkraujo.engine.graphics;

import br.bkraujo.engine.graphics.intrinsics.Shader;
import org.joml.Matrix4fc;

import java.util.HashMap;
import java.util.Map;

public final class Material implements Bindable, Uniform {
    private Shader shader;

    private final Map<String, int[]> iUniform = new HashMap<>();
    private final Map<String, float[]> fUniform = new HashMap<>();
    private final Map<String, Object[]> fMatrix = new HashMap<>();
    private final Map<String, Object[]> mMatrix = new HashMap<>();

    public Shader getShader() { return shader; }
    public void setShader(Shader shader) { this.shader = shader; }

    @Override
    public void bind() {
        for (var entry : iUniform.entrySet()) shader.setUniform(entry.getKey(), entry.getValue());
        for (var entry : fUniform.entrySet()) shader.setUniform(entry.getKey(), entry.getValue());

        for (var entry : fMatrix.entrySet()) {
            final var transpose = (boolean) entry.getValue()[0];
            final var value = (float) entry.getValue()[1];
            shader.setUniformMatrix(entry.getKey(), transpose, value);
        }

        for (var entry : mMatrix.entrySet()) {
            final var transpose = (boolean) entry.getValue()[0];
            final var value = (Matrix4fc) entry.getValue()[1];
            shader.setUniformMatrix(entry.getKey(), transpose, value);
        }
    }

    public boolean setUniform(String name, int ... value) {
        return iUniform.put(name, value) == null;
    }

    public boolean setUniform(String name, float ... value) {
        return fUniform.put(name, value) == null;
    }

    public boolean setUniformMatrix(String name, boolean transpose, float ... value) {
        return fMatrix.put(name, new Object[]{transpose, value}) == null;
    }

    public boolean setUniformMatrix(String name, boolean transpose, Matrix4fc value) {
        return mMatrix.put(name, new Object[]{transpose, value}) == null;
    }

    @Override
    public void unbind() {
    }
}
