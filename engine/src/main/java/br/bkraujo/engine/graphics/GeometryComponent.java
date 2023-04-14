package br.bkraujo.engine.graphics;

import br.bkraujo.engine.graphics.intrinsics.VertexArray;
import br.bkraujo.engine.scene.actor.Component;

public final class GeometryComponent implements Component {
    public final Material material = new Material();
    public final VertexArray vertex = GraphicsFactory.intrinsic().vertexArray();
}
