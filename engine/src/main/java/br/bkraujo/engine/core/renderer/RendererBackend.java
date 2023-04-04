package br.bkraujo.engine.core.renderer;

import br.bkraujo.engine.graphics.intrinsics.VertexArray;

public interface RendererBackend {

    void drawIndexed(VertexArray array);

}
