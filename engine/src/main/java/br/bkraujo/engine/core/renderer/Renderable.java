package br.bkraujo.engine.core.renderer;

import br.bkraujo.engine.graphics.intrinsics.VertexArray;
import org.joml.Matrix4fc;

public final class Renderable {

    public final VertexArray vertexArray;
    public final Matrix4fc transform;

    public Renderable(VertexArray vertexArray, Matrix4fc transform) {
        this.vertexArray = vertexArray;
        this.transform = transform;
    }
}
