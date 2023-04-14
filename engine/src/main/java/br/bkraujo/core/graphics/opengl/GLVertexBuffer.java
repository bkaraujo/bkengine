package br.bkraujo.core.graphics.opengl;

import br.bkraujo.engine.graphics.intrinsics.BufferLayout;
import br.bkraujo.engine.graphics.intrinsics.VertexBuffer;

import java.util.ArrayList;
import java.util.List;

import static br.bkraujo.engine.Logger.error;
import static br.bkraujo.engine.Logger.fatal;
import static org.lwjgl.opengl.GL20.*;

final class GLVertexBuffer extends GLObject implements VertexBuffer {

    final int handle;
    private final List<BufferLayout> layouts = new ArrayList<>();
    private final int count;
    public GLVertexBuffer(float ... vertices) {
        if (vertices == null) { fatal("Null or Empty vertices"); }
        count = vertices.length;
        handle = glGenBuffers(); hasError();
        glBindBuffer(GL_ARRAY_BUFFER, handle); hasError();
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW); hasError();
        glBindBuffer(GL_ARRAY_BUFFER, GL_NONE);
    }

    public void setLayout(BufferLayout ... layout) {
        if (!layouts.isEmpty()) { error("Layout already set"); return; }
        if (layout == null || layout.length == 0) { error("Null or Empty layout"); return; }

        var stride = 0;
        for (var entry : layout) stride += entry.type.size;

        var offset = 0;
        for (final BufferLayout entry : layout) {
            layouts.add(new BufferLayout(entry.type, entry.name, entry.normalized, stride, offset));
            offset += entry.type.size;
        }
    }

    public List<BufferLayout> getLayout() { return layouts; }
    public int getVertexCount() { return count; }
    public void bind() { glBindBuffer(GL_ARRAY_BUFFER, handle); }
    public void unbind() { glBindBuffer(GL_ARRAY_BUFFER, GL_NONE); }
    public void terminate() { glDeleteBuffers(handle); }
}
