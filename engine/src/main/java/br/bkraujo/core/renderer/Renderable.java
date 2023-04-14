package br.bkraujo.core.renderer;

import br.bkraujo.engine.graphics.Material;
import br.bkraujo.engine.graphics.intrinsics.VertexArray;
import org.joml.Matrix4fc;

public final class Renderable {

    public final Material material;
    public final VertexArray vertex;
    public final Matrix4fc transform;

    public Renderable(VertexArray vertex, Material material, Matrix4fc transform) {
        this.vertex = vertex;
        this.material = material;
        this.transform = transform;
    }
}
