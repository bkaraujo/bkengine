package br.bkraujo.engine.graphics;

import br.bkraujo.engine.graphics.intrinsics.IndexBuffer;
import br.bkraujo.engine.graphics.intrinsics.Shader;
import br.bkraujo.engine.graphics.intrinsics.VertexArray;
import br.bkraujo.engine.graphics.intrinsics.VertexBuffer;

public interface GraphicsIntrinsicFactory {

    Shader shader();
    VertexArray vertexArray();
    VertexBuffer vertexBuffer(float ... vertices);
    IndexBuffer indexBuffer(int ... indices);

}
