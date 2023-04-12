package br.bkraujo.core.graphics.opengl;

import br.bkraujo.engine.graphics.GraphicsIntrinsicFactory;
import br.bkraujo.engine.graphics.intrinsics.IndexBuffer;
import br.bkraujo.engine.graphics.intrinsics.Shader;
import br.bkraujo.engine.graphics.intrinsics.VertexArray;
import br.bkraujo.engine.graphics.intrinsics.VertexBuffer;

final class GLIntrinsicFactory implements GraphicsIntrinsicFactory {
        public Shader shader() { return new GLShader(); }
        public VertexArray vertexArray() { return new GLVertexArray(); }
        public VertexBuffer vertexBuffer(float ... vertices) { return new GLVertexBuffer(vertices); }
        public IndexBuffer indexBuffer(int ... indices) { return new GLIndexBuffer(indices); }
}
