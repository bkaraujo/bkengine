package br.bkraujo.core.graphics.opengl;

import br.bkraujo.engine.Lifecycle;
import br.bkraujo.engine.graphics.GraphicsFactory;
import br.bkraujo.engine.graphics.intrinsics.BufferLayout;
import br.bkraujo.engine.graphics.intrinsics.IndexBuffer;
import br.bkraujo.engine.graphics.intrinsics.VertexArray;
import br.bkraujo.engine.graphics.intrinsics.VertexBuffer;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL30.*;

final class GLVertexArray extends GLObject implements VertexArray {
    final int handle;
    private final List<VertexBuffer> vertexes = new ArrayList<>();
    private IndexBuffer index;
    private int vertexCount;
    public GLVertexArray() {
        handle = glGenVertexArrays();
    }

    @Override
    public void addVertex(float[] vertices, BufferLayout... layout) {
        final var factory = GraphicsFactory.intrinsic();
        final var buffer = factory.vertexBuffer(vertices);
        buffer.setLayout(layout);

        addVertex(buffer);
    }

    public void addVertex(VertexBuffer buffer) {
        glBindVertexArray(handle);
        try {
            buffer.bind();

            final var layouts = buffer.getLayout();
            for (int i = 0; i < layouts.size(); ++i) {
                final var layout = layouts.get(i);

                glEnableVertexAttribArray(i);
                hasError();

                glVertexAttribPointer(i, layout.type.count, GLShader.translate(layout.type), layout.normalized, layout.stride, layout.offset);
                hasError();
            }

            vertexCount += buffer.getVertexCount();
            vertexes.add(buffer);
        } finally {
            glBindVertexArray(GL_NONE);

        }
    }

    public void setIndex(int ... indices) {
        final var factory = GraphicsFactory.intrinsic();
        final var indexBuffer = factory.indexBuffer(indices);

        setIndex(indexBuffer);
    }

    public void setIndex(IndexBuffer buffer) {
        glBindVertexArray(handle);

        try {
            index = buffer;
            index.bind();
        } finally {
            glBindVertexArray(GL_NONE);

        }
    }

    public List<VertexBuffer> getVertexes() { return vertexes; }

    public int getVertexCount() {
        return vertexCount;
    }

    public IndexBuffer getIndex() { return index; }
    public void bind() { glBindVertexArray(handle); }
    public void unbind() { glBindVertexArray(GL_NONE); }
    public void terminate() {
        if (index != null) index.terminate();
        vertexes.forEach(Lifecycle::terminate);
        glDeleteVertexArrays(handle);
    }
}
