package br.bkraujo.engine.graphics.intrinsics;

import br.bkraujo.engine.graphics.Bindable;

import java.util.List;

public interface VertexArray extends Buffer, Bindable {

    void addVertex(float[] vertices, BufferLayout... layout);
    void addVertex(VertexBuffer buffer);

    void setIndex(int ... indices);
    void setIndex(IndexBuffer buffer);

    IndexBuffer getIndex();
    List<VertexBuffer> getVertexes();
}
