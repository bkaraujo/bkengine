package br.bkraujo.engine.scene.actor.component;

import br.bkraujo.engine.graphics.GraphicsFactory;
import br.bkraujo.engine.graphics.intrinsics.Shader;
import br.bkraujo.engine.graphics.intrinsics.VertexArray;
import br.bkraujo.engine.scene.actor.Component;

public final class MeshComponent implements Component {
    public Shader shader;
    public final VertexArray array = GraphicsFactory.intrinsic().vertexArray();
}
